package design.behavioral.iterator;

/**
 * 数组实现 -> {@link ArrayList}
 * <p>
 * 链表实现 -> {@link LinkedList}
 * <p>
 * Created by changbao on 2018/7/19 星期四 .
 */
public class Main {
    public static void main(String[] args) {
//        ArrayList list = new ArrayList();
//        LinkedList list = new LinkedList();

        // 面向接口编程
        Collection list = new ArrayList();
        for (int i = 0; i < 15; i++) {
            list.add(new Dog(i));  // 演示断点
        }
        System.out.println("size : " + list.size());

        // Iterator
        Iterator<Dog> iterator = list.iterator();
        while (iterator.hasNext()) {
            Dog dog = iterator.next();
            System.out.print(dog + "  ");
        }
    }

}
