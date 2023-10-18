package cr.ac.una.util;

import static org.junit.jupiter.api.Assertions.*;
class EvaluationTreeTest {

    @org.junit.jupiter.api.Test
    void isNumber() {
        EvaluationTree tree = new EvaluationTree();
        Vertex<String> v1 = new Vertex<>("1,4");
        Vertex<String> v2 = new Vertex<>("2.4");
        Vertex<String> v3 = new Vertex<>("7,3");
        Vertex<String> v4 = new Vertex<>("5.9");
        Vertex<String> v5 = new Vertex<>("4");

        System.out.println(tree.isNumber(v1));
        System.out.println(tree.isNumber(v2));
        System.out.println(tree.isNumber(v3));
        System.out.println(tree.isNumber(v4));
        System.out.println(tree.isNumber(v5));
    }
}