package controle;

import bean.FornecedorBean;
import dao.FornecedorDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.UtilInterface;

@ManagedBean
@SessionScoped
public class FornecedorControle {

    private FornecedorBean fornecedor = new FornecedorBean();
    private List<FornecedorBean> listaFornecedor;
    private boolean salvar = false;
    
    public List<FornecedorBean> getListaFornecedor() {
        try {
            listaFornecedor = FornecedorDAO.getLista();
            return listaFornecedor;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void preparaAlterar() {
        salvar = false;
    }

    public void preparaIncluir() {
        salvar = true;
        fornecedor = new FornecedorBean();      
    }

    public void salvar() {
        try {
            if (salvar) {
                FornecedorDAO.inserir(fornecedor);
            } else {
                FornecedorDAO.alterar(fornecedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public void excluir() {
        try {
            FornecedorDAO.excluir(fornecedor);
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            UtilInterface.msgErro("Impossível realizar a operação\nO registro não pode ser excluir pois está sendo utilizado!");
//            FacesContext context = FacesContext.getCurrentInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setListaFornecedor(List<FornecedorBean> listaFornecedor) {
        this.listaFornecedor = listaFornecedor;
    }

    public FornecedorBean getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorBean fornec) {
        this.fornecedor = fornec;
    }
}