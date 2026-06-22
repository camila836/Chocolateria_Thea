package Controlador;


import Conexion.Conexion;
import Modelo.PedidosDetalle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidosDetalleDAO {

    public boolean insertar(PedidosDetalle d) {
        String sql = "INSERT INTO pedidos_detalle (cantidad_unitaria, subtotal_ped, "
                   + "id_pedidos_cabeza, id_productos) VALUES (?,?,?,?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, d.getCantidadUnitaria());
            ps.setDouble(2, d.getSubtotalPed());
            ps.setInt(3, d.getIdPedidosCabeza());
            ps.setInt(4, d.getIdProductos());
            ps.executeUpdate();
            System.out.println("PedidosDetalle insertado.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar PedidosDetalle: " + e.getMessage());
            return false;
        }
    }

    public PedidosDetalle consultar(int id) {
        String sql = "SELECT id_pedidos_detalle, cantidad_unitaria, subtotal_ped, "
                   + "id_pedidos_cabeza, id_productos FROM pedidos_detalle WHERE id_pedidos_detalle = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId PedidosDetalle: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(PedidosDetalle d) {
        String sql = "UPDATE pedidos_detalle SET cantidad_unitaria=?, subtotal_ped=?, "
                   + "id_pedidos_cabeza=?, id_productos=? WHERE id_pedidos_detalle=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, d.getCantidadUnitaria());
            ps.setDouble(2, d.getSubtotalPed());
            ps.setInt(3, d.getIdPedidosCabeza());
            ps.setInt(4, d.getIdProductos());
            ps.setInt(5, d.getIdPedidosDetalle());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar PedidosDetalle: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM pedidos_detalle WHERE id_pedidos_detalle = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar PedidosDetalle: " + e.getMessage());
            return false;
        }
    }

    public List<PedidosDetalle> listarTodos() {
        List<PedidosDetalle> lista = new ArrayList<>();
        String sql = "SELECT id_pedidos_detalle, cantidad_unitaria, subtotal_ped, "
                   + "id_pedidos_cabeza, id_productos FROM pedidos_detalle";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos PedidosDetalle: " + e.getMessage());
        }
        return lista;
    }

    /** Método adicional: obtener líneas de un pedido cabeza específico. */
    public List<PedidosDetalle> buscarPorPedidoCabeza(int idPedidosCabeza) {
        List<PedidosDetalle> lista = new ArrayList<>();
        String sql = "SELECT id_pedidos_detalle, cantidad_unitaria, subtotal_ped, "
                   + "id_pedidos_cabeza, id_productos FROM pedidos_detalle WHERE id_pedidos_cabeza = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedidosCabeza);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorPedidoCabeza PedidosDetalle: " + e.getMessage());
        }
        return lista;
    }

    private PedidosDetalle mapear(ResultSet rs) throws SQLException {
        PedidosDetalle d = new PedidosDetalle();
        d.setIdPedidosDetalle(rs.getInt("id_pedidos_detalle"));
        d.setCantidadUnitaria(rs.getDouble("cantidad_unitaria"));
        d.setSubtotalPed(rs.getDouble("subtotal_ped"));
        d.setIdPedidosCabeza(rs.getInt("id_pedidos_cabeza"));
        d.setIdProductos(rs.getInt("id_productos"));
        return d;
    }
}