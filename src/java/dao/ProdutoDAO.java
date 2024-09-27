package dao;

import util.SessionContext;
import bean.UsuarioBean;
import bean.FornecedorBean;
import bean.TipoBean;
import bean.MarcaBean;
import bean.ProdutoBean;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexao;

public class ProdutoDAO {

    public static List<ProdutoBean> getLista() throws SQLException {
        List<ProdutoBean> lista = new ArrayList<ProdutoBean>();
        Connection con = Conexao.getConexao();
        String sql = "SELECT \n"
                + "	produto.*,\n"
                + "	marca.nome as nomeMarca,"
                + "tipo.nome as nomeTipo \n"
                + "FROM \n"
                + "	produto, \n"
                + "	marca, \n"
                + "	tipo \n"
                + "WHERE \n"
                + "	produto.idMarca = marca.idMarca and\n"
                + "	produto.idTipo = tipo.idTipo \n"
                + "ORDER BY produto.nome";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            if (((SessionContext.getInstance().getUsuarioLogado().getTipoUsuario() == 1) || (!(rs.getInt("estoqueAtual") <= 0) ))) {

                MarcaBean marca = new MarcaBean();
                marca.setIdMarca(rs.getInt("idMarca"));
                marca.setNomeMarca(rs.getString("nomeMarca"));

                TipoBean tipo = new TipoBean();
                tipo.setIdTipo(rs.getInt("idTipo"));
                tipo.setNomeTipo(rs.getString("nomeTipo"));


                ProdutoBean produto = new ProdutoBean();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setEstoqueAtual(rs.getInt("estoqueAtual"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setMarca(marca);
                produto.setTipo(tipo);

                lista.add(produto);
            }
        }
        rs.close();
        stmt.close();
        con.close();
        return lista;
    }

    public static void inserir(ProdutoBean produto) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("insert into produto (nome, idMarca,  idTipo, preco, estoqueAtual, descricao) values (?,?,?,?,?,?)");
        stmt.setString(1, produto.getNome());
        stmt.setInt(2, produto.getMarca().getIdMarca());
        stmt.setInt(3, produto.getTipo().getIdTipo());
        stmt.setDouble(4, produto.getPreco());
        stmt.setInt(5, produto.getEstoqueAtual());
        stmt.setString(6, produto.getDescricao());

        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(ProdutoBean produto) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("UPDATE produto "
                + "SET nome=?, "
                + "idMarca=?, "
                + "idTipo=?, "
                + "preco=?, "
                + "estoqueAtual=?, "
                + "descricao=?"
                + "WHERE idProduto=?");
        stmt.setString(1, produto.getNome());
        stmt.setInt(2, produto.getMarca().getIdMarca());
        stmt.setInt(3, produto.getTipo().getIdTipo());
        stmt.setDouble(4, produto.getPreco());
        stmt.setInt(5, produto.getEstoqueAtual());
        stmt.setString(6, produto.getDescricao());
        stmt.setInt(7, produto.getIdProduto());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(ProdutoBean produto) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("delete from produto where idProduto = ?");
        stmt.setInt(1, produto.getIdProduto());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

}
