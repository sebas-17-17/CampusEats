package clases.campuseats.modelo;

import java.time.LocalDateTime;

public record Pago(
        String referencia, 
        double monto, 
        String estadoTx,
        String codAutorizacion, 
        String motivoRechazo, 
        LocalDateTime fechaHora
        ){}

