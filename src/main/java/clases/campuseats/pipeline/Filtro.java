package clases.campuseats.pipeline;

import clases.campuseats.modelo.LineaPedido;
import java.util.List;
public interface Filtro{
    List<LineaPedido>filtrar(List<LineaPedido> lineas);
    String getNombre();
}