package Controlador;

import Conexion.Conexion;
import Modelo.Productos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAO {

    public boolean insertar(Productos p) {
        String sql = "INSERT INTO productos (descripcion_productos, precio_productos, "
                   + "id_unidades_medida, id_categoria_productos) VALUES (?,?,?,?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getDescripcionProductos());
            ps.setDouble(2, p.getPrecioProductos());
            ps.setInt(3, p.getIdUnidadesMedida());
            ps.setInt(4, p.getIdCategoriaProductos());
            ps.executeUpdate();
            System.out.println("Producto insertado.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar Productos: " + e.getMessage());
            return false;
        }
    }

    public Productos consultar (int id) {
        String sql = "SELECT id_productos, descripcion_productos, precio_productos, "
                   + "id_unidades_medida, id_categoria_productos FROM productos WHERE id_productos = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId Productos: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Productos p) {
        String sql = "UPDATE productos SET descripcion_productos=?, precio_productos=?, "
                   + "id_unidades_medida=?, id_categoria_productos=? WHERE id_productos=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getDescripcionProductos());
            ps.setDouble(2, p.getPrecioProductos());
            ps.setInt(3, p.getIdUnidadesMedida());
            ps.setInt(4, p.getIdCategoriaProductos());
            ps.setInt(5, p.getIdProductos());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar Productos: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id_productos = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar Productos: " + e.getMessage());
            return false;
        }
    }

    public List<Productos> listarTodos() {
        List<Productos> lista = new ArrayList<>();
        String sql = "SELECT id_productos, descripcion_productos, precio_productos, "
                   + "id_unidades_medida, id_categoria_productos FROM productos";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos Productos: " + e.getMessage());
        }
        return lista;
    }

    /** Método adicional: buscar productos por categoría. */
    public List<Productos> buscarPorCategoria(int idCategoria) {
        List<Productos> lista = new ArrayList<>();
        String sql = "SELECT id_productos, descripcion_productos, precio_productos, "
                   + "id_unidades_medida, id_categoria_productos FROM productos WHERE id_categoria_productos = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorCategoria Productos: " + e.getMessage());
        }
        return lista;
    }

    private Productos mapear(ResultSet rs) throws SQLException {
        Productos p = new Productos();
        p.setIdProductos(rs.getInt("id_productos"));
        p.setDescripcionProductos(rs.getString("descripcion_productos"));
        p.setPrecioProductos(rs.getDouble("precio_productos"));
        p.setIdUnidadesMedida(rs.getInt("id_unidades_medida"));
        p.setIdCategoriaProductos(rs.getInt("id_categoria_productos"));
        return p;
    }
}