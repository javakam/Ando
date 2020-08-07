package design.filter.web;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter {
    private List<Filter> mFilters = new ArrayList<>();
    private int index = 0;

    public FilterChain addFilter(Filter f) {
        this.mFilters.add(f);
        return this;// 开发小技巧：return this，返回类对象本身，方便外部进行链式调用
    }

    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        if (index == mFilters.size()) {
            return;
        }
        mFilters.get(index++).doFilter(request, response, chain);
        // 死循环...
//        index++;
    }
}
