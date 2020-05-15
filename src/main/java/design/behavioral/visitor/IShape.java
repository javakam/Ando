package design.behavioral.visitor;

/**
 * 通用形状接口
 */
public interface IShape {
    void move(int x, int y);

    void draw();

    String accept(IVisitor visitor);
}