package design.iterator;

/**
 * 链表节点 - 包含数据本身和下一节点信息
 * <p>
 * {@link LinkedList}
 * <p>
 * Created by changbao on 2018/7/19 星期四 .
 */
public class Node {
    private Object data;
    private Node next;

    public Node(Object data, Node next) {
        this.data = data;
        this.next = next;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
