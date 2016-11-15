package fsis;

/**
 * Created by Thu Thuy Nguyen on 14/11/2016.
 */
public class HighEarner extends Customer {
    private float income;
    public HighEarner(int id, String name, String phoneNumber, String address) {
        super(id, name, phoneNumber, address);
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }
}
