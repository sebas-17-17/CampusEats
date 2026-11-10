package clases.campuseats.servicio; // o en un package repositorio
import clases.campuseats.modelo.LineaPedido;
import clases.campuseats.modelo.Pago;
import java.time.LocalDate;
import java.util.List;

public interface RepoAnalitica {
    void guardarLineas(List<LineaPedido> lineas);
    void guardarPagos(List<Pago> pagos);
    List<LineaPedido> lineasEntre(LocalDate desde, LocalDate hasta);
    List<Pago> pagosEntre(LocalDate desde, LocalDate hasta);
}