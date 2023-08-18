package cr.ac.una.experimento1;

public class Experimento1 {

    public static void main(String[] args) {
        new Experimento1().init();
    }

    public void init() {
        // Muestra los encabezados..
        System.out.println("-".repeat(72));
        System.out.print(" ".repeat(24));
        for (int i = 1; i <= 3; i++) {
            System.out.printf("\t%9s%d", "f", i);
        }
        System.out.println();
        System.out.println("=".repeat(72));

        // Muestra el resultado de evaluar cada una
        // de las funciones..
        for (int n = 0; n <= 12; n++) {
            System.out.printf(
                    "n = %2d, f(n) = n! = \t%10d\t%10d\t%10d%n",
                    n, f1(n), f2(n), f3(n));
        }
    }

    public long f1(int n) { // O(n)
        long r = 1;
        for (int i = 2; i <= n; i++) {
            r *= i;
        }
        return r;
    }

    // Recursión de pila (stack recursion)
    //
    public long f2(int n) { // O(n)
        // if (n <= 1) {
        //     return 1;
        // } else {
        //     return n * f2(n - 1);
        // }
        return (n <= 1) ? 1 : n * f2(n - 1);
    }

    public long f3(int n) {
        return f3(n, 1);
    }

    // Recursión de cola (tail recursion)
    // La función utiliza recursión de cola cuando la
    // invocación recursiva es la última expresión
    // que se evalúa. El resultado del llamado recursivo
    // no se usa para completar ningún cálculo pendiente.
    //
    private long f3(int n, long r) { // O(n)
        return (n <= 1) ? r : f3(n - 1, r * n);
    }

}
