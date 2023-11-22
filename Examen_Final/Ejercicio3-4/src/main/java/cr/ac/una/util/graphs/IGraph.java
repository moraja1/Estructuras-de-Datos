package cr.ac.una.util.graphs;

import java.util.List;

public interface IGraph<T> {

    int count();

    int degree();

    int indexOf(T info);

    IGraph<T> add(T info) throws Exception;

    IGraph<T> addAll(List<T> vertices) throws Exception;

    IGraph<T> addAll(T... vertices) throws Exception;

    IGraph<T> remove(T info);

    List<T> getVertices();

    VTuple<T> findEdge(T start, T end);

    IGraph<T> addEdge(T start, T end, double weight) throws Exception;

    IGraph<T> removeEdge(T start, T end);

    List<VTuple<T>> getMatrix();

    int[][] getAdjacency();

}
