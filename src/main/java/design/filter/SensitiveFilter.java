package design.filter;

/**
 * Created by changbao on 2018/7/18  星期三 .
 */
public class SensitiveFilter implements Filter {
    @Override
    public String doFilter(String s) {
        return s.replace("大保健", "大宝剑");
    }
}
