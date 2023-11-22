package cr.ac.una.util.graphs;

import java.util.ArrayList;
import java.util.List;

public class Vertex<T> {

    public Vertex(T info) {
        this.info = info;
        this.edges = new ArrayList<>();
    }

    public int degree() {
        return edges.size();
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

    public List<Edge<T>> listEdges() {
        return new ArrayList<>(getEdges());
    }

    List<Edge<T>> getEdges() {
        return edges;
    }

    private T info;
    private final List<Edge<T>> edges;
}
