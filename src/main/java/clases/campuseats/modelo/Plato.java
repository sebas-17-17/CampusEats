package clases.campuseats.modelo;


public record Plato(
        int idPlato, 
        String nombrePlato, 
        String categoria,
        double precioActual, 
        String estado
        ){}
