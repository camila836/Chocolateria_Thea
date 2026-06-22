/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.Conexion;
import Modelo.Ciudades;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CiudadesDAO {

    public boolean insertar(Ciudades ciudad) {
        String sql = "INSERT INTO ciudades (codigo_ciudad, nombre_ciudad, codigo_postal) VALUES (?,?,?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ciudad.getCodigoCiudad());
            ps.setString(2, ciudad.getNombreCiudad());
            ps.setString(3, ciudad.getCodigoPostal());
            ps.executeUpdate();
            System.out.println("Ciudad insertada.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar Ciudades: " + e.getMessage());
            return false;
        }
    }

    public Ciudades consultar(int id) {
        String sql = "SELECT id_ciudades, codigo_ciudad, nombre_ciudad, codigo_postal "
                   + "FROM ciudades WHERE id_ciudades = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId Ciudades: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Ciudades ciudad) {
        String sql = "UPDATE ciudades SET codigo_ciudad=?, nombre_ciudad=?, codigo_postal=? WHERE id_ciudades=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ciudad.getCodigoCiudad());
            ps.setString(2, ciudad.getNombreCiudad());
            ps.setString(3, ciudad.getCodigoPostal());
            ps.setInt(4, ciudad.getIdCiudades());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar Ciudades: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM ciudades WHERE id_ciudades = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar Ciudades: " + e.getMessage());
            return false;
        }
    }

    public List<Ciudades> listarTodos() {
        List<Ciudades> lista = new ArrayList<>();
        String sql = "SELECT id_ciudades, codigo_ciudad, nombre_ciudad, codigo_postal FROM ciudades";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos Ciudades: " + e.getMessage());
        }
        return lista;
    }

   
    public List<Ciudades> buscarPorNombre(String nombre) {
        List<Ciudades> lista = new ArrayList<>();
        String sql = "SELECT id_ciudades, codigo_ciudad, nombre_ciudad, codigo_postal "
                   + "FROM ciudades WHERE nombre_ciudad LIKE ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorNombre Ciudades: " + e.getMessage());
        }
        return lista;
    }

    private Ciudades mapear(ResultSet rs) throws SQLException {
        Ciudades c = new Ciudades();
        c.setIdCiudades(rs.getInt("id_ciudades"));
        c.setCodigoCiudad(rs.getString("codigo_ciudad"));
        c.setNombreCiudad(rs.getString("nombre_ciudad"));
        c.setCodigoPostal(rs.getString("codigo_postal"));
        return c;
    }
}