package cr.ac.una.util.graphs;

import cr.ac.una.util.collections.Collection;
import cr.ac.una.util.collections.ICollection;
import cr.ac.una.util.collections.List;

import java.util.NoSuchElementException;

public class Graph<T> implements IGraph<T> {

    private static int instances = 0;
    private String label;
    private final Collection<Vertex<T>> vertices;

    public Graph(String label) {
        this.label = label;
        this.vertices = new List<>();
        instances++;
    }

    public Graph() {
        this(String.format("G%d", instances));
    }

    @Override
    public int count() {
        return vertices.count();
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
    public IGraph<T> addAll(ICollection<T> vertices) throws Exception {
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
    public ICollection<T> getVertices() {
        ICollection<T> r = new List<>();
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
    public ICollection<VTuple<T>> getMatrix() {
        ICollection<VTuple<T>> r = new List<>();
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
        int n = vertices.count();
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
}
