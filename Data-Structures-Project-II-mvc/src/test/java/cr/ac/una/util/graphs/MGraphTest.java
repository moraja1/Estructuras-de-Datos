package cr.ac.una.util.graphs;

import org.junit.Test;

import static org.junit.Assert.*;

public class MGraphTest {

    @Test
    public void generateMaze() {
        MGraph mGraph = new MGraph();
        System.out.println("MATRIX");
        System.out.println(mGraph);
        System.out.println("\n\n\n");
        System.out.println("MINIMUM SPANNING TREE");
        System.out.println(mGraph.getMinimumSpanningTree());
        System.out.println("\n\n\n");
        System.out.println("MAZE EDGES");
        System.out.println(mGraph.getMazeEdges());

    }
}