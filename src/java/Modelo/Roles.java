/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;


public class Roles {

    private int    idRoles;
    private String descripcionRol;

    public Roles() {}

    public Roles(int idRoles, String descripcionRol) {
        this.idRoles       = idRoles;
        this.descripcionRol = descripcionRol;
    }

    public int    getIdRoles()                   { return idRoles; }
    public void   setIdRoles(int idRoles)        { this.idRoles = idRoles; }

    public String getDescripcionRol()                        { return descripcionRol; }
    public void   setDescripcionRol(String descripcionRol)   { this.descripcionRol = descripcionRol; }

    @Override
    public String toString() {
        return "Roles{id=" + idRoles + ", descripcion='" + descripcionRol + "'}";
    }
}