package cr.ac.una;

import cr.ac.una.util.EvaluationTree;

public class Main {
    public static void main(String[] args) {
        EvaluationTree tree = new EvaluationTree();
        tree.add("+");
        tree.add("-");
        tree.add("*");
        tree.add("/");
        tree.add("25");
        tree.add("4");
        /*tree.add("5");
        tree.add("+");
        tree.add("9");
        tree.add("8");
        tree.add("10");*/
        System.out.println();
    }
}