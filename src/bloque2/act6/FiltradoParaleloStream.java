package bloque2.act6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class FiltradoParaleloStream {

    public static void main(String[] args) {
        Path rutaDataset = Paths.get("data", "iris.arff");
        String claseObjetivo = "Iris-setosa";
        AtomicBoolean enDatos = new AtomicBoolean(false);

        try (Stream<String> lineas = Files.lines(rutaDataset)) {
            long coincidencias = lineas
                .map(String::trim)
                .filter(linea -> !linea.isEmpty() && !linea.startsWith("%"))
                .peek(linea -> {
                    if (linea.equalsIgnoreCase("@data")) {
                        enDatos.set(true);
                    }
                })
                .filter(linea -> enDatos.get() && !linea.equalsIgnoreCase("@data"))
                .parallel()
                .filter(fila -> {
                    String hilo = Thread.currentThread().getName();
                    boolean coincide = fila.endsWith(claseObjetivo);
                    if (coincide) {
                        System.out.println("[" + hilo + "] Caso válido: " + fila);
                    }
                    return coincide;
                })
                .count();

            System.out.println("Total de registros '" + claseObjetivo + "': " + coincidencias);
        } catch (IOException e) {
            System.err.println("Error en filtrado paralelo: " + e.getMessage());
        }
    }
}