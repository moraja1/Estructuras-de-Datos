package cr.ac.una.util.graphs;

public class VInfo<T> {
    private T info;
    private Boolean room;
    private final int x;
    private final int y;

    public VInfo(T info, int x, int y) {
        this.info = info;
        this.room = true;
        this.x = x;
        this.y = y;
    }

    public T getInfo() {
        return info;
    }

    public Boolean hasRoom() {
        return room;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setInfo(T info) {
        this.info = info;
    }

    public void setRoom(Boolean room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return info.toString();
    }
}
