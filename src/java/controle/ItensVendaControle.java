package controle;

import controle.ItensVendaControle;
import bean.ProdutoBean;
import dao.ProdutoDAO;
import bean.VendaBean;
import bean.ItensVendaBean;
import dao.ItensVendaDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.UtilInterface;

@ManagedBean
@SessionScoped
public class ItensVendaControle {

    private ProdutoBean itensVenda = new ProdutoBean();
    private List<ProdutoBean> listaItensVenda;
    private int idProduto = 0;
    private List<ProdutoBean> listaProduto;

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }
    private boolean salvar = false;

    public List<ProdutoBean> getListaItensVenda() throws SQLException {
        return listaItensVenda;
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
        itensVenda = new ProdutoBean();
    }

    public void salvar() {
        try {

            ProdutoBean produto = new ProdutoBean();
            produto.setIdProduto(idProduto);
            

            if (salvar) {
                ProdutoDAO.inserir(itensVenda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir() {
        try {
            ProdutoDAO.excluir(itensVenda);
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            UtilInterface.msgErro("Impossível realizar a operação\nO registro não pode ser excluir pois está sendo utilizado!");
//            FacesContext context = FacesContext.getCurrentInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setListaItensVenda(List<ProdutoBean> listaItensVenda) {
        this.listaItensVenda = listaItensVenda;
    }

    public ProdutoBean getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(ProdutoBean itensVenda) throws SQLException {
        for (int i = 0; i < listaItensVenda.size(); i++) {
            ProdutoDAO.inserir(itensVenda);
        }
        this.itensVenda = itensVenda;
    }
}
