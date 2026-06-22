package PruebasConsultar;

import Controlador.TransportadorasDAO;
import Modelo.Transportadoras;
import Conexion.Conexion;
import java.sql.Connection;
import java.util.Scanner;

public class PruebaConsultarTransportadoras {
    public static void main(String[] args) {
        Connection conn = Conexion.getConn();
        TransportadorasDAO dao = new TransportadorasDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el ID de la transportadora: ");
        int id = sc.nextInt();

        Transportadoras transportadora = dao.consultar(id);

        if (transportadora != null) {
            System.out.println("Transportadora encontrada");
            System.out.println("ID: " + transportadora.getIdTransportadoras());
            System.out.println("Nombre: " + transportadora.getNombreTransportadoras());
            System.out.println("NIT: " + transportadora.getNit());
            System.out.println("Correo: " + transportadora.getCorreo());
            System.out.println("Teléfono: " + transportadora.getTelefono());
        } else {
            System.out.println("No se encontró la transportadora");
        }

        sc.close();
    }
}