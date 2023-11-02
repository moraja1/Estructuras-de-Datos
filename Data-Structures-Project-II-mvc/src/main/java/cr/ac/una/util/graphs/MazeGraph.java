package cr.ac.una.util.graphs;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

public class MazeGraph {
    private static final int MIN_SIZE = 4;
    private int sizeX;
    private int sizeY;
    private final Vertex<MazeVertexModel>[][] matrix;

    public MazeGraph() {
        this(MIN_SIZE, MIN_SIZE);
    }

    public MazeGraph(int sizeX, int sizeY) {
        matrix = new Vertex[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        generateMaze();
    }

    public void generateMaze() {
        createMatrix(); //Inicializa la matrix con aristas de peso aleatorio



        System.out.println(this);
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
            }
        }
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
