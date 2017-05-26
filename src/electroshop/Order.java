package electroshop;

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
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    private String adress;
    
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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
    
    public ObservableList<Product> getKeySet(){
        List<Product> keys = new ArrayList<>(productMap.keySet());
        
        ObservableList<Product> keyList = FXCollections.observableArrayList(keys);
        
        return keyList;
        
    }
    
    
}
