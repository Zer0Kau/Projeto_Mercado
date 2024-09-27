package bean;

import java.util.Date;
import util.SessionContext;

public class VendaBean {

    private int idVenda;
    private String dataVenda;
    private UsuarioBean usuario;
    private String StatusVenda;

    public String getStatusVenda() {
        return StatusVenda;
    }

    public void setStatusVenda(String StatusVenda) {
        this.StatusVenda = StatusVenda;
    }



    public UsuarioBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBean usuario) {
        this.usuario = usuario;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }
    

}
