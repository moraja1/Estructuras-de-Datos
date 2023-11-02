package cr.ac.una.util.graphs;

import cr.ac.una.util.ICollection;
import cr.ac.una.util.List;
import cr.ac.una.util.SortableList;
import cr.ac.una.util.trees.RootNotNullException;
import cr.ac.una.util.trees.Tree;
import cr.ac.una.util.trees.VertexNotFoundException;

import java.util.Random;

public class MazeGraph {
    private static final int MIN_SIZE = 4;
    private int sizeX;
    private int sizeY;
    private int edgesCount = 0;
    private final Vertex<MazeVertexModel>[][] matrix;
    private final SortableList<Edge<MazeVertexModel>> edges;
    private final Tree<Vertex<MazeVertexModel>> maze;
    public MazeGraph() {
        this(MIN_SIZE, MIN_SIZE);
    }

    public MazeGraph(int sizeX, int sizeY) {
        matrix = new Vertex[sizeX][sizeY];
        edges = new SortableList<>();
        maze = new Tree<>();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        generateMaze();
    }

    public void generateMaze() {
        createMatrix(); //Init Matrix with rando weight's edge values

        //createMaze(); //Creates a maze tree using kruskal algorithm
    }

    private void createMatrix() {
        Random r = new Random();
        char var = 'A';
        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                Vertex<MazeVertexModel> node = new Vertex<>(new MazeVertexModel(true, var++));
                matrix[i][j] = node;
            }
        }

        for(int i = 0; i < sizeY; i++){
            for(int j = 0; j < sizeX; j++){
                Vertex<MazeVertexModel> vNext = null;
                Vertex<MazeVertexModel> vAbove = null;
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

    public void createMaze() {
        ICollection<Edge<MazeVertexModel>> pathEdges = new List<>();
        for(Edge<MazeVertexModel> e : edges) {
            boolean startHasRoom = e.start.getInfo().hasRoom();
            boolean endHasRoom = e.end.getInfo().hasRoom();
            if(startHasRoom || endHasRoom) {
                ++edgesCount;
                Vertex<MazeVertexModel> vStart = e.getStart();
                int indexOf = 0;
                for(Edge<MazeVertexModel> vEdge : vStart.getEdges()) {
                    if(vEdge.equals(e)) break;
                    ++indexOf;
                }
                pathEdges.add(vStart.removeEdge(indexOf));
                vStart.getInfo().setRoom(false);
            }
            if (edgesCount == (sizeX * sizeY - 1)) break;
        }

        for(Edge<MazeVertexModel> e : pathEdges) {
            int indexOf = 0;
            for(Edge<MazeVertexModel> ex : edges) {
                if(e.equals(ex)) edges.remove(indexOf);
                ++indexOf;
            }

        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Vertex<MazeVertexModel>[][] getMatrix() {
        return matrix;
    }

    public SortableList<Edge<MazeVertexModel>> getEdges() {
        return edges;
    }

    public Tree<Vertex<MazeVertexModel>> getMaze() {
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
