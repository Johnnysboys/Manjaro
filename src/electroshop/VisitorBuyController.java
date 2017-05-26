/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alsle
 */
public class VisitorBuyController implements Initializable {

    @FXML
    private Label messageLabel;
    @FXML
    private ChoiceBox<String> cityChoiceBox;
    @FXML
    private TextField phoneNumber;
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
    @FXML
    private TextField visitorEmail;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> cityList = FXCollections.observableArrayList(
                "Odense",
                "Koebenhavn",
                "Esbjerg",
                "Aalborg",
                "Noerrelyndelse",
                "Naever"
        );
        this.cityChoiceBox.setItems(cityList);
        // TODO
    }

    @FXML
    private void handleClose(ActionEvent event) {
    }

    @FXML
    private void check(ActionEvent event) {
    }

    public void setVariables(Stage dialog, Order order) {
        this.order = order;
        this.dialog = dialog;
        this.cancelButton.setOnAction(e -> {

            dialog.close();

        });

        this.okButton.setOnAction(e -> {
            this.order.setName(firstnameField.getText());
            this.order.setLastName(lastnameField.getText());
            this.order.setAdress(cityChoiceBox.getValue());
            this.order.setPhoneNumber(phoneNumber.getText());
            this.order.setEmail(visitorEmail.getText());

            dialog.close();
        });
    }

    Order getOrder() {
        return this.order;
    }
}
