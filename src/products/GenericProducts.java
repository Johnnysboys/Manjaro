/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;

import java.util.ArrayList;

/**
 *
 * @author Anders
 */
public class GenericProducts extends Product {
    //Det er et generisk produkt. Alle produkter har et id, navn, pris og beskrivelse. 
    //Klassen er lavet, fordi produkt er abstrakt og der derfor ikke kan laves en instans af den.
    //Da genericProduct ikke er abstract. 
    //Det gjorde det nemmere ift. databaseopsætningen
    public GenericProducts(int productId, String productName, double price, String productDesc) {
        super(productId, productName, price, productDesc);
        
    }

    //Det er en fejl, denne metode skulle være fjernet
    @Override 
    public ArrayList<String> getProductRow() {  
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
