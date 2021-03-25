package product;

public class ProductType {
    String typeLiteral;

    public ProductType(String typeLiteral) {
        this.typeLiteral = typeLiteral;
    }

    public boolean equals(ProductType productType) {
        return this.getTypeLiteral().equals(productType.getTypeLiteral());
    }

    public String getTypeLiteral() {
        return typeLiteral;
    }
}
