
package App;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Principal {
       private static SistemaGestion sistema = new SistemaGestion();

    public static void main(String[] args) {
        while (true) {
            String[] options = {"Agregar Instrumento", "Eliminar Instrumento", "Actualizar Instrumento", 
                                "Solicitar Préstamo", "Registrar Devolución", "Generar Reporte", "Salir"};
            int opcion = JOptionPane.showOptionDialog(null, 
                    "Selecciona una opción", 
                    "Sistema de Gestión de Préstamos", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE, 
                    null, 
                    options, 
                    options[0]);

            switch (opcion) {
                case 0:
                    agregarInstrumento();
                    break;
                case 1:
                    eliminarInstrumento();
                    break;
                case 2:
                    actualizarInstrumento();
                    break;
                case 3:
                    solicitarPrestamo();
                    break;
                case 4:
                    registrarDevolucion();
                    break;
                case 5:
                    generarReporte();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        }
    }

    private static void agregarInstrumento() {
        try {
            String nombre = JOptionPane.showInputDialog("Nombre del instrumento:");
            String grupo = JOptionPane.showInputDialog("Grupo (cuerdas, cuerdas frotadas, vientos o maderas, metales, percusión):");
            String estado = JOptionPane.showInputDialog("Estado (nuevo, usado, en reparación):");
            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad disponible:"));

            Instrumentos instrumento = new Instrumentos(nombre, grupo, estado, cantidad);
            sistema.agregarInstrumento(instrumento);
            JOptionPane.showMessageDialog(null, "Instrumento agregado.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cantidad inválida. Debe ser un número entero.");
        }
    }

    private static void eliminarInstrumento() {
        String nombre = JOptionPane.showInputDialog("Nombre del instrumento a eliminar:");
        sistema.eliminarInstrumento(nombre);
        JOptionPane.showMessageDialog(null, "Instrumento eliminado.");
    }

    private static void actualizarInstrumento() {
        try {
            String nombre = JOptionPane.showInputDialog("Nombre del instrumento a actualizar:");
            String estado = JOptionPane.showInputDialog("Nuevo estado (nuevo, usado, en reparación):");
            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Nueva cantidad disponible:"));

            sistema.actualizarInstrumento(nombre, estado, cantidad);
            JOptionPane.showMessageDialog(null, "Instrumento actualizado.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cantidad inválida. Debe ser un número entero.");
        }
    }

    private static void solicitarPrestamo() {
        try {
            String nombreInstrumento = JOptionPane.showInputDialog("Nombre del instrumento a solicitar:");
            String miembro = JOptionPane.showInputDialog("Nombre del miembro:");
            LocalDate fechaInicio = LocalDate.parse(JOptionPane.showInputDialog("Fecha de inicio (YYYY-MM-DD):"));
            LocalDate fechaDevolucion = LocalDate.parse(JOptionPane.showInputDialog("Fecha de devolución (YYYY-MM-DD):"));

            boolean exito = sistema.solicitarPrestamo(nombreInstrumento, miembro, fechaInicio, fechaDevolucion);
            if (exito) {
                JOptionPane.showMessageDialog(null, "Préstamo solicitado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Instrumento no disponible o fecha inválida.");
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Fecha inválida. Debe seguir el formato YYYY-MM-DD.");
        }
    }

    private static void registrarDevolucion() {
        String nombreInstrumento = JOptionPane.showInputDialog("Nombre del instrumento devuelto:");
        String miembro = JOptionPane.showInputDialog("Nombre del miembro que devuelve el instrumento:");
        sistema.registrarDevolucion(nombreInstrumento, miembro);
        JOptionPane.showMessageDialog(null, "Devolución registrada.");
    }

    private static void generarReporte() {
        JTextArea textoReporte = new JTextArea(20, 40);
        textoReporte.setText("Instrumentos disponibles:\n");
        for (Instrumentos instrumento : sistema.getInventario().values()) {
            textoReporte.append(instrumento.toString() + "\n");
        }

        textoReporte.append("\nHistorial de préstamos:\n");
        for (Prestamo prestamo : sistema.getPrestamos()) {
            textoReporte.append(prestamo.getMiembro() + " - " + prestamo.getInstrumento().getNombreInstrumento() +
                    " - " + prestamo.getFechaInicio() + " - " + prestamo.getFechaDevolucion() + "\n");
        }

        textoReporte.append("\nInstrumentos no devueltos a tiempo:\n");
        for (Prestamo prestamo : sistema.getPrestamos()) {
            if (prestamo.estaRetrasado()) {
                textoReporte.append(prestamo.getMiembro() + " - " + prestamo.getInstrumento().getNombreInstrumento() +
                        " - " + prestamo.getFechaDevolucion() + "\n");
            }
        }

        JOptionPane.showMessageDialog(null, new JScrollPane(textoReporte), "Reporte", JOptionPane.INFORMATION_MESSAGE);
    }
}
