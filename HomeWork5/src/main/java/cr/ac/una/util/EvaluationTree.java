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
            if(root.isLeaf()) return String.valueOf(Double.parseDouble(root.info));
            else {
                str.append("(").append(inorder(root.left)).append(" ").append(root.info).append(" ");
                str.append(inorder(root.right)).append(")");
            }
            return str.toString();
        } else {
            throw new NullPointerException();
        }
    }
    //------------------------------------------------------------------
    //
    public String evaluate() {
        return (root != null) ? evaluate(root): null;
    }

    private String evaluate(Vertex<String> root) {
        if(root != null){
            StringBuilder str = new StringBuilder();
            if(!root.isLeaf()) {
                if (isNumber(root.left) && isNumber(root.right)) {
                    double left = Double.parseDouble(root.left.info);
                    double right = Double.parseDouble(root.right.info);
                    double result = 0;
                    switch (root.info) {
                        case "+":
                            result = left + right;
                            break;
                        case "-":
                            result = left - right;
                            break;
                        case "*":
                            result = left * right;
                            break;
                        case "/":
                            result = left / right;
                            break;
                        case "^":
                            result = left;
                            for (int i = 1; i < right; i++) {
                                result *= left;
                            }
                            break;
                        default:
                            break;
                    }
                    root.left = null;
                    root.right = null;
                    root.info = String.valueOf(result);
                    return str.append(inorder()).toString();
                } else {
                    if (isNumber(root.right)) {
                        str.append(evaluate(root.left));
                    } else {
                        str.append(evaluate(root.right));
                    }
                }
            }
            return str.toString();
        } else {
            throw new NullPointerException();
        }
    }

    //------------------------------------------------------------------
    //
    @Override
    public String toString() {
        return String.format("%s", (!isEmpty()) ? root.toString() : "{}");
    }

    public String toString(boolean indented) {
        return ((root != null) && indented)
                ? toString(new StringBuilder(), root, 0).toString()
                : toString();
    }

    private StringBuilder toString(StringBuilder sb, Vertex<String> root, int level) {
        if (root != null) {
            sb.append(String.format("%s%s", "\t".repeat(level), root.info));
            if ((root.left != null) || (root.right != null)) {
                toString(sb.append("\n"), root.left, level + 1);
                toString(sb.append("\n"), root.right, level + 1);
            }
        } else {
            sb.append(String.format("%s%s", "\t".repeat(level), "_"));
        }
        return sb;
    }
}
