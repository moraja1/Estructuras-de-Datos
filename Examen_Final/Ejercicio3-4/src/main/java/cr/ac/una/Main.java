package cr.ac.una;

import cr.ac.una.util.graphs.Graph;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Ejercicio3\n");
        new Main().Ejercicio3();
        System.out.println("\n\n");
        System.out.println("-".repeat(100));
        System.out.println("\n\n");
        System.out.println("Ejercicio4\n");
        new Main().Ejercicio4();
    }

    private void Ejercicio3() {
        Graph<Integer> graph = new Graph<>();
        try {
            graph
                    .add(1)
                    .add(2)
                    .add(3)
                    .add(4)
                    .add(5)
                    .add(6)
                    .add(7)
                    .add(8)
                    .add(9)
                    .add(10)
                    .add(11);
            graph
                    .addEdge(1, 2, 0)
                    .addEdge(2, 4, 0)
                    .addEdge(4, 3, 0)
                    .addEdge(4, 11, 0)
                    .addEdge(4, 5, 0)
                    .addEdge(5, 2, 0)
                    .addEdge(5, 6, 0)
                    .addEdge(6, 7, 0)
                    .addEdge(7, 8, 0)
                    .addEdge(8, 10, 0)
                    .addEdge(10, 9, 0)
                    .addEdge(9, 7, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(graph.getCycleNodes());
    }

    private void Ejercicio4() {
        Graph<Character> graph = new Graph<>();

        try {
            graph
                    .add('A')
                    .add('B')
                    .add('C')
                    .add('D')
                    .add('E')
                    .add('F')
                    .add('G')
                    .add('H');
            graph
                    .addEdge('A', 'C', 0)
                    .addEdge('A', 'B', 0)
                    .addEdge('B', 'A', 0)
                    .addEdge('B', 'C', 0)
                    .addEdge('B', 'D', 0)
                    .addEdge('C', 'A', 0)
                    .addEdge('C', 'B', 0)
                    .addEdge('C', 'D', 0)
                    .addEdge('C', 'E', 0)
                    .addEdge('D', 'B', 0)
                    .addEdge('D', 'C', 0)
                    .addEdge('E', 'C', 0)
                    .addEdge('E', 'F', 0)
                    .addEdge('F', 'E', 0)
                    .addEdge('F', 'G', 0)
                    .addEdge('F', 'H', 0)
                    .addEdge('G', 'F', 0)
                    .addEdge('G', 'H', 0)
                    .addEdge('H', 'F', 0)
                    .addEdge('H', 'G', 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        graph.printMatrix();
    }
}