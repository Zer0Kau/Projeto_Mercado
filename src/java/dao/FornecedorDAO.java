package dao;

import bean.FornecedorBean;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexao;

public class FornecedorDAO {

        
    public static List<FornecedorBean> getLista() throws SQLException {
        List<FornecedorBean> lista = new ArrayList<FornecedorBean>();
        Connection con = Conexao.getConexao();
        String sql = "select * from fornecedor order by nome";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            FornecedorBean tipo = new FornecedorBean();
            tipo.setIdFornecedor(rs.getInt("idFornecedor"));
            tipo.setNomeFornec(rs.getString("nome"));
      
            lista.add(tipo);
        }
        rs.close();
        stmt.close();
        con.close();
        return lista;
    }
    
    public static void inserir(FornecedorBean fornecedor) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("insert into fornecedor (nome) values (?)");
            stmt.setString(1, fornecedor.getNomeFornec());

        stmt.execute();
        stmt.close();
        con.close();
    }


    public static void alterar(FornecedorBean fornecedor) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("update fornecedor set nome=? where idFornecedor=?");
            stmt.setString(1, fornecedor.getNomeFornec());
            stmt.setInt(2, fornecedor.getIdFornecedor());

        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(FornecedorBean fornecedor) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt= con.prepareStatement("delete from fornecedor where idFornecedor = ?");
        stmt.setInt(1, fornecedor.getIdFornecedor());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    
}
