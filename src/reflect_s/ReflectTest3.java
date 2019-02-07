package reflect_s;

import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

/**
 * 框架的概念 及 用反射技术开发框架的原理 - 下载的视频没有27集，需到官网去看
 * <p>
 * 用配置文件的方式创建 ArrayList和HashSet对象 {@link ReflectTest2} {@see config.properties}
 * <p>
 * mahcangbao 2018-8-5 16:17:23
 */
public class ReflectTest3 {

    /**
     * 分别尝试使用 java.util.ArrayList 和 java.util.HashSet
     */
    public static void main(String[] args) throws Exception {
        /*
        getRealPath //金山词霸/内部；
        一定要记住完整的路径，但完整的路径不是硬编码，而是运算出来的!!!
         */

        // 方式一 此时 config.properties 是在 JavaImprove 下创建的
//        InputStream ins = new FileInputStream("config.properties");

        // 方式二 使用 类加载器 加载 src下的 配置文件，通常我们写程序都喜欢把配置文件和源代码放到一起。。。
        // 【推荐使用】 getClassLoader 方式会从 src 文件下开始找！
        InputStream ins = ReflectTest3.class.getClassLoader().getResourceAsStream("resouces/config.properties");

        // 当然，我们也可以自己卖可乐。。。 缺陷：使用的是相对路径 “/resouces” ，当然也可以写成全路径
//        InputStream ins = ReflectTest3.class.getResourceAsStream("/resouces/config.properties");

        Properties prop = new Properties();
        prop.load(ins);
        ins.close();//关闭和系统资源的连接，防止内存泄漏

        String className = prop.getProperty("className");
        Collection collection = (Collection) Class.forName(className).newInstance();

        ReflectPoint rp1 = new ReflectPoint(1, 2, 3);
        ReflectPoint rp2 = new ReflectPoint(8, 5, 2);
        collection.add(rp1);
        collection.add(rp2);
        collection.add(rp1);
        System.out.println(collection.size());

    }
}
