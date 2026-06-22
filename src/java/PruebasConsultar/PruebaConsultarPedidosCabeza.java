package PruebasConsultar;

import Controlador.PedidosCabezaDAO;
import Modelo.PedidosCabeza;
import Conexion.Conexion;
import java.sql.Connection;
import java.util.Scanner;

public class PruebaConsultarPedidosCabeza {
    public static void main(String[] args) {
        Connection conn = Conexion.getConn();
        PedidosCabezaDAO dao = new PedidosCabezaDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el Id del pedido cabeza: ");
        int id = sc.nextInt();

        // Usando tu método: consultar
        PedidosCabeza pedido = dao.consultar(id);

        if (pedido != null) {
            System.out.println("Pedido (Cabeza) encontrado");
            System.out.println("Id Pedido Cabeza: " + pedido.getIdPedidosCabeza());
            System.out.println("Número Pedido: " + pedido.getNumeroPedido());
            System.out.println("Fecha Pedido: " + pedido.getFechaPedido());
            System.out.println("Descripción: " + pedido.getDescripcionPedido());
            System.out.println("Valor Total: $" + pedido.getValorTotal());
            System.out.println("ID Usuario: " + pedido.getIdUsuarios());
        } else {
            System.out.println(" No se encontró el pedido cabeza con el ID ingresado.");
        }

        sc.close();
    }
}