package dao;

import bean.UsuarioBean;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexao;

public class UsuarioDAO {

    public static List<UsuarioBean> getLista() throws SQLException {
        List<UsuarioBean> lista = new ArrayList<UsuarioBean>();
        Connection con = Conexao.getConexao();
        String sql = "select * from usuario order by nome";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            UsuarioBean usuario = new UsuarioBean();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setBairro(rs.getString("bairro"));
            usuario.setCep(rs.getString("cep"));
            usuario.setCidade(rs.getString("cidade"));
            usuario.setDataNascimento(rs.getDate("dataNascimento"));
            usuario.setRua(rs.getString("rua"));
            usuario.setLogin(rs.getString("login"));
            usuario.setNumeroCasa(rs.getInt("numeroCasa"));
            usuario.setCpf(rs.getString("cpf"));
            usuario.setSenha(rs.getString("senha"));

            lista.add(usuario);
        }
        rs.close();
        stmt.close();
        con.close();
        return lista;
    }

    public static void inserir(UsuarioBean usuario) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("insert into usuario (nome, login, senha, dataNascimento, cpf, cep, cidade, rua, bairro, numeroCasa) values (?, ?, md5(?), ?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getLogin());
        stmt.setString(3, usuario.getSenha());
        stmt.setDate(4, new java.sql.Date(usuario.getDataNascimento().getTime()));
        stmt.setString(5, usuario.getCpf());
        stmt.setString(6, usuario.getCep());
        stmt.setString(7, usuario.getCidade());
        stmt.setString(8, usuario.getRua());
        stmt.setString(9, usuario.getBairro());
        stmt.setInt(10, usuario.getNumeroCasa());

        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(UsuarioBean usuario) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("update usuario set nome=?, "
                + "login=?, "
                + "senha=md5(?), "
                + "dataNascimento=?, "
                + "cpf=?, "
                + "cep=?, "
                + "cidade=?, "
                + "rua=?, "
                + "bairro=?, "
                + "numeroCasa=? "
                + "where idUsuario=?");
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getLogin());
        stmt.setString(3, usuario.getSenha());
        stmt.setDate(4, new java.sql.Date(usuario.getDataNascimento().getTime()));
        stmt.setString(5, usuario.getCpf());
        stmt.setString(6, usuario.getCep());
        stmt.setString(7, usuario.getCidade());
        stmt.setString(8, usuario.getRua());
        stmt.setString(9, usuario.getBairro());
        stmt.setInt(10, usuario.getNumeroCasa());
        stmt.setInt(11, usuario.getIdUsuario());

        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(UsuarioBean usuario) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("delete from usuario where idUsuario = ?");
        stmt.setInt(1, usuario.getIdUsuario());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    
    public static UsuarioBean getUsuarioLogado(UsuarioBean usuario) throws SQLException {

            Connection con = Conexao.getConexao();
            String sql = "select * from USUARIO where login = ? and senha = md5(?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());
            ResultSet rs = stmt.executeQuery();

            UsuarioBean u;

            if (rs.next()) {
                u = new UsuarioBean();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setTipoUsuario(rs.getInt("tipoUsuario"));
            } else {
                u = null;
            }
            rs.close();
            stmt.close();
            con.close();
            return u;
        }

    }
