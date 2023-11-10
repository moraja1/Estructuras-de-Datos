package cr.ac.una.model;

public interface DrawingPath {
    public boolean hasLine();
    public void setHasLine(boolean hasLine);

    public boolean isStart();

    public void setStart(boolean start);

    public boolean isEnd();
    public void setEnd(boolean end);
}
