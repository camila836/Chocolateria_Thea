package PruebasConsultar;


import Modelo.Roles;
import Conexion.Conexion;
import Controlador.RolesDAO;
import java.sql.Connection;
import java.util.Scanner;

public class PruebaConsultarRoles {

    public static void main(String[] args) {

        Connection conn = Conexion.getConn();
        RolesDAO dao = new RolesDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el ID del rol: ");
        int id = sc.nextInt();

        Roles rol = dao.consultar (id);

        if (rol != null) {
            System.out.println("Rol encontrado");
            System.out.println("ID: " + rol.getIdRoles());
            System.out.println("Descripción: " + rol.getDescripcionRol());
        } else {
            System.out.println("No se encontró el rol");
        }

        sc.close();
    }
}