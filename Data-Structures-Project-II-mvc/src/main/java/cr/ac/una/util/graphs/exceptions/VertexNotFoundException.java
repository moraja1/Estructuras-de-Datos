package cr.ac.una.util.graphs.exceptions;

public class VertexNotFoundException extends  Exception{
    public VertexNotFoundException() {
        super("Vertex not found within the graph.");
    }
}
