package clases.campuseats.estadistica;

import java.util.List;

import clases.campuseats.modelo.LineaPedido;
import clases.campuseats.modelo.Pago;

public interface Metrica {
    String nombre();
    void calcular(List<LineaPedido> lineasLimpias, List<Pago> resgistroPagos);
}
