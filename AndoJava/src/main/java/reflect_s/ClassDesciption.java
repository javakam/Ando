package reflect_s;


/**
 * 透析分析反射基础
 * <p>
 * 【重】 Class 是用于描述Java类的类
 * <p>
 * Created by javakam on 2018-6-21 .
 */
public class ClassDesciption {
    /*
    思考：
    class
    Class ---> 代表一类什么样的事物？  一个Class代表一份字节码

    Person p1=new Person();
    Person p2=new Person();
    Date
    Match

    // cls1代表对象在内存中的字节码
    Class cls1=Date.class;//字节码1
    Class cls2=Person.class;//字节码2

    // p1 代表 Person类 在内存中的 字节码 所生成的对象
    // 通过对象调用getClass方法也可以获得对象所对应的那个Class
    p1.getClass();

    // 面试题： Class.forName 的作用？
    作用是返回类对象在JVM中的字节码，分两种情况：
            1 当JVM（虚拟机）中已经加载过这个类的字节码，会从缓存中直接返回；
            2 如果JVM中没有这个字节码，则用类加载器重新加载，并返回
    Class.forName("java.lang.String");//指定类的完整名称


    总结：如何获得各个字节码所对应的实例对象（Class对象）？
        三种方式：1 类.class;
                2 对象.getClass();
                3 Class.forName(" 包名 + 类名 ");
     */

    public static void main(String[] args) throws ClassNotFoundException {
        //1 私有构造器
//        Class cls=new Class<>();

        /*
          2 九个预定义Class实例对象 (八种基本数据类型 + void ):
              1 参见 Class.isPrimitive() 方法的帮助；
               //用于判别是不是基本数据类型的实例对象
               System.out.println(Integer.class.isPrimitive());
               System.out.println(int.class.isPrimitive());
              2 int.class==Integer.TYPE
         */
//        Class cls1=Void.class;
//        Class cls2=void.class;
//        Class cls3=Class.class;

//        if (int.class==Integer.TYPE){}
        String str = "abc";

        Class cls1 = str.getClass();
        Class cls2 = String.class;
        Class cls3 = Class.forName("java.lang.String");
        //输出结果全是 trur ， 说明都是同一份字节码
        System.out.println(cls1 == cls2);
        System.out.println(cls1 == cls3);
        System.out.println(cls2 == cls3);


        System.out.println(cls1.isPrimitive());
        System.out.println(char.class.isPrimitive());
        System.out.println(char.class == Character.class);
        System.out.println(char.class == Character.TYPE);

        /*
         数组类型的Class实例对象
         Class.isArray()
         */
        System.out.println(char[].class.isPrimitive());
        System.out.println(char[].class.isArray());
    }

}
