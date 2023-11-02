package cr.ac.una.util.graphs;

public class MazeVertexModel {
    private boolean hasRoom;
    private char info;

    public MazeVertexModel(boolean hasRoom, char info) {
        this.hasRoom = hasRoom;
        this.info = info;
    }

    public boolean isHasRoom() {
        return hasRoom;
    }

    public void setHasRoom(boolean hasRoom) {
        this.hasRoom = hasRoom;
    }

    public char getInfo() {
        return info;
    }

    public void setInfo(char info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return String.valueOf(info);
    }
}
