package bean;

import java.util.Date;

public class ProdutoBean {

    private int idProduto;
    private String nome;
    private MarcaBean marca;
    private double preco;
    private int estoqueAtual;
    private TipoBean tipo;
    private String[] selectedProduto;
    private String descricao;
    private int quantidade;

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String[] getSelectedProduto() {
        return selectedProduto;
    }

    public void setSelectedProduto(String[] selectedProduto) {
        this.selectedProduto = selectedProduto;
    }
        
    

    public TipoBean getTipo() {
        return tipo;
    }

    public void setTipo(TipoBean tipo) {
        this.tipo = tipo;
    }

    public int getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(int estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public MarcaBean getMarca() {
        return marca;
    }

    public void setMarca(MarcaBean marca) {
        this.marca = marca;
    }   
    
}
