package enum_s;

/**
 * 实现带有抽象方法的枚举
 */
public enum TrafficLamp {
    /**
     * 每个元素分别是由枚举类的子类来生成的实例对象，这些子类采用类似内部类的方式进行定义
     */
    RED {
        @Override
        public TrafficLamp nextLamp() {
            return YELLOW;
        }
    },
    YELLOW {
        @Override
        public TrafficLamp nextLamp() {
            return GREEN;
        }
    },
    GREEN {
        @Override
        public TrafficLamp nextLamp() {
            return RED;
        }
    };

    /**
     * 获取下一个交通灯
     *
     * @return
     */
    public abstract TrafficLamp nextLamp();
}
