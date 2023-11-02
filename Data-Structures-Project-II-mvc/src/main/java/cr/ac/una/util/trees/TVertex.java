package cr.ac.una.util.trees;

import cr.ac.una.util.Array;
import cr.ac.una.util.ICollection;
import cr.ac.una.util.List;

import java.awt.Graphics;
import java.awt.Rectangle;

public class TVertex<T> implements Printable {

    public TVertex(T info) {
        this.info = info;
        this.firstChild = null;
        this.nextSibling = null;
    }

    public int count() {
        int r = 1;
        for (TVertex<T> v : getChildren()) {
            r += v.count();
        }
        return r;
    }

    public int getOrder() {
        int r = getChildrenCount();
        for (TVertex<T> v : getChildren()) {
            r = Math.max(r, v.getOrder());
        }
        return r;
    }

    public int getHeight() {
        int r = 0;
        for (TVertex<T> v : getChildren()) {
            r = Math.max(r, v.getHeight());
        }
        return r + 1;
    }

    public TVertex<T> find(T info) {
        TVertex<T> r = null;
        if (info != null) {
            if (getInfo().equals(info)) {
                r = this;
            } else {
                if (firstChild != null) {
                    r = firstChild.find(info);
                }
                if ((r == null) && (getNextSibling() != null)) {
                    r = getNextSibling().find(info);
                }
            }
        }
        return r;
    }

    public ICollection<T> findAll() {
        ICollection<T> r = new List<>();
        r.add(getInfo());
        for (TVertex<T> v : getChildren()) {
            r.addAll(v.findAll());
        }
        return r;
    }

    public int getChildrenCount() {
        int r = 0;
        TVertex<T> current = getFirstChild();
        while (current != null) {
            r++;
            current = current.getNextSibling();
        }
        return r;
    }

    public ICollection<TVertex<T>> getChildren() {
        ICollection<TVertex<T>> vertices;

        // Se utiliza un array para guardar cada nodo
        // de modo que puedan recuperarse por medio de get()
        // en un tiempo constante.
        //
        int n = getChildrenCount();
        if (n > 0) {
            vertices = new Array<>(n);
            TVertex<T> current = getFirstChild();
            while (current != null) {
                vertices.add(current);
                current = current.getNextSibling();
            }
        } else {
            vertices = new List<>();
        }

        return vertices;
    }

    public TVertex<T> appendChild(T info) {
        if (getFirstChild() == null) {
            firstChild = new TVertex<>(info);
        } else {
            getFirstChild().addSibling(info);
        }
        return this;
    }

    void addSibling(T info) {
        if (getNextSibling() == null) {
            setNextSibling(new TVertex<>(info));
        } else {
            getNextSibling().addSibling(info);
        }
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("{");
        r.append(getInfo());
        if (getFirstChild() != null) {
            r.append(", [");
            TVertex<T> cursor = getFirstChild();
            while (cursor != null) {
                r.append(cursor.toString());
                if (cursor.getNextSibling() != null) {
                    r.append(", ");
                }
                cursor = cursor.getNextSibling();
            }
            r.append("]");
        }
        r.append("}");
        return r.toString();
    }

    public String toString(boolean indent, int level) {
        if (indent) {
            StringBuilder r = new StringBuilder();
            r.append(String.format("%s%s%n", "\t".repeat(level), getInfo()));
            if (getFirstChild() != null) {
                r.append(getFirstChild().toString(indent, level + 1));
            }
            if (getNextSibling() != null) {
                r.append(getNextSibling().toString(indent, level));
            }
            return r.toString();
        } else {
            return toString();
        }
    }

    @Override
    public void paint(Graphics g) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void paint(Graphics g, Rectangle bounds) {
        throw new UnsupportedOperationException();
    }

    public T getInfo() {
        return info;
    }

    public TVertex<T> getFirstChild() {
        return firstChild;
    }

    TVertex<T> getNextSibling() {
        return nextSibling;
    }

    void setNextSibling(TVertex<T> nextSibling) {
        this.nextSibling = nextSibling;
    }

    private final T info;
    private TVertex<T> firstChild;
    private TVertex<T> nextSibling;

}
