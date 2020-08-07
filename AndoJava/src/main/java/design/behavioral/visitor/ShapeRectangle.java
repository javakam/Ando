package design.behavioral.visitor;

public class ShapeRectangle extends BaseShape {

    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println("ShapeRectangle draw x=" + x + "  y=" + y);
    }

    @Override
    public String accept(IVisitor visitor) {
        return visitor.visitRectAngle(this);
    }

}