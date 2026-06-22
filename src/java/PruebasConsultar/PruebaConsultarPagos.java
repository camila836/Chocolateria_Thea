package PruebasConsultar;


import Modelo.Pagos;
import Conexion.Conexion;
import Controlador.PagosDAO;
import java.sql.Connection;
import java.util.Scanner;

public class PruebaConsultarPagos {
    public static void main(String[] args) {
        Connection conn = Conexion.getConn();
        PagosDAO dao = new PagosDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el Id del pago: ");
        int id = sc.nextInt();

        // Usando tu método: consultar
        Pagos pagos = dao.consultar(id);

        if (pagos != null) {
            System.out.println(" Pago encontrado");
            System.out.println("Id Pago: " + pagos.getIdPagos());
            System.out.println("Fecha Pago: " + pagos.getFechaPagos());
            System.out.println("Descripción: " + pagos.getDescripcionPagos());
            System.out.println("Monto: $" + pagos.getMonto());
            System.out.println("Referencia: " + pagos.getReferenciaPago());
            System.out.println("Comprobante: " + pagos.getComprobantePago());
            System.out.println("ID Medios Pago: " + pagos.getIdMediosPagos());
            System.out.println("ID Pedido Cabeza: " + pagos.getIdPedidosCabeza());
        } else {
            System.out.println("No se encontró el pago con el ID ingresado.");
        }

        sc.close();
    }
}