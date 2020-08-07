package design.behavioral.visitor;

public interface IVisitor {

    String visitDot(ShapeDot shape);

    String visitCircle(ShapeCircle shape);

    String visitRectAngle(ShapeRectangle shape);

    String visitCompoundGraphic(ShapeCompound shape);

}