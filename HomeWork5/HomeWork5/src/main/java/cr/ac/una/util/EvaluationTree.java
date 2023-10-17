package cr.ac.una.util;

// Binary Search Tree
public class EvaluationTree{
    private Vertex<String> root;

    public EvaluationTree() {
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

    public static int count(Vertex<String> root) {
        return (root == null)
                ? 0
                : root.count + count(root.left) + count(root.right);
    }

    public String getRoot() {
        return (!isEmpty() ? root.info : null);
    }

    // -----------------------------------------------------------------
    //
    public EvaluationTree add(String info) {
        if (info != null) {
            root = add(root, info);
        } else {
            throw new NullPointerException();
        }
        return this;
    }

    private Vertex<String> add(Vertex<String> root, String info) {
        Vertex<String> r = root;
        if (root == null) {
            r = new Vertex<>(info);
        } else {
            if(r.right == null) r.right = add(r.right, info);
            else {
                if(r.right.info.matches("\\d+\\.\\d+?")) r.right = add(r.right, info);
                if(!isComplete(r.right)) r.right = add(r.right, info);
            }
        }
        return r;
    }

    // -----------------------------------------------------------------
    //
}
