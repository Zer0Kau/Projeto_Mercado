package dao;

import bean.TipoBean;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexao;

public class TipoDAO {

        
    public static List<TipoBean> getLista() throws SQLException {
        List<TipoBean> lista = new ArrayList<TipoBean>();
        Connection con = Conexao.getConexao();
        String sql = "select * from tipo order by nome";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            TipoBean tipo = new TipoBean();
            tipo.setIdTipo(rs.getInt("idTipo"));
            tipo.setNomeTipo(rs.getString("nome"));
      
            lista.add(tipo);
        }
        rs.close();
        stmt.close();
        con.close();
        return lista;
    }
    
    public static void inserir(TipoBean tipo) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("insert into tipo (nome) values (?)");
            stmt.setString(1, tipo.getNomeTipo());

        stmt.execute();
        stmt.close();
        con.close();
    }


    public static void alterar(TipoBean tipo) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("update tipo set nome=? where idTipo=?");
            stmt.setString(1, tipo.getNomeTipo());
            stmt.setInt(2, tipo.getIdTipo());

        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(TipoBean tipo) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt= con.prepareStatement("delete from tipo where idTipo = ?");
        stmt.setInt(1, tipo.getIdTipo());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    
}
