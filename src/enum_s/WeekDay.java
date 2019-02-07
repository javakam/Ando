package enum_s;

/**
 * 实现带有构造方法的枚举
 */
public enum WeekDay {
    //枚举元素必须放在第一行
    //枚举元素使用传参 SUN(1)
    SUN(1), MON("星期一。。。"), TUE(), WEN, THU, FRI, SAT;

    // 默认是 private 修饰 , 可省略
    // Modifier 'public/protected' not allowed here .
    WeekDay() {
        System.out.println("first");
    }

    WeekDay(int day) {
        System.out.println("second");
    }

    WeekDay(String day) {
        System.out.println("third : " + day);
    }

    public WeekDay nextDay() {
        if (this == SUN) {
            return MON;
        } else if (this == MON) {
            return TUE;
        } else if (this == TUE) {
            return WEN;
        } else {
            return THU;
        }
    }
}
