package design.behavioral.strategy;

/**
 * Created by changbao on 2018/7/23 0023 星期一 .
 */
public class Main {
    public static void main(String[] args) {
        int[] a = {9, 3, 7, 2, 5, 1};
        DataSorter.sort(a);
        DataSorter.print(a);
    }
}
