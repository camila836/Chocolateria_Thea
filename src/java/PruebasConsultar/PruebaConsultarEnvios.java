package PruebasConsultar;

import Controlador.EnviosDAO;
import Modelo.Envios;
import Conexion.Conexion;
import java.sql.Connection;
import java.util.Scanner;

public class PruebaConsultarEnvios {
    public static void main(String[] args) {
        Connection conn = Conexion.getConn();
        EnviosDAO dao = new EnviosDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el Id del envío: ");
        int id = sc.nextInt();

        Envios envios = dao.consultar (id);

        if (envios != null) {
            System.out.println("Envío encontrado");
            System.out.println("Id: " + envios.getIdEnvios());
            System.out.println("Fecha envío: " + envios.getFechaEnvios());
            System.out.println("Descripción: " + envios.getDescripcionEnvios());
            System.out.println("Número guía: " + envios.getNumeroGuia());
            System.out.println("ID Pedidos cabeza: " + envios.getIdPedidosCabeza());
            System.out.println("ID Estado envío: " + envios.getIdEstadoEnvio());
            System.out.println("ID Transportadora: " + envios.getIdTransportadoras());
        } else {
            System.out.println("No se encontró el envío");
        }

        sc.close();
    }
}
