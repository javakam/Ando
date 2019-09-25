package enum_s;

public abstract class WeekDay1 {
    private WeekDay1() {
    }

    //{}定义了一个WeekDay1的一个子类，并复写了其方法
    public static final WeekDay1 SUN = new WeekDay1() {
        @Override
        public WeekDay1 nextDay() {
            return MON;
        }
    };
    public static final WeekDay1 MON = new WeekDay1() {
        @Override
        public WeekDay1 nextDay() {
            return TUE;
        }
    };
    public static final WeekDay1 TUE = new WeekDay1() {
        @Override
        public WeekDay1 nextDay() {
            return SUN;
        }
    };

    //zxx:用抽象方法定义nextDay就将大量的if else语句转移成了一个个独立的类
    public abstract WeekDay1 nextDay();

   /* public WeekDay1 nextDay() {
        if (this == SUN) {
            return MON;
        } else if (this == MON) {
            return TUE;
        } else {
            return SUN;
        }
    }*/

    @Override
    public String toString() {
        return this == SUN ? "SUN" : "MON";
    }
}
