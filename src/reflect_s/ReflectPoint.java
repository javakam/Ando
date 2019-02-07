package reflect_s;

import java.util.Objects;

public class ReflectPoint {
    public int x;
    protected int y;
    private int z;

    public ReflectPoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReflectPoint)) return false;
        ReflectPoint point = (ReflectPoint) o;
        return x == point.x &&
                y == point.y &&
                z == point.z;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y, z);
    }

    //=============================================> homework
    public String str1 = "ball";
    public String str2 = "basketball";
    public String str3 = "machangbao";

    @Override
    public String toString() {
        return "ReflectPoint{" +
                "str1='" + str1 + '\'' +
                ", str2='" + str2 + '\'' +
                ", str3='" + str3 + '\'' +
                '}';
    }
}
