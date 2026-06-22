package PruebasConsultar;

import Controlador.ProductosDAO;
import Modelo.Productos;
import Conexion.Conexion;
import java.sql.Connection;
import java.util.Scanner;

public class PruebaConsultarProductos {
    public static void main(String[] args) {
        Connection conn = Conexion.getConn();
        ProductosDAO dao = new ProductosDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el Id del producto (chocolate): ");
        int id = sc.nextInt();

        // Usando tu método: consultar
        Productos producto = dao.consultar(id);

        if (producto != null) {
            System.out.println(" Producto encontrado");
            System.out.println("Id Producto: " + producto.getIdProductos());
            System.out.println("Descripción/Nombre: " + producto.getDescripcionProductos());
            System.out.println("Precio: $" + producto.getPrecioProductos());
            System.out.println("ID Unidad de Medida: " + producto.getIdUnidadesMedida());
            System.out.println("ID Categoría: " + producto.getIdCategoriaProductos());
        } else {
            System.out.println(" No se encontró el producto con el ID ingresado.");
        }

        sc.close();
    }
}