// Aaron Jacob
// AXJ210111

public class Customer {
    private String firstName;
    private String lastName;
    private String guestId;
    private double amountSpent;

    public Customer(String first, String last, String id, double amount) {
        firstName = first;
        lastName = last;
        guestId = id;
        amountSpent = amount;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGuestId() {
        return guestId;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public void setFirstName(String first) {
        firstName = first;
    }

    public void setLastName(String last) {
        lastName = last;
    }

    public void setGuestId(String id) {
        guestId = id;
    }

    public void setAmountSpent(double amount) {
        amountSpent = amount;
    }
}
