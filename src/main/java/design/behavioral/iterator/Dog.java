package design.behavioral.iterator;

/**
 * Created by changbao on 2018/7/19 星期四 .
 */
public class Dog {
    private int id;

    public Dog(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                '}';
    }
}
