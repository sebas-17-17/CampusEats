package clases.campuseats.pipeline;

import clases.campuseats.modelo.LineaPedido;
import java.util.ArrayList;
import java.util.List;

public class Pipeline{
    private final List<Filtro> filtros= new ArrayList<>();
    public void agregarFiltro(Filtro filtro){
        filtros.add(filtro);
    }
    public List<LineaPedido> procesar(List<LineaPedido> datosEntrada){
        List<LineaPedido> actual = datosEntrada;
        System.out.println("EJECUCION PIPELINE");
        System.out.println("Total registros iniciales= " + actual.size());
        for (Filtro filtro : filtros){
            int anterior = actual.size();
            actual = filtro.filtrar(actual);
            int descartados = anterior - actual.size();
            System.out.println("- Nuestro filtro [" + filtro.getNombre() + "] descarto: " +descartados+ " registros.");
        }
        System.out.println("Total registros limpios al final: " + actual.size());
        return actual;
    }
}