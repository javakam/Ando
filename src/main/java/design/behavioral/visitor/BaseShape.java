package design.behavioral.visitor;

/**
 * 通用形状接口
 */
public abstract class BaseShape implements IShape {
    private String id;
    protected int x;
    protected int y;
    protected IVisitor visitor;

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}