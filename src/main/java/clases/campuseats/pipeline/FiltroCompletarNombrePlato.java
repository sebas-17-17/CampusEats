package clases.campuseats.pipeline;

import clases.campuseats.modelo.LineaPedido;
import clases.campuseats.modelo.Plato;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FiltroCompletarNombrePlato implements Filtro{
    private final Map<Integer, Plato> mapaMenu;

    public FiltroCompletarNombrePlato(Map<Integer, Plato> mapaMenu){
        this.mapaMenu= mapaMenu;
    }
    @Override
    public List<LineaPedido> filtrar(List<LineaPedido> lineas){
        List<LineaPedido> resultado = new ArrayList<>();
        for (LineaPedido l : lineas){
            String nombreReal = "";
            Plato plato = mapaMenu.get(l.platoId());
            if (plato != null){
                nombreReal = plato.nombrePlato();
            }
            resultado.add(new LineaPedido(
                l.numeroPedido(),
                l.platoId(),
                nombreReal,
                l.cantidad(),
                l.precioUnitario(),
                l.estadoPedido(),
                l.fechaPago()
            )
            );
        }
        return resultado;
    }
    @Override
    public String getNombre(){
        return "Completar nombre del plato desde el menu";
    }
}
