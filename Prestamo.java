
package App;

import java.time.LocalDate;

public class Prestamo {
    private Instrumentos instrumento;
    private String miembro;
    private LocalDate fechaInicio;
    private LocalDate fechaDevolucion;

    public Prestamo(Instrumentos instrumento, String miembro, LocalDate fechaInicio, LocalDate fechaDevolucion) {
        this.instrumento = instrumento;
        this.miembro = miembro;
        this.fechaInicio = fechaInicio;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Getters y Setters
    public Instrumentos getInstrumento() { return instrumento; }
    public void setInstrumento(Instrumentos instrumento) { this.instrumento = instrumento; }

    public String getMiembro() { return miembro; }
    public void setMiembro(String miembro) { this.miembro = miembro; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public boolean estaRetrasado() {
        return LocalDate.now().isAfter(fechaDevolucion);
    }
}
