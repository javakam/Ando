package thread_s;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 传统定时器技术回顾
 * <p>
 * Created by javakam on 2018-6-19 .
 */
public class TraditionalTimerTest {

    public static void main(String[] args) {

       /* new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("1:" + Thread.currentThread().getName());
            }
        }, 5000, 1500);//第一次 延时5秒后执行，然后每隔1.5秒执行一次
*/

        // Exception in thread "Timer-0" bomb !
        //java.lang.IllegalStateException: Task already scheduled or cancelled
       /* new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("bomb !");

                new Timer().schedule(this, 2000);
            }
        }, 2000);*/

        new Timer().schedule(new MyTimerTask(), 3000);


        while (true) {
            //noinspection deprecation
            System.out.println(Calendar.getInstance().getTime().getSeconds());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MyTimerTask extends TimerTask {
        static int count = 0;

        @Override
        public void run() {
            count = (count + 1) % 2;
            System.out.println("bomb !");
            new Timer().schedule(new MyTimerTask(), 2000 + 2000 * count);
        }
    }
}
