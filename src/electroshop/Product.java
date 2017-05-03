/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop;

/**
 *
 * @author Jakob
 */
public class Product {
    
    private int productId;
    private String productName;
    private double price;
    private String productDesc;
    private String[] category;
    
    public Product(int productId, String productName, double price, String productDesc){
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.productDesc = productDesc;
    }
    
    public int getProductId(){
        return productId;
    }
    
    public String getProductName(){
        return productName;
    }
    
    public double getPrice(){
        return price;
    }
    
    public String getProductDesc(){
        return productDesc;
    }
}
