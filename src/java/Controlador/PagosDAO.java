package Controlador;


import Conexion.Conexion;
import Modelo.Pagos;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagosDAO {

    public boolean insertar(Pagos p) {
        String sql = "INSERT INTO pagos (fecha_pagos, descripcion_pagos, monto, referencia_pago, "
                   + "comprobante_pago, id_medios_pagos, id_pedidos_cabeza) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, p.getFechaPagos());
            ps.setString(2, p.getDescripcionPagos());
            ps.setDouble(3, p.getMonto());
            ps.setString(4, p.getReferenciaPago());
            ps.setString(5, p.getComprobantePago());
            ps.setInt(6, p.getIdMediosPagos());
            ps.setInt(7, p.getIdPedidosCabeza());
            ps.executeUpdate();
            System.out.println("Pago insertado.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertar Pagos: " + e.getMessage());
            return false;
        }
    }

    public Pagos consultar (int id) {
        String sql = "SELECT id_pagos, fecha_pagos, descripcion_pagos, monto, referencia_pago, "
                   + "comprobante_pago, id_medios_pagos, id_pedidos_cabeza FROM pagos WHERE id_pagos = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorId Pagos: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Pagos p) {
        String sql = "UPDATE pagos SET fecha_pagos=?, descripcion_pagos=?, monto=?, referencia_pago=?, "
                   + "comprobante_pago=?, id_medios_pagos=?, id_pedidos_cabeza=? WHERE id_pagos=?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, p.getFechaPagos());
            ps.setString(2, p.getDescripcionPagos());
            ps.setDouble(3, p.getMonto());
            ps.setString(4, p.getReferenciaPago());
            ps.setString(5, p.getComprobantePago());
            ps.setInt(6, p.getIdMediosPagos());
            ps.setInt(7, p.getIdPedidosCabeza());
            ps.setInt(8, p.getIdPagos());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar Pagos: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM pagos WHERE id_pagos = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar Pagos: " + e.getMessage());
            return false;
        }
    }

    public List<Pagos> listarTodos() {
        List<Pagos> lista = new ArrayList<>();
        String sql = "SELECT id_pagos, fecha_pagos, descripcion_pagos, monto, referencia_pago, "
                   + "comprobante_pago, id_medios_pagos, id_pedidos_cabeza FROM pagos";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listarTodos Pagos: " + e.getMessage());
        }
        return lista;
    }

    /** Método adicional: pagos de un pedido específico. */
    public List<Pagos> buscarPorPedido(int idPedidosCabeza) {
        List<Pagos> lista = new ArrayList<>();
        String sql = "SELECT id_pagos, fecha_pagos, descripcion_pagos, monto, referencia_pago, "
                   + "comprobante_pago, id_medios_pagos, id_pedidos_cabeza FROM pagos WHERE id_pedidos_cabeza = ?";
        try (Connection con = Conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedidosCabeza);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error buscarPorPedido Pagos: " + e.getMessage());
        }
        return lista;
    }

    private Pagos mapear(ResultSet rs) throws SQLException {
        Pagos p = new Pagos();
        p.setIdPagos(rs.getInt("id_pagos"));
        p.setFechaPagos(rs.getObject("fecha_pagos", java.time.LocalDateTime.class));
        p.setDescripcionPagos(rs.getString("descripcion_pagos"));
        p.setMonto(rs.getDouble("monto"));
        p.setReferenciaPago(rs.getString("referencia_pago"));
        p.setComprobantePago(rs.getString("comprobante_pago"));
        p.setIdMediosPagos(rs.getInt("id_medios_pagos"));
        p.setIdPedidosCabeza(rs.getInt("id_pedidos_cabeza"));
        return p;
    }
}