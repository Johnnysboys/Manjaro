/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import products.Product;

/**
 *
 * @author Jakob
 */
public class Basket {
    
    //Der erklæres et hashMap bestående af Product-objekt og Integer
    private HashMap<Product, Integer> productMap;
    
    //Instantiere mappet til at være et hashMap
    public Basket() {
        this.productMap = new HashMap<>();
    }

    /**
     * Enters a new product in the Basket HashMap. If the given key already
     * exists it adds the quantity to the value.
     *
     * @param product - The product to add to the basket (Key).
     * @param quantity - The quantity of said product to add (Value).
     */
    //Tilføjer produkt til kurven
    public void addProduct(Product product, Integer quantity) {
        if (productMap.containsKey(product)) {
            //Hvis produktet allerede findes tilføjes mængden (quantity)
            productMap.put(product, productMap.get(product) + quantity);
        } else {
            //Ellers lægges produktet og mængden i hashMappet
            productMap.put(product, quantity);
        }
    }

    /**
     * Removes a previously entered product from the HashMap.
     *
     * @param product - The product (Key) to remove from the HashMap.
     */
    public void removeProduct(Product product) {
        //Hvis mappet indeholder produktet som key, fjern det
        if (productMap.containsKey(product)) {
            productMap.remove(product);
        }
    }

    /**
     * Clears the baskets HashMap of all entries.
     */
    //Ryd kurven
    public void emptyBasket() {
        productMap.clear();
    }

    /**
     * Iterates through the basket, and returns a double with the total price of
     * all products.
     *
     * @return - A double containing the price for the entire basket.
     */
    public double getTotal() {
        double total = 0;

        Set<Product> keySet = productMap.keySet();
        Iterator<Product> keyIterator = keySet.iterator();
        
        while (keyIterator.hasNext()) {
            Product product = keyIterator.next();
            int currentValue = productMap.get(product);

            total = total + (product.getPrice() * currentValue);

        }

        return total;
    }

    /**
     * Returns the baskets HashMap, containing entries for every product added
     * to the basket.
     *
     * @return - Returns a HashMap: <Product, String> where the integer is the
     * quantity of the matching product in the basket.
     */
    public HashMap getProductMap() {
        return productMap;
    }

}
