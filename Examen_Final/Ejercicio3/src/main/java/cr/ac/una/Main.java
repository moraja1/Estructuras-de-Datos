package cr.ac.una;

import cr.ac.una.util.graphs.Graph;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        new Main().init();
    }

    private void init() {
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
}