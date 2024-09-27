package dao;

import bean.ItensVendaBean;
import bean.VendaBean;
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

public class ItensVendaDAO {

    public static List<ItensVendaBean> getLista() throws SQLException {
        List<ItensVendaBean> lista = new ArrayList<ItensVendaBean>();
        Connection con = Conexao.getConexao();
        String sql = "SELECT \n"
                + "	itensvenda.*,\n"
                + "venda.dataVenda as dataVenda,"
                + "produto.nome as nomeProduto,"
                + "quantidade as quantidade"
                + "FROM \n"
                + "	venda, \n"
                + "	itensvenda,"
                + "     produto \n"
                + "WHERE \n"
                + "	itensvenda.idVenda = venda.idVenda\n"
                + "ORDER BY itensvenda.idVenda";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            ProdutoBean produto = new ProdutoBean();
            produto.setIdProduto(rs.getInt("idProduto"));
            produto.setNome(rs.getString("nomeProduto"));

            VendaBean venda = new VendaBean();
            venda.setIdVenda(rs.getInt("idVenda"));
            venda.setDataVenda(rs.getString("dataVenda"));

            ItensVendaBean itensVenda = new ItensVendaBean();
            itensVenda.setQuantidade(rs.getInt("quantidade"));
            itensVenda.setVenda(venda);

            lista.add(itensVenda);
        }
        rs.close();
        stmt.close();
        con.close();
        return lista;
    }

    public static void inserir(List<ProdutoBean> itensvenda, VendaBean venda, int quantidade) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        for (int i = 0; i < itensvenda.size(); i++) {
            ProdutoBean itens = itensvenda.get(i);

            stmt = con.prepareStatement("insert into itensvenda (idProduto, idVenda, quantidade) values (?,?,?)");

            stmt.setInt(1, itens.getIdProduto());
            stmt.setInt(2, venda.getIdVenda());
            stmt.setInt(3, itens.getQuantidade());

            stmt.execute();

            stmt2 = con.prepareStatement("update produto set estoqueAtual = ? where idProduto = ?");

            stmt2.setInt(1, itens.getEstoqueAtual() - itens.getQuantidade());
            stmt2.setInt(2, itens.getIdProduto());

            stmt2.executeUpdate();

        }
        stmt.close();
        con.close();
    }

    public static void excluir(ItensVendaBean itensvenda) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("delete from itensvenda where idVenda = ?");
        stmt.setInt(1, itensvenda.getVenda().getIdVenda());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

}
