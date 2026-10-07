
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class Ejercicio1 {

    public static void main(String[] args) {
        if (args.length <= 0) {
            System.err.println("Error: Se requiere un comando para ejecutar.");
            System.exit(-1);
        }

        ProcessBuilder pb = new ProcessBuilder(Arrays.asList(args));

        try {
            Process process = pb.start();

            // Esperar un máximo de 2 segundos a que el proceso hijo finalice
            boolean finalizado = process.waitFor(2, TimeUnit.SECONDS);

            if (!finalizado) {
                // Si pasa de 2 segundos, se muestra el mensaje de tiempo agotado y se termina
                System.err.println("Error: El tiempo de espera de 2 segundos se ha agotado.");
                process.destroyForcibly();
                System.exit(1);
            }

            // Obtener el valor de salida del proceso hijo
            int exitValue = process.exitValue();
            System.out.println("Valor de salida del proceso hijo: " + exitValue);

            if (exitValue != 0) {
                // Si el hijo sale con error, lee e imprime el mensaje de error del hijo y termina
                System.err.println("El subproceso finalizó con errores:");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.err.println(line);
                    }
                }
                System.exit(exitValue);
            } else {
                // Si termina normalmente, imprime la salida y la guarda en output.txt
                System.out.println("El subproceso terminó correctamente. Resultado:");

                StringBuilder output = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append(System.lineSeparator());
                    }
                }

                // Guardar la salida en el archivo output.txt
                File outputFile = new File("output.txt");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                    writer.write(output.toString());
                }
                System.out.println("Resultado guardado exitosamente en 'output.txt'.");
            }

        } catch (Exception e) {
            // Captura de excepciones e información al usuario
            System.err.println("Error durante la ejecución del proceso: " + e.getMessage());
        }
    }
}
