package enum_s;

/**
 * 用带有单一元素的枚举做单例：
 * <p>
 * 一方面是因为枚举默认的构造器是 private 的；
 * 另一方面是因为元素的唯一性
 */
public enum SingletonEnum {
    INSTANCE;
}
