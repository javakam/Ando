package design.filter;

/**
 * Created by changbao on 2018/7/18  星期三 .
 */
public class MsgProcessor {
    private FilterChain mFilterChain;
    //    filter[] filters = {new HTMLFilter(), new SensitiveFilter()};
    private String msg;

    public FilterChain getmFilterChain() {
        return mFilterChain;
    }

    public void setmFilterChain(FilterChain mFilterChain) {
        this.mFilterChain = mFilterChain;
    }

    public MsgProcessor(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String process() {
        return mFilterChain.doFilter(msg);
    }
}
