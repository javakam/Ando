package jvm;

public class JVM01_Math {

    public static final Integer CONSTANT = 666;

    public int compute() {
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) {

        JVM01_Math jvm01Math = new JVM01_Math();
        jvm01Math.compute();


    }

}
