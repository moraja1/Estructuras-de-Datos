package cr.ac.una.util.graphs;

import java.util.*;

public class Graph<T> implements IGraph<T> {

    private static int instances = 0;
    private String label;
    private final List<Vertex<T>> vertices;

    public Graph(String label) {
        this.label = label;
        this.vertices = new ArrayList<>();
        instances++;
    }

    public Graph() {
        this(String.format("G%d", instances));
    }

    @Override
    public int count() {
        return vertices.size();
    }

    @Override
    public int degree() {
        int r = 0;
        for (Vertex<T> v : vertices) {
            r = Math.max(r, v.degree());
        }
        return r;
    }

    @Override
    public int indexOf(T info) {
        int p = -1;
        int i = 0;
        for (Vertex<T> v : vertices) {
            if (v.getInfo().equals(info)) {
                p = i;
                break;
            }
            i++;
        }
        return p;
    }

    public Vertex<T> findVertex(T info) {
        Vertex<T> r = null;
        for (Vertex<T> v : vertices) {
            if (v.getInfo().equals(info)) {
                r = v;
                break;
            }
        }
        return r;
    }

    @Override
    public IGraph<T> add(T info) throws Exception {
        if (info != null) {
            for (Vertex<T> v : vertices) {
                if (v.getInfo().equals(info)) {
                    throw new Exception("Duplicate vertex");
                }
            }
            vertices.add(new Vertex<>(info));
        } else {
            throw new NullPointerException();
        }
        return this;
    }

    @Override
    public IGraph<T> addAll(List<T> vertices) throws Exception {
        for (T e : vertices) {
            add(e);
        }
        return this;
    }

    @Override
    public IGraph<T> addAll(T... vertices) throws Exception {
        for (T e : vertices) {
            add(e);
        }
        return this;
    }

    @Override
    public IGraph<T> remove(T info) {
        int i = indexOf(info);
        if (i >= 0) {

            System.out.printf("Eliminando vértice: '%s'..%n", info);

            // Primero  elimina todas las referencias al nodo
            // que se va a eliminar..
            //
            for (Vertex<T> v : vertices) {
                int j = 0;
                boolean found = false;
                for (Edge<T> e : v.getEdges()) {
                    if (e.getEnd().getInfo().equals(info)) {
                        found = true;
                        break;
                    }
                    j++;
                }
                if (found) {
                    System.out.printf("\tEliminando arco: '%s'..%n",
                            v.getEdges().get(j));
                    v.removeEdge(j);
                }
            }

            // Luego se elimina el vértice de la lista..
            //
            vertices.remove(i);

            System.out.println();
        }
        return this;
    }

    @Override
    public List<T> getVertices() {
        List<T> r = new ArrayList<>();
        for (Vertex<T> v : vertices) {
            r.add(v.getInfo());
        }
        return r;
    }

    @Override
    public VTuple<T> findEdge(T start, T end) {
        VTuple<T> r = null;
        Vertex<T> v1 = findVertex(start);
        if (v1 != null) {
            for (Edge<T> e : v1.getEdges()) {
                if (e.getEnd().getInfo().equals(end)) {
                    r = new VTuple<>(start, end, e.getWeight());
                }
            }
        }
        return r;
    }

    @Override
    public IGraph<T> addEdge(T start, T end, double weight) throws Exception {
        if (findEdge(start, end) == null) {
            Vertex<T> v1 = findVertex(start);
            Vertex<T> v2 = findVertex(end);
            if ((v1 != null) && (v2 != null)) {
                v1.addEdge(v2, weight);
            } else {
                throw new NoSuchElementException();
            }
        } else {
            throw new Exception("Duplicate edge");
        }
        return this;
    }

    @Override
    public IGraph<T> removeEdge(T start, T end) {
        Vertex<T> v1 = findVertex(start);
        if (v1 != null) {
            System.out.printf("Eliminando arco: (%s, %s)..%n", start, end);

            int j = 0;
            boolean found = false;
            for (Edge<T> e : v1.getEdges()) {
                if (e.getEnd().getInfo().equals(end)) {
                    found = true;
                    break;
                }
                j++;
            }
            if (found) {
                v1.getEdges().remove(j);
            }
        }

        return this;
    }

    @Override
    public List<VTuple<T>> getMatrix() {
        List<VTuple<T>> r = new ArrayList<>();
        for (Vertex<T> v : vertices) {
            for (Edge<T> e : v.getEdges()) {
                assert (v.getInfo().equals(e.getStart().getInfo()));
                r.add(new VTuple<>(v.getInfo(), e.getEnd().getInfo(), e.getWeight()));
            }
        }
        return r;
    }

    @Override
    public int[][] getAdjacency() {
        int n = vertices.size();
        int[][] r = new int[n][n];
        int i = 0;
        for (Vertex<T> v : vertices) {
            for (Edge<T> e : v.getEdges()) {
                r[i][indexOf(e.getEnd().getInfo())] = 1;
            }
            i++;
        }
        return r;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder(String.format("%s: {", label));

        r.append(String.format("%n\tV: %s, %n\tE: [", getVertices()));
        for (VTuple<T> t : getMatrix()) {
            r.append(String.format("%n\t\t%s", t));
        }

        r.append("\n\t]\n}");
        return r.toString();
    }

    public String getLabel() {
        return label;
    }

    public List<List<T>> getCycleNodes() {
        List<List<T>> cycles = new ArrayList<>();
        //Creo cola con primer nodo (abiertos)
        Queue<Vertex<T>> opened = new ArrayDeque<>();
        opened.add(vertices.get(0));
        //Creo una pila (cerrados)
        Stack<Vertex<T>> closed = new Stack<>();
        //Invoco un metodo que reciba la cola, la pila y la lista de ciclos
        getCycleNodes(cycles, opened, closed);

        return cycles;
    }

    public List<List<T>> getCycleNodes(List<List<T>> cycles, Queue<Vertex<T>> opened, Stack<Vertex<T>> closed) {
        if (opened.isEmpty()) {
            return cycles;
        }
        var v = opened.remove();
        if (closed.contains(v)) {
            List<T> cycle = new ArrayList<>();
            var vx = closed.peek();
            int idx = closed.indexOf(vx);
            while (vx != v) {
                cycle.add(vx.getInfo());
                vx = closed.get(--idx);
            }
            cycle.add(vx.getInfo());
            cycles.add(cycle);
        } else {
            var edges = v.getEdges();
            if (edges.isEmpty()) {
                return getCycleNodes(cycles, opened, closed);
            } else {
                for (var e : edges) {
                    var vx = e.getEnd();
                    if (!vx.equals(v)) {
                        opened.add(vx);
                    }
                }
                closed.add(v);
            }
        }
        return getCycleNodes(cycles, opened, closed);
    }
}
