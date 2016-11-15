package fsis;

import java.util.Iterator;
import java.util.Arrays;

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
    public CustomerWorkSheet(){}

    /**
     * @effects
     *   invoke promptForCustomer to prompt the user to enter details of
     *   a customer, create a Customer object from these details and
     *   invoke addCustomer to add the object to this.
     *
     *   If invalid details were entered then throws NotPossibleException.
     */
    public void enterACustomer() throws NotPossibleException{}

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
        return null;
    }

    /**
     * @effects
     *   add c to this.objects and
     *   add to this.engine a Doc object created from c.toHtmlDoc
     */
    public void addCustomer(Customer c){}

    /**
     * @modifies  System.out
     * @effects
     *    if this.objects == null
     *      prints "empty"
     *    else
     *      prints each object in this.objects one per line to the standard output
     *      invoke writeHTMLReport to write an HTML report to file
     */
    public void displayReport(){}

    /**
     * @effects
     *   if objects is empty
     *     return "empty"
     *   else
     *     return a string containing each object in this.objects one per line
     */
    @Override
    public String toString(){
        return null;
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
    public void writeHTMLReport(){}

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
    public void search() throws NotPossibleException{}

    /**
     * @effects
     *   prompt the user to enter some keywords and
     *   return an array containing these or null if no keywords were entered
     */
    public String[] promptForKeywords(){
        return null;
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
        return null;
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
    public void writeSearchReport(Query q){}

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

