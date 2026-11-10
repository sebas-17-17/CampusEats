package clases.campuseats.estadistica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import clases.campuseats.modelo.LineaPedido;
import clases.campuseats.modelo.Pago;

public class PedidosPorHora implements Metrica {
    @Override
    public String nombre() {
        return "Pedidos por hora";
    }

    @Override
    public void calcular(List<LineaPedido> lineasLimpias, List<Pago> registroPagos) {
        Set<String> pedidosVistos = new HashSet<>();
        Map<Integer, Integer> conteoPorHora = new HashMap<>();

        for (LineaPedido l : lineasLimpias) {
            String numPedido = l.numeroPedido();
            if (!pedidosVistos.contains(numPedido)) {
                pedidosVistos.add(numPedido);
                if (l.fechaPago() != null) {
                    int hora = l.fechaPago().getHour();
                    conteoPorHora.put(hora, conteoPorHora.getOrDefault(hora, 0) + 1);
                }
            }
        }

        List<Map.Entry<Integer, Integer>> listaOrdenada = new ArrayList<>(conteoPorHora.entrySet());
        listaOrdenada.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        for (Map.Entry<Integer, Integer> entry : listaOrdenada) {
            String horaFormateada = String.format("%02d:00", entry.getKey());
            System.out.println("• " + horaFormateada + " hs; " + entry.getValue() + " pedidos");
        }
    }
}
