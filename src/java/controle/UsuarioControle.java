package controle;

import bean.UsuarioBean;
import dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.UtilInterface;
import util.SessionContext;
import util.AutorizacaoFilter;

@ManagedBean
@SessionScoped
public class UsuarioControle {

    private UsuarioBean usuario = new UsuarioBean();
    private List<UsuarioBean> listaUsuario;
    private boolean salvar = false;

    public List<UsuarioBean> getListaUsuario() {
        try {
            listaUsuario = UsuarioDAO.getLista();
            return listaUsuario;
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
        usuario = new UsuarioBean();
    }

    public void salvar() {
        try {

            if (salvar) {
                UsuarioDAO.inserir(usuario);
                FacesContext facesContext = FacesContext.getCurrentInstance();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ":D", "Conta Criada com Sucesso!"));
            } else {
                UsuarioDAO.alterar(usuario);
                FacesContext facesContext = FacesContext.getCurrentInstance();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Login ja existente."));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir() {
        try {
            UsuarioDAO.excluir(usuario);
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            UtilInterface.msgErro("Impossível realizar a operação\nO registro não pode ser excluir pois está sendo utilizado!");
//            FacesContext context = FacesContext.getCurrentInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private UsuarioBean usuarioLogado = new UsuarioBean();

    public String login() throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        usuarioLogado = UsuarioDAO.getUsuarioLogado(usuario);

        if (usuarioLogado != null) {
            if (usuarioLogado.getTipoUsuario() == 1) {
                SessionContext.getInstance().setAttribute("usuarioLogado", usuarioLogado);
                return "/faces/inicial?faces-redirect=true";
            } else {
                SessionContext.getInstance().setAttribute("usuarioLogado", usuarioLogado);
                return "/faces/inicial?faces-redirect=true";
            }

        } else {
            usuarioLogado = null;
            usuario = new UsuarioBean();
            doLogout();
            System.out.println("nao encontrou usuário");
            FacesMessage mensagem = new FacesMessage("Usuário/senha inválidos!");
            mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, mensagem);
//            return "/faces/inicial/loginAdm?faces-redirect=true";
            return null;
        }
    }

    public void setListaUsuario(List<UsuarioBean> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public UsuarioBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBean usuario) {
        this.usuario = usuario;
    }

    public String doLogout() {
        util.UtilInterface.usuarioLogado = null;
        SessionContext.getInstance().encerrarSessao();
        return "/faces/login/login.xhtml?faces-redirect=true";
    }
}
