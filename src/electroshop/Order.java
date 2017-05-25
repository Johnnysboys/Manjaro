package electroshop;

import electroshop.Basket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import products.Product;

/**
 *
 * @author Troels
 */
public class Order {
    
    private int orderID;
    private Date orderDate;
    private HashMap<Product, Integer> productMap;
    private int customerId;
    private double priceTotal;
    private boolean isPaid;
    
    public Order(int orderID, long date, int customerId) {
    this.orderID = orderID;    
    this.customerId = customerId;
        this.orderDate = new Date(date);
    }
    
     public Order(int customerId, double priceTotal, boolean isPaid) {
        this.customerId = customerId;
        this.orderDate = new Date();
        this.priceTotal = priceTotal;
        this.isPaid = isPaid;
    }

    public Order(Basket basket) {
        this.productMap = basket.getProductMap();
        this.priceTotal = basket.getTotal();
        this.orderDate = new Date();
    }
    
    public Order(Basket basket, int orderID) {
        this.productMap = basket.getProductMap();
        this.priceTotal = basket.getTotal();
        this.orderID = orderID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public HashMap<Product, Integer> getProductMap() {
        return productMap;
    }

    public void setProductMap(HashMap<Product, Integer> productMap) {
        this.productMap = productMap;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    
    public void addProduct(Product product, int amount){
        productMap.put(product, amount);
    }
    
    public ObservableList<Product> getKeySet(){
        List<Product> keys = new ArrayList<>(productMap.keySet());
        
        ObservableList<Product> keyList = FXCollections.observableArrayList(keys);
        
        return keyList;
        
    }
    
    
}
