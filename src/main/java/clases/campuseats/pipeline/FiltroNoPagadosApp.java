package clases.campuseats.pipeline;
import clases.campuseats.modelo.LineaPedido;
import java.util.ArrayList;
import java.util.List;

public class FiltroNoPagadosApp implements Filtro{
    @Override
    public List<LineaPedido> filtrar(List<LineaPedido> lineas){
        List<LineaPedido> resultado =new ArrayList<>();
        for (LineaPedido l : lineas){
            String estadoApp = l.estadoPedido();
            if ("PAGADO".equalsIgnoreCase(estadoApp) || "ENTREGADO".equalsIgnoreCase(estadoApp)){
                resultado.add(l);
            }
        }
        return resultado;
    }
    @Override
    public String getNombre(){
        return "Pedidos que no fueron pagados";
    }
}
