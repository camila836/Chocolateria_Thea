/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class PedidosDetalle {

    private int    idPedidosDetalle;
    private double cantidadUnitaria;
    private double subtotalPed;
    // FK
    private int    idPedidosCabeza;
    private int    idProductos;

    public PedidosDetalle() {}

    public PedidosDetalle(int idPedidosDetalle, double cantidadUnitaria, double subtotalPed,
                          int idPedidosCabeza, int idProductos) {
        this.idPedidosDetalle  = idPedidosDetalle;
        this.cantidadUnitaria  = cantidadUnitaria;
        this.subtotalPed       = subtotalPed;
        this.idPedidosCabeza   = idPedidosCabeza;
        this.idProductos       = idProductos;
    }

    public int    getIdPedidosDetalle()                        { return idPedidosDetalle; }
    public void   setIdPedidosDetalle(int idPedidosDetalle)    { this.idPedidosDetalle = idPedidosDetalle; }

    public double getCantidadUnitaria()                            { return cantidadUnitaria; }
    public void   setCantidadUnitaria(double cantidadUnitaria)     { this.cantidadUnitaria = cantidadUnitaria; }

    public double getSubtotalPed()                     { return subtotalPed; }
    public void   setSubtotalPed(double subtotalPed)   { this.subtotalPed = subtotalPed; }

    public int    getIdPedidosCabeza()                         { return idPedidosCabeza; }
    public void   setIdPedidosCabeza(int idPedidosCabeza)      { this.idPedidosCabeza = idPedidosCabeza; }

    public int    getIdProductos()                   { return idProductos; }
    public void   setIdProductos(int idProductos)    { this.idProductos = idProductos; }

    @Override
    public String toString() {
        return "PedidosDetalle{id=" + idPedidosDetalle + ", cantidad=" + cantidadUnitaria + ", subtotal=" + subtotalPed + "}";
    }
}