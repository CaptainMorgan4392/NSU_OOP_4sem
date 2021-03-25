package delivery.service_interface;

public abstract class Path implements Comparable <Path> {
    private final int length;

    protected Path(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    @Override
    public int compareTo(Path other) {
        return Integer.compare(this.getLength(), other.getLength());
    }
}
