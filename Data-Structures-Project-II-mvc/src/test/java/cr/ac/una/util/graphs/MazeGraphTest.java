package cr.ac.una.util.graphs;

import static org.junit.Assert.*;

public class MazeGraphTest {
    public static void main(String[] args) {
        MazeGraph maze = new MazeGraph();

        System.out.println("Before Creating Maze");
        System.out.println(maze); //Nodes Matrix
        System.out.println();
        System.out.println(maze.getEdges()); //Sorted Edges
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("After Creating Maze");
        maze.createMaze();
        System.out.println(maze);
        System.out.println();
        System.out.println(maze.getEdges());
        System.out.println();
        System.out.println("Maze Tree");
        System.out.println(maze.getMaze().toString(true));
    }
}