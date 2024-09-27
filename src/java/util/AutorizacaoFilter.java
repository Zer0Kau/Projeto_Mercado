/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bean.UsuarioBean;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Héber Morais
 * @since 28/10/2015
 */
@WebFilter("*.xhtml")
public class AutorizacaoFilter implements Filter {

    private static final String[][] DIREITO_ACESSO = {
        {"0",
            "/faces/inicialUsuario.xhtml",
            "/faces/selecionarProdutos.xhtml",
            "/faces/produtos.xhtml",
            "/faces/vendaUsuario.xhtml",

        },
        {"1",
            "/faces/inicial.xhtml",
            "/faces/cadastroUsuario.xhtml",
            "/faces/cadastroFornecedor.xhtml",
            "/faces/cadastroTipos.xhtml",
            "/faces/cadastroMarca.xhtml",
            "/faces/cadastroProduto.xhtml",
            "/faces/venda.xhtml",
            "/faces/compra.xhtml",
            
   
        }
    };

    public static void main(String[] args) {
        for (int i = 1; i < DIREITO_ACESSO[0].length; i++) {
            System.out.print(DIREITO_ACESSO[0][i] + ",");

            System.out.println("");
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        UsuarioBean user = null;

//        HttpSession sess = ((HttpServletRequest) req).getSession(false);
        HttpSession sess = ((HttpServletRequest) req).getSession();

        if (sess != null) {
            user = (UsuarioBean) sess.getAttribute("usuarioLogado");

        }

        if ((user == null) && 
                !request.getRequestURI().endsWith("/faces/login/login.xhtml") && 
                !request.getRequestURI().contains("/javax.faces.resource/") 
                ) {
            response.sendRedirect(request.getContextPath() + "/faces/login/login.xhtml");
            System.out.println("USUÁRIO NÃO LOGADO");
        } else {
            try {
                boolean naoFoi = false;
                if (user.getTipoUsuario()==0) {
//                    System.out.println("LOGADO COMO PROFESSOR");
                    for (int i = 1; i < DIREITO_ACESSO[0].length; i++) {
//                        System.out.println(DIREITO_ACESSO[0][i]);
                        if (request.getRequestURI().endsWith(DIREITO_ACESSO[0][i])) {
                            System.out.println(DIREITO_ACESSO[0][i] + " - USUARIO COMUM TEM ACESSO");
                            chain.doFilter(req, res);
                            naoFoi = false;
                            break;
                        } else {
                            naoFoi = true;
                        }
                    }
                    if (naoFoi) {
                        response.sendRedirect(request.getContextPath() + "/faces/inicialUsuario.xhtml");
                    }
                } else if (user.getTipoUsuario() == 1) {
//                    System.out.println("LOGADO COMO ADMINISTRADOR");
                    for (int i = 1; i < DIREITO_ACESSO[1].length; i++) {
//                        System.out.println(DIREITO_ACESSO[1][i]);
                        if (request.getRequestURI().endsWith(DIREITO_ACESSO[1][i])) {
                            System.out.println(DIREITO_ACESSO[1][i] + " - ADMINSITRADOR TEM ACESSO");
                            chain.doFilter(req, res);
                            naoFoi = false;
                            break;
                        } else {
                            naoFoi = true;
                        }
                    }
                    if (naoFoi) {
                        response.sendRedirect(request.getContextPath() + "/faces/inicial.xhtml");
                    }

                }
            } catch (NullPointerException e) {
                chain.doFilter(req, res);
            }

        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}

