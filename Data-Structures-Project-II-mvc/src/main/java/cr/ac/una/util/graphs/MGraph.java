package cr.ac.una.util.graphs;

import cr.ac.una.Main;
import cr.ac.una.model.VInfo;
import cr.ac.una.util.graphs.exceptions.VertexNotFoundException;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MGraph {
    private static int instances = 0;
    private static final int MIN_SIZE = 4;
    private final int sizeX;
    private final int sizeY;
    private String label;
    private final Vertex<VInfo<Character>>[][] MATRIX;

    private final Set<Edge<VInfo<Character>>> EDGES;
    public MGraph() {
        this(MIN_SIZE, MIN_SIZE);
    }

    public MGraph(int sizeX, int sizeY) {
        MATRIX = new Vertex[sizeX][sizeY];
        EDGES = new HashSet<>();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        ++instances;
        this.label = String.format("Maze #%d", instances);

        char c = 'A';
        for (Vertex<VInfo<Character>>[] vertices : MATRIX) {
            for (int j = 0; j < vertices.length; j++) {
                vertices[j] = new Vertex<>(new VInfo<Character>(c++));
            }
        }

        try {
            init();
        } catch (VertexNotFoundException ignored) {}
    }

    private void init() throws VertexNotFoundException {
        Random rdm = new Random();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if(i != sizeX - 1) {
                    addEdge(MATRIX[i][j], MATRIX[i + 1][j], rdm.nextDouble());
                }
                if(j != sizeY - 1) {
                    addEdge(MATRIX[i][j], MATRIX[i][j + 1], rdm.nextDouble());
                }
            }
        }
    }

    public boolean addEdge(Vertex<VInfo<Character>> start, Vertex<VInfo<Character>> end)
            throws VertexNotFoundException {
        return addEdge(start, end, 0.0);
    }

    public boolean addEdge(Vertex<VInfo<Character>> start, Vertex<VInfo<Character>> end, Double weight)
            throws VertexNotFoundException {
        if(!vertexExists(start) || !vertexExists(end)) {
            throw new VertexNotFoundException();
        }
        Edge<VInfo<Character>> newEdge = new Edge<>(start, end, weight);
        boolean done = start.addEdge(end, weight);
        done &= end.addEdge(start, weight);
        done &= EDGES.add(newEdge);
        if(!done) {
            //rollback
            start.removeEdge(newEdge);
            end.removeEdge(newEdge);
            EDGES.remove(newEdge);
            return false;
        }
        return true;
    }

    @SuppressWarnings({})
    private boolean vertexExists(Vertex<VInfo<Character>> v) {
        for (Vertex<VInfo<Character>>[] vertices : MATRIX) {
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
        return MATRIX;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Vertex<VInfo<Character>>[] vertices : MATRIX) {
            for (int j = 0; j < vertices.length; j++) {
                s.append(String.format("%-20s", vertices[j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
