package reflect_s;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * 反射 --【透彻！】 反射就是把Java类中的各种成分映射成相应的Java类
 * <p>
 * 反射会造成程序性能下降。{@link Class#newInstance()  cachedConstructor = c;}
 * <p>
 * 缓存的是耗时操作！
 */
public class ReflectTest {

    public static void main(String[] args) throws Exception {
        System.out.println("理解反射的概念---------------------------------------->");
        //判断是否是原始(也称预定义)类型
        System.out.println(Integer.class.isPrimitive());
        System.out.println(int.class.isPrimitive());
        System.out.println(int.class == Integer.class);

        // 原始类型的包装类提供了获取它们对应的原始类型的方法  eg: Character.TYPE,Boolean.TYPE,Float.TYPE etc...
        // {@link java.lang.Integer.TYPE}
        // public static final Class<Integer>  TYPE = (Class<Integer>) Class.getPrimitiveClass("int");
        System.out.println(int.class == Integer.TYPE);

        // 数组类型的 Class 实例对象
        // Class.isArray()
        System.out.println(int[].class.isPrimitive());
        System.out.println(int[].class.isArray());

        //==================================================================================================//

        System.out.println("Constructor 构造方法的反射---------------------------------------->");
        constructor();

        System.out.println("Filed 成员变量的反射---------------------------------------->");
        field();// Filed  代表类的一个成员变量

        System.out.println("作业---------------------------------------->");
        // 作业   将任一对象中的所有String类型的 成员变量 所对应的 字符串内容 中的“b”改成“a”
        homework();

        System.out.println("Method 代表某个【类】中的成员方法---------------------------------------->");
        method();

        System.out.println("用反射方式执行某个类中的main方法 ---------------------------------------->");
        methodMain(args);

        System.out.println("数组的反射 ---------------------------------------->");
        array();

        System.out.println("数组反射的应用 ---------------------------------------->");
        arrayUsage();

        // 思考：怎么得到数组中的元素类型？
        /*
        答案是没有办法，比如说：Object[] objects=new Object[]{1,true,"bao",'h'};
        我们只能通过 objects[0].getClass().getName() 获取到某个元素的数据类型。
         */
    }

    /**
     * 类似main方法那块
     */
    private static void arrayUsage() {
        int[] arrInt = new int[]{1, 3, 6};
        String[] arrStr = new String[]{"a", "c", "e"};

        printObject(arrInt);

        printObject(arrStr);

        printObject("hello");
    }

    private static void printObject(Object obj) {
        Class cls = obj.getClass();
        if (cls.isArray()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                System.out.println(Array.get(obj, i));
//                System.out.println(Array.get(obj, i).getClass().getName());
            }
        } else {
            System.out.println(obj);
        }
    }

    /**
     * 数组是一种Object
     */
    private static void array() {
        int[] ar1 = new int[3];
        int[] ar2 = new int[6];
        int[][] ar3 = new int[2][8];
        System.out.println(ar1.getClass().getName());
        System.out.println(ar1.getClass().getSuperclass().getName());//java.lang.Object
        /*
        【超重】下面这个例子直接说明：数组是 Object 的直接子类！！！ （他爷爷报空指针了！）
         */
        String[] args = new String[5];
        //java.lang.Object
        System.out.println(args.getClass().getSuperclass().getName());
        //异常：java.lang.NullPointerException
//        System.out.println(args.getClass().getSuperclass().getSuperclass().getName());

        //由上可知，数组是一种特殊的 Object
        Object objArr1 = ar1;
        Object objArr12 = ar2;
        //【注】基本数据类型的数组 不能转换成 Object数组！！！
//        Object[] objArr3 = ar1;
        Object[] objArr4 = ar3;//二维数组转换成一维数组，每个元素是一个一维数组
        Object[] objArr5 = args;//String类型的数组

        System.out.println("Arrays.asList处理int[]和String[]时的差异---------------------------------------");
        //结合上面分析，很不好想。。。
        int[] arrInt = new int[]{1, 3, 6};
        String[] arrStr = new String[]{"a", "c", "e"};
        System.out.println(arrInt);
        System.out.println(arrStr);
        System.out.println(Arrays.asList(arrInt));
        System.out.println(Arrays.asList(arrStr));
        /*
        打印结果：
        [I@7f31245a
        [Ljava.lang.String;@6d6f6e28
        [[I@7f31245a]
        [a, c, e]
         */
    }

    private static void methodMain(String[] args) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //长见识系列。。。添加 main方法执行需要的参数
        //args[0]== com.improve.reflect_s.MethodMain
        Class cls = Class.forName(args[0]);

        Method mainMethod = cls.getMethod("main", String[].class);
        /*
        到达目标方法，会对 new String[]{"a","b"} 进行拆分，所以要多包装一层
        解决方式：告诉编译器，我给你的是一个对象，不是数组，不要给我拆包。。。（间接说明数组也是一种对象Object）
         */
        //异常 ：java.lang.IllegalArgumentException: wrong number of arguments
//        mainMethod.invoke(null, new String[]{"a","b"});

//        mainMethod.invoke(null, new Object[]{new String[]{"a", "b"}});
        //或者
        mainMethod.invoke(null, (Object) new String[]{"a", "b"});
    }

    /**
     * 传入方法名 + 参数类型
     * getMethod
     * <p>
     * 静态方法 invoke(null)
     *
     * @throws Exception
     */
    private static void method() throws Exception {
        String str = new String("abc");
        Method method = String.class.getMethod("charAt", int.class);
        //【注】如果invoke第一个参数为null - invoke(null, 2); 说明这是个静态方法
        char invoke = (char) method.invoke(str, 2); // 或者 char -> Character
        System.out.println("invoke = " + invoke);
    }

    /**
     * 作业   将任一对象中的所有 String类型的成员变量 所对应的字符串内容中的“b”改成“a”
     */
    private static void homework() throws Exception {
        ReflectPoint point = new ReflectPoint(3, 5, 8);

        Field[] fields = point.getClass().getDeclaredFields();
        for (Field field : fields) {
            //== 不仅用于比较地址，还可以用于比较字节码文件是否相同（本质也是地址）
            if (field.getType() == String.class) {
                String str = (String) field.get(point);
                /*
                【重】 replace(char oldChar, char newChar) 会替换掉全部的字符
                Returns a string resulting from replacing all occurrences of
                {@code oldChar} in this string with {@code newChar}.
                 */
                String newStr = str.replace('b', 'a');
                field.set(point, newStr);
            }
        }
        System.out.println(point.toString());
    }

    /**
     * 传入成员变量名字
     * <p>
     * getField  getDeclaredField
     *
     * @throws Exception
     */
    private static void field() throws Exception {
        /*
        ReflectPoint中的三个字段，分别使用不同的权限修饰符
        public int x;
        protected int y;
        private int z;
         */
        ReflectPoint rp1 = new ReflectPoint(3, 5, 8);

        // fieldX不是对象身上的变量，而是类上，要用它去取某个对象上对应的值
        Field fieldX = rp1.getClass().getField("x");
        int x = fieldX.getInt(rp1);
        System.out.println("x = " + x);

        //异常 ： java.lang.NoSuchFieldException: y
//        Field fieldY = rp1.getClass().getField("y");
        Field fieldY = rp1.getClass().getDeclaredField("y");
        int y = fieldY.getInt(rp1);
        System.out.println("y = " + y);

        Field fieldZ = rp1.getClass().getDeclaredField("z");
        /*
        暴力反射！
        异常：Exception in thread "main" java.lang.IllegalAccessException:
         Class com.improve.reflect_s.ReflectTest
         can not access a member of class com.improve.reflect_s.ReflectPoint with modifiers "private"
         */
        fieldZ.setAccessible(true);//  暴力反射！
        int z = fieldZ.getInt(rp1);
        System.out.println("z = " + z);
    }

    /**
     * Constructor -- 构造方法的反射应用
     * <p>
     * 传入参数类型 String.class.getConstructor(StringBuffer.class,int.class);
     *
     * @throws Exception
     */
    private static void constructor() throws Exception {
        // class -> constructor -> new object
//        String str=new String("abc");

        // 获得方法时要用到类型！
        // 方式一（通用）：获取有参构造方法
        Constructor constructor = String.class.getConstructor(StringBuffer.class);
        // 编译器只看变量的定义，不看代码的执行。  所以说，强转，是给编译器看的。
        String str2 = (String) constructor.newInstance(new StringBuffer("abc"));
        System.out.println(str2.charAt(2));

        // 方式二（方式一的简化，但只适用无参构造器）：直接用 Class 的newInstance去创建对象。当然也可以用方式一去创建。
        Class cl = String.class;
        String sss = (String) cl.newInstance();
        //反射会造成程序性能下降
    }

}
