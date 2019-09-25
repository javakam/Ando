package reflect_s;

public class MethodMain {

    public static void main(String[] args) {
        System.out.println(" ......MethodMain.main ......");
        if (args != null && args.length > 0) {
            for (String s : args) {
                System.out.println("s = " + s);
            }
        }
    }
}
