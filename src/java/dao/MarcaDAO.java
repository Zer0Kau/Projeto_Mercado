package dao;

import bean.MarcaBean;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexao;

public class MarcaDAO {

        
    public static List<MarcaBean> getLista() throws SQLException {
        List<MarcaBean> lista = new ArrayList<MarcaBean>();
        Connection con = Conexao.getConexao();
        String sql = "select * from marca order by nome";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            MarcaBean marca = new MarcaBean();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNomeMarca(rs.getString("nome"));
      
            lista.add(marca);
        }
        rs.close();
        stmt.close();
        con.close();
        return lista;
    }
    
    public static void inserir(MarcaBean marca) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("insert into marca (nome) values (?)");
            stmt.setString(1, marca.getNomeMarca());

        stmt.execute();
        stmt.close();
        con.close();
    }


    public static void alterar(MarcaBean marca) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("update marca set nome=?");
            stmt.setString(1, marca.getNomeMarca());

        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(MarcaBean marca) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt= con.prepareStatement("delete from marca where idMarca = ?");
        stmt.setInt(1, marca.getIdMarca());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    
}
