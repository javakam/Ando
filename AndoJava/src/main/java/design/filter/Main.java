package design.filter;

/**
 * 责任链模式学习
 * <p>
 * Created by changbao on 2018/7/18  星期三 .
 */
public class Main {
    public static void main(String[] args) {
        /*
        已过滤该字符串为例，演示责任链模式
         */
        String str = "大家好(:，<html>，今天天气不错，眼光明媚，非常适合大保健。";
        // 采用 处理器+过滤链进行处理，也就是说 MsgProcessor 这个类并不多余
        MsgProcessor processor = new MsgProcessor(str);
        //【注】可以写到配置文件里！
        FilterChain chain = new FilterChain();
        chain.addFilter(new HTMLFilter())
                .addFilter(new SensitiveFilter());
        FilterChain chain2 = new FilterChain();
        chain2.addFilter(new FaceFilter());
        chain.addFilter(chain2);//

        processor.setmFilterChain(chain);

        String resultStr = processor.process();
        System.out.println("resultStr = " + resultStr);
        // resultStr = 大家好O(∩_∩)O，[html]，今天天气不错，眼光明媚，非常适合大宝剑。
    }
}
