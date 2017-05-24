/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop;

import electroshop.connectors.PayPalConnector;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TheSinding
 */
public class PaypalDialogController implements Initializable {

    @FXML
    private Label messageLabel;
    @FXML
    private ChoiceBox<String> cardChoiceBox;
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField monthField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField ccv2Field;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private HBox actionParent;
    @FXML
    private Button cancelButton;
    @FXML
    private HBox okParent;
    @FXML
    private Button okButton;
    
    private Stage dialog;
    private Order order;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boolean debug = true;
        if(debug){
            this.cardChoiceBox.setValue("visa");
            this.firstnameField.setText("John");
            this.lastnameField.setText("Doe");
            this.cardNumberField.setText("4417119669820331");
            this.ccv2Field.setText("012");
            this.monthField.setText("11");
            this.yearField.setText("2019"); 
        }
        ObservableList<String> cardList  = FXCollections.observableArrayList(
                "visa",
                "mastercard");
        this.cardChoiceBox.setItems(cardList);
    }    

    @FXML
    private void checkCard(ActionEvent event) {
        String cardType         = this.cardChoiceBox.getValue();
        String cardFirstName    = this.firstnameField.getText();
        String cardLastName     = this.lastnameField.getText();
        String cardNumber       = this.cardNumberField.getText();
        String cardccv2         = this.ccv2Field.getText();
        try{
            int cardMonth   = Integer.parseInt(this.monthField.getText());
            int cardYear    = Integer.parseInt(this.yearField.getText()); 
            boolean passed  = PayPalConnector.validateCard(cardType, cardNumber, cardMonth, cardYear, cardccv2, cardFirstName, cardLastName);
            
            if(passed){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("You payment passed!");
                ButtonType coolButton = new ButtonType("Cool");
                alert.getButtonTypes().setAll(coolButton);
                alert.showAndWait();
                this.order.setIsPaid(true);
                dialog.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("You payment was decliend or wrong card try again.");
                alert.showAndWait();
            }
        } catch(NumberFormatException e){
            System.out.println("Wrong");
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
    }

    void setVariables(Stage dialog, Order order) {
        this.order  = order;
        this.dialog = dialog;
        this.cancelButton.setOnAction(e -> { dialog.close(); });
    }

    Order getOrder() {
        return this.order;
    }
    
    
    
}
