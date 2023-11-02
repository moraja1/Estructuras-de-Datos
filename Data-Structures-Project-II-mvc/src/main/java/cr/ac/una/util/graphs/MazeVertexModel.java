package cr.ac.una.util.graphs;

public class MazeVertexModel {
    private boolean room;
    private char info;

    public MazeVertexModel(boolean room, char info) {
        this.room = room;
        this.info = info;
    }

    public boolean hasRoom() {
        return room;
    }

    public void setRoom(boolean room) {
        this.room = room;
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
