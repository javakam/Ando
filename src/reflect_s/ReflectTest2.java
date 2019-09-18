package reflect_s;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * 反射应用
 * <p>
 * ArrayList_HashSet的比较及Hashcode分析
 * <p>
 * hashCode方法与HashSet类.png
 * 【重】HashSet造成的内存泄漏
 */
public class ReflectTest2 {

    public static void main(String[] args) {
        /*
        ArrayList 放的是对象的引用地址，同一对象的地址可能被添加多次；
        HashSet 会在放入对象引用地址之前，判断里面是否已存在该对象，若已存在，则不插入该对象
         */
        Collection collection1 = new ArrayList();
        Collection collection2 = new HashSet();

        ReflectPoint rp1 = new ReflectPoint(1, 2, 3);
        ReflectPoint rp2 = new ReflectPoint(8, 5, 2);
        //rp3 和 rp1 不是同一对象,需要重写hashCode和equals方法
        ReflectPoint rp3 = new ReflectPoint(1, 2, 3);

        collection1.add(rp1);
        collection1.add(rp2);
        collection1.add(rp3);
        collection1.add(rp1);
        System.out.println(collection1.size());


        collection2.add(rp1);
        collection2.add(rp2);
        collection2.add(rp3);
        collection2.add(rp1);

        /*
        添加完成后，修改hashCode方法中用于参与计算的变量值，会改变当前对象哈希值使得与HashSet中的哈希值不匹配，
        从而造成内存泄漏！！！
         */
        // 参照：【重】HashSet造成的内存泄漏
//        rp1.x = 9;
        collection2.remove(rp1);
        System.out.println(collection2.size());
    }
}
