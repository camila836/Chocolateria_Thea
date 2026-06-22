package Controlador;


import Conexion.Conexion;
import Modelo.PedidosCabeza;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidosCabezaDAO {

    public boolean insertar(PedidosCabeza p) {
        String sql = "INSERT INTO pedidos_cabeza (numero_pedido, fecha_pedido, descripcion_pedido, "
                   + "valor_total, id_usuarios) VALUES (?,?,?,?,?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNumeroPedido());
            ps.setObject(2, p.getFechaPedido());
            ps.setString(3, p.getDescripcionPedido());
            ps.setDouble(4, p.getValorTotal());
            ps.setInt(5, p.getIdUsuarios());
            ps.executeUpdate();
            System.out.println("PedidosCabeza insertado.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar PedidosCabeza: " + e.getMessage());
            return false;
        }
    }

    public PedidosCabeza consultar(int id) {
        String sql = "SELECT id_pedidos_cabeza, numero_pedido, fecha_pedido, descripcion_pedido, "
                   + "valor_total, id_usuarios FROM pedidos_cabeza WHERE id_pedidos_cabeza = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId PedidosCabeza: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(PedidosCabeza p) {
        String sql = "UPDATE pedidos_cabeza SET numero_pedido=?, fecha_pedido=?, descripcion_pedido=?, "
                   + "valor_total=?, id_usuarios=? WHERE id_pedidos_cabeza=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNumeroPedido());
            ps.setObject(2, p.getFechaPedido());
            ps.setString(3, p.getDescripcionPedido());
            ps.setDouble(4, p.getValorTotal());
            ps.setInt(5, p.getIdUsuarios());
            ps.setInt(6, p.getIdPedidosCabeza());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar PedidosCabeza: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM pedidos_cabeza WHERE id_pedidos_cabeza = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar PedidosCabeza: " + e.getMessage());
            return false;
        }
    }

    public List<PedidosCabeza> listarTodos() {
        List<PedidosCabeza> lista = new ArrayList<>();
        String sql = "SELECT id_pedidos_cabeza, numero_pedido, fecha_pedido, descripcion_pedido, "
                   + "valor_total, id_usuarios FROM pedidos_cabeza";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos PedidosCabeza: " + e.getMessage());
        }
        return lista;
    }

    /** Método adicional: pedidos de un usuario. */
    public List<PedidosCabeza> buscarPorUsuario(int idUsuario) {
        List<PedidosCabeza> lista = new ArrayList<>();
        String sql = "SELECT id_pedidos_cabeza, numero_pedido, fecha_pedido, descripcion_pedido, "
                   + "valor_total, id_usuarios FROM pedidos_cabeza WHERE id_usuarios = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorUsuario PedidosCabeza: " + e.getMessage());
        }
        return lista;
    }

    private PedidosCabeza mapear(ResultSet rs) throws SQLException {
        PedidosCabeza p = new PedidosCabeza();
        p.setIdPedidosCabeza(rs.getInt("id_pedidos_cabeza"));
        p.setNumeroPedido(rs.getString("numero_pedido"));
        p.setFechaPedido(rs.getObject("fecha_pedido", java.time.LocalDateTime.class));
        p.setDescripcionPedido(rs.getString("descripcion_pedido"));
        p.setValorTotal(rs.getDouble("valor_total"));
        p.setIdUsuarios(rs.getInt("id_usuarios"));
        return p;
    }
}