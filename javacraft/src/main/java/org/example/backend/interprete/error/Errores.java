package org.example.backend.interprete.error;

/**
 * @author giovanic
 */
public class Errores {

    private TipoError tipo;
    private String descripcion;
    private int linea;
    private int columa;

    public Errores(TipoError tipo, String desc, int lineaa, int columaa) {
        this.tipo = tipo;
        this.descripcion = desc;
        this.linea = lineaa;
        this.columa = columaa;
    }

    public TipoError getTipo() {
        return tipo;
    }

    public void setTipo(TipoError tipo) {
        this.tipo = tipo;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getlinea() {
        return linea;
    }

    public void setlinea(int linea) {
        this.linea = linea;
    }

    public int getcoluma() {
        return columa;
    }

    public void setcoluma(int columa) {
        this.columa = columa;
    }

    @Override
    public String toString() {
        return "ErrorM{" +
                "tipo=" + tipo +
                ", descripcion='" + descripcion + '\'' +
                ", linea=" + linea +
                ", columa=" + columa +
                '}';
    }
}
