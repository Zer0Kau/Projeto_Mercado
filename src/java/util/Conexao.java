package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection
                ("jdbc:mysql://localhost/meubanco", "root", "ifpr");
//                ("jdbc:mysql://localhost/meubanco", "root", "264504");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        
        try {
            Conexao.getConexao();
            System.out.println("CONEXÃO EFETUADA COM SUCESSO!");
        } catch (SQLException ex) {
            System.out.println("ERRO AO EFETUAR UMA CONEXÃO");
            ex.printStackTrace();
        }
    }
    
}