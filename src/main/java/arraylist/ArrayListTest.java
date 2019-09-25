package arraylist;


import java.util.ArrayList;

/**
 * ArrayList 验证了一些疑虑
 * <p>
 * machangbao 2018-8-6 15:37:25
 */
public class ArrayListTest {
    public static void main(String[] args) {
        // String
//        string();

        // 自定义数据实体
        customList();
    }

    private static void customList() {
        ArrayList<LayoutParams> list = new ArrayList();
        LayoutParams lp1 = new LayoutParams(1, 2, "textview");
        LayoutParams lp2 = new LayoutParams(3, 5, "button");
        LayoutParams lp3 = new LayoutParams(6, 8, "card");
        list.add(lp1);
        list.add(lp2);
        list.add(lp3);
        System.out.println(list.indexOf(lp1) + "  " + list.indexOf(lp3));

        /*
        【重】前两种方法都可以移除掉，其本质是相同的，ArrayList里的数组存储的是对象的内存地址，
        remove(lp2)是遍历数组找到和lp2相同的内存地址的元素进行删除，
        list.remove(1)是根据数组的索引值（下标）进行删除
         */
//        list.remove(lp2);
//        list.remove(1);
        /*
        第三种方法 需要重写 equals&hashCode 方法才能成功删除
        ￣□￣｜｜ 一直用的IDE提供的快捷方式生成这两种方法。。。惭愧惭愧
         */
        System.out.println(lp2.equals(new LayoutParams(3, 5, "button")));
        list.remove(new LayoutParams(3, 5, "button"));
        System.out.println(list.size());

        LayoutParams lpNew = new LayoutParams(2, 9, "checkbox");
        list.add(1, lpNew);
        System.out.println(list.size());

        for (LayoutParams lp : list) {
            System.out.println(list.indexOf(lp) + " : " + lp.toString());
        }
    }

    private static void string() {
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("小明，&2多·阿达大大774&……￥￥）——+、】【1.1 Activity的生命周期全面分析");
        arrayList.add("明，&2多·阿达大大774&……￥￥）——+、】【1.1 Activity的生命");
        arrayList.add("小明，&2多·阿达大大y的生命周期774&……￥￥）——+、】【1.1 Activit全面");
        System.out.println(arrayList.size());

//        arrayList.remove(2);
        arrayList.remove("小明，&2多·阿达大大y的生命周期774&……￥￥）——+、】【1.1 Activit全面");
        System.out.println(arrayList.size());

        arrayList.add(2, "x");
        System.out.println(arrayList.size());

        for (String s : arrayList) {
            System.out.print(arrayList.indexOf(s) + " : " + s + "    ");
        }
    }
}
