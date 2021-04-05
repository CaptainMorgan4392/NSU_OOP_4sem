package production;

public class ProductUnificator {
    private static int currentID = 1;

    static synchronized int generateNextID() {
        return currentID++;
    }
}
