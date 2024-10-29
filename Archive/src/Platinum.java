// Aaron Jacob
// AXJ210111

public class Platinum extends Customer {
    private int bonusBucks;

    public Platinum(String first, String last, String id, double amount, int bonusBucks) {
        super(first, last, id, amount);
        this.bonusBucks = bonusBucks;
    }

    public int getBonusBucks() {
        return bonusBucks;
    }

    public void setBonusBucks(int bonusBucks) {
        this.bonusBucks = bonusBucks;
    }
}
