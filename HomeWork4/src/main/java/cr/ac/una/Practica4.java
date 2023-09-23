package cr.ac.una;

import cr.ac.una.util.SortableList;
import cr.ac.una.util.ICollection;
import cr.ac.una.util.List;

import java.util.Random;

public class Practica4 {

    public static void main(String[] args) {
        new Practica4().init();
    }

    public void init() {
        Random r = new Random();
        int n = 8;
        int m = 1_000;

        Integer v[] = new Integer[n];
        for (int i = 0; i < n; i++) {
            v[i] = r.nextInt(m);
        }

        SortableList<Integer> L1 = new SortableList<>();
        L1.addAll(v);

        System.out.println(H1);
        System.out.printf("L1: %s%n", L1);
        System.out.println();

        System.out.println("Elementos en posición aleatoria..");
        System.out.println();
        doTests(L1);
        System.out.println(H1);

        SortableList<Integer> L2 = new SortableList<>();
        L2.addAll(L1);
        L2.sort();
        System.out.println("Elementos ordenados ascendentemente..");
        System.out.println();
        doTests(L2);
        System.out.println(H1);

        SortableList<Integer> L3 = new SortableList<>();
        L3.addAll(L1);
        L3.sort();
        reverse(L3);
        System.out.println("Elementos ordenados descendentemente..");
        System.out.println();
        doTests(L3);
        System.out.println(H1);

        System.out.println();
        System.out.println("Programa finalizado normalmente..");
    }

    private void doTests(SortableList<Integer> L1) {
        SortableList<Integer> L2 = new SortableList<>();
        L2.addAll(L1);
        test1(L2);
        System.out.println(H2);

        SortableList<Integer> L3 = new SortableList<>();
        L3.addAll(L1);
        test2(L3);
        System.out.println(H2);
    }

    private void test1(SortableList<Integer> L) {
        System.out.println(L);
        System.out.println("Insertion Sort..");
        L.insertionSort(true);
        assert (isSorted(L));
        System.out.println(L);
        System.out.println();
    }

    private void test2(SortableList<Integer> L) {
        System.out.println(L);
        System.out.println("Merge Sort..");
        L.mergeSort(true);
        assert (isSorted(L));
        System.out.println(L);
        System.out.println();
    }

    public static <T extends Comparable<T>> void reverse(List<T> L) {
        List<T> tmp = new List<>();
        while (!L.isEmpty()) {
            tmp.add(L.remove());
        }
        while (!tmp.isEmpty()) {
            L.add(tmp.remove(), 0);
        }
    }

    public static <T extends Comparable<T>> boolean isSorted(ICollection<T> collection) {
        boolean sorted = true;
        T prev = null;
        for (T e : collection) {
            if ((prev != null) && (prev.compareTo(e) > 0)) {
                sorted = false;
                break;
            }
            prev = e;
        }
        return sorted;
    }

    private static final String H1 = "=".repeat(60);
    private static final String H2 = "-".repeat(60);
}
