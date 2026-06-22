
package Controlador;

import Conexion.Conexion;
import Modelo.Inventario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {

    public boolean insertar(Inventario inv) {
        String sql = "INSERT INTO inventario (descripcion_inventario, stock, id_productos) VALUES (?,?,?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, inv.getDescripcionInventario());
            ps.setDouble(2, inv.getStock());
            ps.setInt(3, inv.getIdProductos());
            ps.executeUpdate();
            System.out.println("Inventario insertado.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar Inventario: " + e.getMessage());
            return false;
        }
    }

    public Inventario consultar(int id) {
        String sql = "SELECT id_inventario, descripcion_inventario, stock, id_productos "
                   + "FROM inventario WHERE id_inventario = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId Inventario: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Inventario inv) {
        String sql = "UPDATE inventario SET descripcion_inventario=?, stock=?, id_productos=? WHERE id_inventario=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, inv.getDescripcionInventario());
            ps.setDouble(2, inv.getStock());
            ps.setInt(3, inv.getIdProductos());
            ps.setInt(4, inv.getIdInventario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar Inventario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM inventario WHERE id_inventario = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar Inventario: " + e.getMessage());
            return false;
        }
    }

    public List<Inventario> listarTodos() {
        List<Inventario> lista = new ArrayList<>();
        String sql = "SELECT id_inventario, descripcion_inventario, stock, id_productos FROM inventario";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos Inventario: " + e.getMessage());
        }
        return lista;
    }

    /** Método adicional: buscar inventario por producto. */
    public Inventario buscarPorProducto(int idProducto) {
        String sql = "SELECT id_inventario, descripcion_inventario, stock, id_productos "
                   + "FROM inventario WHERE id_productos = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorProducto Inventario: " + e.getMessage());
        }
        return null;
    }

    private Inventario mapear(ResultSet rs) throws SQLException {
        Inventario i = new Inventario();
        i.setIdInventario(rs.getInt("id_inventario"));
        i.setDescripcionInventario(rs.getString("descripcion_inventario"));
        i.setStock(rs.getDouble("stock"));
        i.setIdProductos(rs.getInt("id_productos"));
        return i;
    }
}