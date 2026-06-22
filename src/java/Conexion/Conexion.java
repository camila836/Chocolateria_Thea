package Conexion;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Método estático corregido para obtener la conexión sin lanzar excepción
    public static Connection getConn() {
        Connection conn = null;
        String driver    = "com.mysql.cj.jdbc.Driver";
        String user      = "root";
        String password  = "";
        String baseDatos = "thea";
        String url       = "jdbc:mysql://localhost:3306/thea?useTimezone=true&serverTimezone=UTC";

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion establecida a la base de datos: " + baseDatos);
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error de conexion: " + ex.getMessage());
        }

        return conn;
    }

    private Connection conn;
    private String driver    = "com.mysql.cj.jdbc.Driver";
    private String user      = "root";
    private String password  = "";           
    private String baseDatos = "thea";
    private String url       = "jdbc:mysql://localhost:3306/thea?useTimezone=true&serverTimezone=UTC";

    public Conexion() {
        conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);

            if (conn == null) {
                System.out.println("No se establecio la conexion\n" + url);
            } else {
                System.out.println("Conexion establecida a la base de datos: " + baseDatos);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error de conexion: " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }
}