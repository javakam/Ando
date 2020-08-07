package design.behavioral.visitor;

import java.util.ArrayList;
import java.util.List;

public class ShapeCompound extends BaseShape {

    private final List<IShape> mChildrenShapes = new ArrayList<IShape>();

    @Override
    public void draw() {
    }

    @Override
    public String accept(IVisitor visitor) {
        return visitor.visitCompoundGraphic(this);
    }

    public void add(IShape shape) {
        mChildrenShapes.add(shape);
    }

    public List<IShape> getChildrenShapes() {
        return mChildrenShapes;
    }

}