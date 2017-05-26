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

    public GenericProducts(int productId, String productName, double price, String productDesc) {
        super(productId, productName, price, productDesc);
    }

    @Override
    public ArrayList<String> getProductRow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
