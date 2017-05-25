/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop.connectors;

import electroshop.Order;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import products.Desktop;
import products.Fridge;
import products.Laptop;
import products.Product;

/**
 *
 * @author Troels
 */
public class OrderConnector extends SuperDB {
    
    public OrderConnector(String host, String user, String pword) {
        super(host, user, pword);
    }
    
    public ObservableList<Order> getOrderOverview(String email) throws SQLException{
        ObservableList <Order> orderOverview = FXCollections.observableArrayList();
        int customerID = 0;
        String productType;
        
        String query1 = "SELECT id FROM accounts WHERE email = ?" ;
        PreparedStatement ps = this.getCon().prepareStatement(query1);
        ps.setString(1, email);
        ResultSet rs1 = ps.executeQuery();

        
        while (rs1.next()){
            customerID = rs1.getInt(1);
        }
        
        Statement s = this.getCon().createStatement();
        query1 = "SELECT * FROM orders WHERE customerID = " + Integer.toString(customerID);
        rs1 = s.executeQuery(query1);
        
        
        while (rs1.next()){
            
            Order order = new Order(rs1.getInt(1), rs1.getLong(2), rs1.getInt(3));
            orderOverview.add(order);
            
            
            String query2 = "SELECT productID FROM orderlines WHERE orderID = " + Integer.toString(rs1.getInt(1));
            ResultSet rs2 = s.executeQuery(query2);
            
            while (rs2.next()){
            
            String query3 = "SELECT producttype FROM  products WHERE product id = " + Integer.toString(rs2.getInt(1));
            ResultSet rs3 = s.executeQuery(query3);
            rs3.next();
            productType = rs3.getString(1);
            String query4;
            ResultSet rs4 ;
            
            switch (productType) {
                case "desktop":
                    query4 = "SELECT * from desktops WHERE productid = " + Integer.toString(rs2.getInt(1));
                    rs4 = s.executeQuery(query4);
                    while (rs4.next()){
                        Product product = new Desktop(rs4.getString(8), rs4.getBoolean(9), rs4.getDouble(5), rs4.getInt(6), rs4.getInt(7), rs4.getInt(1), rs4.getString(2), rs4.getDouble(3), rs4.getString(4));
                    }
                    break;
                    
                case "freezer":
                    query4 = "SELECT * from freezers WHERE productid = " + Integer.toString(rs2.getInt(1));
                    rs4 = s.executeQuery(query4);
                    while (rs4.next()){
                        Product product = new Fridge (rs4. getDouble(7), rs4.getInt(8), rs4.getInt(5), rs4.getInt(6), rs4.getInt(1), rs4.getString(2), rs4.getDouble(3), rs4.getString(4));
                    }
                    break;
                    
                case "laptop":
                    query4 = "SELECT * from laptops WHERE productid = " + Integer.toString(rs2.getInt(1));
                    rs4 = s.executeQuery(query4);
                    while(rs4.next()){
                        Product product = new  Laptop(rs4.getDouble(8), rs4.getDouble(9), rs4.getInt(10), rs4.getDouble(5), rs4.getInt(6), rs4.getInt(7), rs4.getInt(1), rs4.getString(2), rs4.getDouble(3), rs4.getString(4));
                    }
                    
                    break;
                    
                case "washing machine":
                    
                    break;
                
                case "radio":
                    
                    break;
                
                case "tv":
                    
                    break;
            }
            
            
        }
        
        return orderOverview;
    }  
    
    
   
    
    
}
