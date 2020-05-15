package design.behavioral.visitor;

public class ShapeCircle extends BaseShape {

    private float radius;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("ShapeCircle draw x=" + x + "  y=" + y);
    }

    @Override
    public String accept(IVisitor visitor) {
        return visitor.visitCircle(this);
    }

}