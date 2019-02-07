package enum_s;

/**
 * 枚举的基本使用
 */
public class MainEnum {

    public static void main(String[] args) {
        WeekDay1 SUN = WeekDay1.SUN;
        WeekDay1 MON = WeekDay1.MON;
        System.out.println(SUN.nextDay());
        System.out.println("------------------------------------->");

        WeekDay weekDay = WeekDay.MON;
        System.out.println("weekDay: " + weekDay);
        System.out.println(weekDay.nextDay());
        System.out.println(weekDay.name());
        System.out.println(weekDay.ordinal());
        System.out.println(weekDay.compareTo(WeekDay.SUN));//1 不相同  0相同
        //静态方法
        System.out.println(WeekDay.values().length);
        // java.lang.IllegalArgumentException: No enum constant com.improve.enum_s.WeekDay.TUE1
        System.out.println(WeekDay.valueOf("TUE"));// TUE1

    }
}
