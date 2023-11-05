package cr.ac.una.util.graphs;

import cr.ac.una.model.VInfo;
import cr.ac.una.util.graphs.exceptions.VertexNotFoundException;

import java.util.Arrays;

public class MGraph {
    private static int instances = 0;
    private static final int MIN_SIZE = 4;
    private final int sizeX;
    private final int sizeY;
    private String label;
    private final Vertex<VInfo<Character>>[][] matrix;
    public MGraph() {
        this(MIN_SIZE, MIN_SIZE);
    }

    public MGraph(int sizeX, int sizeY) {
        matrix = new Vertex[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        ++instances;
        this.label = String.format("Maze #%d", instances);

        char c = 'A';
        for (Vertex<VInfo<Character>>[] vertices : matrix) {
            for (int j = 0; j < vertices.length; j++) {
                vertices[j] = new Vertex<>(new VInfo<Character>(c++));
            }
        }
    }

    public boolean addEdge(Vertex<VInfo<Character>> start, Vertex<VInfo<Character>> end) throws VertexNotFoundException {
        return addEdge(start, end, 0.0);
    }

    public boolean addEdge(Vertex<VInfo<Character>> start, Vertex<VInfo<Character>> end, Double weight) throws VertexNotFoundException {
        if(!vertexExists(start) || !vertexExists(end)) {
            throw new VertexNotFoundException();
        }
        return start.addEdge(end, weight) && end.addEdge(start, weight);
    }

    @SuppressWarnings({})
    private boolean vertexExists(Vertex<VInfo<Character>> v) {
        for (Vertex<VInfo<Character>>[] vertices : matrix) {
            for (int j = 0; j < vertices.length; j++) {
                if (v.equals(vertices[j])) return true;
            }
        }
        return false;
    }

    public static int getInstances() {
        return instances;
    }

    public String getLabel() {
        return label;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
    public Vertex<VInfo<Character>>[][] getMatrix() {
        return matrix;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Vertex<VInfo<Character>>[] vertices : matrix) {
            for (int j = 0; j < vertices.length; j++) {
                s.append(vertices[j]);
            }
            s.append("\n");
        }
        return s.toString();
    }
}
