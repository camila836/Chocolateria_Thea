/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Transportadoras {

    private int    idTransportadoras;
    private String nombreTransportadoras;
    private String nit;
    private String correo;
    private String telefono;   // cambiado a String para soportar formatos como +57 310...

    public Transportadoras() {}

    public Transportadoras(int idTransportadoras, String nombreTransportadoras,
                           String nit, String correo, String telefono) {
        this.idTransportadoras      = idTransportadoras;
        this.nombreTransportadoras  = nombreTransportadoras;
        this.nit                    = nit;
        this.correo                 = correo;
        this.telefono               = telefono;
    }

    public int    getIdTransportadoras()                            { return idTransportadoras; }
    public void   setIdTransportadoras(int idTransportadoras)       { this.idTransportadoras = idTransportadoras; }

    public String getNombreTransportadoras()                                    { return nombreTransportadoras; }
    public void   setNombreTransportadoras(String nombreTransportadoras)        { this.nombreTransportadoras = nombreTransportadoras; }

    public String getNit()              { return nit; }
    public void   setNit(String nit)    { this.nit = nit; }

    public String getCorreo()               { return correo; }
    public void   setCorreo(String correo)  { this.correo = correo; }

    public String getTelefono()                 { return telefono; }
    public void   setTelefono(String telefono)  { this.telefono = telefono; }

    @Override
    public String toString() {
        return "Transportadoras{id=" + idTransportadoras + ", nombre='" + nombreTransportadoras + "'}";
    }
}