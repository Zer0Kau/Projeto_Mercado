package controle;

import bean.FornecedorBean;
import bean.TipoBean;
import bean.MarcaBean;
import bean.ProdutoBean;
import dao.ProdutoDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.UtilInterface;

@ManagedBean
@SessionScoped
public class ProdutoControle {

    private ProdutoBean produto = new ProdutoBean();
    private List<ProdutoBean> listaProduto;
    private int idMarca = 0;
    private int idTipo = 0;


    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    private boolean salvar = false;

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public List<ProdutoBean> getListaProduto() {
        try {
            listaProduto = ProdutoDAO.getLista();
            return listaProduto;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void preparaAlterar() {
        salvar = false;
        idMarca = produto.getMarca().getIdMarca();
        idTipo = produto.getTipo().getIdTipo();
    }

    public void preparaIncluir() {
        salvar = true;
        produto = new ProdutoBean();
        idMarca = 0;
        idTipo = 0;
    }

    public void salvar() {
        try {

            MarcaBean marca = new MarcaBean();
            marca.setIdMarca(idMarca);
            produto.setMarca(marca);

            TipoBean tipo = new TipoBean();
            tipo.setIdTipo(idTipo);
            produto.setTipo(tipo);


            if (salvar) {
                ProdutoDAO.inserir(produto);
            } else {
                ProdutoDAO.alterar(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir() {
        try {
            ProdutoDAO.excluir(produto);
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            UtilInterface.msgErro("Impossível realizar a operação\nO registro não pode ser excluir pois está sendo utilizado!");
//            FacesContext context = FacesContext.getCurrentInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setListaProduto(List<ProdutoBean> listaProduto) {
        this.listaProduto = listaProduto;
    }

    public ProdutoBean getProduto() {
        return produto;
    }

    public void setProduto(ProdutoBean produto) {
        this.produto = produto;
    }
}
