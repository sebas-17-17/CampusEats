package clases.campuseats.ingesta;

import clases.campuseats.modelo.Plato;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MenuCsvAdapter implements FuenteDatos<Plato> {
    
    private final Path rutaArchivoMenu;

    public MenuCsvAdapter(Path rutaArchivoMenu) {
        this.rutaArchivoMenu = rutaArchivoMenu;
    }

    @Override
    public List<Plato> leer() {
        List<Plato> platos = new ArrayList<>();
        
        try (Stream<String> lineas = Files.lines(rutaArchivoMenu)) {
            lineas.skip(1).forEach(linea -> {
                String[] columnas = linea.split(",");
                
                int idPlato = Integer.parseInt(columnas[0].trim()); 
                String nombrePlato = columnas[1].trim();
                String categoria = columnas[2].trim();
                double precioActual = Double.parseDouble(columnas[3].trim());
                String estado = columnas[4].trim();
                
                platos.add(new Plato(idPlato, nombrePlato, categoria, precioActual, estado));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return platos;
    } 
}
