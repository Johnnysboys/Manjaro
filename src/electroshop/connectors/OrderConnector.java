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
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import products.Desktop;
import products.Fridge;
import products.GenericProducts;
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

    public ObservableList<Order> getOrderOverview(String email) throws SQLException {
        ObservableList<Order> orderOverview = FXCollections.observableArrayList();
        String query = "SELECT orders.orderdate, orders.customerid, orderlines.*, products.name, products.description, products.price, products.producttype FROM orders\n" +
                        "JOIN accounts ON accounts.id = orders.customerID \n" +
                        "JOIN orderlines ON orders.orderID = orderlines.orderID\n" +
                        "JOIN products ON orderlines.productid = products.id\n" +
                        "WHERE accounts.email = ?;";

        PreparedStatement ps = this.getCon().prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        int checkingId = -1;
        Order currentOrder = null;
        while (rs.next()) {
            Product product = new GenericProducts(rs.getInt("productid"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
            if(checkingId != rs.getInt("orderid")){
                if(!rs.isFirst()){
                    orderOverview.add(currentOrder);
                }
                System.out.println("New Order");
                currentOrder = new Order(rs.getInt("orderid"), 1, false);
                checkingId = rs.getInt("orderid");
            } 
            System.out.println("Adding product " + rs.getString("name"));
            currentOrder.addProduct(product, rs.getInt("amount"));
            if(rs.isLast()){
                orderOverview.add(currentOrder);
            }
        }
        return orderOverview;
    }
    
    public boolean insertOrder(Order order){
        String query = "insert into orders";
        return true;
    }

}
