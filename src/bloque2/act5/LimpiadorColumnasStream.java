package bloque2.act5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LimpiadorColumnasStream {

    public static void main(String[] args) {
        Path rutaDataset = Paths.get("data", "iris.arff");
        int indiceColumnaDescartar = 2; // Ejemplo: descartar la 3.ª columna (índice 2)
        AtomicBoolean enDatos = new AtomicBoolean(false);

        try (Stream<String> lineas = Files.lines(rutaDataset)) {
            lineas
                .map(String::trim)
                .filter(linea -> !linea.isEmpty() && !linea.startsWith("%"))
                .peek(linea -> {
                    if (linea.equalsIgnoreCase("@data")) {
                        enDatos.set(true);
                    }
                })
                .filter(linea -> enDatos.get() && !linea.equalsIgnoreCase("@data"))
                .map(fila -> descartarColumna(fila, indiceColumnaDescartar))
                .limit(10) // Muestra solo 10 filas limpias para verificar sin acumular todo en memoria
                .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error procesando columnas: " + e.getMessage());
        }
    }

    private static String descartarColumna(String filaCsv, int indiceDescartar) {
        String[] columnas = filaCsv.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columnas.length; i++) {
            if (i == indiceDescartar) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(columnas[i].trim());
        }
        return sb.toString();
    }
}