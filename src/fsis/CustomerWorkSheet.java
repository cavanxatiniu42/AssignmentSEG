package fsis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import static fsis.TextIO.*;

import kengine.Engine;
import kengine.Doc;
import kengine.DocCnt;
import kengine.Query;
import kengine.NotPossibleException;

/**
 * @overview
 *   Represents a worksheet program that enables a user to create customer objects,
 *   display a report about them, and to search for objects of interest using keywords.
 *
 * @attributes
 *   objects    SortedSet<Customer>
 *   engine     Engine
 *
 * @abstract_properties
 *   mutable(objects)=true /\ optional(objects)=false /\
 *   mutable(engine)=false /\ optional(engine)=false /\
 *   size(objects) > 0 ->
 *     (for all o in objects: o.toHtmlDoc() is in engine)
 * @author dmle
 */
public class CustomerWorkSheet {
    @DomainConstraint(type="Collection",mutable=true,optional=false)
    private SortedSet objects;
    @DomainConstraint(type="Engine",mutable=false,optional=false)
    private Engine engine;

    /**
     * @effects
     *   initialise this to include an empty set of objects and an empty engine
     */
    public CustomerWorkSheet(){
        objects = new SortedSet();
        engine = new Engine();
    }

    /**
     * @effects
     *   invoke promptForCustomer to prompt the user to enter details of
     *   a customer, create a Customer object from these details and
     *   invoke addCustomer to add the object to this.
     *
     *   If invalid details were entered then throws NotPossibleException.
     */
    public void enterACustomer() throws NotPossibleException{
        TextIO.putln("Input details of a customer: ");
        TextIO.putln("Input customer's id: ");
        int id = TextIO.getInt();
        TextIO.getln();
        TextIO.putln("Input customer's name: ");
        String name = TextIO.getln();
        TextIO.putln("Input customer's phone number:");
        String phoneNumber = TextIO.getln();
        TextIO.putln("Input customer's address: ");
        String address = TextIO.getln();
        Customer customer = new Customer(id, name, phoneNumber, address);
        addCustomer(customer);
    }

    /**
     * @effects
     *   prompt the user whether to enter details for Customer or HighEarner,
     *   then prompt the user for the data values needed to create an object
     *   for the selected type.
     *
     *   Create and return a Customer object from the entered data. Throws
     *   NotPosibleException if invalid data values were entered.
     */
    public Customer promptForCustomer() throws NotPossibleException{
            TextIO.putln("Do you want to enter details for Customer or HighEarner?");
            TextIO.putln("1. Customer");
            TextIO.putln("2. HighEarner");
            int choice;
            switch (choice = TextIO.getInt()){
                case 1:
                    TextIO.putln("Input customer's id: ");
                    int id = TextIO.getInt();
                    TextIO.getln();
                    TextIO.putln("Input customer's name: ");
                    String name = TextIO.getln();
                    TextIO.putln("Input customer's phone number: ");
                    String phoneNumber = TextIO.getln();
                    TextIO.putln("Input customer's address: ");
                    String address = TextIO.getln();
                    Customer customer = new Customer(id, name, phoneNumber, address);
                    return customer;
                case 2:
                    TextIO.putln("Input high earner's id: ");
                    int earnerId = TextIO.getInt();
                    TextIO.getln();
                    TextIO.putln("Input high earner's name: ");
                    String earnerName = TextIO.getln();
                    TextIO.putln("Input high earner's phone number: ");
                    String earnerPhoneNumber = TextIO.getln();
                    TextIO.putln("Input high earner's address: ");
                    String earnerAddress = TextIO.getln();
                    TextIO.putln("Input high earner's income: ");
                    float income = TextIO.getFloat();
                    HighEarner highEarner = new HighEarner(earnerId,earnerName, earnerPhoneNumber, earnerAddress, income);
                    return highEarner;
            }
        return null;
    }

    /**
     * @effects
     *   add c to this.objects and
     *   add to this.engine a Doc object created from c.toHtmlDoc
     */
    public void addCustomer(Customer c){
        this.objects.insert(c);
        Doc doc = new Doc(c.toHtmlDoc());
        this.engine.addDoc(doc);
    }

    /**
     * @modifies  System.out
     * @effects
     *    if this.objects == null
     *      prints "empty"
     *    else
     *      prints each object in this.objects one per line to the standard output
     *      invoke writeHTMLReport to write an HTML report to file
     */
    public void displayReport(){
        if (this.objects == null){
            System.out.println("empty");
        } else {
            System.out.println("Display customer report: ");
            System.out.println(toString());
            writeHTMLReport();

        }
    }

    /**
     * @effects
     *   if objects is empty
     *     return "empty"
     *   else
     *     return a string containing each object in this.objects one per line
     */
    @Override
    public String toString(){
        String string = " ";
        if (this.objects.size() == 0){
            System.out.println("empty");
        } else {
            Iterator iterator = objects.element();
            while (iterator.hasNext()){
                Object object = iterator.next();
                if (object instanceof Customer){
                    string += ((Customer)object).toString()+"\n";
                } else if (object instanceof HighEarner){
                    string += ((HighEarner)object).toString()+"\n";
                }
            }
        }
        return string;
    }

    /**
     * @modifies  objects.html (in the program directory)
     * @effects
     *    if this.objects is empty
     *      write an HTML document to file with the word "empty" in the body
     *    else
     *      write an HTML document to file containing a table, each row of which
     *      lists an object in this.objects
     *
     *    The HTML document must be titled "Customer report".
     */
    public void writeHTMLReport(){
        TextIO.writeFile("objects.html");
        TextIO.putln("<html>" +
                "<head><title>Customer report</title></head>" +
                "<body>");
        if(this.objects==null){
            TextIO.putln("<center>"
                    + "<h1>Empty<h1>"
                    + "</center>");
        }else{
            TextIO.putln("<table border=\"1\">" +
                    "<th>ID</th>" +
                    "<th>Name</th>" +
                    "<th>PhoneNumber</th>" +
                    "<th>Address</th>" +
                    "<th>Income</th>");
            Iterator items=objects.element();
            while(items.hasNext()){
                TextIO.putln("<tr>");
                Object element= items.next();
                if(element instanceof HighEarner){
                    HighEarner highEarner=(HighEarner) element;
                    TextIO.putln("<td>"+highEarner.getId()+"</td>"
                            +"<td>" +highEarner.getName()+"</td>"
                            +"<td>" +highEarner.getPhoneNumber()+"</td>"
                            +"<td>"+ highEarner.getAddress()+"</td>"
                            +"<td>"+ highEarner.getIncome()+"</td>");
                }else if (element instanceof Customer) {
                    Customer cust=(Customer)element;
                    TextIO.putln("<td>"+cust.getId()+"</td>"
                            +"<td>" +cust.getName()+"</td>"
                            +"<td>" +cust.getPhoneNumber()+"</td>"
                            +"<td>"+ cust.getAddress()+"</td>");
                }
                TextIO.putln("</tr>");
            }
            TextIO.putln("</table>");
        }
        TextIO.put("</body>" +
                "</html>");
    }

    /**
     * @modifies System.out
     * @effects
     *   prompt the user to enter one or more keywords
     *   if keywords != null AND keywords.length > 0
     *     invoke operation search(String[]) to search using keywords,
     *
     *     if fails to execute the query
     *       throws NotPossibleException
     *     else
     *       print the query string to the standard output.
     *       invoke operation writeSearchReport(Query) to output the query to an HTML file
     *   else
     *     print "no search keywords"
     */
    public void search() throws NotPossibleException{
        TextIO.putln("Input your keyword");
        String[] keyword = promptForKeywords();
        if (keyword != null &&keyword.length >0){
            try {
                Query query = search(keyword);
                System.out.println(query.toString());
                writeSearchReport(query);
            }catch (fsis.NotPossibleException e){
                throw new NotPossibleException("Fail to execute query");
            }
        } else {
            System.out.println("no search keyword");
        }
    }

    /**
     * @effects
     *   prompt the user to enter some keywords and
     *   return an array containing these or null if no keywords were entered
     */
    public String[] promptForKeywords(){
        TextIO.putln("Enter some keywords to search");
        try {
            String keywords = TextIO.getln();
            String[] arrayList = keywords.split(" ");
            return arrayList;
        }catch (RuntimeException e){
             return null;
        }
    }

    /**
     * @requires words != null /\ words.length > 0
     * @effects
     *   search for objects whose HTML documents match with the query containing words
     *   and return a Query object containing the result
     *
     *   If fails to execute query using words
     *     throws NotPossibleException
     */
    public Query search(String[] words) throws NotPossibleException{
        Query query = new Query();
        if (words.length == 1){
            query = engine.queryFirst(words[0]);
        } else {
            query = engine.queryFirst(words[0]);
            for (int i = 1; i <words.length; i++) {
                query = engine.queryMore(words[i]);
            }
        }
        return query;
    }

    /**
     * @requires q != null
     * @modifies  search.html (in the program directory)
     * @effects
     *   write to file an HTML document containing the query keys and a table,
     *   each row of which lists a match
     *
     *   The HTML document must be titled "Search report".
     */
    public void writeSearchReport(Query q){
        Iterator items=q.matchIterator();
        TextIO.writeFile("search.html");
        TextIO.putln(
                "<html>" +
                        "<title>Search report</title>" +
                        "<body>"+
                        "Query: ");

        String str=Arrays.toString(q.keys());

        TextIO.putln(str+"<br>"+
                "Results:"+"<br>"+
                "<table border=\"1\">" +
                "<th>Documents</th>" +
                "<th>Sum freqs</th>");
        while(items.hasNext()){
            DocCnt	docCnt=(DocCnt) items.next();
            Doc doc=docCnt.getDoc();
            TextIO.putln(
                    "<tr>"+
                            "<td>"+doc.title()+"</td>"+
                            "<td>"+docCnt.getCount()+"</td>"+
                            "</tr>");
        }
        TextIO.putln(
                "</table>"+
                        "</body>" +
                        "</html>");
    }

    /**
     * @effects
     *   initialise a CustomerWorkSheet
     *   ask the users to create the five Customer objects
     *   display report about the objects
     *   ask the users to enter a keyword query to search for objects and display
     *   the result
     */
    public static void main(String[] args) {
        // initialise a CustomerWorkSheet
        putln("Initialising program...");
        CustomerWorkSheet worksheet = new CustomerWorkSheet();

        try {
            // ask user to create 5 test customer objects
            putln("\nCreating some customers...");
            int num = 5;
            for (int i = 0; i < num; i++) {
                worksheet.enterACustomer();
            }

            // display report about the objects
            worksheet.displayReport();

            // ask the users to enter a keyword query to search for objects and display
            // the result
            worksheet.search();

            // end
            putln("Good bye.");
        } catch (NotPossibleException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

