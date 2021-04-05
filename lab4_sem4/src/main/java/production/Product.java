package production;

public class Product {
    private final ProductType productType;
    private final int uniqueID;

    public Product(ProductType productType) {
        this.productType = productType;
        this.uniqueID = ProductUnificator.generateNextID();
    }

    public ProductType getProductType() {
        return productType;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    @Override
    public String toString() {
        return "Product of type: " + this.getProductType().getTypeLiteral() + " with ID: " + this.getUniqueID() + " ";
    }
}
