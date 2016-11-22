package fsis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Thu Thuy Nguyen on 13/11/2016.
 */
public class SortedSet {

    private ArrayList<Comparable> elements;

    public SortedSet(){
        elements = new ArrayList<>();
    }

    public void insert (Comparable customer){
        if (elements.size() >0){
            Iterator iterator = element();
            while (iterator.hasNext()){
                Customer customer1 = (Customer) iterator.next();
                if (customer1.compareTo(customer)>0 && getIndex(customer) <0 ){
                    elements.add(getIndex(customer1),customer);
                    customer = customer1;
                }
            }
            if (getIndex(customer)<0){
                elements.add(customer);
            }
        }else {
            elements.add(customer);
        }
    }


    public void remove (Comparable customer){
        int i = getIndex(customer);
        if (i < 0){
            elements.set(i, elements.get(elements.size()-1));
            elements.remove(elements.size()-1);
        }
    }
    public Iterator element (){
        return null;
    }

    public int size(){
        return elements.size();
    }
    @Override
    public String toString() {
        if (elements.size()==0){
            return "List Customer = {}";
        }

        String customers = "(List Customer {";
        for (Comparable element : elements) {
            customers = customers + element.toString();
        }
            return customers + "}";
    }
    private int getIndex (Comparable customer){
        for (int i = 0; i <elements.size() ; i++) {
            if (customer.compareTo(elements.get(i))==0){
                return i;
            }
        }
        return -1;
    }


    private class InSetGen implements Iterator {
        private int index;

        public InSetGen (){
            index = -1;
        }
        @Override
        public boolean hasNext() {
            return (index < elements.size() -1);
        }

        @Override
        public Object next() {
            if (index < elements.size() -1){
                index++;
                return elements.get(index);
            }
            throw new NoSuchElementException("InSet.elements");
        }
        public void remove(){

        }
    }
}
