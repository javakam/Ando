package arraylist;

public class LayoutParams {
    public int width;
    public int height;
    public String tag;

    public LayoutParams(int width, int height, String tag) {
        this.width = width;
        this.height = height;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "LayoutParams{" +
                "width=" + width +
                ", height=" + height +
                ", tag='" + tag + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LayoutParams that = (LayoutParams) o;

        if (width != that.width) return false;
        if (height != that.height) return false;
        return tag.equals(that.tag);
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + tag.hashCode();
        return result;
    }
}
