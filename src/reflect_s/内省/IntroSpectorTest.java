package reflect_s.内省;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Introspector 内省
 * <p>
 * 使用内省API操作JavaBean
 * machangbao 2018年8月5日18:33:59
 */
public class IntroSpectorTest {

    public static void main(String[] args) throws Exception {
        MyJavaBean point = new MyJavaBean(3, 9);

        //如果用我们之前的反射方式会很复杂：
        //"x"-->"X"-->"getX"-->MethodGetX--> ...
        //使用内省API
        String propertyName = "x";

        // 调用 MyJavaBean.getX()
        Object retVal = getProperties(point, propertyName);
        System.out.println("retVal = " + retVal);

        // 调用 MyJavaBean.setX(int x)
        Object value = 8;
        setProperties(point, propertyName, value);
        System.out.println(point.getX());
    }

    private static void setProperties(Object point, String propertyName, Object value)
            throws IllegalAccessException, InvocationTargetException, IntrospectionException {
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, point.getClass());
        Method methodSetX = pd.getWriteMethod();
        methodSetX.invoke(point, value);
    }

    /**
     * @param point        操作的对象
     * @param propertyName 变量名
     */
    private static Object getProperties(Object point, String propertyName)
            throws IllegalAccessException, InvocationTargetException, IntrospectionException {
        /*PropertyDescriptor pd = new PropertyDescriptor(propertyName, point.getClass());
        Method methodGetX = pd.getReadMethod();
        Object retVal = methodGetX.invoke(point, null);
        return retVal;*/

        /*
        zxx   内省案例.png  遍历BeanInfo的方式去做 -- 繁琐的方式
         */
        BeanInfo beanInfo = Introspector.getBeanInfo(point.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Object retVal = null;
        for (PropertyDescriptor prop : propertyDescriptors) {
            if (prop.getName().equals(propertyName)) {
                Method methodRead = prop.getReadMethod();
                retVal = methodRead.invoke(point, (Object) null);
                break;
            }
        }
        return retVal;
    }
    // TODO 2018年8月5日18:31:44  31_对JavaBean的复杂内省操作.avi
}
