package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Conexion.Conexion;
import Modelo.CategoriaProductos;


public class CategoriaProductosDAO {

    public boolean insertar(CategoriaProductos categoria) {
        String sql = "INSERT INTO categoria_productos (descripcion_categoria_productos) VALUES (?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoria.getDescripcionCategoriaProductos());
            ps.executeUpdate();
            System.out.println("CategoriaProductos insertada.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar CategoriaProductos: " + e.getMessage());
            return false;
        }
    }

    public CategoriaProductos consultar(int idCategoriaProductos) {
        String sql = "SELECT id_categoria_productos, descripcion_categoria_productos "
                   + "FROM categoria_productos WHERE id_categoria_productos = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCategoriaProductos);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error consultar CategoriaProductos: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(CategoriaProductos categoria) {
        String sql = "UPDATE categoria_productos SET descripcion_categoria_productos = ? "
                   + "WHERE id_categoria_productos = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoria.getDescripcionCategoriaProductos());
            ps.setInt(2, categoria.getIdCategoriaProductos());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar CategoriaProductos: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idCategoriaProductos) {
        String sql = "DELETE FROM categoria_productos WHERE id_categoria_productos = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCategoriaProductos);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar CategoriaProductos: " + e.getMessage());
            return false;
        }
    }

    public List<CategoriaProductos> listarTodos() {
        List<CategoriaProductos> lista = new ArrayList<>();
        String sql = "SELECT id_categoria_productos, descripcion_categoria_productos FROM categoria_productos";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos CategoriaProductos: " + e.getMessage());
        }
        return lista;
    }

    private CategoriaProductos mapear(ResultSet rs) throws SQLException {
        CategoriaProductos c = new CategoriaProductos();
        c.setIdCategoriaProductos(rs.getInt("id_categoria_productos"));
        c.setDescripcionCategoriaProductos(rs.getString("descripcion_categoria_productos"));
        return c;
    }
}