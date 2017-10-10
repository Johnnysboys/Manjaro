/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import electroshop.Order;
import electroshop.connectors.OrderConnector;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import products.GenericProducts;

/**
 *
 * @author TheSinding
 */
public class testOrderInsert {
    public static void main(String[] args) {
        OrderConnector conn = new OrderConnector("jdbc:postgresql://151.80.57.19:5432/admin_semester", "admin_willf", "2111993");
        Order order = new Order(1, 2.0, false);
        order.addProduct(new GenericProducts(1, "Test", 2.0, "A test"), 2);
        order.addProduct(new GenericProducts(2, "Test1", 2.0, "A test"), 3);
        order.addProduct(new GenericProducts(3, "Test2", 2.0, "A test"), 4);
        try {
            conn.insertOrder(order);
        } catch (SQLException ex) {
            Logger.getLogger(testOrderInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
