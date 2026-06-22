/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.Conexion;
import Modelo.MediosPagos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MediosPagosDAO {

    public boolean insertar(MediosPagos mp) {
        String sql = "INSERT INTO medios_pagos (descripcion_medios_pagos) VALUES (?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, mp.getDescripcionMediosPagos());
            ps.executeUpdate();
            System.out.println("MediosPagos insertado.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar MediosPagos: " + e.getMessage());
            return false;
        }
    }

    public MediosPagos consultar(int id) {
        String sql = "SELECT id_medios_pagos, descripcion_medios_pagos FROM medios_pagos WHERE id_medios_pagos = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId MediosPagos: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(MediosPagos mp) {
        String sql = "UPDATE medios_pagos SET descripcion_medios_pagos=? WHERE id_medios_pagos=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, mp.getDescripcionMediosPagos());
            ps.setInt(2, mp.getIdMediosPagos());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar MediosPagos: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM medios_pagos WHERE id_medios_pagos = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar MediosPagos: " + e.getMessage());
            return false;
        }
    }

    public List<MediosPagos> listarTodos() {
        List<MediosPagos> lista = new ArrayList<>();
        String sql = "SELECT id_medios_pagos, descripcion_medios_pagos FROM medios_pagos";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos MediosPagos: " + e.getMessage());
        }
        return lista;
    }

    private MediosPagos mapear(ResultSet rs) throws SQLException {
        MediosPagos m = new MediosPagos();
        m.setIdMediosPagos(rs.getInt("id_medios_pagos"));
        m.setDescripcionMediosPagos(rs.getString("descripcion_medios_pagos"));
        return m;
    }
}