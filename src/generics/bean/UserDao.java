package generics.bean;

import java.util.Set;

//dao data access object--->crud 增删改查
public class UserDao<E> {
    public void add(E x) {

    }

    public E findById(int id) {
        return null;
    }

    public void delete(E obj) {

    }

    public void delete(int id) {

    }

    public void update(E obj) {

    }

    //【注】跟类的 E 没关系，因为static方法不能使用类的泛型！！！
    public static <A> A update2(A obj) {
        return obj;
    }

    public E findByUserName(String name) {
        return null;
    }

    public Set<E> findByConditions(String where) {
        return null;
    }
}