package clases.campuseats.ingesta;

import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;

public class FabricaFuentes {

    private static final Map<String, Function<Path, FuenteDatos<?>>> mapaFuentes = Map.of(
        "menu_csv", MenuCsvAdapter::new,
        "pagos_csv", PasarelaCsvAdapter::new,
        "app_json", AppPedidosJsonAdapter::new
    );

    public static FuenteDatos<?> crearFuente(String tipo, String ruta) {
        Function<Path, FuenteDatos<?>> creador = mapaFuentes.get(tipo);
        if (creador == null) {
            throw new IllegalArgumentException(tipo + " No es una fuente valida");
        }
        return creador.apply(Path.of(ruta));
    }
}