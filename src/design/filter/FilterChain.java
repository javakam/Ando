package design.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * 让 FilterChain 实现 filter 接口的妙处在于，可以为过滤链添加其他过滤链！
 * <p>
 * Created by changbao on 2018/7/18  星期三 .
 */
public class FilterChain implements Filter {
    private List<Filter> mFilters = new ArrayList<>();

    public FilterChain addFilter(Filter f) {
        this.mFilters.add(f);
        return this;// 开发小技巧：return this，返回类对象本身，方便外部进行链式调用
    }

    public String doFilter(String s) {
        for (Filter f : mFilters) {
            s = f.doFilter(s);
        }
        return s;
    }
}
