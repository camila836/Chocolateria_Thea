/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Conexion.Conexion;
import Modelo.Envios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnviosDAO {

    public boolean insertar(Envios envio) {
        String sql = "INSERT INTO envios (fecha_envios, descripcion_envios, numero_guia, "
                   + "id_pedidos_cabeza, id_estado_envio, id_transportadoras) VALUES (?,?,?,?,?,?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, envio.getFechaEnvios());
            ps.setString(2, envio.getDescripcionEnvios());
            ps.setString(3, envio.getNumeroGuia());
            ps.setInt(4, envio.getIdPedidosCabeza());
            ps.setInt(5, envio.getIdEstadoEnvio());
            ps.setInt(6, envio.getIdTransportadoras());
            ps.executeUpdate();
            System.out.println("Envio insertado.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar Envios: " + e.getMessage());
            return false;
        }
    }

    public Envios consultar (int id) {
        String sql = "SELECT id_envios, fecha_envios, descripcion_envios, numero_guia, "
                   + "id_pedidos_cabeza, id_estado_envio, id_transportadoras "
                   + "FROM envios WHERE id_envios = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId Envios: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Envios envio) {
        String sql = "UPDATE envios SET fecha_envios=?, descripcion_envios=?, numero_guia=?, "
                   + "id_pedidos_cabeza=?, id_estado_envio=?, id_transportadoras=? "
                   + "WHERE id_envios=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, envio.getFechaEnvios());
            ps.setString(2, envio.getDescripcionEnvios());
            ps.setString(3, envio.getNumeroGuia());
            ps.setInt(4, envio.getIdPedidosCabeza());
            ps.setInt(5, envio.getIdEstadoEnvio());
            ps.setInt(6, envio.getIdTransportadoras());
            ps.setInt(7, envio.getIdEnvios());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar Envios: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM envios WHERE id_envios = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar Envios: " + e.getMessage());
            return false;
        }
    }

    public List<Envios> listarTodos() {
        List<Envios> lista = new ArrayList<>();
        String sql = "SELECT id_envios, fecha_envios, descripcion_envios, numero_guia, "
                   + "id_pedidos_cabeza, id_estado_envio, id_transportadoras FROM envios";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos Envios: " + e.getMessage());
        }
        return lista;
    }

    /** Método adicional: obtener envíos de un pedido específico. */
    public List<Envios> buscarPorPedido(int idPedidosCabeza) {
        List<Envios> lista = new ArrayList<>();
        String sql = "SELECT id_envios, fecha_envios, descripcion_envios, numero_guia, "
                   + "id_pedidos_cabeza, id_estado_envio, id_transportadoras "
                   + "FROM envios WHERE id_pedidos_cabeza = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedidosCabeza);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorPedido Envios: " + e.getMessage());
        }
        return lista;
    }

    private Envios mapear(ResultSet rs) throws SQLException {
        Envios e = new Envios();
        e.setIdEnvios(rs.getInt("id_envios"));
        e.setFechaEnvios(rs.getObject("fecha_envios", java.time.LocalDateTime.class));
        e.setDescripcionEnvios(rs.getString("descripcion_envios"));
        e.setNumeroGuia(rs.getString("numero_guia"));
        e.setIdPedidosCabeza(rs.getInt("id_pedidos_cabeza"));
        e.setIdEstadoEnvio(rs.getInt("id_estado_envio"));
        e.setIdTransportadoras(rs.getInt("id_transportadoras"));
        return e;
    }
}