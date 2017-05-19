/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;

import java.util.ArrayList;

/**
 *
 * @author Jakob
 */
public abstract class Product {
    
    private int productId;
    private String productName;
    private double price;
    private String description;
    private String firstAtt;
    
    
    
    
    public Product(int productId, String productName, double price, String productDesc){
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = productDesc;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString(){
        return "This product is named: " + productName;
    }
    
    public abstract ArrayList<String> getProductRow();

}
