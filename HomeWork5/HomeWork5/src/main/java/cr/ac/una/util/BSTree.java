package cr.ac.una.util;

// Binary Search Tree
public class BSTree<T> {
    private BSTVertex<T> root;
    
    public BSTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    public int count() {
        return count(root);
    }

    public static <V> int count(BSTVertex<V> root) {
        return (root == null)
                ? 0
                : root.count + count(root.left) + count(root.right);
    }

    public T getRoot() {
        return (!isEmpty() ? root.info : null);
    }

    // -----------------------------------------------------------------
    //
    public BSTree<T> add(T info) {
        if (info != null) {
            root = add(root, info);
        } else {
            throw new NullPointerException();
        }
        return this;
    }

    private BSTVertex<T> add(BSTVertex<T> root, T info) {
        BSTVertex<T> r = root;
        if (r == null) {
            r = new BSTVertex<>(info);
        } else {
            /*

            AQUI VA EL ALGORITMO DE INSERCIÓN

             */
        }
        return r;
    }

    // -----------------------------------------------------------------
    //
}
