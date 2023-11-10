package cr.ac.una.model;

import java.awt.*;

public class RectanglePaper extends Rectangle implements DrawingPath {
    private boolean hasLine;
    private boolean isStart;
    private boolean isEnd;
    public RectanglePaper(int x, int y, int width, int height) {
        super(x, y, width, height);
        hasLine = false;
        isStart = false;
        isEnd = false;
    }

    @Override
    public boolean hasLine() {
        return hasLine;
    }
    @Override
    public void setHasLine(boolean hasLine) {
        this.hasLine = hasLine;
    }
    @Override
    public boolean isStart() {
        return isStart;
    }
    @Override
    public void setStart(boolean start) {
        isStart = start;
    }
    @Override
    public boolean isEnd() {
        return isEnd;
    }
    @Override
    public void setEnd(boolean end) {
        isEnd = end;
    }

    public enum Direction {
        RIGHT(),
        LEFT(),
        UP(),
        DOWN();

        Direction() {
        }
    }
}
