package cr.ac.una.util;

public class BSTVertex<T> {
    T info;
    int count;
    BSTVertex<T> left;
    BSTVertex<T> right;

    public BSTVertex(T info) {
        this(info, null, null);
    }

    public BSTVertex(T info, BSTVertex<T> left, BSTVertex<T> right) {
        this.info = info;
        this.count = 1;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return (left == null) && (right == null);
    }
}
