package controle;

import bean.MarcaBean;
import dao.MarcaDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.UtilInterface;

@ManagedBean
@SessionScoped
public class MarcaControle {

    private MarcaBean marca = new MarcaBean();
    private List<MarcaBean> listaMarca;
    private boolean salvar = false;
    
    public List<MarcaBean> getListaMarca() {
        try {
            listaMarca = MarcaDAO.getLista();
            return listaMarca;
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
        marca = new MarcaBean();      
    }

    public void salvar() {
        try {
            if (salvar) {
                MarcaDAO.inserir(marca);
            } else {
                MarcaDAO.alterar(marca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public void excluir() {
        try {
            MarcaDAO.excluir(marca);
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            UtilInterface.msgErro("Impossível realizar a operação\nO registro não pode ser excluir pois está sendo utilizado!");
//            FacesContext context = FacesContext.getCurrentInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setListaMarca(List<MarcaBean> listaMarca) {
        this.listaMarca = listaMarca;
    }

    public MarcaBean getMarca() {
        return marca;
    }

    public void setMarca(MarcaBean marca) {
        this.marca = marca;
    }
}