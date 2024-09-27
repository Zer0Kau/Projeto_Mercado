package dao;

import util.Impressao;
import bean.CompraBean;
import bean.FornecedorBean;
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

public class CompraDAO {

    public static List<CompraBean> getLista() throws SQLException {
        List<CompraBean> lista = new ArrayList<CompraBean>();
        Connection con = Conexao.getConexao();
        String sql = "SELECT \n"
                + "		 compra.idUsuario,\n"
                + "                		 compra.dataCompra,\n"
                + "                		 itenscompra.idcompra,\n"
                + "                		 fornecedor.nome as nomeFornec,\n"
                + "                              sum(produto.preco)\n"
                + "                FROM \n"
                + "                		 compra\n"
                + "                INNER JOIN \n"
                + "                	 	 itenscompra\n"
                + "                ON\n"
                + "                		 itenscompra.idcompra = compra.idcompra\n"
                + "                INNER JOIN\n"
                + "                		 produto\n"
                + "                ON\n"
                + "                		 itenscompra.idProduto = produto.idproduto\n"
                + "                INNER JOIN\n"
                + "                		 usuario\n"
                + "                ON\n"
                + "                		 usuario.idUsuario = compra.idUsuario\n"
                + "                INNER JOIN\n"
                + "                		 fornecedor\n"
                + "                ON\n"
                + "                		 fornecedor.idFornecedor = compra.idFornecedor\n"
                + "                GROUP BY	\n"
                + "                		 compra.idCompra\n"
                + "                ORDER BY\n"
                + "                		 compra.dataCompra";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            UsuarioBean usuario = new UsuarioBean();
            usuario.setIdUsuario(rs.getInt("idUsuario"));

            FornecedorBean fornec = new FornecedorBean();
            fornec.setIdFornecedor(rs.getInt("idFornecedor"));
            fornec.setNomeFornec(rs.getString("nomeFornec"));

            CompraBean compra = new CompraBean();
            compra.setIdCompra(rs.getInt("idCompra"));
            compra.setDataCompra(rs.getDate("dataCompra"));
            compra.setUsuario(usuario);
            compra.setFornec(fornec);

            lista.add(compra);
        }
        rs.close();
        stmt.close();
        con.close();
        return lista;
    }

    public static CompraBean pegaUltimo(CompraBean compra) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("insert into compra (dataCompra, idUsuario, idFornecedor) values (?,?,?)");
        stmt.setDate(1, new java.sql.Date(compra.getDataCompra().getTime()));
        stmt.setInt(2, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        stmt.setInt(3, compra.getFornec().getIdFornecedor());

        stmt.execute();

        String sql = "SELECT max(idCompra) as compra from compra where idUsuario = " + SessionContext.getInstance().getUsuarioLogado().getIdUsuario();
        stmt = con.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        rs.first();
        compra.setIdCompra(rs.getInt("compra"));
        stmt.close();
        con.close();

        return compra;
    }

    public static void excluir(CompraBean compra) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = con.prepareStatement("delete from compra where idCompra = ?");
        stmt.setInt(1, compra.getIdCompra());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

}
