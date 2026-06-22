/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas ;


import Conexion.Conexion;
import java.sql.Connection;

/**
 *
 * @author Maria Camila R
 */
public class Pruebas {
    
    public static void main(String[] agrs) {
  Conexion con = new Conexion();
        
        Connection reg = con.getConn();
    }

}

