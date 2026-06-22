package Controlador;

import Conexion.Conexion;
import Modelo.Usuarios;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UsuariosDAO {

    // Columnas nuevas pedidas en el tablero (11-jun-2026):
    //   fechaNacimiento                -> DATE
    //   fechaVencimientoClave          -> DATE
    //   autorizacionTratamientoDatos   -> BOOLEAN (consentimiento de datos personales, Ley 1581)

    public boolean insertar(Usuarios u) {
        String sql = "INSERT INTO usuarios (nombres, apellidos, identificacion, correo, "
                   + "direccion, telefono, clave, isActivo, fechaNacimiento, fechaVencimientoClave, "
                   + "autorizacionTratamientoDatos, idRoles, idTipoDocumento, idCiudades) "
                   + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString (1,  u.getNombres());
            ps.setString (2,  u.getApellidos());
            ps.setString (3,  u.getIdentificacion());
            ps.setString (4,  u.getCorreo());
            ps.setString (5,  u.getDireccion());
            ps.setString (6,  u.getTelefono());
            ps.setString (7,  u.getClave());
            ps.setBoolean(8,  u.isActivo());
            ps.setObject (9,  u.getFechaNacimiento());
            ps.setObject (10, u.getFechaVencimientoClave());
            ps.setBoolean(11, u.isAutorizacionTratamientoDatos());
            ps.setInt    (12, u.getIdRoles());
            ps.setInt    (13, u.getIdTipoDocumento());
            ps.setInt    (14, u.getIdCiudades());
            ps.executeUpdate();
            System.out.println("Usuario insertado.");
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Correo o documento duplicado: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.err.println("Error insertar Usuarios: " + e.getMessage());
            return false;
        }
    }

    public Usuarios consultar(int id) {
        String sql = "SELECT idUsuarios, nombres, apellidos, identificacion, correo, "
                   + "direccion, telefono, clave, isActivo, fechaNacimiento, fechaVencimientoClave, "
                   + "autorizacionTratamientoDatos, idRoles, idTipoDocumento, idCiudades "
                   + "FROM usuarios WHERE idUsuarios = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error consultar Usuarios: " + e.getMessage());
        }
        return null;
    }

    /** Buscar usuario por correo — útil para login y validaciones. */
    public Usuarios consultarPorCorreo(String correo) {
        String sql = "SELECT idUsuarios, nombres, apellidos, identificacion, correo, "
                   + "direccion, telefono, clave, isActivo, fechaNacimiento, fechaVencimientoClave, "
                   + "autorizacionTratamientoDatos, idRoles, idTipoDocumento, idCiudades "
                   + "FROM usuarios WHERE correo = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error consultarPorCorreo Usuarios: " + e.getMessage());
        }
        return null;
    }

    /** Validar login: retorna el nombre del usuario si las credenciales son correctas, null si no. */
    public String validarLogin(String correo, String clave) {
        String sql = "SELECT nombres FROM usuarios WHERE correo=? AND clave=? AND isActivo=true";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, clave);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("nombres");
            }
        } catch (SQLException e) {
            System.err.println("Error validarLogin Usuarios: " + e.getMessage());
        }
        return null;
    }

    /** True si la clave del usuario ya venció (fechaVencimientoClave es anterior a hoy). */
    public boolean claveVencida(int idUsuarios) {
        String sql = "SELECT fechaVencimientoClave FROM usuarios WHERE idUsuarios = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuarios);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    java.time.LocalDate vencimiento = rs.getObject("fechaVencimientoClave", java.time.LocalDate.class);
                    return vencimiento != null && vencimiento.isBefore(java.time.LocalDate.now());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error claveVencida Usuarios: " + e.getMessage());
        }
        return false;
    }

    public boolean actualizar(Usuarios u) {
        String sql = "UPDATE usuarios SET nombres=?, apellidos=?, identificacion=?, correo=?, "
                   + "direccion=?, telefono=?, clave=?, isActivo=?, fechaNacimiento=?, "
                   + "fechaVencimientoClave=?, autorizacionTratamientoDatos=?, idRoles=?, "
                   + "idTipoDocumento=?, idCiudades=? WHERE idUsuarios=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString (1,  u.getNombres());
            ps.setString (2,  u.getApellidos());
            ps.setString (3,  u.getIdentificacion());
            ps.setString (4,  u.getCorreo());
            ps.setString (5,  u.getDireccion());
            ps.setString (6,  u.getTelefono());
            ps.setString (7,  u.getClave());
            ps.setBoolean(8,  u.isActivo());
            ps.setObject (9,  u.getFechaNacimiento());
            ps.setObject (10, u.getFechaVencimientoClave());
            ps.setBoolean(11, u.isAutorizacionTratamientoDatos());
            ps.setInt    (12, u.getIdRoles());
            ps.setInt    (13, u.getIdTipoDocumento());
            ps.setInt    (14, u.getIdCiudades());
            ps.setInt    (15, u.getIdUsuarios());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar Usuarios: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE idUsuarios = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar Usuarios: " + e.getMessage());
            return false;
        }
    }

    public List<Usuarios> listarTodos() {
        List<Usuarios> lista = new ArrayList<>();
        String sql = "SELECT idUsuarios, nombres, apellidos, identificacion, correo, "
                   + "direccion, telefono, clave, isActivo, fechaNacimiento, fechaVencimientoClave, "
                   + "autorizacionTratamientoDatos, idRoles, idTipoDocumento, idCiudades "
                   + "FROM usuarios";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos Usuarios: " + e.getMessage());
        }
        return lista;
    }

    private Usuarios mapear(ResultSet rs) throws SQLException {
        Usuarios u = new Usuarios();
        u.setIdUsuarios(rs.getInt("idUsuarios"));
        u.setNombres(rs.getString("nombres"));
        u.setApellidos(rs.getString("apellidos"));
        u.setIdentificacion(rs.getString("identificacion"));
        u.setCorreo(rs.getString("correo"));
        u.setDireccion(rs.getString("direccion"));
        u.setTelefono(rs.getString("telefono"));
        u.setClave(rs.getString("clave"));
        u.setIsActivo(rs.getBoolean("isActivo"));
        u.setFechaNacimiento(rs.getObject("fechaNacimiento", java.time.LocalDate.class));
        u.setFechaVencimientoClave(rs.getObject("fechaVencimientoClave", java.time.LocalDate.class));
        u.setAutorizacionTratamientoDatos(rs.getBoolean("autorizacionTratamientoDatos"));
        u.setIdRoles(rs.getInt("idRoles"));
        u.setIdTipoDocumento(rs.getInt("idTipoDocumento"));
        u.setIdCiudades(rs.getInt("idCiudades"));
        return u;
    }
}

