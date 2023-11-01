package cr.ac.una.util.graphs;

public class VTuple<T> {

    public VTuple(T start, T end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public VTuple(T start, T end) {
        this(start, end, 0.0);
    }

    @Override
    public String toString() {
        return String.format("{(%s, %s), %5.3f}",
                getStart(), getEnd(), getWeight());
    }

    public T getStart() {
        return start;
    }

    public T getEnd() {
        return end;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    private T start;
    private T end;
    private double weight;
}
