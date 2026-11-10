package clases.campuseats.modelo;

import java.time.LocalDateTime;

public record LineaPedido(
        String numeroPedido,
        int platoId,
        String nombrePlato,  // Inicia en null/vacío y se llena en el filtro de la Tarea 3
        int cantidad,
        double precioUnitario,
        String estadoPedido, 
        LocalDateTime fechaPago
        ){}

