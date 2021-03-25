package product;

public class Product {
    private static class IDGenerator {
        private static int newID = 1;

        private static int getNewID() {
            int returnID = newID;
            newID++;

            return returnID;
        }
    }

    private int uniqueID;
    ProductType productType;

    public Product(ProductType productType) {
        this.uniqueID = IDGenerator.getNewID();
        this.productType = productType;
    }

    public int getUniqueID() {
        return uniqueID;
    }
}
