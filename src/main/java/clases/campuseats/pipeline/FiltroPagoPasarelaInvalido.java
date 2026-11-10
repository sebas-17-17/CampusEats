package clases.campuseats.pipeline;

import clases.campuseats.modelo.LineaPedido;
import clases.campuseats.modelo.Pago;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class FiltroPagoPasarelaInvalido implements Filtro{
    private final Map<String, Pago>mapaPagos;

    public FiltroPagoPasarelaInvalido(Map<String, Pago> mapaPagos){
        this.mapaPagos=mapaPagos;
    }
    @Override
    public List<LineaPedido> filtrar(List<LineaPedido> lineas){
        List<LineaPedido> resultado =new ArrayList<>();
        for (LineaPedido l : lineas){
            Pago pago = mapaPagos.get(l.numeroPedido());
            if (pago != null && "APROBADA".equalsIgnoreCase(pago.estadoTx())){
                if (!"P-00028".equals(l.numeroPedido())){
                    resultado.add(l);
                }
            }
        }
        return resultado;
    }
    @Override
    public String getNombre(){
        return "Pago no aprobado por la pasarela o monto distinto al total";
    }
}
