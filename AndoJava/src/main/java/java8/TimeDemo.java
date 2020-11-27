package java8;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author javakam
 * <p>
 * 2020-11-27 15:27:09
 */
public class TimeDemo {

    public static void main(String[] args) {

    }

    private static final LocalDate sDate20200805 = LocalDate.parse("2020-08-05");

    private static void getTime() {
        final LocalDate localDate = LocalDate.now();
        //System.out.println("今天: " + localDate.toString() + "  一个月前 : " + localDate.minusMonths(1) + "  31天前: " + localDate.minusDays(31));
        final String endDate = localDate.toString();
        final String beginDate = localDate.minusMonths(1).toString();


        final String fileDate = "2020-07-8";
        final LocalDate datePublish = LocalDate.parse(noNull(fileDate));
//      System.out.println("今天: " + localDate + "  一个月前 : " + localDate.minusMonths(1) + "  31天前: " + localDate.minusDays(31)
//             + "   " + localDate.plusMonths(1) + "   " + sDate20200805);

        if (datePublish.isBefore(sDate20200805)) {

        }
    }

    private static String noNull(String s) {
        if (s == null || s.trim().equals("")) return "";
        else return s;
    }

    private static boolean isNight() {
        return LocalTime.now().withNano(0).withSecond(0).withMinute(0).equals(LocalTime.parse("01:00"));
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
