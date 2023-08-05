package ejemplo3;

import java.util.Arrays;

public class Ejemplo3 {

    public static void main(String[] args) {
        int[] a = new int[6];
        int[] b = {4, 6, 7, 8, 3};

        for (int i = 0; i < b.length; i++) {
            System.out.printf("b[%d]: %d%n", i, b[i]);
        }
        System.out.println();

        for (int x : b) {
            System.out.printf("b[] %d%n", x);
        }
        System.out.println();

        Arrays.stream(b)
                .forEach(x -> System.out.printf("b[]: %d%n", x));
        System.out.println();
    }

}
