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
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import products.GenericProducts;
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
        String query = "SELECT orders.orderdate, orders.customerid, orderlines.*, "
                + "products.name, products.description, products.price,"
                + "products.producttype FROM orders\n"
                + "JOIN accounts ON accounts.id = orders.customerID \n"
                + "JOIN orderlines ON orders.orderID = orderlines.orderID\n"
                + "JOIN products ON orderlines.productid = products.id\n"
                + "WHERE accounts.email = ?;";

        PreparedStatement ps = this.getCon().prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        // Temp int to keep track of which order we are looking at
        int checkingId = -1;
        Order currentOrder = null;
        while (rs.next()) {
            Product product = new GenericProducts(rs.getInt("productid"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
            if (checkingId != rs.getInt("orderid")) {
                // Dont add the currentOrder to the list if it's the first tuple
                if (!rs.isFirst()) {
                    orderOverview.add(currentOrder);
                }
                // Create a new order
                currentOrder = new Order(rs.getInt("orderid"), rs.getDouble("price"), false, rs.getLong("orderdate"));
                // Change the current order ID that we are using
                checkingId = rs.getInt("orderid");
            }
            if (currentOrder != null) {
                currentOrder.addProduct(product, rs.getInt("amount"));
            }
            
            currentOrder.setPriceTotal(currentOrder.getCalcTotal());
            // If it's the last tuple, add the currentOrder to the list
            if (rs.isLast()) {
                orderOverview.add(currentOrder);
            }
        }
        return orderOverview;
    }

    public boolean insertOrder(Order order) throws SQLException {
        String orderLineQuery = "insert into orderlines (orderid, productid, amount, price) VALUES (?,?,?,?)";
        String query = "insert into orders (orderdate, customerid) VALUES (?, ?) RETURNING orderid";
        PreparedStatement ps = this.getCon().prepareStatement(query);
        ps.setLong(1, order.getOrderDate().getTime());
        ps.setInt(2, order.getCustomerId());
        
        ResultSet rs = ps.executeQuery();
        // Move the pointer in the result set
        rs.next();
        int orderId = rs.getInt("orderid");
        
        ps.close();
        rs.close();
        Map<Product, Integer> orderLines = order.getProductMap();
        
        Statement statement = this.getCon().createStatement();
        
        PreparedStatement orderLineStatement = this.getCon().prepareStatement(orderLineQuery);
        for(Product p : orderLines.keySet()){
            orderLineStatement.setInt(1, orderId);
            orderLineStatement.setInt(2, p.getProductId());
            orderLineStatement.setInt(3, orderLines.get(p));
            orderLineStatement.setDouble(4, p.getPrice());
            orderLineStatement.addBatch();
        }
        orderLineStatement.executeBatch();
        orderLineStatement.close();
        return true;
    }

}
