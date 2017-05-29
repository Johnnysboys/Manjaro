package electroshop;

import electroshop.connectors.OrderConnector;
import electroshop.connectors.SuperDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TheSinding
 */
public class WebShopMediator {
    private FXMLDocumentController mainController;
    private String hostName = "jdbc:postgresql://151.80.57.19:5432/admin_semester";
    private String userName = "admin_willf";
    private String password = "2111993";
    
    
    public void startMain(Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root = loader.load();        
            mainController = loader.getController();
            mainController.injectMediator(this);
            
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(WebShopMediator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private SuperDB dbConnection(int type){
        SuperDB conn = null;
        switch(type){
            case 1:
                conn = new OrderConnector(hostName, userName, password);
            break;
        }
        return conn;
    }
    
    public ArrayList<Order> getOrders(String email){
        try {
            return ((OrderConnector) this.dbConnection(1)).getOrderOverviewList(email);
        } catch (SQLException ex) {
            Logger.getLogger(WebShopMediator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//    public void startPaypalDialog(Node node, Order order){
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("PaypalDialog.fxml"));
//            Parent root = loader.load();
//            // Get the paypal controller
//            PaypalDialogController paypalDialogController = loader.getController();
//            Scene scene = new Scene(root);
//            Stage dialog = new Stage();
//
//            paypalDialogController.setVariables(dialog, order);
//            // Show paypal window and set parent to the current window.
//            dialog.initOwner(parent);
//            dialog.initModality(Modality.WINDOW_MODAL);
//            dialog.setScene(scene);
//            
//            // Show the window and wait, until user is finished
//            dialog.showAndWait();
//
//            order = paypalDialogController.getOrder();
//
//            if (!order.isIsPaid()) {
//                String msg = "You didnt pay for the order";
//                createModal(msg, Alert.AlertType.WARNING, true);
//               } else {
//                orderCon.insertOrder(order);
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
}
