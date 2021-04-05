package production;

import java.util.Objects;

public class ProductType {
    private final String typeLiteral;

    public ProductType(String typeLiteral) {
        this.typeLiteral = typeLiteral;
    }

    public String getTypeLiteral() {
        return typeLiteral;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductType that = (ProductType) o;
        return Objects.equals(typeLiteral, that.typeLiteral);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeLiteral);
    }
}
