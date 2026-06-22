package PruebasConsultar;

import Controlador.PedidosDetalleDAO;
import Modelo.PedidosDetalle;
import Conexion.Conexion;
import java.sql.Connection;
import java.util.Scanner;

public class PruebaConsultarPedidosDetalle {
    public static void main(String[] args) {
        Connection conn = Conexion.getConn();
        PedidosDetalleDAO dao = new PedidosDetalleDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el Id del detalle de pedido: ");
        int id = sc.nextInt();

        // Usando tu método: consultar
        PedidosDetalle detalle = dao.consultar(id);

        if (detalle != null) {
            System.out.println("Detalle de Pedido encontrado");
            System.out.println("Id Pedido Detalle: " + detalle.getIdPedidosDetalle());
            System.out.println("Cantidad Unitaria: " + detalle.getCantidadUnitaria());
            System.out.println("Subtotal: $" + detalle.getSubtotalPed());
            System.out.println("ID Pedido Cabeza: " + detalle.getIdPedidosCabeza());
            System.out.println("ID Producto (Chocolate): " + detalle.getIdProductos());
        } else {
            System.out.println(" No se encontró el detalle de pedido con el ID ingresado.");
        }

        sc.close();
    }
}