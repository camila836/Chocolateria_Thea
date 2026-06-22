
package Controlador;
 
import Conexion.Conexion;
import Modelo.Roles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
public class RolesDAO {
 
    public boolean insertar(Roles rol) {
        String sql = "INSERT INTO roles (descripcion_rol) VALUES (?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rol.getDescripcionRol());
            ps.executeUpdate();
            System.out.println("Rol insertado.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar Roles: " + e.getMessage());
            return false;
        }
    }
 
    public Roles consultar(int id) {
        String sql = "SELECT id_roles, descripcion_rol FROM roles WHERE id_roles = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId Roles: " + e.getMessage());
        }
        return null;
    }
 
    /**
     * Busca un rol por su descripción (nombre) y retorna el objeto completo,
     * o null si no existe. Se usa cuando el formulario pide el NOMBRE del rol
     * en vez del número de id, y se necesita obtener el id_roles real para
     * guardarlo en la tabla usuarios.
     */
    public Roles buscarPorDescripcion(String descripcion) {
        String sql = "SELECT id_roles, descripcion_rol FROM roles WHERE descripcion_rol = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, descripcion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorDescripcion Roles: " + e.getMessage());
        }
        return null;
    }
 
    public boolean actualizar(Roles rol) {
        String sql = "UPDATE roles SET descripcion_rol=? WHERE id_roles=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rol.getDescripcionRol());
            ps.setInt(2, rol.getIdRoles());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar Roles: " + e.getMessage());
            return false;
        }
    }
 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM roles WHERE id_roles = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar Roles: " + e.getMessage());
            return false;
        }
    }
 
    public List<Roles> listarTodos() {
        List<Roles> lista = new ArrayList<>();
        String sql = "SELECT id_roles, descripcion_rol FROM roles";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos Roles: " + e.getMessage());
        }
        return lista;
    }
 
    private Roles mapear(ResultSet rs) throws SQLException {
        Roles r = new Roles();
        r.setIdRoles(rs.getInt("id_roles"));
        r.setDescripcionRol(rs.getString("descripcion_rol"));
        return r;
    }
}
 