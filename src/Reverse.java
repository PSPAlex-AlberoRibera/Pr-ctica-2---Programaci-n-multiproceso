import java.util.Scanner;

public class Reverse {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String cadena = sc.nextLine();

        while (!"stop".equalsIgnoreCase(cadena.trim())) {
            String reverse = new StringBuilder(cadena).reverse().toString();
            System.out.println(reverse);
            cadena = sc.nextLine();
        }

        sc.close();
    }
}
