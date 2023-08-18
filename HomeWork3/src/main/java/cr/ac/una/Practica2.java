package cr.ac.una;

import java.math.BigInteger;

public class Practica2 {

    public static void main(String[] args) {
        new Practica2().init();
    }

    public void init() {
        // Muestra los encabezados..
        System.out.println("-".repeat(72));
        System.out.print(" ".repeat(16));
        for (int i = 1; i <= 2; i++) {
            System.out.printf("\t%19s%d", "f", i);
        }
        System.out.println();
        System.out.println("=".repeat(72));

        // Muestra el resultado de evaluar cada una
        // de las funciones..
        for (int n = 0; n <= 10; n++) {
            System.out.printf("n = %2d, F(n) = %s\t%20s%n",
                    n, f1(n), f2(n));
        }
    }

    public BigInteger f1(int n) {
        if (n <= 1) {
            return new BigInteger(String.valueOf(n));
        } else {
            BigInteger lastRes1 = new BigInteger("0");
            BigInteger lastRes2 = new BigInteger("1");
            BigInteger r = new BigInteger("0");
            for (int i = 1; i < n; i++) {
                r = lastRes1.add(lastRes2);
                if(!lastRes1.equals(BigInteger.ZERO)){
                    lastRes2 = lastRes1;
                }
                lastRes1 = r;
            }
            return r;
        }
    }

    public BigInteger f2(int n) { // O(2^n)
        return f2(n, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE);
    }

    public BigInteger f2(int n, BigInteger r, BigInteger lastRes1, BigInteger lastRes2){
        if(n <= 1) return r;
        if(r.equals(BigInteger.ZERO)) r.add(BigInteger.ONE);
        return f2(n-1, lastRes1.add(lastRes2), lastRes1.add(lastRes2),
                !lastRes1.equals(BigInteger.ZERO) ? lastRes1 : lastRes2);
    }

}
