package cr.ac.una.util.graphs;

import cr.ac.una.util.collections.ICollection;
import cr.ac.una.util.collections.List;

import java.util.Objects;

public class Vertex<T> {
    private T info;
    private final ICollection<Edge<T>> edges;
    public Vertex(T info) {
        this.info = info;
        this.edges = new List<>();
    }

    public int degree() {
        return edges.count();
    }

    public void addEdge(Vertex<T> end, double weight) {
        edges.add(new Edge<>(this, end, weight));
    }

    Edge<T> removeEdge(Edge<T> edge) {
        boolean done = false;
        Edge<T> removed = null;
        for(int i = 0; i < edges.count() && !done; i++) {
            if(edges.get(i).equals(edge)) {
                removed = edges.remove(i);
                done = true;
            }
        }
        return removed;
    }

    Edge<T> removeEdge(int i) {
        return edges.remove(i);
    }

    @Override
    public String toString() {
        return String.format("{%s, %s}", getInfo(), edges);
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public ICollection<Edge<T>> listEdges() {
        return new List<Edge<T>>().addAll(getEdges());
    }

    ICollection<Edge<T>> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) o;
        return Objects.equals(info, vertex.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info);
    }
}
