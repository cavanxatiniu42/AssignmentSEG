package fsis;

/**
 * Created by Thu Thuy Nguyen on 13/11/2016.
 */
public class Customer implements Comparable, Document{
    private int id;
    private String name;
    private String phoneNumber;
    private String address;

    public Customer(int id, String name, String phoneNumber, String address) throws NotPossibleException {
        if (validateId(id) && validateName(name) && validatePhoneNumber(phoneNumber) && validateAddress(address)){
            this.id = id;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.address = address;
        }
        throw new NotPossibleException("Customer info is not validated!");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    private boolean validateId (int id){
        return id >=1 && id <= 99999999;
    }

    private boolean validateName (String name){
        return name!= null && name.length()<=50;
    }

    private boolean validatePhoneNumber (String phoneNumber){
        return phoneNumber!=null && phoneNumber.length()<=10;
    }

    private boolean validateAddress (String address){
        return address!=null && address.length() <=100;
    }
    public boolean repOK(int id, String name, String phoneNumber, String address){
        return validateId(id) && validateName(name) &&
                validatePhoneNumber(phoneNumber) && validateAddress(address);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) throws NullPointerException, ClassCastException {
        if (o == null)
            throw new NullPointerException("Customer.compareByName");
        else if (!(o instanceof Customer))
            throw new ClassCastException("Customer.compareByName: not a Customer " + o);

        Customer v = (Customer) o;
        return this.name.compareTo(v.name);
    }

    @Override
    public String toHtmlDoc() {
        return "<html>" +
                "<head>" +
                "<title>Customer: " + this.getName() + "</title>" +
                "</head>" +
                "<body>" + this.id + " " + this.name + " " + this.phoneNumber + " " + this.address + "</body>" +
                "</html>";
    }
}

