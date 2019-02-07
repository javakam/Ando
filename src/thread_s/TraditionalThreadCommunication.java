package thread_s;

/**
 * 传统线程同步通信技术
 * <p>
 * 如题：子线程循环10，主线程循环100次，接着又回到子线程循环10次，接着再回到主线程循环100次，
 * 如此循环50次，请写出程序。
 * <p>
 * Created by javakam on 2018-6-20 .
 */
public class TraditionalThreadCommunication {

    public static void main(String[] args) {
        final Business business = new Business();
        // 子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 50; i++) {
                    business.sub(i);
                }
            }
        }
        ).start();
        // 主线程
        for (int i = 1; i <= 50; i++) {
            business.main(i);
        }
    }

    /**
     * 面向对象的思想，锁同一个对象，而不是直接在线程里去操作
     */
    static class Business {
        //标志位 交替输出
        private boolean isSubShotting = true;

        public synchronized void sub(int i) {
            while (!isSubShotting) {
                try {
                    //注： wait 对象和 synchronized 锁住的对象必须是同一对象！
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 1; j <= 10; j++) {
                System.out.println("sub thread sequence of :  " + "j = " + j + "  i = " + i);
            }
            isSubShotting = false;
            this.notify();
        }

        /**
         * 方便演示，缩减到30次
         *
         * @param i
         */
        public synchronized void main(int i) {
            while (isSubShotting) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 1; j <= 30; j++) {
                System.out.println("main thread sequence of :  " + "j = " + j + "  i = " + i);
            }
            isSubShotting = true;
            this.notify();
        }
    }
}



