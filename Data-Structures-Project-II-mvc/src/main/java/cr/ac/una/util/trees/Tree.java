package cr.ac.una.util.trees;

import cr.ac.una.util.trees.exceptions.RootNotNullException;
import cr.ac.una.util.trees.exceptions.VertexNotFoundException;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Tree<T> implements Printable {
    private TVertex<T> root;

    public Tree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    public int count() {
        return (isEmpty()) ? 0 : root.count();
    }

    public int getOrder() {
        return (isEmpty()) ? 0 : root.getOrder();
    }

    public int getHeight() {
        return (isEmpty()) ? 0 : root.getHeight();
    }

    public TVertex<T> find(T info) {
        return (!isEmpty()) ? root.find(info) : null;
    }

    public Tree<T> add(T ancestor, T info) throws RootNotNullException, VertexNotFoundException {
        if (info != null) {
            if (ancestor == null) {
                if (root == null) {
                    root = new TVertex<>(info);
                } else {
                    throw new RootNotNullException();
                }
            } else {
                if (root != null) {
                    TVertex<T> ant = root.find(ancestor);
                    if (ant != null) {
                        ant.appendChild(info);
                    } else {
                        throw new VertexNotFoundException();
                    }
                } else {
                    add(null, ancestor);
                    add(ancestor, info);
                }
            }
        } else {
            throw new NullPointerException();
        }
        return this;
    }

    public List<T> listAll() {
        List<T> r = new ArrayList<>();
        if (!isEmpty()) {
            r.addAll(root.findAll());
        }
        return r;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean indented) {
        return (!isEmpty()) ? root.toString(indented, 0) : "{}";
    }

    @Override
    public void paint(Graphics g) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void paint(Graphics g, Rectangle bounds) {
        throw new UnsupportedOperationException();
    }
}
