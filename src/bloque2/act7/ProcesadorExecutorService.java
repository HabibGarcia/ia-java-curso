package bloque2.act7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class ProcesadorExecutorService {

    private static final int TAMANO_LOTE = 25;

    public static void main(String[] args) {
        Path rutaDataset = Paths.get("data", "iris.arff");
        int numHilos = Math.max(2, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(numHilos);
        AtomicBoolean enDatos = new AtomicBoolean(false);
        List<String> loteActual = new ArrayList<>(TAMANO_LOTE);

        try (Stream<String> lineas = Files.lines(rutaDataset)) {
            lineas
                .map(String::trim)
                .filter(linea -> !linea.isEmpty() && !linea.startsWith("%"))
                .forEach(linea -> {
                    if (linea.equalsIgnoreCase("@data")) {
                        enDatos.set(true);
                        return;
                    }
                    if (!enDatos.get()) {
                        return;
                    }
                    loteActual.add(linea);
                    if (loteActual.size() == TAMANO_LOTE) {
                        List<String> loteParaEnviar = new ArrayList<>(loteActual);
                        executor.submit(() -> procesarLote(loteParaEnviar));
                        loteActual.clear();
                    }
                });

            if (!loteActual.isEmpty()) {
                List<String> ultimoLote = new ArrayList<>(loteActual);
                executor.submit(() -> procesarLote(ultimoLote));
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el dataset: " + e.getMessage());
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Procesamiento por lotes finalizado con ExecutorService (" + numHilos + " hilos).");
    }

    private static void procesarLote(List<String> lote) {
        String nombreHilo = Thread.currentThread().getName();
        double sumaPrimerAtributo = 0.0;
        int validos = 0;

        for (String fila : lote) {
            String[] partes = fila.split(",");
            if (partes.length > 0) {
                try {
                    sumaPrimerAtributo += Double.parseDouble(partes[0].trim());
                    validos++;
                } catch (NumberFormatException ignored) {
                    // Fila no numérica
                }
            }
        }

        double media = validos > 0 ? sumaPrimerAtributo / validos : 0.0;
        System.out.println("[" + nombreHilo + "] Lote de " + validos + " filas -> media col 0: " + String.format("%.4f", media));
    }
}