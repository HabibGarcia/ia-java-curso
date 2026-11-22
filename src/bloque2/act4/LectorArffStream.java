package bloque2.act4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class LectorArffStream {

    public static void main(String[] args) {
        Path rutaDataset = Paths.get("data", "iris.arff");
        AtomicBoolean enSeccionDatos = new AtomicBoolean(false);
        AtomicLong contadorInstancias = new AtomicLong(0);

        try (Stream<String> lineas = Files.lines(rutaDataset)) {
            lineas
                .map(String::trim)
                .filter(linea -> !linea.isEmpty() && !linea.startsWith("%"))
                .forEach(linea -> {
                    if (linea.equalsIgnoreCase("@data")) {
                        enSeccionDatos.set(true);
                        return;
                    }
                    if (!enSeccionDatos.get()) {
                        if (linea.toLowerCase().startsWith("@relation") || linea.toLowerCase().startsWith("@attribute")) {
                            System.out.println("[Cabecera] " + linea);
                        }
                        return;
                    }
                    long n = contadorInstancias.incrementAndGet();
                    if (n <= 5) {
                        System.out.println("[Fila " + n + "] " + linea);
                    }
                });

            System.out.println("Total de instancias procesadas en streaming: " + contadorInstancias.get());
        } catch (IOException e) {
            System.err.println("Error leyendo el dataset en " + rutaDataset.toAbsolutePath() + ": " + e.getMessage());
        }
    }
}