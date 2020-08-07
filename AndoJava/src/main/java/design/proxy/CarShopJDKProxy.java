package design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by changbao on 2018/8/19 0019 星期日 .
 */
public class CarShopJDKProxy implements InvocationHandler {

    public CarShopJDKProxy() {
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
