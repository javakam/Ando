package design.iterator;

/**
 * 面向接口编程 - 声明父类new子类
 * <p>
 * Created by changbao on 2018/7/19 星期四 .
 */
public interface Collection {
    void add(Object o);

    int size();

    Iterator iterator();
}
