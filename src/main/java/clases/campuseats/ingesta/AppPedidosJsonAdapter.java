package clases.campuseats.ingesta;

import clases.campuseats.modelo.LineaPedido;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppPedidosJsonAdapter implements FuenteDatos<LineaPedido> {

    private final Path rutaArchivoJson;
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); 

    public AppPedidosJsonAdapter(Path rutaArchivoJson) {
        this.rutaArchivoJson = rutaArchivoJson;
    }

    @Override
    public List<LineaPedido> leer() {
        List<LineaPedido> lineasPedido = new ArrayList<>();

        try {
            PedidoJson[] pedidos = mapper.readValue(rutaArchivoJson.toFile(), PedidoJson[].class);

            for (PedidoJson pedido : pedidos) {
                LocalDateTime fechaPago = pedido.obtenerFechaPago();

                for (LineaJson linea : pedido.lineas) {
                    lineasPedido.add(new LineaPedido(
                        pedido.numeroPedido,
                        linea.platoId,
                        "", // nombrePlato (se asigna en Tarea 3)
                        linea.cantidad,
                        linea.precioUnitario,
                        pedido.estado,
                        fechaPago
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lineasPedido;
    }
}


class PedidoJson {
    public String numeroPedido;
    public String estado;
    public List<HistorialJson> historialEstados = new ArrayList<>();
    public List<LineaJson> lineas = new ArrayList<>();

    public LocalDateTime obtenerFechaPago() {
        for (HistorialJson h : historialEstados) {
            if ("PAGADO".equals(h.estado) && h.fechaHora != null) {
                return LocalDateTime.parse(h.fechaHora);
            }
        }
        return null;
    }
}

class HistorialJson {
    public String estado;
    public String fechaHora;
}

class LineaJson {
    public int platoId;
    public int cantidad;
    public double precioUnitario;
}