package design.behavioral.visitor;

/**
 * 访问者模式
 * https://refactoringguru.cn/design-patterns/visitor
 * https://juejin.im/post/5ebe33335188256d5952500c
 * <p>
 * Created by javakam 2020年5月15日 15:10:30
 */
public class Main {
    public static void main(String[] args) {
        ShapeDot dot = new ShapeDot();
        dot.move(10, 20);

        ShapeCircle circle = new ShapeCircle();
        circle.move(20, 30);
        circle.setRadius(99F);

        ShapeRectangle rectangle = new ShapeRectangle();
        rectangle.move(30, 50);
        rectangle.setWidth(60);
        rectangle.setHeight(200);

        ShapeCompound compound = new ShapeCompound();
        compound.move(300, 301);
        compound.add(dot);
        compound.add(circle);
        compound.add(rectangle);

        XmlExportVisitor xmlExportVisitor = new XmlExportVisitor();
        xmlExportVisitor.export(dot, circle, rectangle, compound);

        //JDK 中的 访问者模式 -> https://juejin.im/post/5ebe33335188256d5952500c
        /*
        javax.lang.model.element.AnnotationValue 和  AnnotationValueVisitor
        javax.lang.model.element.Element 和  ElementVisitor
        javax.lang.model.type.TypeMirror 和  TypeVisitor
        java.nio.file.FileVisitor 和  SimpleFileVisitor
        javax.faces.component.visit.VisitContext 和  VisitCallback
         */
        //AnnotationValueVisitor
        //ElementVisitor
        //...
    }
}