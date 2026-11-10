package clases.campuseats.ingesta;

import clases.campuseats.modelo.Pago;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PasarelaCsvAdapter implements FuenteDatos<Pago> {

    private final Path rutaArchivoPasarela;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public PasarelaCsvAdapter(Path rutaArchivoPasarela) {
        this.rutaArchivoPasarela = rutaArchivoPasarela;
    }

    @Override
    public List<Pago> leer() {
        List<Pago> pagos = new ArrayList<>();

        try (Stream<String> lineas = Files.lines(rutaArchivoPasarela)) {
            lineas.skip(1).forEach(linea -> {
                String[] columnas = linea.split(";", -1);

                String referencia = columnas[0].trim();
                double monto = Double.parseDouble(columnas[1].replace(',', '.').trim());
                String estadoTx = columnas[2].trim();
                String codAutorizacion = columnas[3].trim();
                String motivoRechazo = columnas[4].trim();
                LocalDateTime fechaHora = LocalDateTime.parse(columnas[5].trim(), FORMATO_FECHA);

                pagos.add(new Pago(referencia, monto, estadoTx, codAutorizacion, motivoRechazo, fechaHora));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pagos;
    }
}