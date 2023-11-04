package cr.ac.una.util.graphs;

import cr.ac.una.util.collections.List;
import cr.ac.una.util.collections.SortableList;
import cr.ac.una.util.trees.RootNotNullException;
import cr.ac.una.util.trees.Tree;
import cr.ac.una.util.trees.VertexNotFoundException;

import java.util.Random;

public class MazeGraph {
    private static final int MIN_SIZE = 4;
    private final int sizeX;
    private final int sizeY;
    private final int vertexCount;
    private final Vertex<MazeVertexModel<Character>>[][] matrix;
    private final SortableList<Edge<MazeVertexModel<Character>>> edges;
    private final Tree<MazeVertexModel<Character>> maze;
    public MazeGraph() {
        this(MIN_SIZE, MIN_SIZE);
    }

    public MazeGraph(int sizeX, int sizeY) {
        matrix = new Vertex[sizeX][sizeY];
        edges = new SortableList<>();
        maze = new Tree<>();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        vertexCount = sizeX * sizeY;
        init();
    }

    private void init() {
        char var = 'A';
        for(int i = 0; i < sizeY; i++){
            for(int j = 0; j < sizeX; j++){
                Vertex<MazeVertexModel<Character>> node = new Vertex<>(new MazeVertexModel<>(true, var++, j, i));
                matrix[i][j] = node;
            }
        }

        Random r = new Random();
        for(int i = 0; i < sizeY; i++){
            for(int j = 0; j < sizeX; j++){
                Vertex<MazeVertexModel<Character>> vNext = null;
                Vertex<MazeVertexModel<Character>> vAbove = null;
                if(i != sizeY-1) {
                    vAbove = matrix[i+1][j];
                }
                if(j != sizeX-1) {
                    vNext = matrix[i][j+1];
                }
                if(vNext != null) {
                    matrix[i][j].addEdge(vNext, r.nextDouble()*10);
                }
                if(vAbove != null) {
                    matrix[i][j].addEdge(vAbove, r.nextDouble()*10);
                }
                edges.addAll(matrix[i][j].getEdges());
            }
        }
        edges.mergeSort(false);
    }

    public void generateMaze() {
        int count = 0;
        List<Edge<MazeVertexModel<Character>>> pathMaze = new List<>();
        for(var e : edges) {
            var vStart = e.getStart();
            var vEnd = e.getEnd();
            if (vStart.getInfo().hasRoom() || vEnd.getInfo().hasRoom()) {
                vStart.removeEdge(e);
                vStart.getInfo().setRoom(false);
                pathMaze.add(e);
                ++count;
            }
            if (count == vertexCount - 1) break;
        }

        /*

        FALTA PODER CREAR EL ARBOL, YA SE OBTIENE EL ARBOL MINIMO GENERADOR PERO EN FORMA DE LISTA CON LOS VERTICES DESORDENADOS

         */
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Vertex<MazeVertexModel<Character>>[][] getMatrix() {
        return matrix;
    }

    public SortableList<Edge<MazeVertexModel<Character>>> getEdges() {
        return edges;
    }

    public Tree<MazeVertexModel<Character>> getMaze() {
        return maze;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                s.append(matrix[i][j]).append("\t");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
