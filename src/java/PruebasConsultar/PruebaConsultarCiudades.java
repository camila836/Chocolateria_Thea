package PruebasConsultar;

import Conexion.Conexion;
import Controlador.CiudadesDAO;
import Modelo.Ciudades;
import java.sql.Connection;
import java.util.Scanner;

public class PruebaConsultarCiudades {

    public static void main(String[] args) {

        Connection conn = Conexion.getConn();
        CiudadesDAO dao = new CiudadesDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el Id de la ciudad: ");
        int id = sc.nextInt();

        Ciudades ciudades = dao.consultar (id);

        if (ciudades != null) {
            System.out.println("Ciudades encontrada");
            System.out.println("Id: " + ciudades.getIdCiudades());
            System.out.println("Código ciudades: " + ciudades.getCodigoCiudad());
            System.out.println("Nombre ciudades: " + ciudades.getNombreCiudad());
            System.out.println("Código postal: " + ciudades.getCodigoPostal());
        } else {
            System.out.println("No se encontró la ciudad");
        }

        sc.close();
    }
}
