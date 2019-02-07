package thread_s;

/**
 * 传统线程技术回顾
 * <p>
 * Thread 开启的两种方式
 * <p>
 * Created by javakam on 2018-6-19 .
 */
public class MainThread {
    public static void main(String[] args) {
        // 线程创建
        // 方式一
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("11:" + Thread.currentThread().getName());
                    System.out.println("12:" + this.getName());
                }
            }
        };
//        thread.start();

        // 方式二
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("2:" + Thread.currentThread().getName());
                }
            }
        });
//        thread1.start();

        // 方式三
        // 涉及到一个知识点：匿名内部类对象的构造方法如何调用父类的非默认构造方法。
        // 问执行的是那个方法？ 提示：通过面向对象的思想去分析
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("31:" + Thread.currentThread().getName());
                }
            }
        }) {
            @Override
            public void run() {
//                super.run();
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("32:" + Thread.currentThread().getName());
                }
            }
        }.start();


        // 思考: 多线程机制会提高程序的运行效率吗？ 为什么还要用多线程？
        // 不会提高效率，更低！ 比如说汽车在高速上行驶，走直线快，还是三条60 . 90 .110 上来回切着走斜线快。
        // 车多了，开始时间地点不同，速度也不同，走不了直线，通过不同的限速通道，使车辆间协调速度上的差异，是高速路符合均衡。

    }
}
