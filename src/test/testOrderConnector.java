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

/**
 *
 * @author Anders
 */
public class testOrderConnector {
    public static void main(String[] args) {
        try {
            OrderConnector con = new OrderConnector("jdbc:postgresql://151.80.57.19:5432/admin_semester", "admin_willf", "2111993");
            for(Order o : con.getOrderOverview("jakob.bisander@hotmail.com")){
                System.out.println(o.getCustomerId());
            };
        } catch (SQLException ex) {
            Logger.getLogger(testOrderConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
