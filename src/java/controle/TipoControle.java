package controle;

import bean.TipoBean;
import dao.TipoDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.UtilInterface;

@ManagedBean
@SessionScoped
public class TipoControle {

    private TipoBean tipo = new TipoBean();
    private List<TipoBean> listaTipo;
    private boolean salvar = false;
    
    public List<TipoBean> getListaTipo() {
        try {
            listaTipo = TipoDAO.getLista();
            return listaTipo;
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
        tipo = new TipoBean();      
    }

    public void salvar() {
        try {
            if (salvar) {
                TipoDAO.inserir(tipo);
            } else {
                TipoDAO.alterar(tipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public void excluir() {
        try {
            TipoDAO.excluir(tipo);
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            UtilInterface.msgErro("Impossível realizar a operação\nO registro não pode ser excluir pois está sendo utilizado!");
//            FacesContext context = FacesContext.getCurrentInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setListaTipo(List<TipoBean> listaTipo) {
        this.listaTipo = listaTipo;
    }

    public TipoBean getTipo() {
        return tipo;
    }

    public void setTipo(TipoBean tipo) {
        this.tipo = tipo;
    }
} 