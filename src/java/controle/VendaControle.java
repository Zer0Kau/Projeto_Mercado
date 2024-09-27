package controle;

import bean.ProdutoBean;
import bean.UsuarioBean;
import bean.VendaBean;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import dao.VendaDAO;
import dao.ItensVendaDAO;
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
import util.SessionContext;

@ManagedBean
@SessionScoped
public class VendaControle {

    private VendaBean venda = new VendaBean();
    private List<VendaBean> listaVenda;
    private List<VendaBean> listaLogado;


    public void setListaLogado(List<VendaBean> listaLogado) {
        this.listaLogado = listaLogado;
    }
    private int idUsuario = 0;
    private Date dataInicial;
    private Date dataFinal;
    private List<Integer> listaQuantidade;
    private int idVenda;

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }
    private int idCompra;

    public List<Integer> getListaQuantidade() {
        return listaQuantidade;
    }

    public void setListaQuantidade(List<Integer> listaQuantidade) {
        this.listaQuantidade = listaQuantidade;
    }
    
    private int quantidade;

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

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }
    private List<ProdutoBean> listaProduto;
    private int idStatus = 1;

    public List<ProdutoBean> getListaProduto() {
        return listaProduto;
    }

    public void setListaProduto(List<ProdutoBean> listaProduto) {
        this.listaProduto = listaProduto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    private boolean salvar = false;

    public List<VendaBean> getListaLogado() {
        try {
            listaLogado = VendaDAO.getListaLogado();
            return listaLogado;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<VendaBean> getListaVenda() {
        try {
            listaVenda = VendaDAO.getLista();
            return listaVenda;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void preparaAlterar(VendaBean venda) {
        salvar = false;
        this.venda = new VendaBean();
        this.venda = venda;
    }

    public void preparaIncluir() {
        salvar = true;
        venda = new VendaBean();
    }

    public void salvar() {
        try {

            UsuarioBean usuario = new UsuarioBean();
            usuario.setIdUsuario(idUsuario);
            venda.setUsuario(usuario);

            if (salvar) {
                VendaBean novaVenda = VendaDAO.pegaUltimo(venda);
                ItensVendaDAO.inserir(listaProduto, novaVenda, quantidade);
            } else {
                VendaDAO.alterar(venda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir() {
        try {
            VendaDAO.excluir(venda);
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            UtilInterface.msgErro("Impossível realizar a operação\nO registro não pode ser excluir pois está sendo utilizado!");
//            FacesContext context = FacesContext.getCurrentInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setListaVenda(List<VendaBean> listaVenda) {
        this.listaVenda = listaVenda;
    }

    public VendaBean getVenda() {
        return venda;
    }

    public void setVenda(VendaBean venda) {
        this.venda = venda;
    }

    public void dataEs() {
        try {
            System.out.println("entrou");
            HashMap<String, Object> lista = new HashMap<String, Object>();
            lista.put("dataInicial", new java.sql.Date(dataInicial.getTime()) + "");
            lista.put("dataFinal", new java.sql.Date(dataFinal.getTime()) + "");
            System.out.println(new java.sql.Date(dataInicial.getTime()));
            System.out.println(new java.sql.Date(dataFinal.getTime()));
            String caminho = "/relatorios/relatoriodevendas.jasper";
            String nome = "Relatório de Vendas";
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
    public void dataEs1() {
        try {
            System.out.println("entrou");
            HashMap<String, Object> lista = new HashMap<String, Object>();
            lista.put("dataInicial", new java.sql.Date(dataInicial.getTime()) + "");
            lista.put("dataFinal", new java.sql.Date(dataFinal.getTime()) + "");
            System.out.println(new java.sql.Date(dataInicial.getTime()));
            System.out.println(new java.sql.Date(dataFinal.getTime()));
            String caminho = "/relatorios/relatoriodecompras.jasper";
            String nome = "Relatório de Compras";
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
    public void vendaEs() {
        try {
            System.out.println("entrou");
            HashMap<String, Object> lista = new HashMap<String, Object>();
            lista.put("idVenda", getIdVenda()+"");
            System.out.println(idVenda);
            String caminho = "/relatorios/itensdavenda2.jasper";
            String nome = "Relatório de itens da venda";
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
    public void compraEs() {
        try {
            System.out.println("entrou");
            HashMap<String, Object> lista = new HashMap<String, Object>();
            lista.put("idcompra", idCompra+"");
            System.out.println(idCompra);
            String caminho = "/relatorios/itensdacompra.jasper";
            String nome = "Relatório de itens da compra";
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
    public void idEs(VendaBean venda) {
        try {
            System.out.println("entrou");
            HashMap<String, Object> lista = new HashMap<String, Object>();
            lista.put("idVenda", venda.getIdVenda()+"");
            System.out.println(venda.getIdVenda());
            String caminho = "/relatorios/Boletinho.jasper";
            String nome = "Boleto Bancario";
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
