package jvm;

/**
 * JVM内幕  http://www.importnew.com/17770.html
 * <p>
 * Created by changbao on 2018/8/19 0019 星期日 .
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
//        System.err.println(ClassLoaderTest.class.getClassLoader().getClass().getName());

        //打印结果为 null ，因为 BootStrap 这个类加载器负责加载 jre/lib/rt.jar 下面的 .class 文件，
        //BootStrap 在JVM内核启动时就已经存在了，它不是java类，而是用C++写的一段二进制代码。  eg: java.lang.*
        System.out.println(Object.class.getClassLoader());

        /*【重】
        默认输出：sun.misc.Launcher$AppClassLoader
                sun.misc.Launcher$ExtClassLoader--可见，AppClassLoader的类加载器是父类ExtClassLoader，ExtClassLoader在ext中
        打包到 C:\fastwork\Java\jdk1.8.0_172\jre\lib\ext 目录下后，输出为：  sun.misc.Launcher$ExtClassLoader
        【附】打包方法->IDEA导出可执行jar包  https://blog.csdn.net/ouyang111222/article/details/73105086
        */
        Class superclass = ClassLoaderTest.class;
        while (superclass != null) {
            System.out.println(superclass.getName());
            superclass = superclass.getSuperclass();
        }
        System.out.println("....................................................");
        ClassLoader loader = ClassLoaderTest.class.getClassLoader();
        while (loader != null) {
            System.out.println(loader.getClass().getName() + "    " + loader.getClass().getSimpleName());
            loader = loader.getParent();
        }

    }
}
