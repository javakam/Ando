package generics;

import generics.bean.User;
import generics.bean.UserDao;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

public class GenericsPractice {

    public static void main(String[] args) throws Exception {
        //1 自动将Object类型的对象转换成其他类型 -- 说白了就是根据指定的返回值，确定强转的类型！
        Object obj = "123";
        //java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
        Object obj2 = 666;
        String str = dynamicConverter(obj);
        System.out.println("str = " + str);

        //2 将任意类型的数组中的所有元素填充为相应类型的某个对象
//        fillArray();

        //3
        copy1(new Vector<String>(), new String[10]);
        //取交集
        copy2(new Date[10], new String[10]);
        //【重】 类型具有传播性，所以说是一件比较复杂的事情
//        copy1(new Vector<Date>(),new String[10]);

        //4
        UserDao<User> dao = new UserDao<User>();
        dao.add(new User("长宝123", 26));
        User user = dao.findByUserName("长宝123");
        dao.findById(26);

        //5 【注】跟类的 User类的泛型E 没关系，因为static方法不能使用类的泛型！！！
        User u = UserDao.update2(new User("长宝233", 18));

        System.out.println("-----------------------------------");
        //【高难度知识点】 43_通过反射获得泛型的实际类型参数
        ArrayList<User> arrayList = new ArrayList<User>();
        Method method = GenericsPractice.class.getMethod("applyUserList", ArrayList.class);
        Type[] types = method.getGenericParameterTypes();
        ParameterizedType pType = (ParameterizedType) types[0];
        System.out.println(pType.getRawType());
        System.out.println(pType.getActualTypeArguments()[0]);
    }

    public static void applyUserList(ArrayList<User> datas) {
    }

    private static <T> void copy1(Collection<T> collection, T[] a) {
    }

    private static <T> void copy2(T[] des, T[] a) {
    }

    private static <T> void fillArray(T[] a, T obj) {

    }

    private static <T> T dynamicConverter(Object obj) {
        return (T) obj;
    }

}
