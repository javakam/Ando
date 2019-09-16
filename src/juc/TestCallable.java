package juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
 * 一、创建执行线程的方式三：实现 Callable 接口。 相较于实现 Runnable 接口的方式，方法可以有返回值，并且可以抛出异常。
 *
 * 二、执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。  FutureTask 是  Future 接口的实现类
 */
public class TestCallable {

    public static void main(String[] args) {
		ThreadDemo2 td = new ThreadDemo2();

        //1.执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。
        // FutureTask 本质上也是 Runnable
        FutureTask<Integer> result = new FutureTask<>(td);

        new Thread(result).start();

        //2.接收线程运算后的结果
        try {
            // 此时 get() 不会立刻返回值,而是等到线程执行完并返回值后,才会执行下面的代码,可见 FutureTask 也可用于 [闭锁] !!!!!!
            // CountDownLatch.java
            Integer sum = result.get();
            System.out.println(sum);
            System.out.println("------------------------------------");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class ThreadDemo2 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;

        for (int i = 0; i <= 999999999L; i++) {
            sum += i;
        }

        return sum;
    }
}

/*class ThreadDemo implements Runnable{

	@Override
	public void run() {
	}
	
}*/

