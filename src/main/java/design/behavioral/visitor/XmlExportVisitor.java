package design.behavioral.visitor;

public class XmlExportVisitor implements IVisitor {

    public void export(IShape... shapes) {
        final StringBuilder sb = new StringBuilder();
        for (IShape shape : shapes) {
            sb

                    .append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                    .append("\n")
                    .append(shape.accept(this))
                    .append("\n");
            System.out.println(sb.toString());
            sb.setLength(0);
        }
    }

    @Override
    public String visitDot(ShapeDot shape) {
        return "<dot>" + "\n" +
                "    <id>" + shape.getId() + "</id>" + "\n" +
                "    <x>" + shape.getX() + "</x>" + "\n" +
                "    <y>" + shape.getY() + "</y>" + "\n" +
                "</dot>";
    }

    @Override
    public String visitCircle(ShapeCircle shape) {
        return "<circle>" + "\n" +
                "    <id>" + shape.getId() + "</id>" + "\n" +
                "    <x>" + shape.getX() + "</x>" + "\n" +
                "    <y>" + shape.getY() + "</y>" + "\n" +
                "    <radius>" + shape.getRadius() + "</radius>" + "\n" +
                "</circle>";
    }

    @Override
    public String visitRectAngle(ShapeRectangle shape) {
        return "<rectangle>" + "\n" +
                "    <id>" + shape.getId() + "</id>" + "\n" +
                "    <x>" + shape.getX() + "</x>" + "\n" +
                "    <y>" + shape.getY() + "</y>" + "\n" +
                "    <width>" + shape.getWidth() + "</width>" + "\n" +
                "    <height>" + shape.getHeight() + "</height>" + "\n" +
                "</rectangle>";
    }

    @Override
    public String visitCompoundGraphic(ShapeCompound cg) {
        return "<compound_graphic>" + "\n" +
                "   <id>" + cg.getId() + "</id>" + "\n" +
                _visitCompoundGraphic(cg) +
                "</compound_graphic>";
    }

    private String _visitCompoundGraphic(ShapeCompound cg) {
        StringBuilder sb = new StringBuilder();
        if (!cg.getChildrenShapes().isEmpty()) {
            for (IShape shape : cg.getChildrenShapes()) {
                String obj = shape.accept(this);
                // Proper indentation for sub-objects.
                obj = "    " + obj.replace("\n", "\n    ") + "\n";
                sb.append(obj);
            }
        }
        return sb.toString();
    }

}