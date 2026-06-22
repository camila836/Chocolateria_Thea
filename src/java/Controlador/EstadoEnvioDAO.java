package Controlador;

import Conexion.Conexion;
import Modelo.EstadoEnvio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoEnvioDAO {

    public boolean insertar(EstadoEnvio estado) {
        String sql = "INSERT INTO estado_envio (descripcion_estado_envio) VALUES (?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado.getDescripcionEstadoEnvio());
            ps.executeUpdate();
            System.out.println("EstadoEnvio insertado.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar EstadoEnvio: " + e.getMessage());
            return false;
        }
    }

    public EstadoEnvio consultar(int id) {
        String sql = "SELECT id_estado_envio, descripcion_estado_envio FROM estado_envio WHERE id_estado_envio = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId EstadoEnvio: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(EstadoEnvio estado) {
        String sql = "UPDATE estado_envio SET descripcion_estado_envio=? WHERE id_estado_envio=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado.getDescripcionEstadoEnvio());
            ps.setInt(2, estado.getIdEstadoEnvio());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar EstadoEnvio: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM estado_envio WHERE id_estado_envio = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar EstadoEnvio: " + e.getMessage());
            return false;
        }
    }

    public List<EstadoEnvio> listarTodos() {
        List<EstadoEnvio> lista = new ArrayList<>();
        String sql = "SELECT id_estado_envio, descripcion_estado_envio FROM estado_envio";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos EstadoEnvio: " + e.getMessage());
        }
        return lista;
    }

    private EstadoEnvio mapear(ResultSet rs) throws SQLException {
        EstadoEnvio e = new EstadoEnvio();
        e.setIdEstadoEnvio(rs.getInt("id_estado_envio"));
        e.setDescripcionEstadoEnvio(rs.getString("descripcion_estado_envio"));
        return e;
    }
}