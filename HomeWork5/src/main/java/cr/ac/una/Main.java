package cr.ac.una;

import cr.ac.una.util.EvaluationTree;

public class Main {
    public static void main(String[] args) {
        EvaluationTree tree = new EvaluationTree();
        tree.add("+");
        tree.add("1.4");
        tree.add("+");
        tree.add("*");
        tree.add("5");
        tree.add("4");
        tree.add("*");
        tree.add("3");
        tree.add("2");
        System.out.println(tree.inorder());
    }
}