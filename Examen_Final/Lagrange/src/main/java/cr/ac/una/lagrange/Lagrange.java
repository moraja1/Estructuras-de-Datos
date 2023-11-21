package cr.ac.una.lagrange;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * -------------------------------------------------------------------
 *
 * (c) 2023
 *
 * @author Georges Alfaro S.
 * @version 1.0.0 2023-11-20
 *
 * --------------------------------------------------------------------
 */
//
public class Lagrange {

    public static void main(String[] args) {
        new Lagrange().init();
    }

    public void init() {
        final int M = 1_000;
        k0 = 0;
        k1 = 0;

        long t0 = System.currentTimeMillis();
        for (int n = 0; n <= M; n++) {
            solucion(n);
        }
        long et = System.currentTimeMillis() - t0;

        System.out.printf("k0:     %12s%n", FMT.format(k0));
        System.out.printf("k1:     %12s%n", FMT.format(k1));
        System.out.printf("tiempo: %9s ms.%n", FMT.format(et));
    }

    private void solucion(int n) {
        int k = 0;
        Set<Integer> solutions = new HashSet<>();

        int a2;
        for (int a = 0; (a2 = a * a) <= n; a++) {
            int b2;
            for (int b = a; (b2 = b * b) <= n - a2; b++) {
                int c2;
                for (int c = b; (c2 = c * c) <= n - (a2 + b2); c++) {
                    int d2;
                    for (int d = c; (d2 = d * d) <= n - (a2 + b2 + c2); d++) {
                        k0++;
                        if (n == (a2 + b2 + c2 + d2)) {
                            Set<Integer> solution = new HashSet<>(Arrays.asList(a, b, c, d));
                            if(!solutions.contains(solution)){
                                solutions.addAll(solution);
                                k++;
                                System.out.printf(
                                        "%2d = %2d^2 + %2d^2 + %2d^2 + %2d^2 "
                                                + "= %3d + %3d + %3d + %3d; "
                                                + "s(%2$d, %3$d, %4$d, %5$d)%n",
                                        n, a, b, c, d, a2, b2, c2, d2);
                            }
                        }
                    }
                }
            }
        }

        k1 += k;

        System.out.printf("r(%d) = %d%n", n, k);
        System.out.println();
    }

    public boolean p(int n, int a, int b, int c, int d) {
        return n == a * a + b * b + c * c + d * d;
    }

    public int r4(int n) {
        int sd = 0;
        for (int d = 1; d <= Math.sqrt(n); d++) {
            if (((d % 4) != 0) && ((n % d) == 0)) {
                sd += d;
            }
        }
        return 8 * sd;
    }

    private static final DecimalFormat FMT = new DecimalFormat("#,##0",
            DecimalFormatSymbols.getInstance(Locale.US));
    private int k0;
    private int k1;
}
