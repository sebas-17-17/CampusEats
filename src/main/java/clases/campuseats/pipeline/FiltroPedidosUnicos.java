package clases.campuseats.pipeline;
import clases.campuseats.modelo.LineaPedido;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FiltroPedidosUnicos implements Filtro{
    @Override
    public List<LineaPedido> filtrar(List<LineaPedido> lineas){
        List<LineaPedido> resultado=new ArrayList<>();
        Set<String> pedidosVistos=new HashSet<>();
        String ultimoPedido = "";

        for (LineaPedido l : lineas){
            String numPedido = l.numeroPedido();
            if (!numPedido.equals(ultimoPedido)){
                if (pedidosVistos.contains(numPedido)){
                    continue;
                } else{
                    pedidosVistos.add(numPedido);
                    ultimoPedido = numPedido;
                }
            }
            resultado.add(l);
        }

        return resultado;
    }
    @Override
    public String getNombre(){
        return "Pedidos repetidos";
    }
}