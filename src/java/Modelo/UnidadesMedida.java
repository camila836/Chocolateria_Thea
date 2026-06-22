package Modelo;

public class UnidadesMedida {

    private int    idUnidadesMedida;
    private String descripcionUnidadesMed;

    public UnidadesMedida() {}

    public UnidadesMedida(int idUnidadesMedida, String descripcionUnidadesMed) {
        this.idUnidadesMedida       = idUnidadesMedida;
        this.descripcionUnidadesMed = descripcionUnidadesMed;
    }

    public int    getIdUnidadesMedida()                         { return idUnidadesMedida; }
    public void   setIdUnidadesMedida(int idUnidadesMedida)     { this.idUnidadesMedida = idUnidadesMedida; }

    public String getDescripcionUnidadesMed()                               { return descripcionUnidadesMed; }
    public void   setDescripcionUnidadesMed(String descripcionUnidadesMed)  { this.descripcionUnidadesMed = descripcionUnidadesMed; }

    @Override
    public String toString() {
        return "UnidadesMedida{id=" + idUnidadesMedida + ", descripcion='" + descripcionUnidadesMed + "'}";
    }
}