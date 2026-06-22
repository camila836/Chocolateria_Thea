package Controlador;

import Conexion.Conexion;
import Modelo.UnidadesMedida;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadesMedidaDAO {

    public boolean insertar(UnidadesMedida u) {
        String sql = "INSERT INTO unidades_medida (descripcion_unidades_med) VALUES (?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getDescripcionUnidadesMed());
            ps.executeUpdate();
            System.out.println("UnidadesMedida insertada.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar UnidadesMedida: " + e.getMessage());
            return false;
        }
    }

    public UnidadesMedida consultar (int id) {
        String sql = "SELECT id_unidades_medida, descripcion_unidades_med FROM unidades_medida WHERE id_unidades_medida = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId UnidadesMedida: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(UnidadesMedida u) {
        String sql = "UPDATE unidades_medida SET descripcion_unidades_med=? WHERE id_unidades_medida=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getDescripcionUnidadesMed());
            ps.setInt(2, u.getIdUnidadesMedida());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar UnidadesMedida: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM unidades_medida WHERE id_unidades_medida = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar UnidadesMedida: " + e.getMessage());
            return false;
        }
    }

    public List<UnidadesMedida> listarTodos() {
        List<UnidadesMedida> lista = new ArrayList<>();
        String sql = "SELECT id_unidades_medida, descripcion_unidades_med FROM unidades_medida";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos UnidadesMedida: " + e.getMessage());
        }
        return lista;
    }

    private UnidadesMedida mapear(ResultSet rs) throws SQLException {
        UnidadesMedida u = new UnidadesMedida();
        u.setIdUnidadesMedida(rs.getInt("id_unidades_medida"));
        u.setDescripcionUnidadesMed(rs.getString("descripcion_unidades_med"));
        return u;
    }
}