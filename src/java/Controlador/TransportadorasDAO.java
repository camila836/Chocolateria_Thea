package Controlador;

import Conexion.Conexion;
import Modelo.Transportadoras;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransportadorasDAO {

    public boolean insertar(Transportadoras t) {
        String sql = "INSERT INTO transportadoras (nombre_transportadoras, nit, correo, telefono) VALUES (?,?,?,?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombreTransportadoras());
            ps.setString(2, t.getNit());
            ps.setString(3, t.getCorreo());
            ps.setString(4, t.getTelefono());
            ps.executeUpdate();
            System.out.println("Transportadora insertada.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar Transportadoras: " + e.getMessage());
            return false;
        }
    }

    public Transportadoras consultar (int id) {
        String sql = "SELECT id_transportadoras, nombre_transportadoras, nit, correo, telefono "
                   + "FROM transportadoras WHERE id_transportadoras = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId Transportadoras: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Transportadoras t) {
        String sql = "UPDATE transportadoras SET nombre_transportadoras=?, nit=?, correo=?, telefono=? "
                   + "WHERE id_transportadoras=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombreTransportadoras());
            ps.setString(2, t.getNit());
            ps.setString(3, t.getCorreo());
            ps.setString(4, t.getTelefono());
            ps.setInt(5, t.getIdTransportadoras());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar Transportadoras: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM transportadoras WHERE id_transportadoras = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar Transportadoras: " + e.getMessage());
            return false;
        }
    }

    public List<Transportadoras> listarTodos() {
        List<Transportadoras> lista = new ArrayList<>();
        String sql = "SELECT id_transportadoras, nombre_transportadoras, nit, correo, telefono FROM transportadoras";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos Transportadoras: " + e.getMessage());
        }
        return lista;
    }

    private Transportadoras mapear(ResultSet rs) throws SQLException {
        Transportadoras t = new Transportadoras();
        t.setIdTransportadoras(rs.getInt("id_transportadoras"));
        t.setNombreTransportadoras(rs.getString("nombre_transportadoras"));
        t.setNit(rs.getString("nit"));
        t.setCorreo(rs.getString("correo"));
        t.setTelefono(rs.getString("telefono"));
        return t;
    }
}