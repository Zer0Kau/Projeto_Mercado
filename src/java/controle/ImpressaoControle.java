package controle;

import java.util.HashMap;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.Impressao;

@SessionScoped
@ManagedBean

public class ImpressaoControle {

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void imprimirPDF() {
        try {
            HashMap<String, Object> lista = new HashMap<String, Object>();
            lista.put("nome", nome+"%");
            String caminho = "/relatorio/r2.jasper";
            String nome = "Relatório de conteúdo mensal";
            Impressao i = new Impressao();
            i.gerarRelatorio(lista, caminho, nome);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage mensagem = new FacesMessage(e.getMessage());
            mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, mensagem);
        }
    }

}
