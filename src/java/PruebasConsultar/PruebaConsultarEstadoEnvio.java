package PruebasConsultar;

import Controlador.EstadoEnvioDAO;
import Modelo.EstadoEnvio;
import Conexion.Conexion;
import java.sql.Connection;
import java.util.Scanner;

public class PruebaConsultarEstadoEnvio {
    public static void main(String[] args) {
        Connection conn = Conexion.getConn();
        EstadoEnvioDAO dao = new EstadoEnvioDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el ID del estado de envío: ");
        int id = sc.nextInt();

        EstadoEnvio estado = dao.consultar(id);

        if (estado != null) {
            System.out.println("Estado de envío encontrado");
            System.out.println("ID: " + estado.getIdEstadoEnvio());
            System.out.println("Descripción: " + estado.getDescripcionEstadoEnvio());
        } else {
            System.out.println("No se encontró el estado de envío");
        }

        sc.close();
    }
}
