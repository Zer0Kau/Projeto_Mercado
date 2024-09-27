package bean;

import java.util.Date;

public class ItensCompraBean {

    private ProdutoBean produto;
    private CompraBean compra;
    private int quantidade;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public ProdutoBean getProduto() {
        return produto;
    }

    public void setProduto(ProdutoBean produto) {
        this.produto = produto;
    }

    public CompraBean getCompra() {
        return compra;
    }

    public void setCompra(CompraBean compra) {
        this.compra = compra;
    }
    
}
