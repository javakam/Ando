package design.iterator;

/**
 * LinkedList - 底层使用链表
 * <p>
 * Created by changbao on 2018/7/19 星期四 .
 */
public class LinkedList implements Collection {
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    @Override
    public void add(Object o) {
        Node next = new Node(o, null);
        if (head == null) {
            head = next;
            tail = next;
        }
        tail.setNext(next);
        tail = next;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            if (currentIndex >= size) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public Object next() {
            // TODO 2018年7月23日20:55:51
//            Object obj = tail.getNext();
            return null;
        }
    }
}
