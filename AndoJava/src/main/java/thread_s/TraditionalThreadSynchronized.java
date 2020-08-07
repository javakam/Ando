package thread_s;

/**
 * 传统线程互斥技术
 * <p>
 * 互斥，产生在要抢资源的时候，说白了，也就是让几个线程共用同一把锁！
 * <p>
 * 面试中的奇葩问法：分析静态方法所使用的同步监视器对象是什么？ {@link Outprint#output3(String)}
 * <p>
 * 总结：以下锁机制是等效的
 * <p>
 * 1 synchronized (this) <=>   synchronized void method()
 * 2 synchronized (Outprint.class) <=> synchronized static void method()
 * <p>
 * Created by javakam on 2018/6/19 .
 */
public class TraditionalThreadSynchronized {
    public static void main(String[] args) {
        Outprint outprint = new Outprint();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outprint.output("machangbao");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Outprint.output3("hawangjiaowolaixunshan");
                }
            }
        }).start();
    }

    /**
     * synchronized 锁住的是同一个对象
     * <p>
     * 1 & 2两种写法本质上是一样的，都是锁住当前方法所在的对象 {@link #output & synchronized (this){...} }
     */
    static class Outprint {
        //1 锁方法
        public synchronized void output(String name) {
            int len = name.length();
            // 不要锁变量 synchronized（name）
            for (int i = 0; i < len; i++) {
                System.out.print(name.charAt(i));
            }
            //2 锁住方法所在的对象
            //
          /*  synchronized (this) {
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
            }*/
            System.out.println();
        }

        public void output2(String name) {
            int len = name.length();
            synchronized (Outprint.class) {
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            }
        }

        /**
         * 静态方法锁的不是当前对象(this)而是JVM内存中对象的字节码，因为静态方法在执行时虽然不需要实例化对象，
         * 但在内存中有一个对象跟它关联，这个对象就是该类的字节码对象。
         * <p>
         * 所以，为了保持同步，都用字节码对象即可，
         * 将synchronized (this) 改成 synchronized (Outprint.class) 即可实现同步 {@link #output2}
         */
        public synchronized static void output3(String name) {
            int len = name.length();
            for (int i = 0; i < len; i++) {
                System.out.print(name.charAt(i));
            }
            System.out.println();
        }
    }
}