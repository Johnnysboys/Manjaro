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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import products.Product;
import products.Computer;
import products.Desktop;
import products.Fridge;
import products.Laptop;
import products.Radio;
import products.Tv;
import products.TvRadio;
import products.WashingMachine;
import products.WhiteGoods;

/**
 *
 * @author Jakob
 */
public class FXMLDocumentController implements Initializable {

    private Person activeUser;
    private LoggedInPerson loggedInPerson;
    private AccountsConnector accCon;
    private ProductConnector prodCon;
    private ArrayList<String> catColumnsList;
    private ObservableList<Product> prodSearch;

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
    private Pane namePane;
    @FXML
    private Pane pricePane;
    @FXML
    private Pane column1Pane;
    @FXML
    private Pane column2Pane;
    @FXML
    private Pane column3Pane;
    @FXML
    private Pane column4Pane;
    @FXML
    private Pane column5Pane;
    @FXML
    private Pane column6Pane;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<Product> productTable;

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
            activeUser = loggedInPerson;
            loginPane.setVisible(false);

            StringBuilder sb = new StringBuilder();

            sb.append(loggedInPerson.getEmail()).append(" ");
            sb.append(loggedInPerson.getTitle());
            userInfoLabel.setText(sb.toString());
            loggedInPane.setVisible(true);

            //productTable.
        }

    }

    @FXML
    private void searchProducts(ActionEvent event) throws SQLException {
        String category = (String) categoryDrop.getValue();
        String name = nameField.getText();
        String price = priceField.getText();
        String col1 = column1Field.getText();
        String col2 = column2Field.getText();
        String col3 = column3Field.getText();
        String col4 = column4Field.getText();
        String col5 = column5Field.getText();
        String col6 = column6Field.getText();

        prodSearch = FXCollections.observableArrayList(prodCon.findProducts(category, catColumnsList, name, price, col1, col2, col3, col4, col5, col6));
        if(prodSearch == null){
            System.out.println("No products found"); // WHAT DO WE DO 
            return;
        }
        
        
        
        productTable.setEditable(true);
        int columnAmount = catColumnsList.size();
        System.out.println(columnAmount);
        productTable.getColumns().clear();

        if (columnAmount == 4) {
            System.out.println("printing 4 columns");
            TableColumn c1 = new TableColumn(catColumnsList.get(1));
            TableColumn c2 = new TableColumn(catColumnsList.get(2));
            TableColumn c3 = new TableColumn(catColumnsList.get(3));
            productTable.getColumns().addAll(c1, c2, c3);
            
            c1.setCellValueFactory (new PropertyValueFactory<Product, String>("productName"));
            c2.setCellValueFactory (new PropertyValueFactory<Product, String>("price"));
            c3.setCellValueFactory (new PropertyValueFactory<Product, String>("description"));
           
           
        } else if (columnAmount == 5) {
            System.out.println("printing 5 columns");
            TableColumn c1 = new TableColumn(catColumnsList.get(1));
            TableColumn c2 = new TableColumn(catColumnsList.get(2));
            TableColumn c3 = new TableColumn(catColumnsList.get(3));
            TableColumn c4 = new TableColumn(catColumnsList.get(4));
            productTable.getColumns().addAll(c1, c2, c3, c4);
            
            
            c1.setCellValueFactory (new PropertyValueFactory<Product, String>("productName"));
            c2.setCellValueFactory (new PropertyValueFactory<Product, String>("price"));
            c3.setCellValueFactory (new PropertyValueFactory<Product, String>("description"));
            c4.setCellValueFactory (new PropertyValueFactory<Product, String>("firstAtt"));
        } else if (columnAmount == 6) {
            System.out.println("printing 6 columns");
            TableColumn c1 = new TableColumn(catColumnsList.get(1));
            TableColumn c2 = new TableColumn(catColumnsList.get(2));
            TableColumn c3 = new TableColumn(catColumnsList.get(3));
            TableColumn c4 = new TableColumn(catColumnsList.get(4));
            TableColumn c5 = new TableColumn(catColumnsList.get(5));
            productTable.getColumns().addAll(c1, c2, c3, c4, c5);
            
            
            
            c1.setCellValueFactory (new PropertyValueFactory<Product, String>("productName"));
            c2.setCellValueFactory (new PropertyValueFactory<Product, String>("price"));
            c3.setCellValueFactory (new PropertyValueFactory<Product, String>("description"));            
            c4.setCellValueFactory (new PropertyValueFactory<Product, String>("firstAtt"));
            c5.setCellValueFactory (new PropertyValueFactory<Product, String>("secondAtt"));
        } else if (columnAmount == 7){
            System.out.println("printing 7 columns");
            TableColumn c1 = new TableColumn(catColumnsList.get(1));
            TableColumn c2 = new TableColumn(catColumnsList.get(2));
            TableColumn c3 = new TableColumn(catColumnsList.get(3));
            TableColumn c4 = new TableColumn(catColumnsList.get(4));
            TableColumn c5 = new TableColumn(catColumnsList.get(5));
            TableColumn c6 = new TableColumn(catColumnsList.get(6));
            productTable.getColumns().addAll(c1, c2, c3, c4, c5, c6);
            
            
            c1.setCellValueFactory (new PropertyValueFactory<Product, String>("productName"));
            c2.setCellValueFactory (new PropertyValueFactory<Product, String>("price"));
            c3.setCellValueFactory (new PropertyValueFactory<Product, String>("description"));
            c4.setCellValueFactory (new PropertyValueFactory<Product, String>("firstAtt"));
            c5.setCellValueFactory (new PropertyValueFactory<Product, String>("secondAtt"));
            c6.setCellValueFactory (new PropertyValueFactory<Product, String>("thirdAtt"));
        } else if (columnAmount == 8) {
            System.out.println("printing 8 columns");
            TableColumn c1 = new TableColumn(catColumnsList.get(1));
            TableColumn c2 = new TableColumn(catColumnsList.get(2));
            TableColumn c3 = new TableColumn(catColumnsList.get(3));
            TableColumn c4 = new TableColumn(catColumnsList.get(4));
            TableColumn c5 = new TableColumn(catColumnsList.get(5));
            TableColumn c6 = new TableColumn(catColumnsList.get(6));
            TableColumn c7 = new TableColumn(catColumnsList.get(7));
            
            c1.setCellValueFactory (new PropertyValueFactory<Product, String>("productName"));
            c2.setCellValueFactory (new PropertyValueFactory<Product, String>("price"));
            c3.setCellValueFactory (new PropertyValueFactory<Product, String>("description"));
            c4.setCellValueFactory (new PropertyValueFactory<Product, String>("firstAtt"));
            c5.setCellValueFactory (new PropertyValueFactory<Product, String>("secondAtt"));
            c6.setCellValueFactory (new PropertyValueFactory<Product, String>("thirdAtt"));
            c7.setCellValueFactory (new PropertyValueFactory<Product, String>("forthAtt"));
            
            productTable.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7);
        } else if (columnAmount == 9){
            System.out.println("printing 9 columns");
            TableColumn c1 = new TableColumn(catColumnsList.get(1));
            TableColumn c2 = new TableColumn(catColumnsList.get(2));
            TableColumn c3 = new TableColumn(catColumnsList.get(3));
            TableColumn c4 = new TableColumn(catColumnsList.get(4));
            TableColumn c5 = new TableColumn(catColumnsList.get(5));
            TableColumn c6 = new TableColumn(catColumnsList.get(6));
            TableColumn c7 = new TableColumn(catColumnsList.get(7));
            TableColumn c8 = new TableColumn(catColumnsList.get(8));
            productTable.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8);
            
            c1.setCellValueFactory (new PropertyValueFactory<Product, String>("productName"));
            c2.setCellValueFactory (new PropertyValueFactory<Product, String>("price"));
            c3.setCellValueFactory (new PropertyValueFactory<Product, String>("description"));
            c4.setCellValueFactory (new PropertyValueFactory<Product, String>("processor"));
            c5.setCellValueFactory (new PropertyValueFactory<Product, String>("secondAtt"));
            c6.setCellValueFactory (new PropertyValueFactory<Product, String>("thirdAtt"));
            c7.setCellValueFactory (new PropertyValueFactory<Product, String>("forthAtt"));
            c8.setCellValueFactory (new PropertyValueFactory<Product, String>("fifthAtt"));
            
            
        } else {
            System.out.println("printing 10 columns");
            TableColumn c1 = new TableColumn(catColumnsList.get(1));
            TableColumn c2 = new TableColumn(catColumnsList.get(2));
            TableColumn c3 = new TableColumn(catColumnsList.get(3));
            TableColumn c4 = new TableColumn(catColumnsList.get(4));
            TableColumn c5 = new TableColumn(catColumnsList.get(5));
            TableColumn c6 = new TableColumn(catColumnsList.get(6));
            TableColumn c7 = new TableColumn(catColumnsList.get(7));
            TableColumn c8 = new TableColumn(catColumnsList.get(8));
            TableColumn c9 = new TableColumn(catColumnsList.get(9));
            productTable.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9);
            
            
            c1.setCellValueFactory (new PropertyValueFactory<Product, String>("productName"));
            c2.setCellValueFactory (new PropertyValueFactory<Product, String>("price"));
            c3.setCellValueFactory (new PropertyValueFactory<Product, String>("description"));
            c4.setCellValueFactory (new PropertyValueFactory<Product, String>("firstAtt"));
            c5.setCellValueFactory (new PropertyValueFactory<Product, String>("secondAtt"));
            c6.setCellValueFactory (new PropertyValueFactory<Product, String>("thirdAtt"));
            c7.setCellValueFactory (new PropertyValueFactory<Product, String>("forthAtt"));
            c8.setCellValueFactory (new PropertyValueFactory<Product, String>("fifthAtt"));
            c9.setCellValueFactory (new PropertyValueFactory<Product, String>("sixthAtt"));
        }

        int a = prodSearch.size();
        System.out.println("Showing "+a+" products.");
        
        

        // productTable.getColumns().addAll(catColumnsList.get(0));
        //prodSearch = prodCon.findProducts(category, catColumnsList, name, price, col1, col2, col3, col4, col5, col6);
        productTable.setItems(prodSearch);
        //System.out.println(prodCon.findProducts(category, catColumnsList, name, price, col1, col2, col3, col4, col5, col6));
    }

    @FXML
    private void changeSelection(ActionEvent event) throws SQLException {
        String s = (String) categoryDrop.getValue();
        catColumnsList = prodCon.getColumns(s);
        searchButton.setDisable(false);

        int columnSize = catColumnsList.size();

        column1Pane.setVisible(false);
        column2Pane.setVisible(false);
        column3Pane.setVisible(false);
        column4Pane.setVisible(false);
        column5Pane.setVisible(false);
        column6Pane.setVisible(false);

        switch (columnSize) {
            case 5:
                column1Label.setText(catColumnsList.get(4));
                column1Pane.setVisible(true);
                break;
            case 6:
                column1Label.setText(catColumnsList.get(4));
                column2Label.setText(catColumnsList.get(5));
                column1Pane.setVisible(true);
                column2Pane.setVisible(true);
                break;
            case 7:
                column1Label.setText(catColumnsList.get(4));
                column2Label.setText(catColumnsList.get(5));
                column3Label.setText(catColumnsList.get(6));
                column1Pane.setVisible(true);
                column2Pane.setVisible(true);
                column3Pane.setVisible(true);
                break;
            case 8:
                column1Label.setText(catColumnsList.get(4));
                column2Label.setText(catColumnsList.get(5));
                column3Label.setText(catColumnsList.get(6));
                column4Label.setText(catColumnsList.get(7));
                column1Pane.setVisible(true);
                column2Pane.setVisible(true);
                column3Pane.setVisible(true);
                column4Pane.setVisible(true);
                break;
            case 9:
                column1Label.setText(catColumnsList.get(4));
                column2Label.setText(catColumnsList.get(5));
                column3Label.setText(catColumnsList.get(6));
                column4Label.setText(catColumnsList.get(7));
                column5Label.setText(catColumnsList.get(8));
                column1Pane.setVisible(true);
                column2Pane.setVisible(true);
                column3Pane.setVisible(true);
                column4Pane.setVisible(true);
                column5Pane.setVisible(true);
                break;
            case 10:
                column1Label.setText(catColumnsList.get(4));
                column2Label.setText(catColumnsList.get(5));
                column3Label.setText(catColumnsList.get(6));
                column4Label.setText(catColumnsList.get(7));
                column5Label.setText(catColumnsList.get(8));
                column6Label.setText(catColumnsList.get(9));
                column1Pane.setVisible(true);
                column2Pane.setVisible(true);
                column3Pane.setVisible(true);
                column4Pane.setVisible(true);
                column5Pane.setVisible(true);
                column6Pane.setVisible(true);
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

    }

}
