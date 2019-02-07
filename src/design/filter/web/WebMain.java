package design.filter.web;

/**
 * 可以参考 java.servlet 下面的Filter进行学习
 */
public class WebMain {
    public static void main(String[] args) {
        String str = "大家好(:，<html>，今天天气不错，眼光明媚，非常适合大保健。";
        Request request = new Request();
        request.setRequestStr(str);
        Response response = new Response();
        response.setResponseStr("response");
        FilterChain chain = new FilterChain();
        chain.addFilter(new HTMLFilter())
                .addFilter(new SensitiveFilter());
        // TODO 演示断点
        chain.doFilter(request, response, chain);
        System.out.println("请求 : " + request.getRequestStr());
        System.out.println("响应 : " + response.getResponseStr());
    }
}
