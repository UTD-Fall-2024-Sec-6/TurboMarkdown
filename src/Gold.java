// Aaron Jacob
// AXJ210111

public class Gold extends Customer {
    private int discountPercentage;

    public Gold(String first, String last, String id, double amount, int discount) {
        super(first, last, id, amount);
        discountPercentage = discount;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discount) {
        discountPercentage = discount;
    }
}
