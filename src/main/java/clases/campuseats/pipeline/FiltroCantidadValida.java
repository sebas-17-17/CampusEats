package clases.campuseats.pipeline;
import clases.campuseats.modelo.LineaPedido;
import java.util.ArrayList;
import java.util.List;
public class FiltroCantidadValida implements Filtro{
    @Override
    public List<LineaPedido> filtrar(List<LineaPedido> lineas){
        List<LineaPedido> resultado =new ArrayList<>();
        for (LineaPedido l : lineas){
            if (l.cantidad() > 0){
                resultado.add(l);
            }
        }
        return resultado;
    }
    @Override
    public String getNombre(){
        return "Cantidad igual a cero";
    }
}