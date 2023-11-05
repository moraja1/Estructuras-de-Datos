package cr.ac.una.util.graphs;

import cr.ac.una.model.VInfo;
import cr.ac.una.util.graphs.exceptions.VertexNotFoundException;

import java.util.*;

public class MGraph {
    private static int instances = 0;
    private static final int MIN_SIZE = 4;
    private final int sizeX;
    private final int sizeY;
    private String label;
    private final Vertex<VInfo<Integer>>[][] MATRIX;

    private final Set<Edge<VInfo<Integer>>> EDGES;

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

        int c = 1;
        for (int i = 0; i < MATRIX.length; i++) {
            for (int j = 0; j < MATRIX[0].length; j++) {
                MATRIX[i][j] = new Vertex<>(new VInfo<>(c++, i, j));
            }
        }
        try {
            init();
        } catch (VertexNotFoundException ignored) {
        }
    }

    private void init() throws VertexNotFoundException {
        Random rdm = new Random();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (i != sizeX - 1) {
                    addEdge(MATRIX[i][j], MATRIX[i + 1][j], rdm.nextDouble() * 10);
                }
                if (j != sizeY - 1) {
                    addEdge(MATRIX[i][j], MATRIX[i][j + 1], rdm.nextDouble() * 10);
                }
            }
        }

        generateMaze();
    }

    public void generateMaze() {
        List<Edge<VInfo<Integer>>> close;
        List<Edge<VInfo<Integer>>> open = new ArrayList<>();
        close = generateMaze(open, MATRIX[0][0]);
    }

    private List<Edge<VInfo<Integer>>> generateMaze(List<Edge<VInfo<Integer>>> openAnt, Vertex<VInfo<Integer>> v) {
        List<Edge<VInfo<Integer>>> close = new ArrayList<>();
        List<Edge<VInfo<Integer>>> open = v.getEdges();
        List<Edge<VInfo<Integer>>> newClose = null;
        //Macro el vertice como revisado
        v.getInfo().setRoom(false);

        //Sorting lists
        openAnt.sort(Comparator.comparing(Edge<VInfo<Integer>>::getWeight));
        open.sort(Comparator.comparing(Edge<VInfo<Integer>>::getWeight));

        //Si la abierta anterior no esta vacia verifico los minimos
        //si la abierta anterior tiene un minimo menor hago backtrace
        if(!openAnt.isEmpty()){
            if(openAnt.get(0).getWeight() < open.get(0).getWeight()) {
                return null; //Backtrace
            }
        }

        //Obtengo el arco minimo de vertice actual siempre y cuando el arco no conecte con un nodo sin cuarto
        Edge<VInfo<Integer>> minEdge = null;
        while (minEdge == null && !open.isEmpty()) {
            minEdge = open.remove(0);
            boolean hasRoom = minEdge.getEnd().getInfo().hasRoom();
            if(!hasRoom) {
                minEdge = null;
            }
        }

        //Si todos los arcos generan un ciclo, hago backtrace
        if(minEdge == null) return null; //Backtrace

        //Si no, añado a close el minimo
        close.add(minEdge);
        open.addAll(openAnt);
        //Hago una nueva close, con el vertice final del arco minimo
        List<Edge<VInfo<Integer>>> newClose = generateMaze(open, minEdge.getEnd());
        //Si el nuevo close recibe backtrace
        if(newClose != null) {
            close.addAll(newClose);
        } else {
            System.out.println();
        }
        return null;
    }

    public boolean addEdge(Vertex<VInfo<Integer>> start, Vertex<VInfo<Integer>> end)
            throws VertexNotFoundException {
        return addEdge(start, end, 0.0);
    }

    public boolean addEdge(Vertex<VInfo<Integer>> start, Vertex<VInfo<Integer>> end, Double weight)
            throws VertexNotFoundException {
        if (!vertexExists(start) || !vertexExists(end)) {
            throw new VertexNotFoundException();
        }
        Edge<VInfo<Integer>> newEdge = new Edge<>(start, end, weight);
        boolean done = start.addEdge(end, weight);
        done &= end.addEdge(start, weight);
        done &= EDGES.add(newEdge);
        if (!done) {
            //rollback
            removeEdge(start, end, newEdge);
            return false;
        }
        return true;
    }

    public void removeEdge(Vertex<VInfo<Integer>> start, Vertex<VInfo<Integer>> end, Edge<VInfo<Integer>> edge) {
        start.removeEdge(edge);
        end.removeEdge(edge);
        EDGES.remove(edge);
    }

    @SuppressWarnings({})
    public boolean vertexExists(Vertex<VInfo<Integer>> v) {
        for (Vertex<VInfo<Integer>>[] vertices : MATRIX) {
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

    public Vertex<VInfo<Integer>>[][] getMatrix() {
        return MATRIX;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Vertex<VInfo<Integer>>[] vertices : MATRIX) {
            for (int j = 0; j < vertices.length; j++) {
                s.append(String.format("%-25s", vertices[j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
