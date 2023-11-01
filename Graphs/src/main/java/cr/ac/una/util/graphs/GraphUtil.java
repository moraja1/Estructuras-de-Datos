package cr.ac.una.util.graphs;

public class GraphUtil {

    public static int[][] copyOf(int[][] a) {
        int[][] r = new int[a.length][];
        for (int i = 0; i < r.length; i++) {
            r[i] = new int[a[i].length];
            System.arraycopy(a[i], 0, r[i], 0, r[i].length);
        }
        return r;
    }

    public static int[][] add(int[][] a, int[][] b) {
        int[][] r = new int[a.length][];
        for (int i = 0; i < r.length; i++) {
            r[i] = new int[a[i].length];
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = m(a[i][j], b[i][j]);
            }
        }
        return r;
    }

    public static int[][] multiply(int[][] a, int[][] b) {
        int[][] r = new int[a.length][];
        for (int i = 0; i < r.length; i++) {
            r[i] = new int[a[i].length];
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = 0;
                for (int k = 0; k < a[i].length; k++) {
                    if ((a[i][k] != 0) && (b[k][j] != 0)) {
                        r[i][j] = m(r[i][j], a[i][k] + b[k][j]);
                    }
                }
            }
        }
        return r;
    }

    private static int m(int a, int b) {
        int r = 0;
        if ((a != 0) && (b != 0)) {
            r = Math.min(a, b);
        } else if (a == 0) {
            r = b;
        } else if (b == 0) {
            r = a;
        }
        return r;
    }

    public static String toString(int[][] a) {
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                r.append("\n");
            }
            r.append("\t");
            for (int j = 0; j < a[i].length; j++) {
                r.append(String.format("\t%s",
                        (a[i][j] > 0 ? String.valueOf(a[i][j]) : ".")));
            }
        }
        return r.toString();
    }

}
