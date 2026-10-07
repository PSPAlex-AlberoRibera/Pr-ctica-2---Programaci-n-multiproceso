
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio2 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        List<String> command = new ArrayList<>();
        command.add("java");
        command.add("Reverse");

        Process reverse = new ProcessBuilder(command).start();
        BufferedWriter entradaReverse = new BufferedWriter(new OutputStreamWriter(reverse.getOutputStream())); // Para escribir en la entrada del proceso hijo(Reverse)
        BufferedReader salidadReverse = new BufferedReader(new InputStreamReader(reverse.getInputStream())); // Para leer la salida del proceso hijo(Reverse)

        System.out.println("A partir de ahora todas las cadenas que me introduzcas y pulses\n" + //
                "enter yo les daré la vuelta, cuando termines, escribe la cadena:\n" + //
                "stop");

        String cadena = scanner.nextLine();
        while (!"stop".equalsIgnoreCase(cadena.trim())) {
            entradaReverse.write(cadena); // Escribimos la cadena en la entrada del proceso hijo(Reverse)
            entradaReverse.newLine(); // Añadimos un salto de línea para que el proceso hijo pueda leer la cadena completa
            entradaReverse.flush(); // Forzamos a que se escriba la cadena en la entrada del proceso hijo(Reverse)
            String resultado = salidadReverse.readLine(); // Leemos la salida del proceso hijo(Reverse) y la guardamos en la variable resultado
            System.out.println("La cadena dada la vuelta es: " + resultado);
            cadena=scanner.nextLine();
        }
        System.out.println("Encantado de haber jugado contigo,un saludo");
        scanner.close();
        entradaReverse.close(); // Cerramos la entrada del proceso hijo(Reverse)
        salidadReverse.close(); // Cerramos la salida del proceso hijo(Reverse)
        reverse.waitFor(); // Esperamos a que el proceso hijo(Reverse) termine

    }
}
