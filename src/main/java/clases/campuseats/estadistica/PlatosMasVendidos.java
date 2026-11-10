package clases.campuseats.estadistica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clases.campuseats.modelo.LineaPedido;
import clases.campuseats.modelo.Pago;

public class PlatosMasVendidos implements Metrica {
     @Override
    public String nombre() {
        return "Platos más vendidos";
    }

    @Override
    public void calcular(List<LineaPedido> lineasLimpias, List<Pago> registroPagos) {
        Map<String, Integer> conteoPlatos = new HashMap<>();

        for (LineaPedido l : lineasLimpias) {
            String nombre = l.nombrePlato();
            int cantidad = l.cantidad();
            conteoPlatos.put(nombre, conteoPlatos.getOrDefault(nombre, 0) + cantidad);
        }

        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(conteoPlatos.entrySet());
        listaOrdenada.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        int limite = Math.min(5, listaOrdenada.size());
        for (int i = 0; i < limite; i++) {
            Map.Entry<String, Integer> entry = listaOrdenada.get(i);
            System.out.println("• " + entry.getKey() + ": " + entry.getValue() + " unidades");
        }
    }
}