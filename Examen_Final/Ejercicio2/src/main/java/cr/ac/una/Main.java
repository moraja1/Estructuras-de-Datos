package cr.ac.una;

import cr.ac.una.util.trees.TVertex;
import cr.ac.una.util.trees.Tree;
import cr.ac.una.util.trees.exceptions.RootNotNullException;
import cr.ac.una.util.trees.exceptions.VertexNotFoundException;
import org.w3c.dom.css.CSSImportRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        new Main().init();
    }

    public void init() {
        Tree<Integer> tree = new Tree<>();
        try {
            tree
                    .add(null, 1)
                    .add(1, 2)
                    .add(1, 3)
                    .add(1, 4)
                    .add(2, 5)
                    .add(2, 6)
                    .add(5, 12)
                    .add(4, 7)
                    .add(4, 8)
                    .add(7, 9)
                    .add(7, 10)
                    .add(7, 11);
        } catch (RootNotNullException | VertexNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(obtenerNivel(tree, 0));
        System.out.println(obtenerNivel(tree, 1));
        System.out.println(obtenerNivel(tree, 2));
        System.out.println(obtenerNivel(tree, 3));
    }

    private <T> List<T> obtenerNivel(Tree<T> tree, int i) {
        if(i == 0) return Arrays.asList((T) tree.getRoot().getInfo());
        if(i < 0 || i > tree.getHeight()) return new ArrayList<>();
        else {
            List<T> siblings = new ArrayList<>();
            var v = tree.getRoot().getFirstChild();
            siblings.addAll(obtenerNivel(v, i-1));
            return siblings;
        }
    }

    private <T> List<T> obtenerNivel(TVertex<T> v, int i){
        List<T> siblings = new ArrayList<>();
        if(i == 0) {
            while(v.getNextSibling() != null) {
                siblings.add(v.getInfo());
                v = v.getNextSibling();
            }
            siblings.add(v.getInfo());
        } else {
            if(v.getFirstChild() != null) {
                siblings.addAll(obtenerNivel(v.getFirstChild(), i-1));
            }
            if(v.getNextSibling() != null) {
                siblings.addAll(obtenerNivel(v.getNextSibling(), i));
            }
        }
        return siblings;
    }
}