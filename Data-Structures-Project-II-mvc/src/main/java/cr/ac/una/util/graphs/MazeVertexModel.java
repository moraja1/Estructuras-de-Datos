package cr.ac.una.util.graphs;

import java.util.Objects;

public class MazeVertexModel<T> {
    private boolean room;
    private T info;
    private int posX;
    private int posY;

    public MazeVertexModel(boolean room, T info, int posX, int posY) {
        this.room = room;
        this.info = info;
        this.posX = posX;
        this.posY = posY;
    }

    public boolean hasRoom() {
        return room;
    }

    public void setRoom(boolean room) {
        this.room = room;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeVertexModel<?> that = (MazeVertexModel<?>) o;
        return room == that.room && posX == that.posX && posY == that.posY && Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, info, posX, posY);
    }

    @Override
    public String toString() {
        return String.valueOf(info);
    }
}
