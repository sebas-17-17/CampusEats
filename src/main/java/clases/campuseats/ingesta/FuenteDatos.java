
package clases.campuseats.ingesta;

import java.util.List;


public interface FuenteDatos<T> {
    List<T> leer();
}
