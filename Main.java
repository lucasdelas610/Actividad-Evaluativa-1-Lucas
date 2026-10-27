import java.util.Scanner;
 
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
 
        Servidor s1 = new Servidor(); // Datos del primer servidor
        System.out.println("Servidor 1");
        System.out.print("Nombre: ");
        s1.nombre = sc.nextLine();
        System.out.print("Origen (interno/externo): ");
        s1.origen = sc.nextLine();
        System.out.print("Critico (si/no): ");
        s1.critico = sc.nextLine();
        System.out.print("Intentos totales: ");
        s1.intentosTotales = sc.nextInt();
        System.out.print("Intentos fallidos: ");
        s1.intentosFallidos = sc.nextInt();
        sc.nextLine(); // esto es para quitar el enter que se queda después del nextInt
 
        Servidor s2 = new Servidor(); // Segundo servidor, es otro objeto distinto al primero
        System.out.println("Servidor 2");
        System.out.print("Nombre: ");
        s2.nombre = sc.nextLine();
        System.out.print("Origen (interno/externo): ");
        s2.origen = sc.nextLine();
        System.out.print("Critico (si/no): ");
        s2.critico = sc.nextLine();
        System.out.print("Intentos totales: ");
        s2.intentosTotales = sc.nextInt();
        System.out.print("Intentos fallidos: ");
        s2.intentosFallidos = sc.nextInt();
 
        System.out.println();
        System.out.println("REPORTE DE SEGURIDAD");
        s1.mostrarResumen();
        s2.mostrarResumen();
 
        int nivel1 = s1.nivelRiesgo(); // Aquí comparo los dos servidores con el número de nivelRiesgo
        int nivel2 = s2.nivelRiesgo();
        if (nivel1 == 0 || nivel2 == 0) {
            System.out.println("COMPARACION NO DISPONIBLE");
        } else if (nivel1 > nivel2) {
            System.out.println("ATENCION PRIORITARIA: " + s1.nombre);
        } else if (nivel2 > nivel1) {
            System.out.println("ATENCION PRIORITARIA: " + s2.nombre);
        } else {
            System.out.println("IGUAL PRIORIDAD");
        }
 
        sc.close();
    }
}
class Servidor {
    String nombre;
    String origen;
    String critico;
    int intentosTotales;
    int intentosFallidos;
 
    void mostrarResumen() {
        if (datosValidos()) {
            System.out.println(nombre + " | " + intentosFallidos + "/" + intentosTotales + " | " + porcentajeFallos() + "% | " + evaluarEstado());
        } else { // si los datos están mal no divido, solo pongo N/D
            System.out.println(nombre + " | " + intentosFallidos + "/" + intentosTotales + " | N/D | " + evaluarEstado());
        }
    }
    boolean datosValidos() { // Aquí se validan las reglas del apartado 3 (equalsIgnoreCase ignora mayúsculas)
        if (!origen.equalsIgnoreCase("interno") && !origen.equalsIgnoreCase("externo")) {
            return false;
        }
        if (!critico.equalsIgnoreCase("si") && !critico.equalsIgnoreCase("no")) {
            return false;
        }
        if (intentosTotales <= 0) {
            return false;
        }
        if (intentosFallidos > intentosTotales) {
            return false;
        }
        return true;
    }
    String evaluarEstado() { // Se mira el estado en orden, primero inválido, luego BLOQUEADO, ALERTA y por último NORMAL
        if (!datosValidos()) {
            return "DATOS INVALIDOS";
        }
        boolean esExterno = origen.equalsIgnoreCase("externo");
        boolean esCritico = critico.equalsIgnoreCase("si");
 
        if (intentosFallidos >= 5 || (esCritico && esExterno && intentosFallidos >= 3)) {
            return "BLOQUEADO";
        }
        if (intentosFallidos >= 3 || (esExterno && intentosFallidos >= 2)) {
            return "ALERTA";
        }
        return "NORMAL";
    }
    double porcentajeFallos() {
        return intentosFallidos * 100.0 / intentosTotales; // pongo 100.0 para que salgan decimales y no se quede en entero
    }
    int nivelRiesgo() { // Paso el estado a número para luego poder comparar los dos servidores (más alto = más prioridad)
        String estado = evaluarEstado();
        if (estado.equals("BLOQUEADO")) {
            return 3;
        } else if (estado.equals("ALERTA")) {
            return 2;
        } else if (estado.equals("NORMAL")) {
            return 1;
        }
        return 0; // esto es cuando los datos son inválidos
    }
}
