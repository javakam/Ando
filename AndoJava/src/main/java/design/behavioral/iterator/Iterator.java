package design.behavioral.iterator;

/**
 * Created by changbao on 2018/7/23 星期一 .
 */
public interface Iterator<E> {
    boolean hasNext();

    E next();
}
