/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bean.UsuarioBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author usuario
 */
public class UtilInterface {

        public static UsuarioBean usuarioLogado = new UsuarioBean();
        
    public static void msgSucesso(String txt) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("SUCESSO!", txt));
    }

    public static void msgErro(String txt) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("ERRO!", txt));
    }
}
