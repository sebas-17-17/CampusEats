package clases.campuseats.estadistica;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import clases.campuseats.modelo.LineaPedido;
import clases.campuseats.modelo.Pago;

public class TicketPromedio implements Metrica { 
    @Override
    public String nombre() {
        return "Ticket promedio";
    }

    @Override
    public void calcular(List<LineaPedido> lineasLimpias, List<Pago> registroPagos) {
        double montoTotalRecaudado = 0.0;
        Set<String> pedidosUnicos = new HashSet<>();

        for (LineaPedido l : lineasLimpias) {
            montoTotalRecaudado += l.cantidad() * l.precioUnitario();
            pedidosUnicos.add(l.numeroPedido());
        }

        int totalPedidos = pedidosUnicos.size();
        double ticketPromedio = totalPedidos > 0 ? montoTotalRecaudado / totalPedidos : 0.0;

        System.out.println(String.format("Promedio por pedido: $%.2f dólares", ticketPromedio));
    }
}
