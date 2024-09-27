package controle;

import bean.FornecedorBean;
import bean.ProdutoBean;
import bean.CompraBean;
import bean.UsuarioBean;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import dao.CompraDAO;
import dao.ItensCompraDAO;
import java.util.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.Impressao;
import util.UtilInterface;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;

@ManagedBean
@SessionScoped
public class CompraControle {

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    private CompraBean compra = new CompraBean();
    private List<CompraBean> listaCompra;
    private int idUsuario = 0;
    private Date dataInicial;
    private Date dataFinal;
    private int quantidade;
    private int idFornecedor = 0;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    private List<ProdutoBean> listaProduto;

    public List<ProdutoBean> getListaProduto() {
        return listaProduto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setListaProduto(List<ProdutoBean> listaProduto) {
        this.listaProduto = listaProduto;
    }

    private boolean salvar = false;

    public List<CompraBean> getListaCompra() {
        try {
            listaCompra = CompraDAO.getLista();
            return listaCompra;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void preparaAlterar(CompraBean compra) {
        salvar = false;
        this.compra = new CompraBean();
        this.compra = compra;
        idFornecedor = compra.getFornec().getIdFornecedor();
    }

    public void preparaIncluir() {
        salvar = true;
        compra = new CompraBean();
        idFornecedor = 0;
    }

    public void salvar() {
        try {
            UsuarioBean usuario = new UsuarioBean();
            usuario.setIdUsuario(idUsuario);
            compra.setUsuario(usuario);
            
            FornecedorBean fornecedor = new FornecedorBean();
            fornecedor.setIdFornecedor(idFornecedor);
            compra.setFornec(fornecedor);

            if (salvar) {
                CompraBean novaCompra = CompraDAO.pegaUltimo(compra);
                ItensCompraDAO.inserir(listaProduto, novaCompra, quantidade);
            } else {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir() {
        try {
            CompraDAO.excluir(compra);
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            UtilInterface.msgErro("Impossível realizar a operação\nO registro não pode ser excluir pois está sendo utilizado!");
//            FacesContext context = FacesContext.getCurrentInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setListaCompra(List<CompraBean> listaCompra) {
        this.listaCompra = listaCompra;
    }

    public CompraBean getCompra() {
        return compra;
    }

    public void setCompra(CompraBean compra) {
        this.compra = compra;
    }

    public void dataEs() {
        try {
            System.out.println("entrou");
            HashMap<String, Object> lista = new HashMap<String, Object>();
            lista.put("dataInicial", new java.sql.Date(dataInicial.getTime()) + "");
            lista.put("dataFinal", new java.sql.Date(dataFinal.getTime()) + "");
            System.out.println(new java.sql.Date(dataInicial.getTime()));
            System.out.println(new java.sql.Date(dataFinal.getTime()));
            String caminho = "/relatorios/CompraRela.jasper";
            String nome = "Relatório de compras";
            Impressao i = new Impressao();
            i.gerarRelatorio(lista, caminho, nome);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage mensagem = new FacesMessage(e.getMessage());
            mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, mensagem);
        }
    }
}
