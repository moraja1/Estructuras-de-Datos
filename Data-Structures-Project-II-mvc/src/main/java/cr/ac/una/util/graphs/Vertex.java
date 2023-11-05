package cr.ac.una.util.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vertex<T> {
    private T info;
    private final List<Edge<T>> edges;
    public Vertex(T info) {
        this.info = info;
        this.edges = new ArrayList<>();
    }

    public Boolean addEdge(Edge<T> edge) {
        return edges.add(edge);
    }

    public Boolean addEdge(Vertex<T> end) {
        return edges.add(new Edge<>(this, end, 0d));
    }

    public Boolean addEdge(Vertex<T> end, Double weight) {
        return edges.add(new Edge<>(this, end, weight));
    }
    public void removeEdge(Edge<T> edge) {
        edges.remove(edge);
    }
    public T getInfo() {
        return info;
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    public void setInfo(T info) {
        this.info = info;
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(info.toString() + " : [");
        for (var e : edges) {
            s.append(e.getEnd().getInfo()).append(", ");
        }
        int idx = s.lastIndexOf(",");
        s.delete(idx != -1 ? idx : s.length(), s.length());
        s.append("]");
        return s.toString();
    }
}
