package controle;

import controle.ItensCompraControle;
import bean.ProdutoBean;
import dao.ProdutoDAO;
import bean.CompraBean;
import bean.ItensCompraBean;
import dao.ItensCompraDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.UtilInterface;

@ManagedBean
@SessionScoped
public class ItensCompraControle {

    private ProdutoBean itensCompra = new ProdutoBean();
    private List<ProdutoBean> listaItensCompra;
    private int idProduto = 0;
    private List<ProdutoBean> listaProduto;


    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }
    private boolean salvar = false;

    public List<ProdutoBean> getListaItensCompra() throws SQLException {
        return listaItensCompra;
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

    public void preparaIncluir() {
        salvar = true;
        itensCompra = new ProdutoBean();
    }

    public void salvar() {
        try {

            ProdutoBean produto = new ProdutoBean();
            produto.setIdProduto(idProduto);
            

            if (salvar) {
                ProdutoDAO.inserir(itensCompra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir() {
        try {
            ProdutoDAO.excluir(itensCompra);
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            UtilInterface.msgErro("Impossível realizar a operação\nO registro não pode ser excluir pois está sendo utilizado!");
//            FacesContext context = FacesContext.getCurrentInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setListaItensCompra(List<ProdutoBean> listaItensCompra) {
        this.listaItensCompra = listaItensCompra;
    }

    public ProdutoBean getItensCompra() {
        return itensCompra;
    }

    public void setItensCompra(ProdutoBean itensCompra) throws SQLException {
        for (int i = 0; i < listaItensCompra.size(); i++) {
            ProdutoDAO.inserir(itensCompra);
        }
        this.itensCompra = itensCompra;
    }
}
