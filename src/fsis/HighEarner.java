package fsis;

/**
 * Created by Thu Thuy Nguyen on 14/11/2016.
 */
public class HighEarner extends Customer implements Document{
    private float income;
    public HighEarner(int id, String name, String phoneNumber, String address, float income) {
        super(id, name, phoneNumber, address);
        this.income = income;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    @Override
    public String toHtmlDoc() {
        return "<html>"+
                "<head><title>Customer: "+getName()+"</title></head>"+
                "<body>"+
                super.getId() +" "+super.getName()+" "+super.getPhoneNumber()+" "+super.getAddress()+" "+income+
                "</body></html>";
    }
}
