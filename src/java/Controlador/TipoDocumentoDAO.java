
package Controlador;
 
import Conexion.Conexion;
import Modelo.TipoDocumento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
public class TipoDocumentoDAO {
 
    public boolean insertar(TipoDocumento tipo) {
        String sql = "INSERT INTO tipo_documento (descripcion) VALUES (?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipo.getDescripcion());
            ps.executeUpdate();
            System.out.println("TipoDocumento insertado.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar TipoDocumento: " + e.getMessage());
            return false;
        }
    }
 
    public TipoDocumento consultar(int id) {
        String sql = "SELECT id_tipo_documento, descripcion FROM tipo_documento WHERE id_tipo_documento = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId TipoDocumento: " + e.getMessage());
        }
        return null;
    }
 
    /**
     * Busca un tipo de documento por su descripción (nombre) y retorna el
     * objeto completo, o null si no existe. Se usa cuando el formulario pide
     * el NOMBRE del tipo de documento en vez del número de id, y se necesita
     * obtener el id_tipo_documento real para guardarlo en la tabla usuarios.
     */
    public TipoDocumento buscarPorDescripcion(String descripcion) {
        String sql = "SELECT id_tipo_documento, descripcion FROM tipo_documento WHERE descripcion = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, descripcion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorDescripcion TipoDocumento: " + e.getMessage());
        }
        return null;
    }
 
    public boolean actualizar(TipoDocumento tipo) {
        String sql = "UPDATE tipo_documento SET descripcion=? WHERE id_tipo_documento=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipo.getDescripcion());
            ps.setInt(2, tipo.getIdTipoDocumento());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar TipoDocumento: " + e.getMessage());
            return false;
        }
    }
 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM tipo_documento WHERE id_tipo_documento = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar TipoDocumento: " + e.getMessage());
            return false;
        }
    }
 
    public List<TipoDocumento> listarTodos() {
        List<TipoDocumento> lista = new ArrayList<>();
        String sql = "SELECT id_tipo_documento, descripcion FROM tipo_documento";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos TipoDocumento: " + e.getMessage());
        }
        return lista;
    }
 
    private TipoDocumento mapear(ResultSet rs) throws SQLException {
        TipoDocumento t = new TipoDocumento();
        t.setIdTipoDocumento(rs.getInt("id_tipo_documento"));
        t.setDescripcion(rs.getString("descripcion"));
        return t;
    }
}
 