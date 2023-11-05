package cr.ac.una.model;

public class VInfo<T> {
    private T info;
    private Boolean room;

    public VInfo(T info) {
        this.info = info;
        this.room = true;
    }

    public T getInfo() {
        return info;
    }

    public Boolean getRoom() {
        return room;
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
