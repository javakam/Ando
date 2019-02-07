package design.iterator;

/**
 * ArrayList - 底层使用数组
 * Created by changbao on 2018/7/19 0019 星期四 .
 */
public class ArrayList implements Collection {
    public static final int SIZE = 10;
    private Object[] objArr = new Object[SIZE];
    private int index = 0;

    @Override
    public void add(Object o) {
        //装满了，换个更大的水缸，并把之前小水缸（objArr）中的水倒入其中
        if (index == SIZE) {
            Object[] newObjArr = new Object[SIZE * 2];
            System.arraycopy(objArr, 0, newObjArr, 0, SIZE);
            //注：把引用指向新的水缸
            objArr = newObjArr;
        }
        objArr[index] = o;
        index++;
    }

    @Override
    public int size() {
        return index;
    }

    @Override
    public Iterator iterator() {
        // 简单处理,可以写个单例一直用
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator {

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            if (currentIndex >= index) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public Object next() {
            Object obj = objArr[currentIndex];
            currentIndex++;
            return obj;
        }
    }

}
