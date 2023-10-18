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
                if(isNumber(r.right)) r.left = add(r.left, info);
                else {
                    if(!isComplete(r.right)) r.right = add(r.right, info);
                    else r.left = add(r.left, info);
                }
            }
        }
        return r;
    }
    // -----------------------------------------------------------------
    //

    private boolean isComplete(Vertex<String> root) {
        if(root.left != null && isNumber(root.left)) return true;
        if(root.left != null && !isNumber(root.left)) return isComplete(root.left);
        return false;
    }

    public boolean isNumber(Vertex<String> root) {
        return root.info.matches("\\d+[\\.?,?\\d+]{0,}");
    }
    // -----------------------------------------------------------------
    //

    public String inorder() {
        return (root != null) ? inorder(root) : null;
    }

    public static String inorder(Vertex<String> root) {
        if(root != null){
            StringBuilder str = new StringBuilder();
            if(root.isLeaf()) return root.info;
            else {
                str.append("(").append(inorder(root.left)).append(" ").append(root.info).append(" ");
                str.append(inorder(root.right)).append(")");
            }
            return str.toString();
        } else {
            throw new NullPointerException();
        }
    }
}
