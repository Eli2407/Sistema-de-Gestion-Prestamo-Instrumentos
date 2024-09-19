
package App;

public class Instrumentos {
    private String nombreInstrumento;
    private String grupo;
    private String estado;
    private int cantidadDisponible;

    public Instrumentos(String nombreInstrumento, String grupo, String estado, int cantidadDisponible) {
        this.nombreInstrumento = nombreInstrumento;
        this.grupo = grupo;
        this.estado = estado;
        this.cantidadDisponible = cantidadDisponible;
    }


    // Getters y Setters
    public String getNombreInstrumento() { return nombreInstrumento; }
    public void setNombreInstrumento(String nombre) { this.nombreInstrumento = nombre; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(int cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }

    public void incrementarCantidad(int cantidad) {
        this.cantidadDisponible += cantidad;
    }

    public void decrementarCantidad(int cantidad) {
        this.cantidadDisponible -= cantidad;
    }
}

