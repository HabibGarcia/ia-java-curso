package bloque1.act2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Actividad 2: "Tipado Fuerte y Colecciones Estructuradas"
 * Explora la declaración estricta de tipos y el uso de colecciones genéricas.
 */
public class TipadoYColecciones {

    public static void main(String[] args) {
        System.out.println("ACTIVIDAD 2: TIPADO Y COLECCIONES");

        // 1. Tipado fuerte: Las variables declaran su tipo estáticamente
        int totalInstancias = 1500;
        double tasaAprendizaje = 0.01;
        boolean modeloEntrenado = false;
        String nombreModelo = "PerceptronSimulado";

        System.out.println("Modelo: " + nombreModelo + " | Registros: " + totalInstancias);

        // 2. Colección estructurada tipada: Listas (ArrayList)
        // Simulamos un vector de características (features) o pesos numéricos
        List<Double> pesosSinapticos = new ArrayList<>();
        pesosSinapticos.add(0.45);
        pesosSinapticos.add(-0.12);
        pesosSinapticos.add(0.89);

        System.out.println("\nPesos sinápticos iniciales (Lista):");
        for (int i = 0; i < pesosSinapticos.size(); i++) {
            System.out.println("  Peso [" + i + "] = " + pesosSinapticos.get(i));
        }

        // 3. Diccionarios o Mapas estructurados clave-valor (HashMap)
        // Útil para almacenar hiperparámetros o diccionarios de métricas de ML
        Map<String, Object> hiperparametros = new HashMap<>();
        hiperparametros.put("epochs", 100);
        hiperparametros.put("optimizer", "GradientDescent");
        hiperparametros.put("learningRate", tasaAprendizaje);
        hiperparametros.put("activo", modeloEntrenado);

        System.out.println("\nHiperparámetros del Modelo (Mapa clave-valor):");
        // Iterando el mapa de forma moderna (Java 8+)
        hiperparametros.forEach((clave, valor) -> {
            System.out.println("  - " + clave + ": " + valor + " (Tipo: " + valor.getClass().getSimpleName() + ")");
        });
    }
}