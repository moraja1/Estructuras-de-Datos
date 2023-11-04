package cr.ac.una.util.graphs;

import cr.ac.una.util.collections.ICollection;
import cr.ac.una.util.collections.List;

public class Vertex<T> {

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

    void removeEdge(int i) {
        edges.remove(i);
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

    private T info;
    private final ICollection<Edge<T>> edges;
}
