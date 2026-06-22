package PruebasConsultar;

import Controlador.InventarioDAO;
import Modelo.Inventario;
import Conexion.Conexion;
import java.sql.Connection;
import java.util.Scanner;

public class PruebaConsultarInventario {
    public static void main(String[] args) {
        Connection conn = Conexion.getConn();
        InventarioDAO dao = new InventarioDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el ID del inventario: ");
        int id = sc.nextInt();

        Inventario inventario = dao.consultar(id);

        if (inventario != null) {
            System.out.println("Inventario encontrado");
            System.out.println("ID: " + inventario.getIdInventario());
            System.out.println("Descripción: " + inventario.getDescripcionInventario());
            System.out.println("Stock: " + inventario.getStock());
            System.out.println("ID Producto: " + inventario.getIdProductos());
        } else {
            System.out.println("No se encontró el inventario");
        }

        sc.close();
    }
}

