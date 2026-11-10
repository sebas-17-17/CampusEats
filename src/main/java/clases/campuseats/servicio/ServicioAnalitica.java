package clases.campuseats.servicio;

import clases.campuseats.estadistica.Metrica;
import clases.campuseats.modelo.LineaPedido;
import clases.campuseats.modelo.Pago;
import java.time.LocalDate;
import java.util.List;

public class ServicioAnalitica {
    private final RepoAnalitica repo;
    private final List<Metrica> metricas;

    // Inyección de dependencias por constructor (¡Pide la Tarea 5!)
    public ServicioAnalitica(RepoAnalitica repo, List<Metrica> metricas) {
        this.repo = repo;
        this.metricas = metricas;
    }

    public void cargar(List<LineaPedido> lineasLimpias, List<Pago> pagos) {
        repo.guardarLineas(lineasLimpias);
        repo.guardarPagos(pagos);
    }

    public void resumen(LocalDate desde, LocalDate hasta) {
        List<LineaPedido> lineas = repo.lineasEntre(desde, hasta);
        List<Pago> pagos = repo.pagosEntre(desde, hasta);

        System.out.println("ESTADÍSTICAS ANALIZADAS (Del " + desde + " al " + hasta + ")");
        for (Metrica m : metricas) {
            System.out.println("\n" + m.nombre() + ":");
            m.calcular(lineas, pagos);
        }
    }
}