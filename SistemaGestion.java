
package App;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaGestion {
     private static final long serialVersionUID = 1L;

    private Map<String, Instrumentos> inventario = new HashMap<>();
    private List<Prestamo> prestamos = new ArrayList<>();

    public void agregarInstrumento(Instrumentos instrumento) {
        inventario.put(instrumento.getNombreInstrumento(), instrumento);
    }

    public void eliminarInstrumento(String nombre) {
        inventario.remove(nombre);
    }

    public void actualizarInstrumento(String nombre, String estado, int cantidadDisponible) {
        Instrumentos instrumento = inventario.get(nombre);
        if (instrumento != null) {
            instrumento.setEstado(estado);
            instrumento.setCantidadDisponible(cantidadDisponible);
        }
    }

    public boolean solicitarPrestamo(String nombreInstrumento, String miembro, LocalDate fechaInicio, LocalDate fechaDevolucion) {
        Instrumentos instrumento = inventario.get(nombreInstrumento);
        if (instrumento != null && instrumento.getCantidadDisponible() > 0 && !fechaDevolucion.isBefore(fechaInicio)) {
            Prestamo prestamo = new Prestamo(instrumento, miembro, fechaInicio, fechaDevolucion);
            prestamos.add(prestamo);
            instrumento.decrementarCantidad(1);
            return true;
        }
        return false;
    }

    public void registrarDevolucion(String nombreInstrumento, String miembro) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getInstrumento().getNombreInstrumento().equals(nombreInstrumento) && prestamo.getMiembro().equals(miembro)) {
                prestamos.remove(prestamo);
                Instrumentos instrumento = inventario.get(nombreInstrumento);
                if (instrumento != null) {
                    instrumento.incrementarCantidad(1);
                }
                break;
            }
        }
    }

    public Map<String, Instrumentos> getInventario() {
        return inventario;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void guardarDatos(String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(inventario);
            oos.writeObject(prestamos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDatos(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            inventario = (Map<String, Instrumentos>) ois.readObject();
            prestamos = (List<Prestamo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
