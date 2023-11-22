package cr.ac.una.util.graphs;

public class Edge<T> {

    public Edge(Vertex<T> start, Vertex<T> end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Edge(Vertex<T> start, Vertex<T> end) {
        this(start, end, 0.0);
    }

    @Override
    public String toString() {
        return String.format("{(%s, %s), %5.3f}",
                getStart().getInfo(), getEnd().getInfo(), getWeight());
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

    public boolean contains(Vertex<T> v) {
        return v.equals(start) || v.equals(end);
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    final Vertex<T> start;
    final Vertex<T> end;
    double weight;
}
