package ods;

import java.util.Iterator;
import java.util.List;

public class AdjacencyLists implements Graph {

    int n;

    List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public AdjacencyLists(int n0) {
        n = n0;
        adj = (List<Integer>[]) new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayStack<>(Integer.class);
        }
    }

    @Override
    public void addEdge(int i, int j) {
        adj[i].add(j);
    }

    @Override
    public boolean hasEdge(int i, int j) {
        return adj[i].contains(j);
    }

    @Override
    public int inDegree(int i) {
        int deg = 0;
        for (int j = 0; j < n; j++) {
            if (adj[j].contains(i)) {
                deg++;
            }
        }
        return deg;
    }

    @Override
    public List<Integer> inEdges(int i) {
        List<Integer> edges = new ArrayStack<>(Integer.class);
        for (int j = 0; j < n; j++) {
            if (adj[j].contains(i)) {
                edges.add(j);
            }
        }
        return edges;
    }

    @Override
    public int nVertices() {
        return n;
    }

    @Override
    public int outDegree(int i) {
        return adj[i].size();
    }

    @Override
    public List<Integer> outEdges(int i) {
        return adj[i];
    }

    @Override
    public void removeEdge(int i, int j) {
        Iterator<Integer> it = adj[i].iterator();
        while (it.hasNext()) {
            if (it.next() == j) {
                it.remove();
                return;
            }
        }
    }

    public static Graph mesh(int n) {
        Graph g = new AdjacencyLists(n * n);
        for (int k = 0; k < n * n; k++) {
            if (k % n > 0) {
                g.addEdge(k, k - 1);
            }
            if (k >= n) {
                g.addEdge(k, k - n);
            }
            if (k % n != n - 1) {
                g.addEdge(k, k + 1);
            }
            if (k < n * (n - 1)) {
                g.addEdge(k, k + n);
            }
        }
        return g;
    }

    public static void main(String[] args) {
        Graph g = mesh(4);
        Algorithms.bfsZ(g, 0);
        Algorithms.dfsZ(g, 0);
        Algorithms.dfs2Z(g, 0);

        AdjacencyMatrix.main(args);
    }

}
