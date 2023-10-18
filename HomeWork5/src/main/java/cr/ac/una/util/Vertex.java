package cr.ac.una.util;

public class Vertex<T> {
    T info;
    int count;
    Vertex<T> left;
    Vertex<T> right;

    public Vertex(T info) {
        this(info, null, null);
    }

    public Vertex(T info, Vertex<T> left, Vertex<T> right) {
        this.info = info;
        this.count = 1;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return (left == null) && (right == null);
    }
}
