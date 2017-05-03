/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop;

import electroshop.persons.Visitor;
import electroshop.connectors.AccountsConnector;
import electroshop.persons.Person;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author Jakob
 */
public class FXMLDocumentController implements Initializable {
    
    private Person activeUser;
    private AccountsConnector accCon;

    @FXML
    private Pane loginPane;
    @FXML
    private Button loginButton;
    @FXML
    private TextField emailField;
    @FXML
    private TextField pwField;
    @FXML
    private Button logoutButton;
    @FXML
    private Pane loggedInPane;
    @FXML
    private TextField emailEntry;
    @FXML
    private TextField nameEntry;
    @FXML
    private TextField addressEntry;
    @FXML
    private TextField phoneEntry;
    @FXML
    private TextField pwEntry;
    @FXML
    private Button createButton;
    @FXML
    private Label userInfoLabel;

    @FXML
    private void handleLogin(ActionEvent event) throws SQLException {
        String email = emailField.getText();
        String pw = pwField.getText();
        
        Person person = accCon.login(email, pw, activeUser.getBasket());
        
        if(person == null){
            System.out.println("LOGIN NOT SUCCESFULL");
            loginPane.setStyle("-fx-background-color: #800000");
        } else {
            System.out.println("LOGIN SUCCESFULL");
            activeUser = accCon.login(email, pw, activeUser.getBasket());
            loginPane.setVisible(false);
            
            StringBuilder sb = new StringBuilder();
            sb.append(activeUser.getEmail()).append(" ");
            sb.append(activeUser.getTitle());
            userInfoLabel.setText(sb.toString());
            loggedInPane.setVisible(true);
        }
        
       
    }
    
    @FXML
    private void handleLogout(ActionEvent event) {
        loggedInPane.setVisible(false);
        loginPane.setVisible(true);
        
        activeUser = new Visitor();
    }
    
    @FXML
    private void createAccount(ActionEvent event) throws SQLException {
        String email = emailEntry.getText();
        String name = nameEntry.getText();
        String address = addressEntry.getText();
        String phone = phoneEntry.getText();
        String pw = pwEntry.getText();
        
        if(Checker.checkAccount(email, name, phone) == true){
            accCon.insertAccount(email, name, phone, address, pw);
        } else {
            // Grafisk visning af fejl i account creation
            System.out.println("something is wrong");
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loggedInPane.setVisible(false);
        activeUser = new Visitor();
        //accCon = new AccountsConnector("jdbc:postgresql://localhost:5432/electroshop", "postgres", "2111993");
        accCon = new AccountsConnector("jdbc:postgresql://151.80.57.19:5432/admin_semester", "admin_willf", "2111993");
    }

}
