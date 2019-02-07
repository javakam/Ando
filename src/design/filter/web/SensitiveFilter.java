package design.filter.web;

public class SensitiveFilter implements Filter {

    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        request.setRequestStr(request.getRequestStr().replace("大保健", "大宝剑")
                + "--SensitiveFilter--");
        chain.doFilter(request, response, chain);
        response.setResponseStr(response.getResponseStr() + "--SensitiveFilter--");
    }
}
