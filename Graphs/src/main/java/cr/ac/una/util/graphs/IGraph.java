package cr.ac.una.util.graphs;

import cr.ac.una.util.ICollection;

public interface IGraph<T> {

    int count();

    int degree();

    int indexOf(T info);

    IGraph<T> add(T info) throws Exception;

    IGraph<T> addAll(ICollection<T> vertices) throws Exception;

    IGraph<T> addAll(T... vertices) throws Exception;

    IGraph<T> remove(T info);

    ICollection<T> getVertices();

    VTuple<T> findEdge(T start, T end);

    IGraph<T> addEdge(T start, T end, double weight) throws Exception;

    IGraph<T> removeEdge(T start, T end);

    ICollection<VTuple<T>> getMatrix();

    int[][] getAdjacency();

}
