package cr.ac.una.util.graphs;

import cr.ac.una.util.graphs.exceptions.VertexNotFoundException;
import cr.ac.una.util.trees.Tree;
import cr.ac.una.util.trees.exceptions.RootNotNullException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MGraph {
    private static int instances = 0;
    private static final int MIN_SIZE = 4;
    private final int sizeX;
    private final int sizeY;
    private final LocalDate creationDate;
    private String label;
    private final Vertex<VInfo<Character>>[][] MATRIX;
    private final Set<Edge<VInfo<Character>>> MATRIX_EDGES;
    private final Tree<VInfo<Character>> MINIMUM_SPANNING;
    private final List<Edge<VInfo<Character>>> MAZE_EDGES;
    private final int vertexCount;

    public MGraph() {
        this(null, MIN_SIZE, MIN_SIZE);
    }

    public MGraph(String name) {
        this(name, MIN_SIZE, MIN_SIZE);
    }

    public MGraph(String name, Integer sizeX, Integer sizeY) {
        this.label = name != null ? name : String.format("Maze #%d", instances);
        this.sizeX = sizeX != null ? sizeX : MIN_SIZE;
        this.sizeY = sizeY != null ? sizeY : MIN_SIZE;
        MATRIX = new Vertex[this.sizeX][this.sizeY];
        MATRIX_EDGES = new HashSet<>();
        MINIMUM_SPANNING = new Tree<>();
        MAZE_EDGES = new LinkedList<>();
        creationDate = LocalDate.now();
        vertexCount = this.sizeX * this.sizeY;
        ++instances;


        char c = 'A';
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

    //------------------------------------------------------------------------------------------------------
    //
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

        try {
            generateMaze();
        } catch (RootNotNullException | cr.ac.una.util.trees.exceptions.VertexNotFoundException e) {
            throw new RuntimeException
                    ("Unable to create the Minimum Spanning Tree, please verify the information and try again.");
        }
    }

    public void generateMaze() throws RootNotNullException, cr.ac.una.util.trees.exceptions.VertexNotFoundException {
        MAZE_EDGES.addAll(generateMaze(new ArrayList<>(), MATRIX[0][0]));
    }

    private List<Edge<VInfo<Character>>> generateMaze(List<Edge<VInfo<Character>>> openAnt, Vertex<VInfo<Character>> v)
            throws RootNotNullException, cr.ac.una.util.trees.exceptions.VertexNotFoundException {
        List<Edge<VInfo<Character>>> close = new ArrayList<>();
        List<Edge<VInfo<Character>>> open = new ArrayList<>(v.getEdges());
        List<Edge<VInfo<Character>>> newClose = null;
        //Macro el vertice como revisado
        v.getInfo().setRoom(false);

        //Backtracking control
        while(close.size() < vertexCount - 1) {
            //Elimino los arcos que generan ciclo
            purifyEdges(open);
            for(var e : openAnt) {
                open.remove(e);
            }

            //Si no tengo arcos disponibles
            if(open.isEmpty()) return close;

            //Sorting lists
            openAnt.sort(Comparator.comparing(Edge<VInfo<Character>>::getWeight));
            open.sort(Comparator.comparing(Edge<VInfo<Character>>::getWeight));

            //Si la abierta anterior no esta vacia verifico los minimos
            //si la abierta anterior tiene un minimo menor hago backtrace
            if (!openAnt.isEmpty()) {
                if (openAnt.get(0).getWeight() < open.get(0).getWeight()) {
                    openAnt.addAll(open);
                    return close; //Backtrace
                }
            }

            //Obtengo el arco minimo de vertice actual siempre y cuando el arco no conecte con un nodo sin cuarto
            Edge<VInfo<Character>> minEdge = null;
            while (minEdge == null && !open.isEmpty()) {
                minEdge = open.remove(0);
                boolean hasRoom = minEdge.getEnd().getInfo().hasRoom();
                if (!hasRoom) {
                    minEdge = null;
                }
            }

            //Si todos los arcos generan un ciclo, hago backtrace
            if (minEdge == null) return close; //Backtrace

            //Si no, añado a close y al arbol
            close.add(minEdge);
            MINIMUM_SPANNING.add(minEdge.getStart().getInfo(), minEdge.getEnd().getInfo());
            open.addAll(openAnt);
            //Hago una nueva close, con el vertice final del arco minimo
            newClose = generateMaze(open, minEdge.getEnd());
            //Si el nuevo close recibe backtrace
            if (newClose != null) {
                close.addAll(newClose);
            }
        }
        return close;
    }

    /**
     * This method deletes from the list passed all edges that connects to a vertex with no room on it.
     * @param open
     */
    private void purifyEdges(List<Edge<VInfo<Character>>> open) {
        //Quito los arcos que no se deben usar para no generar ciclos.
        List<Edge<VInfo<Character>>> officer = new ArrayList<>(open);
        open.clear();
        for (var e : officer) {
            boolean hasRoom = e.getEnd().getInfo().hasRoom();
            if (hasRoom) {
                open.add(e);
            }
        }
    }
    //
    //------------------------------------------------------------------------------------------------------

    public boolean addEdge(Vertex<VInfo<Character>> start, Vertex<VInfo<Character>> end)
            throws VertexNotFoundException {
        return addEdge(start, end, 0.0);
    }

    public boolean addEdge(Vertex<VInfo<Character>> start, Vertex<VInfo<Character>> end, Double weight)
            throws VertexNotFoundException {
        if (!vertexExists(start) || !vertexExists(end)) {
            throw new VertexNotFoundException();
        }
        Edge<VInfo<Character>> newEdge = new Edge<>(start, end, weight);
        boolean done = start.addEdge(end, weight);
        done &= end.addEdge(start, weight);
        done &= MATRIX_EDGES.add(newEdge);
        if (!done) {
            //rollback
            removeEdge(start, end, newEdge);
            return false;
        }
        return true;
    }

    public void removeEdge(Vertex<VInfo<Character>> start, Vertex<VInfo<Character>> end, Edge<VInfo<Character>> edge) {
        start.removeEdge(edge);
        end.removeEdge(edge);
        MATRIX_EDGES.remove(edge);
    }

    @SuppressWarnings({})
    public boolean vertexExists(Vertex<VInfo<Character>> v) {
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

    public LocalDate getCreationDate() {
        return creationDate;
    }
    public String getFormattedCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return creationDate.format(formatter);
    }

    public Tree<VInfo<Character>> getMinimumSpanningTree() {
        return MINIMUM_SPANNING;
    }

    public List<Edge<VInfo<Character>>> getMazeEdges() {
        return MAZE_EDGES;
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
                s.append(String.format("%-25s", vertices[j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
