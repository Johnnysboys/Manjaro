/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop;

import electroshop.persons.Visitor;
import electroshop.persons.LoggedInPerson;
import electroshop.connectors.AccountsConnector;
import electroshop.connectors.ProductConnector;
import electroshop.persons.Person;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author Jakob
 */
public class FXMLDocumentController implements Initializable {

    private Person activeUser;
    private LoggedInPerson loggedInPerson;
    private AccountsConnector accCon;
    private ProductConnector prodCon;

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
    private ComboBox<String> categoryDrop;
    @FXML
    private Pane genPane;
    @FXML
    private Pane desktopPane;
    @FXML
    private ComboBox<String> integratedWifiCombo;
    @FXML
    private Pane computerPane;
    @FXML
    private Pane laptopPane;
    @FXML
    private Pane washingPane;
    @FXML
    private Pane fridgePane;
    @FXML
    private Pane tvRadioPane;
    @FXML
    private Pane tvPane;
    @FXML
    private Pane whiteGoodsPane;
    @FXML
    private Pane radioPane;
    @FXML
    private Pane generalPane;
    @FXML
    private Label column1Label;
    @FXML
    private Label column2Label;
    @FXML
    private Label column3Label;
    @FXML
    private Label column4Label;
    @FXML
    private Label column5Label;
    @FXML
    private Label column6Label;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField column1Field;
    @FXML
    private TextField column2Field;
    @FXML
    private TextField column3Field;
    @FXML
    private TextField column6Field;
    @FXML
    private TextField column5Field;
    @FXML
    private TextField column4Field;

    @FXML
    private void handleLogin(ActionEvent event) throws SQLException {
        String email = emailField.getText();
        String pw = pwField.getText();

        loggedInPerson = accCon.login(email, pw, activeUser.getBasket());

        if (loggedInPerson == null) {
            System.out.println("LOGIN NOT SUCCESFULL");
            loginPane.setStyle("-fx-background-color: #800000");
        } else {
            System.out.println("LOGIN SUCCESFULL");
            //activeUser = accCon.login(email, pw, activeUser.getBasket());
            activeUser = loggedInPerson;
            loginPane.setVisible(false);

            StringBuilder sb = new StringBuilder();

            sb.append(loggedInPerson.getEmail()).append(" ");
            sb.append(loggedInPerson.getTitle());
            userInfoLabel.setText(sb.toString());
            loggedInPane.setVisible(true);
        }

    }

    @FXML
    private void changeSelection(ActionEvent event) throws SQLException {
        String s = (String) categoryDrop.getValue();
        ArrayList<String> list = prodCon.getColumns(s);
        
        int columnSize = list.size();
        
        genPane.setVisible(true);
        computerPane.setVisible(false); laptopPane.setVisible(false); desktopPane.setVisible(false); whiteGoodsPane.setVisible(false); washingPane.setVisible(false); fridgePane.setVisible(false); tvRadioPane.setVisible(false); tvPane.setVisible(false); radioPane.setVisible(false);
        
        switch (s) {
            case "laptops":                
                computerPane.setVisible(true);
                laptopPane.setVisible(true);
                break;
            case "desktops":
                computerPane.setVisible(true);
                desktopPane.setVisible(true);
                break;
            case "washingmachine":
                whiteGoodsPane.setVisible(true);
                washingPane.setVisible(true);
                break;
            case "fridge":
                whiteGoodsPane.setVisible(true);
                fridgePane.setVisible(true);
                break;
            case "tv":
                tvRadioPane.setVisible(true);
                tvPane.setVisible(true);
                break;
            case "radio":
                tvRadioPane.setVisible(true);
                radioPane.setVisible(true);
                break;
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

        if (Checker.checkAccount(email, name, phone) == true) {
            accCon.insertAccount(email, name, phone, address, pw);
        } else {
            // Grafisk visning af fejl i account creation
            System.out.println("something is wrong");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        activeUser = new Visitor();
        //accCon = new AccountsConnector("jdbc:postgresql://localhost:5432/electroshop", "postgres", "2111993");
        accCon = new AccountsConnector("jdbc:postgresql://151.80.57.19:5432/admin_semester", "admin_willf", "2111993");
        prodCon = new ProductConnector("jdbc:postgresql://151.80.57.19:5432/admin_semester", "admin_willf", "2111993");

        try {
            categoryDrop.setItems(prodCon.getCategories());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ObservableList<String> options = FXCollections.observableArrayList("Yes","No");
        integratedWifiCombo.setItems(options);

    }

}
