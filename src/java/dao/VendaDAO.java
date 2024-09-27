package dao;

import util.Impressao;
import bean.VendaBean;
import bean.UsuarioBean;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexao;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.SessionContext;

public class VendaDAO {

    public static List<VendaBean> getLista() throws SQLException {
        List<VendaBean> lista = new ArrayList<VendaBean>();
        Connection con = Conexao.getConexao();
        String sql = "SELECT \n"
                + "		 usuario.nome,\n"
                + "		 venda.dataVenda,\n"
                + "		 itensvenda.idvenda,\n"
                + "              sum(produto.preco),\n"
                + "              venda.StatusVenda\n"
                + "FROM \n"
                + "		 venda\n"
                + "INNER JOIN \n"
                + "	 	 itensvenda \n"
                + "ON\n"
                + "		 itensvenda.idvenda = venda.idvenda\n"
                + "INNER JOIN\n"
                + "		 produto\n"
                + "ON\n"
                + "		 itensvenda.idProduto = produto.idproduto\n"
                + "INNER JOIN\n"
                + "		 usuario\n"
                + "ON\n"
                + "		 usuario.idUsuario = venda.idUsuario\n"
                + "GROUP BY	\n"
                + "		 venda.idVenda\n"
                + "ORDER BY\n"
                + "		 venda.dataVenda";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            UsuarioBean usuario = new UsuarioBean();
            usuario.setNome(rs.getString("nome"));

            VendaBean venda = new VendaBean();
            venda.setIdVenda(rs.getInt("idVenda"));
            venda.setDataVenda(rs.getString("dataVenda"));
            venda.setStatusVenda(rs.getString("statusVenda"));
            venda.setUsuario(usuario);

            lista.add(venda);
        }
        rs.close();
        stmt.close();
        con.close();
        return lista;
    }
    public static List<VendaBean> getListaLogado() throws SQLException {
        List<VendaBean> lista = new ArrayList<VendaBean>();
        Connection con = Conexao.getConexao();
        String sql = "SELECT \n"
                + "		 usuario.nome,\n"
                + "		 venda.dataVenda,\n"
                + "		 itensvenda.idvenda,\n"
                + "              sum(produto.preco),\n"
                + "              venda.StatusVenda\n"
                + "FROM \n"
                + "		 venda\n"
                + "INNER JOIN \n"
                + "	 	 itensvenda \n"
                + "ON\n"
                + "		 itensvenda.idvenda = venda.idvenda\n"
                + "INNER JOIN\n"
                + "		 produto\n"
                + "ON\n"
                + "		 itensvenda.idProduto = produto.idproduto\n"
                + "INNER JOIN\n"
                + "		 usuario\n"
                + "ON\n"
                + "		 usuario.idUsuario = venda.idUsuario\n"
                + "WHERE\n"
                + "		 usuario.idUsuario = ?\n"
                + "GROUP BY	\n"
                + "		 venda.idVenda\n"
                + "ORDER BY\n"
                + "		 venda.dataVenda";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            UsuarioBean usuario = new UsuarioBean();
            usuario.setNome(rs.getString("nome"));

            VendaBean venda = new VendaBean();
            venda.setIdVenda(rs.getInt("idVenda"));
            venda.setDataVenda(rs.getString("dataVenda"));
            venda.setStatusVenda(rs.getString("statusVenda"));
            venda.setUsuario(usuario);

            lista.add(venda);
        }
        rs.close();
        stmt.close();
        con.close();
        return lista;
    }

    public static VendaBean pegaUltimo(VendaBean venda) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("insert into venda (dataVenda, idUsuario, statusVenda) values (curdate(),?,?)");
        stmt.setInt(1, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        stmt.setString(2, "Aguardando Pagamento");

        stmt.execute();

        String sql = "SELECT max(idVenda) as venda from venda where idUsuario = " + SessionContext.getInstance().getUsuarioLogado().getIdUsuario();
        stmt = con.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        rs.first();
        venda.setIdVenda(rs.getInt("venda"));
        stmt.close();
        con.close();

        return venda;
    }

    public static void alterar(VendaBean venda) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("update venda set statusVenda=? "
                + "where idVenda = ?");
        stmt.setString(1, venda.getStatusVenda());
        stmt.setInt(2, venda.getIdVenda());


        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(VendaBean venda) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("delete from venda where idVenda = ?");
        stmt.setInt(1, venda.getIdVenda());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

}
