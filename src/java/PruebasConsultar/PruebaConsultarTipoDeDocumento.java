package PruebasConsultar;

import Controlador.TipoDocumentoDAO;
import Modelo.TipoDocumento;
import Conexion.Conexion;
import java.sql.Connection;
import java.util.Scanner;

public class PruebaConsultarTipoDeDocumento {
    public static void main(String[] args) {
        Connection conn = Conexion.getConn();
       TipoDocumentoDAO dao = new TipoDocumentoDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el ID del tipo de documento: ");
        int id = sc.nextInt();

        TipoDocumento tipo = dao.consultar(id);

        if (tipo != null) {
            System.out.println("Tipo de documento encontrado");
            System.out.println("ID: " + tipo.getIdTipoDocumento());
            System.out.println("Descripción: " + tipo.getDescripcion());
        } else {
            System.out.println("No se encontró el tipo de documento");
        }

        sc.close();
    }
}