package design.behavioral.visitor;

public class ShapeDot extends BaseShape {

    @Override
    public void draw() {
        System.out.println("ShapeDot draw x=" + x + "  y=" + y);
    }

    @Override
    public String accept(IVisitor visitor) {
        return visitor.visitDot(this);
    }

}