package dao;

import bean.CompraBean;
import bean.ItensCompraBean;
import bean.ProdutoBean;
import controle.ProdutoControle;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexao;

public class ItensCompraDAO {

    public static List<ItensCompraBean> getLista() throws SQLException {
        List<ItensCompraBean> lista = new ArrayList<ItensCompraBean>();
        Connection con = Conexao.getConexao();
        String sql = "SELECT \n"
                + "	itenscompra.*,\n"
                + "compra.dataCompra as dataCompra,"
                + "produto.nome as nomeProduto,"
                + "quantidade as quantidade"
                + "FROM \n"
                + "	compra, \n"
                + "	itenscompra,"
                + "     produto \n"
                + "WHERE \n"
                + "	itenscompra.idCompra = compra.idCompra\n"
                + "ORDER BY itenscompra.idCompra";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            ProdutoBean produto = new ProdutoBean();
            produto.setIdProduto(rs.getInt("idProduto"));
            produto.setNome(rs.getString("nomeProduto"));

            CompraBean compra = new CompraBean();
            compra.setIdCompra(rs.getInt("idCompra"));
            compra.setDataCompra(rs.getDate("dataCompra"));

            ItensCompraBean itensCompra = new ItensCompraBean();
            itensCompra.setCompra(compra);

            lista.add(itensCompra);
        }
        rs.close();
        stmt.close();
        con.close();
        return lista;
    }

    public static void inserir(List<ProdutoBean> itenscompra, CompraBean compra, int quantidade) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        for (int i = 0; i < itenscompra.size(); i++) {
            ProdutoBean itens = itenscompra.get(i);
            
            stmt = con.prepareStatement("insert into itenscompra (idProduto, idCompra, quantidade) values (?,?,?)");

            stmt.setInt(1, itens.getIdProduto());
            stmt.setInt(2, compra.getIdCompra());
            stmt.setInt(3, itens.getQuantidade());
            
            stmt.execute();
            
            stmt2 = con.prepareStatement("update produto set estoqueAtual = ? where idProduto = ?");
            
            stmt2.setInt(1, itens.getEstoqueAtual()+itens.getQuantidade());
            stmt2.setInt(2, itens.getIdProduto());
            
            stmt2.executeUpdate();
            

        }
        stmt.close();
        con.close();
    }



}
