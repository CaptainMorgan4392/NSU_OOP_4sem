package delivery.path;

public abstract class Path implements Comparable <Path> {
    private int length;

    protected Path(int length) {
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

    @Override
    public int compareTo(Path other) {
        return Integer.compare(this.getLength(), other.getLength());
    }
}
