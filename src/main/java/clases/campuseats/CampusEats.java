package clases.campuseats;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clases.campuseats.estadistica.Metrica;
import clases.campuseats.estadistica.PedidosPorHora;
import clases.campuseats.estadistica.PlatosMasVendidos;
import clases.campuseats.estadistica.TicketPromedio;
import clases.campuseats.ingesta.AppPedidosJsonAdapter;
import clases.campuseats.ingesta.FuenteDatos;
import clases.campuseats.ingesta.MenuCsvAdapter;
import clases.campuseats.ingesta.PasarelaCsvAdapter;
import clases.campuseats.modelo.LineaPedido;
import clases.campuseats.modelo.Pago;
import clases.campuseats.modelo.Plato;
import clases.campuseats.pipeline.FiltroCantidadValida;
import clases.campuseats.pipeline.FiltroCompletarNombrePlato;
import clases.campuseats.pipeline.FiltroNoPagadosApp;
import clases.campuseats.pipeline.FiltroPagoPasarelaInvalido;
import clases.campuseats.pipeline.FiltroPedidosUnicos;
import clases.campuseats.pipeline.Pipeline;
import clases.campuseats.servicio.RepoAnalitica;
import clases.campuseats.servicio.RepoAnaliticaEnMemoria;
import clases.campuseats.servicio.ServicioAnalitica;

public class CampusEats {
    public static void main(String[] args) {
        // 1. Leer los tres archivos usando los adaptadores (Tarea 1)
        FuenteDatos<LineaPedido> pedidosAdapter = new AppPedidosJsonAdapter(Paths.get("datos/pedidos_app.json"));
        FuenteDatos<Pago> pasarelaAdapter = new PasarelaCsvAdapter(Paths.get("datos/pagos_pasarela.csv"));
        FuenteDatos<Plato> menuAdapter = new MenuCsvAdapter(Paths.get("datos/menu_admin.csv"));

        List<LineaPedido> lineasSinFiltrar = pedidosAdapter.leer();
        List<Pago> listaPagos = pasarelaAdapter.leer();
        List<Plato> listaMenu = menuAdapter.leer();

        // 2. Mapeos necesarios para los filtros de validación
        Map<String, Pago> mapaPagos = new HashMap<>();
        for (Pago p : listaPagos) {
            mapaPagos.put(p.referencia(), p);
        }

        Map<Integer, Plato> mapaMenu = new HashMap<>();
        for (Plato pl : listaMenu) {
            mapaMenu.put(pl.idPlato(), pl);
        }

        // 3. Configurar y ejecutar el Pipeline de limpieza (Tarea 3)
        Pipeline pipeline = new Pipeline();
        pipeline.agregarFiltro(new FiltroPedidosUnicos());
        pipeline.agregarFiltro(new FiltroCantidadValida());
        pipeline.agregarFiltro(new FiltroNoPagadosApp());
        pipeline.agregarFiltro(new FiltroPagoPasarelaInvalido(mapaPagos));
        pipeline.agregarFiltro(new FiltroCompletarNombrePlato(mapaMenu));

        List<LineaPedido> lineasLimpias = pipeline.procesar(lineasSinFiltrar);
        System.out.println("Lineas totales despues del Pipeline: " + lineasLimpias.size());

        // 4. Configurar la Tarea 5: Repositorio + Facade (ServicioAnalitica)
        RepoAnalitica repo = new RepoAnaliticaEnMemoria();
        
        // Definir las métricas de la Tarea 4 (Strategy) que usará el servicio
        List<Metrica> metricas = List.of(
            new PlatosMasVendidos(),
            new PedidosPorHora(),
            new TicketPromedio()
        );

        // Instanciar la Fachada inyectándole el repositorio y las métricas (DIP)
        ServicioAnalitica servicioEstadistico = new ServicioAnalitica(repo, metricas);

        // 5. Cargar los datos limpios y pagos al repositorio a través del servicio
        servicioEstadistico.cargar(lineasLimpias, listaPagos);

        // 6. Generar el resumen de estadísticas para el rango requerido en el taller (19 al 30 de octubre de 2026)
        LocalDate desde = LocalDate.of(2026, 10, 19);
        LocalDate hasta = LocalDate.of(2026, 10, 30);
        
        servicioEstadistico.resumen(desde, hasta);
    }
}