package clases.campuseats.servicio;

import clases.campuseats.modelo.LineaPedido;
import clases.campuseats.modelo.Pago;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepoAnaliticaEnMemoria implements RepoAnalitica {
    private List<LineaPedido> lineasAlmacenadas = new ArrayList<>();
    private List<Pago> pagosAlmacenados = new ArrayList<>();

    @Override
    public void guardarLineas(List<LineaPedido> lineas) {
        this.lineasAlmacenadas = lineas;
    }

    @Override
    public void guardarPagos(List<Pago> pagos) {
        this.pagosAlmacenados = pagos;
    }

    @Override
    public List<LineaPedido> lineasEntre(LocalDate desde, LocalDate hasta) {
        List<LineaPedido> filtradas = new ArrayList<>();
        for (LineaPedido l : lineasAlmacenadas) {
            if (l.fechaPago() != null) {
                LocalDate fechaLinea = l.fechaPago().toLocalDate();
                // Verifica si la fecha está dentro del rango inclusivo [desde, hasta]
                if (!fechaLinea.isBefore(desde) && !fechaLinea.isAfter(hasta)) {
                    filtradas.add(l);
                }
            }
        }
        return filtradas;
    }

    @Override
    public List<Pago> pagosEntre(LocalDate desde, LocalDate hasta) {
        List<Pago> filtrados = new ArrayList<>();
        for (Pago p : pagosAlmacenados) {
            if (p.fechaHora() != null) {
                LocalDate fechaPago = p.fechaHora().toLocalDate();
                if (!fechaPago.isBefore(desde) && !fechaPago.isAfter(hasta)) {
                    filtrados.add(p);
                }
            }
        }
        return filtrados;
    }
}