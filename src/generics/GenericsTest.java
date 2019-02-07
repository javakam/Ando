package generics;


import java.lang.reflect.Constructor;
import java.util.*;

/**
 * ArrayList<Integer> : ArrayList typeof Integer
 */
public class GenericsTest {

    public static void main(String[] args) throws Exception {
        // 1 这段代码展示的是泛型的重要性 -- jdk1.4之前没有泛型时的做法
        ArrayList list = new ArrayList();
        list.add(1);
        list.add(1L);
        list.add("adc");
        // java.lang.ClassCastException: java.lang.Long cannot be cast to java .lang.Integer
        // int i = (Integer) list.get(1);
        printCollection(list);
        printCollection2(list, 6);

        //2 使用泛型替换强转
        Constructor<String> constructor = String.class.getConstructor(StringBuffer.class);
        // javac编译器只看变量的定义，不看代码的执行。  所以说，强转，是给编译器看的。
//        String str2 = (String) constructor.newInstance(new StringBuffer("abc"));
        String str2 = constructor.newInstance(new StringBuffer("abc"));
        System.out.println(str2.charAt(2));

        //3 【重】泛型是给编译器看的，编译器在编译时会去掉类型信息
        ArrayList<String> listString = new ArrayList<String>();
        ArrayList<Integer> listInteger = new ArrayList<Integer>();
        Boolean eqType = listString.getClass() == listInteger.getClass();
        System.out.println("去类型化：" + eqType);
        //根据“去类型”的特点，我们可以利用反射向某个泛型集合中插入其他类型的数据
        listInteger.getClass().getMethod("add", Object.class)
                .invoke(listInteger, "hello");
        System.out.println("ArrayList<Integer> 集合的首个元素：" + listInteger.get(0));

        //4 参数化类型不考虑类型参数的继承关系
        //javac编译时报错
//        ArrayList<Object> arrObje=new ArrayList<String>();
        //【重】注意这种写法不报错...琢磨琢磨
        ArrayList arrObje = new ArrayList<String>();
        ArrayList<Integer> arrObje2 = arrObje;

        //5 Map三部分  Entry
        HashMap<String, Integer> map = new HashMap<>();
        map.put("xxx", 1);
        //键、值、对
        Set<String> keySet = map.keySet();
        Collection<Integer> values = map.values();
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();


        //6 Java中的泛型方法没有C++模板函数功能强大，java中如下代码无法通过编译
        add(1, 3);
        Number f = add(1.5, 3);
        Object o = add(true, "xxx");

        //7 交换数组中的两个数据的位置
        swap(new String[]{"a", "xxx", "hello"}, 1, 2);
        //【注】编译不通过，因为int[]是Object类型，javac不会自作主张装箱成Integer
//        swap(new int[]{2, 5, 9}, 1, 2);

        //8 【重】定义多个类型参数
        HashMap<String, Integer> map1 = new HashMap<>();
        map1.put("adc", 123);
        map1.put("apc", 218);
        map1.put("jungle", 673);
        Integer value = getValue(map1, "adc");
        System.out.println("value = " + value);
    }

    private static <K, V, Z> V getValue(Map<K, V> map, K key) {
        return map.get(key);
    }

    //7 交换数组中的两个数据的位置
    private static <T> void swap(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    //6 Java中的泛型方法没有C++模板函数功能强大，java中如下代码无法通过编译
    public static <T> T add(T x, T y) {
//        return (T)(x+y);
        return null;
    }

    //1
    public static void printCollection(Collection<?> collection) {
        for (Object o : collection) {
            System.out.println("o = " + o + "   ");
        }
    }

    //1 变型 -- 可以传指定类型的参数，而上面的通配符？方式则不能
    public static <T> void printCollection2(Collection<T> collection, T param) {
        for (T t : collection) {
            System.out.println("t = " + t + "   ");
        }
    }
}
