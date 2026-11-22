package bloque1.act3;

/**
 * Actividad 3: "JIT vs AOT (GraalVM)"
 * Demuestra un bucle de alta intensidad computacional para medir el calentamiento JIT (Just-In-Time).
 */
public class AnalisisJitAot {

    public static void main(String[] args) {
        System.out.println("ACTIVIDAD 3: ANÁLISIS DE RENDIMIENTO JIT (JVM)");
        System.out.println("Ejecutando bucle intensivo para observar la optimización dinámica del JIT compiler...\n");

        int iteraciones = 50_000_000;
        
        // Medimos el tiempo de inicio (Warm-up vs Steady state)
        long inicio = System.currentTimeMillis();
        
        double resultadoAcumulado = 0.0;
        for (int i = 1; i <= iteraciones; i++) {
            // Operación matemática repetitiva simulando inferencia o cálculo vectorial
            resultadoAcumulado += Math.sin(i) * Math.cos(i);
        }

        long fin = System.currentTimeMillis();
        long tiempoTotal = fin - inicio;

        System.out.println("Cálculo finalizado con éxito.");
        System.out.println("Resultado acumulado: " + resultadoAcumulado);
        System.out.println("Tiempo transcurrido en milisegundos: " + tiempoTotal + " ms");
        
        System.out.println("\n[Concepto Teórico]:");
        System.out.println("- JIT (Just-In-Time): Compila bytecode a código máquina nativo *durante* la ejecución, optimizando los bucles calientes sobre la marcha.");
        System.out.println("- AOT (Ahead-Of-Time - GraalVM): Compila todo el código a un binario nativo antes de ejecutarlo, eliminando el tiempo de arranque (cold start) de la JVM.");
    }
}