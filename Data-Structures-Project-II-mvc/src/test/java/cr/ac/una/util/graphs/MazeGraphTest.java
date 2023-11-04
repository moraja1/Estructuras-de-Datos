package cr.ac.una.util.graphs;

import static org.junit.Assert.*;

public class MazeGraphTest {
    public static void main(String[] args) {
        MazeGraph maze = new MazeGraph();

        System.out.println("Before Creating Maze");
        System.out.println(maze); //Nodes Matrix
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        maze.generateMaze();
        System.out.println("After Creating Maze");
        System.out.println(maze);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Tree");
        System.out.println(maze.getMaze());
    }
}