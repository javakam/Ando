package design.filter;

/**
 * Created by changbao on 2018/7/18  星期三 .
 */
public class FaceFilter implements Filter {
    @Override
    public String doFilter(String s) {
        return s.replace("(:", "O(∩_∩)O");
    }
}
