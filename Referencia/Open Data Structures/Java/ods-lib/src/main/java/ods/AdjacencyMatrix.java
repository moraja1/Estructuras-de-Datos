package ods;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a directed graph with no parallel edges
 *
 * @author morin
 *
 */
public class AdjacencyMatrix implements Graph {

    protected int n;
    protected boolean[][] a;

    /**
     * Create a new adjacency matrix with n vertices
     *
     * @param n
     */
    public AdjacencyMatrix(int n0) {
        n = n0;
        a = new boolean[n][n];
    }

    @Override
    public void addEdge(int i, int j) {
        a[i][j] = true;
    }

    @Override
    public void removeEdge(int i, int j) {
        a[i][j] = false;
    }

    @Override
    public boolean hasEdge(int i, int j) {
        return a[i][j];
    }

    @Override
    public List<Integer> outEdges(int i) {
        List<Integer> edges = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            if (a[i][j]) {
                edges.add(j);
            }
        }
        return edges;
    }

    @Override
    public List<Integer> inEdges(int i) {
        List<Integer> edges = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            if (a[j][i]) {
                edges.add(j);
            }
        }
        return edges;
    }

    @Override
    public int inDegree(int i) {
        int deg = 0;
        for (int j = 0; i < n; i++) {
            if (a[j][i]) {
                deg++;
            }
        }
        return deg;
    }

    @Override
    public int outDegree(int i) {
        int deg = 0;
        for (int j = 0; i < n; i++) {
            if (a[i][j]) {
                deg++;
            }
        }
        return deg;
    }

    @Override
    public int nVertices() {
        return n;
    }

    public static void main(String[] args) {
        for (int n = 10; n < 500; n *= 2) {
            Graph am = new AdjacencyMatrix(n);
            Graph al = new AdjacencyLists(n);
            System.out.print("Running tests on graphs of size " + n + "...");
            System.out.flush();
            Testum.graphTests(am, al);
            System.out.println("done");
        }
    }

}
