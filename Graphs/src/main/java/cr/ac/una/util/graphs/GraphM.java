package cr.ac.una.util.graphs;

import cr.ac.una.util.Array;
import cr.ac.una.util.ICollection;
import cr.ac.una.util.List;
import java.util.NoSuchElementException;

public class GraphM<T> implements IGraph<T> {

    private final ICollection<T> vertices;
    private final int[][] matrix;

    public GraphM(ICollection<T> vertices) {
        int n = vertices.count();
        this.vertices = new Array<>(n);
        this.vertices.addAll(vertices);
        this.matrix = new int[n][n];
    }

    public GraphM(T... vertices) {
        int n = vertices.length;
        this.vertices = new Array<>(n);
        this.vertices.addAll(vertices);
        this.matrix = new int[n][n];
    }

    @Override
    public int count() {
        return vertices.count();
    }

    @Override
    public int degree() {
        int r = 0;
        for (int[] row : matrix) {
            int k = 0;
            for (int j = 0; j < row.length; j++) {
                if (row[j] > 0) {
                    k++;
                }
            }
            r = Math.max(r, k);
        }
        return r;
    }

    @Override
    public int indexOf(T info) {
        int p = -1;
        int i = 0;
        for (T v : vertices) {
            if (v.equals(info)) {
                p = i;
                break;
            }
            i++;
        }
        return p;
    }

    @Override
    public IGraph<T> add(T info) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public IGraph<T> addAll(ICollection<T> vertices) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public IGraph<T> addAll(T... vertices) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public IGraph<T> remove(T info) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ICollection<T> getVertices() {
        return new List<T>().addAll(vertices);
    }

    @Override
    public VTuple<T> findEdge(T start, T end) {
        VTuple<T> r = null;
        int i = indexOf(start);
        int j = indexOf(end);
        if ((0 <= i) && (0 <= j)) {
            if (matrix[i][j] > 0) {
                r = new VTuple<>(start, end);
            }
        }
        return r;
    }

    @Override
    public IGraph<T> addEdge(T start, T end, double weight) {
        setEdge(start, end, 1);
        return this;
    }

    public IGraph<T> addEdge(T start, T end) {
        return addEdge(start, end, 0);
    }

    @Override
    public IGraph<T> removeEdge(T start, T end) {
        setEdge(start, end, 0);
        return this;
    }

    private void setEdge(T start, T end, int value) {
        try {
            matrix[indexOf(start)][indexOf(end)] = value;
        } catch (IndexOutOfBoundsException ex) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public ICollection<VTuple<T>> getMatrix() {
        ICollection<VTuple<T>> r = new List<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > 0) {
                    r.add(new VTuple<>(
                            vertices.get(i),
                            vertices.get(j)
                    ));
                }
            }
        }
        return r;
    }

    @Override
    public int[][] getAdjacency() {
        return GraphUtil.copyOf(matrix);
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("\t");

        for (int j = 0; j < vertices.count(); j++) {
            r.append(String.format("\t%s", vertices.get(j)));
        }
        for (int i = 0; i < matrix.length; i++) {
            r.append(String.format("%n\t%s", vertices.get(i)));
            for (int j = 0; j < matrix[i].length; j++) {
                r.append(String.format("\t%s",
                        (matrix[i][j] > 0 ? "X" : ".")));
            }
        }

        return r.toString();
    }
}
