package cr.ac.una.util.graphs;

public class Edge<T> {
    private final Vertex<T> start;
    private final Vertex<T> end;
    private double weight;
    public Edge(Vertex<T> start, Vertex<T> end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Edge(Vertex<T> start, Vertex<T> end) {
        this(start, end, 0.0);
    }

    public Vertex<T> getStart() {
        return start;
    }

    public Vertex<T> getEnd() {
        return end;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("[%s - %s]", start.getInfo(), end.getInfo());
    }
}
