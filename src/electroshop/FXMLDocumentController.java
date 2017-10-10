/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop;

import electroshop.persons.Visitor;
import electroshop.persons.LoggedInPerson;
import electroshop.connectors.AccountsConnector;
import electroshop.connectors.OrderConnector;
import electroshop.connectors.ProductConnector;
import electroshop.persons.Person;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import products.Product;

/**
 *
 * @author Jakob
 */
public class FXMLDocumentController implements Initializable {

    private Person activeUser;
    private LoggedInPerson loggedInPerson;
    private AccountsConnector accCon;
    private ProductConnector prodCon;
    private OrderConnector orderCon;
    private ArrayList<String> catColumnsList;
    private ObservableList<Product> prodSearch;
    ObservableList<Order> orderList = FXCollections.observableArrayList();
    ObservableList<Order> empOrderList = FXCollections.observableArrayList();
    private ObservableList<Product> prodManSearch;

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
    private TableView productTable;
    @FXML
    private TextArea productDescArea;
    @FXML
    private TableView basketView;
    @FXML
    private Button updateBasketButton;
    @FXML
    private Button removeFromBasket;
    @FXML
    private Label totalLabel;
    @FXML
    private Label signErrorLabel;
    @FXML
    private Tab orderHistTab;
    @FXML
    private TableView<Order> orderView;
    @FXML
    private Button showOrderButton;
    private TableView orderProdView;
    @FXML
    private Button baskerOrderButton;
    @FXML
    private Tab signUpTab;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab browseTab;
    @FXML
    private Tab basketTab;
    @FXML
    private Tab employeeOrderTab;
    @FXML
    private TableView<Order> empOrderView;
    @FXML
    private Button empFindButton;
    @FXML
    private TextField empOrderSearchField;
    @FXML
    private ComboBox<String> categoryDropMan;
    @FXML
    private Tab prodManTab;
    @FXML
    private TableView<Product> prodManView;
    @FXML
    private TextField prodManSearchField;
    @FXML
    private Button prodManSearchButton;
    @FXML
    private TextArea prodManArea;
    @FXML
    private Button prodManCommitButton;
    @FXML
    private Tab addProductTab;
    @FXML
    private TextField addProductNameField;
    @FXML
    private TextField addProductDescriptionField;
    @FXML
    private TextField addProductPriceField;
    @FXML
    private TextField addProductTypeField;

    @FXML
    private void handleBasketUpdate(ActionEvent event) {
        this.updateBasket(); //Knappen som updaterer kurven
    }

    @FXML
    private void clearBasket(ActionEvent event) {
        this.activeUser.getBasket().emptyBasket();
        this.updateBasket();
    }

    private void updateBasket() {
        //Henter alle kolonner fra tableviewet og fjerner dem.
        this.basketView.getColumns().clear();

        //Laver en ny tablekolonne, som består af Map.entry(som er en klasse der består af en key og dens value)
        //Og så består den også af en String
        TableColumn<Map.Entry<Product, String>, String> column1 = new TableColumn<>("Product Name");
        //Nedenunder assosieres dataen med en kolonne, der bliver altså ikke sat nogen værdi. Den 
        column1.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<Product, String>, String> p) -> new SimpleStringProperty(p.getValue().getKey().getProductName()));

        TableColumn<Map.Entry<Product, String>, String> column2 = new TableColumn<>("Price");
        column2.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<Product, String>, String> p) -> new SimpleStringProperty(String.valueOf(p.getValue().getKey().getPrice())));

        TableColumn<Map.Entry<String, String>, String> column3 = new TableColumn<>("Quantity");
        column3.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) -> new SimpleStringProperty(String.valueOf(p.getValue().getValue())));

        //Opretter en observableList som består af et entrySet med Strings. Det bliver sat til at være en 
        //observableArrayList, som tableView kan arbejde med. Henter personens kurv og derfra hashMappet med produkter
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(this.activeUser.getBasket().getProductMap().entrySet());

        //Sætter mål på kolonnerne
        column1.setMaxWidth(200);
        column2.setMaxWidth(75);
        column3.setMaxWidth(75);
        this.basketView.setMaxWidth(350);

        //Henter de kolonner der er en del af tableviewet og sætter de nyoprettet kolonner
        this.basketView.getColumns().setAll(column1, column2, column3);
        //Sætter værdierne iform af den observablelist i tableviewet
        this.basketView.setItems(items);
        this.totalLabel.setText(String.valueOf(this.activeUser.getBasket().getTotal()));
    }

    @FXML
    private void handleLogin(ActionEvent event) throws SQLException {
        //Håndterer loginvinduet. Henter teksten der bliver skrevet under email og password
        String email = this.emailField.getText();
        String pw = this.pwField.getText();

        //Sætter den nye loggedinperson
        this.loggedInPerson = accCon.login(email, pw, this.activeUser.getBasket());

        //Hvis ikke der findes nogen med de oplysninger popper der et vindue op, som melder fejl
        //Ellers sættes en person = med indloggetperson
        if (this.loggedInPerson == null) {
            this.createModal("Unknown user or password, try again.", AlertType.ERROR, true, "Login", "Login error");
        } else {
            this.activeUser = this.loggedInPerson;
            this.loginPane.setVisible(false);

            //Opretter en stringbuilder, hvor der står:
            //"Logged in as + emailen og titlen"
            StringBuilder sb = new StringBuilder();

            sb.append(this.loggedInPerson.getEmail()).append(" ");
            sb.append(this.loggedInPerson.getTitle());
            this.userInfoLabel.setText(sb.toString());
            this.loggedInPane.setVisible(true);
            this.createModal("Logged in as " + sb.toString(), AlertType.CONFIRMATION, true, "Login", "Login successful");

            //Der laves et switch statement, som henter sikkerhedsniveauet af personen
            //Derefter sættes panes afhængigt af hvem det er der logger ind.
            switch (this.activeUser.getSec()) {
                case 1:
                    // Is a Customer
                    this.tabPane.getTabs().add(this.orderHistTab);
                    this.tabPane.getTabs().remove(this.signUpTab);
                    this.tabPane.getTabs().remove(this.addProductTab);
                    break;
                case 2:
                    // Is an Employee
                    this.tabPane.getTabs().add(this.employeeOrderTab);
                    this.tabPane.getTabs().remove(this.signUpTab);
                    this.tabPane.getTabs().remove(this.addProductTab);
                    break;
                case 3:
                    // Is an Administrator
                    this.tabPane.getTabs().remove(this.signUpTab);
                    this.tabPane.getTabs().add(this.prodManTab);
                    this.tabPane.getTabs().add(this.addProductTab);
                    break;
                default:
                    break;
            }

        }

    }

    //Dette er metoden for logud-knappen
    @FXML
    private void handleLogout(ActionEvent event) {
        //Logind-området sættes bl.a. til false
        this.loggedInPane.setVisible(false);
        this.loginPane.setVisible(true);
        this.signUpTab.setDisable(false);

        //Personen sættes til at være en besøgende, da vedkommende ikke er logget ind
        this.activeUser = new Visitor(this.activeUser.getBasket());
        this.tabPane.getTabs().remove(orderHistTab);
        this.tabPane.getTabs().remove(employeeOrderTab);
        this.tabPane.getTabs().remove(prodManTab);
        this.tabPane.getTabs().add(signUpTab);
        this.loggedInPerson = null;
    }

    //Denne metode hører til knappen commit, når administratoren har ændret
    //en beskrivelse af et produkt
    @FXML
    private void handleManCommit(ActionEvent event) throws SQLException {
        //En metoden, som gør det muligt at vælge ved at føre musen over et produkt
        //i tableviewet af produktet
        int id = this.prodManView.getSelectionModel().getSelectedItem().getProductId();
        //Det som står i redigeringsfeltet af produktet, lægges over i en 
        //String newDesciption
        String newDesc = this.prodManArea.getText();
        //kører metoden changeProductDescription, som
        //sætter id, til at have en ny beskrivelse
        this.prodCon.changeProductDesc(id, newDesc);
        //Rydder redigeringsområdet, efter der er ændret beskrivelse
        this.prodManArea.clear();
        //Commit-knappen sættes til at være usynlig
        this.prodManCommitButton.setDisable(true);
        //Kører metoden nedenunder
        this.prodManSearchHandle(event);
    }

    //Denne metode lader administratoren søge efter produkter,
    //hvis beskrivelse skal redigeres
    @FXML
    private void prodManSearchHandle(ActionEvent event) throws SQLException {
        //String som henter det der står i søgefeltet
        String searchQuery = this.prodManSearchField.getText();
        //henter værdien af det der står i dropdownmenuen
        String category = (String) this.categoryDropMan.getValue();
        //Henter kolonnerne og lægger dem over i en arrayliste af Strings
        ArrayList<String> columns = this.prodCon.getColumns(category);

        //Henter alle aktuelle kolonner i tablewviewet og fjerner indholdet af dem
        this.prodManView.getColumns().clear();

        //laver en ny observableArrayliste, som tableview kan arbejde med. 
        //arraylisten tager category som parameter som er værdien fra dropdownmenuen,
        //men også searchquery,som er det administratoren skriver ind. 
        //Fx. er category fra dropdown desktops og så skrives der lenovo ind i søgefeltet
        this.prodManSearch = FXCollections.observableArrayList(this.prodCon.findProductsByName(category, searchQuery));

        //Der laves en switch over category, der er dropdownmenuen. I tilfælde af desktops....
        switch (category) {
            case "desktops": {
                //Lav en ny tablekolonne
                //Columns indeholder alle kolonner, så afhængigt af switch casen vises de specifikke kolonner.
                
                TableColumn c1 = new TableColumn(columns.get(1));
                TableColumn c2 = new TableColumn(columns.get(2));
                TableColumn c4 = new TableColumn(columns.get(4));
                TableColumn c5 = new TableColumn(columns.get(5));
                TableColumn c6 = new TableColumn(columns.get(6));
                TableColumn c7 = new TableColumn(columns.get(7));
                TableColumn c8 = new TableColumn(columns.get(8));

                //Her tilføjes alle ovenstående kolonner til tableviewet
                this.prodManView.getColumns().addAll(c1, c2, c4, c5, c6, c7, c8);

                /* setCellValueFactory sætter ingen værdier, men udtrækker værdier.
                Det bruges til at associere en kollonne i et tableview med en klasses
                egenskaber, som indeholder data. I dette tilfælde er det databasen. 
                Det fortæller altså at der skal kigges på fx. productName i et table
                 */
                c1.setCellValueFactory(new PropertyValueFactory<>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<>("processor"));
                c5.setCellValueFactory(new PropertyValueFactory<>("ram"));
                c6.setCellValueFactory(new PropertyValueFactory<>("harddrivesSize"));
                c7.setCellValueFactory(new PropertyValueFactory<>("formfactor"));
                c8.setCellValueFactory(new PropertyValueFactory<>("integratedWifi"));
                break;
            }
            case "washingmachine": {
                TableColumn c1 = new TableColumn(columns.get(1));
                TableColumn c2 = new TableColumn(columns.get(2));
                TableColumn c4 = new TableColumn(columns.get(4));
                TableColumn c5 = new TableColumn(columns.get(5));
                TableColumn c6 = new TableColumn(columns.get(6));
                TableColumn c7 = new TableColumn(columns.get(7));

                this.prodManView.getColumns().addAll(c1, c2, c4, c5, c6, c7);
                c1.setCellValueFactory(new PropertyValueFactory<>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<>("noiseLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<>("energyUsage"));
                c6.setCellValueFactory(new PropertyValueFactory<>("rpm"));
                c7.setCellValueFactory(new PropertyValueFactory<>("capacity"));
                break;
            }
            case "freezers": {
                TableColumn c1 = new TableColumn(columns.get(1));
                TableColumn c2 = new TableColumn(columns.get(2));
                TableColumn c4 = new TableColumn(columns.get(4));
                TableColumn c5 = new TableColumn(columns.get(5));
                TableColumn c6 = new TableColumn(columns.get(6));
                TableColumn c7 = new TableColumn(columns.get(7));

                this.prodManView.getColumns().addAll(c1, c2, c4, c5, c6, c7);
                c1.setCellValueFactory(new PropertyValueFactory<>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<>("noiseLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<>("energyUsage"));
                c6.setCellValueFactory(new PropertyValueFactory<>("volume"));
                c7.setCellValueFactory(new PropertyValueFactory<>("shelfs"));
                break;
            }
            case "tv": {
                TableColumn c1 = new TableColumn(columns.get(1));
                TableColumn c2 = new TableColumn(columns.get(2));
                TableColumn c4 = new TableColumn(columns.get(4));
                TableColumn c5 = new TableColumn(columns.get(5));
                TableColumn c6 = new TableColumn(columns.get(6));
                TableColumn c7 = new TableColumn(columns.get(7));
                TableColumn c8 = new TableColumn(columns.get(8));

                this.prodManView.getColumns().addAll(c1, c2, c4, c5, c6, c7, c8);
                c1.setCellValueFactory(new PropertyValueFactory<>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<>("soundLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<>("color"));
                c6.setCellValueFactory(new PropertyValueFactory<>("screenSize"));
                c7.setCellValueFactory(new PropertyValueFactory<>("resolution"));
                c8.setCellValueFactory(new PropertyValueFactory<>("panelType"));
                break;
            }
            case "radio": {
                TableColumn c1 = new TableColumn(columns.get(1));
                TableColumn c2 = new TableColumn(columns.get(2));
                TableColumn c4 = new TableColumn(columns.get(4));
                TableColumn c5 = new TableColumn(columns.get(5));
                TableColumn c6 = new TableColumn(columns.get(6));
                TableColumn c7 = new TableColumn(columns.get(7));

                this.prodManView.getColumns().addAll(c1, c2, c4, c5, c6, c7);
                c1.setCellValueFactory(new PropertyValueFactory<>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<>("soundLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<>("color"));
                c6.setCellValueFactory(new PropertyValueFactory<>("batteryLife"));
                c7.setCellValueFactory(new PropertyValueFactory<>("effect"));
                break;
            }
            case "laptops": {
                TableColumn c1 = new TableColumn(columns.get(1));
                TableColumn c2 = new TableColumn(columns.get(2));
                TableColumn c4 = new TableColumn(columns.get(4));
                TableColumn c5 = new TableColumn(columns.get(5));
                TableColumn c6 = new TableColumn(columns.get(6));
                TableColumn c7 = new TableColumn(columns.get(7));
                TableColumn c8 = new TableColumn(columns.get(8));
                TableColumn c9 = new TableColumn(columns.get(9));

                this.prodManView.getColumns().addAll(c1, c2, c4, c5, c6, c7, c8, c9);
                c1.setCellValueFactory(new PropertyValueFactory<>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<>("processor"));
                c5.setCellValueFactory(new PropertyValueFactory<>("ram"));
                c6.setCellValueFactory(new PropertyValueFactory<>("harddrivesSize"));
                c7.setCellValueFactory(new PropertyValueFactory<>("screenSize"));
                c8.setCellValueFactory(new PropertyValueFactory<>("weight"));
                c9.setCellValueFactory(new PropertyValueFactory<>("batteryLife"));
                break;
            }
            //Hvis andre er gældende køres default, som fortæller at kategorien ikke registreret
            default:
                System.out.println("Category not recognized.");
                break;
        }
        //Indsætter den observablelist i tableviewet
        this.prodManView.setItems(this.prodManSearch);
    }

    @FXML
    private void searchProducts(ActionEvent event) throws SQLException {
        //henter værdien af det der står i dropdownmenuen
        String category = (String) this.categoryDrop.getValue();

        //Henter informationer i de forskellige tekstfelter
        String name = this.nameField.getText();
        String price = this.priceField.getText();
        String col1 = this.column1Field.getText();
        String col2 = this.column2Field.getText();
        String col3 = this.column3Field.getText();
        String col4 = this.column4Field.getText();
        String col5 = this.column5Field.getText();
        String col6 = this.column6Field.getText();

        //laver en ny observableArrayliste, som tableview kan arbejde med. 
        //arraylisten tager category som parameter som er værdien fra dropdownmenuen,
        //men men også alle tesktFelterne, som den besøgende har indtastet. 
        //Fx. er category fra dropdown desktops og så skrives der lenovo ind i søgefeltet
        this.prodSearch = FXCollections.observableArrayList(this.prodCon.findProducts(category, this.catColumnsList, name, price, col1, col2, col3, col4, col5, col6));
        //Hvis den observableList er tom, kommer der et vindue frem med en warning
        if (this.prodSearch == null) {
            String msg = "No products found";
            createModal(msg, AlertType.WARNING, true, "Warning", "Warning");
            return;
        }
        //Tableviewet productTablet kan nu redigeres
        this.productTable.setEditable(true);
        //Bliver ikke brugt. Fejl ved aflevering.
        int columnAmount = this.catColumnsList.size();
        columnAmount--;
        //Henter alle aktuelle kolonner i tablewviewet og fjerner indholdet af dem
        this.productTable.getColumns().clear();

        //Der laves en switch over category, der er dropdownmenuen. I tilfælde af desktops....
        switch (category) {
            case "desktops": {
                TableColumn c1 = new TableColumn(this.catColumnsList.get(1));
                TableColumn c2 = new TableColumn(this.catColumnsList.get(2));
                TableColumn c4 = new TableColumn(this.catColumnsList.get(4));
                TableColumn c5 = new TableColumn(this.catColumnsList.get(5));
                TableColumn c6 = new TableColumn(this.catColumnsList.get(6));
                TableColumn c7 = new TableColumn(this.catColumnsList.get(7));
                TableColumn c8 = new TableColumn(this.catColumnsList.get(8));

                //Her tilføjes alle ovenstående kolonner til tableviewet
                this.productTable.getColumns().addAll(c1, c2, c4, c5, c6, c7, c8);

                /* setCellValueFactory sætter ingen værdier, men udtrækker værdier.
                Det bruges til at associere en kollonne i et tableview med en klasses
                egenskaber, som indeholder data. I dette tilfælde er det databasen. 
                Det fortæller altså at der skal kigges på fx. productName i et table
                 */
                c1.setCellValueFactory(new PropertyValueFactory<>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<>("processor"));
                c5.setCellValueFactory(new PropertyValueFactory<>("ram"));
                c6.setCellValueFactory(new PropertyValueFactory<>("harddrivesSize"));
                c7.setCellValueFactory(new PropertyValueFactory<>("formfactor"));
                c8.setCellValueFactory(new PropertyValueFactory<>("integratedWifi"));
                break;
            }
            case "washingmachine": {
                TableColumn c1 = new TableColumn(this.catColumnsList.get(1));
                TableColumn c2 = new TableColumn(this.catColumnsList.get(2));
                TableColumn c4 = new TableColumn(this.catColumnsList.get(4));
                TableColumn c5 = new TableColumn(this.catColumnsList.get(5));
                TableColumn c6 = new TableColumn(this.catColumnsList.get(6));
                TableColumn c7 = new TableColumn(this.catColumnsList.get(7));

                this.productTable.getColumns().addAll(c1, c2, c4, c5, c6, c7);
                c1.setCellValueFactory(new PropertyValueFactory<>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<>("noiseLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<>("energyUsage"));
                c6.setCellValueFactory(new PropertyValueFactory<>("rpm"));
                c7.setCellValueFactory(new PropertyValueFactory<>("capacity"));
                break;
            }
            case "freezers": {
                TableColumn c1 = new TableColumn(this.catColumnsList.get(1));
                TableColumn c2 = new TableColumn(this.catColumnsList.get(2));
                TableColumn c4 = new TableColumn(this.catColumnsList.get(4));
                TableColumn c5 = new TableColumn(this.catColumnsList.get(5));
                TableColumn c6 = new TableColumn(this.catColumnsList.get(6));
                TableColumn c7 = new TableColumn(this.catColumnsList.get(7));
                this.productTable.getColumns().addAll(c1, c2, c4, c5, c6, c7);
                c1.setCellValueFactory(new PropertyValueFactory<>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<>("noiseLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<>("energyUsage"));
                c6.setCellValueFactory(new PropertyValueFactory<>("volume"));
                c7.setCellValueFactory(new PropertyValueFactory<>("shelfs"));
                break;
            }
            case "tv": {
                TableColumn c1 = new TableColumn(this.catColumnsList.get(1));
                TableColumn c2 = new TableColumn(this.catColumnsList.get(2));
                TableColumn c4 = new TableColumn(this.catColumnsList.get(4));
                TableColumn c5 = new TableColumn(this.catColumnsList.get(5));
                TableColumn c6 = new TableColumn(this.catColumnsList.get(6));
                TableColumn c7 = new TableColumn(this.catColumnsList.get(7));
                TableColumn c8 = new TableColumn(this.catColumnsList.get(8));

                this.productTable.getColumns().addAll(c1, c2, c4, c5, c6, c7, c8);
                c1.setCellValueFactory(new PropertyValueFactory<>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<>("soundLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<>("color"));
                c6.setCellValueFactory(new PropertyValueFactory<>("screenSize"));
                c7.setCellValueFactory(new PropertyValueFactory<>("resolution"));
                c8.setCellValueFactory(new PropertyValueFactory<>("panelType"));
                break;
            }
            case "radio": {
                TableColumn c1 = new TableColumn(this.catColumnsList.get(1));
                TableColumn c2 = new TableColumn(this.catColumnsList.get(2));
                TableColumn c4 = new TableColumn(this.catColumnsList.get(4));
                TableColumn c5 = new TableColumn(this.catColumnsList.get(5));
                TableColumn c6 = new TableColumn(this.catColumnsList.get(6));
                TableColumn c7 = new TableColumn(this.catColumnsList.get(7));

                this.productTable.getColumns().addAll(c1, c2, c4, c5, c6, c7);
                c1.setCellValueFactory(new PropertyValueFactory<>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<>("soundLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<>("color"));
                c6.setCellValueFactory(new PropertyValueFactory<>("batteryLife"));
                c7.setCellValueFactory(new PropertyValueFactory<>("effect"));
                break;
            }
            case "laptops": {
                TableColumn c1 = new TableColumn(this.catColumnsList.get(1));
                TableColumn c2 = new TableColumn(this.catColumnsList.get(2));
                TableColumn c4 = new TableColumn(this.catColumnsList.get(4));
                TableColumn c5 = new TableColumn(this.catColumnsList.get(5));
                TableColumn c6 = new TableColumn(this.catColumnsList.get(6));
                TableColumn c7 = new TableColumn(this.catColumnsList.get(7));
                TableColumn c8 = new TableColumn(this.catColumnsList.get(8));
                TableColumn c9 = new TableColumn(this.catColumnsList.get(9));

                this.productTable.getColumns().addAll(c1, c2, c4, c5, c6, c7, c8, c9);
                c1.setCellValueFactory(new PropertyValueFactory<>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<>("processor"));
                c5.setCellValueFactory(new PropertyValueFactory<>("ram"));
                c6.setCellValueFactory(new PropertyValueFactory<>("harddrivesSize"));
                c7.setCellValueFactory(new PropertyValueFactory<>("screenSize"));
                c8.setCellValueFactory(new PropertyValueFactory<>("weight"));
                c9.setCellValueFactory(new PropertyValueFactory<>("batteryLife"));
                break;
            }
            //Hvis andre er gældende køres default, som fortæller at kategorien ikke registreret
            default:
                System.out.println("Category not recognized.");
                break;
        }
        //Det er jakob der har stået for den sidste del af metoden
        //Tilføjer knappen addBasket
        TableColumn actionCol = new TableColumn(" ");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactory
                = new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
            @Override
            public TableCell call(final TableColumn<Product, String> param) {
                final TableCell<Product, String> cell = new TableCell<Product, String>() {

                    final Button btn = new Button("Add to Basket");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction((ActionEvent event)
                                    -> {
                                Product product = getTableView().getItems().get(getIndex());
                                activeUser.getBasket().addProduct(product, 1);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        actionCol.setCellFactory(cellFactory);

        this.productTable.getColumns().add(actionCol);

        //Row Hover Mechanic
        this.productTable.setRowFactory(tableView -> {
            final TableRow<Product> row = new TableRow<>();

            row.hoverProperty().addListener((observable) -> {
                final Product product = row.getItem();
                this.productDescArea.clear();
                if (row.isHover() && product != null) {
                    this.productDescArea.setText(product.getDescription());
                } else {
                    this.productDescArea.setText("Hover over a product to see its description.");
                }
            });

            return row;
        });
        //Indsætter observablelist i tableviewet
        this.productTable.setItems(this.prodSearch);
    }
    //Denne metode gør det muligt for employee at søge efter ordrer
    @FXML
    private void searchForOrders() throws SQLException {

        //Henter teksten hvor brugeren skriver søgeordet
        String searchCriteria = this.empOrderSearchField.getText();

        //Hvis ikke der bliver søgt på noget returnes ingenting
        if (searchCriteria.equals("")) {
            return;
            //
        } else {
            //Ellers viser den en liste over ordre ud fra den indtastede emailadresse
            this.empOrderList = orderCon.getOrderOverview(searchCriteria);

            //Henter alle aktuelle kolonner i tablewviewet og fjerner indholdet af dem
            this.empOrderView.getColumns().clear();

            TableColumn orderNumber = new TableColumn("Order ID");
            TableColumn orderPrice = new TableColumn("Price Total");
            TableColumn orderDate = new TableColumn("Date");

            orderNumber.setCellValueFactory(new PropertyValueFactory<>("orderID"));
            orderPrice.setCellValueFactory(new PropertyValueFactory("priceTotal"));
            orderDate.setCellValueFactory(new PropertyValueFactory("orderDate"));

            //Her tilføjes alle ovenstående kolonner til tableviewet
            this.empOrderView.getColumns().addAll(orderNumber, orderPrice, orderDate);

            //Indsætter observablelist i tableviewet
            this.empOrderView.setItems(this.empOrderList);

        }

    }
    //Denne metode viser ordrehistorikken
    @FXML
    private void showOrderHistory() {
        //Henter alle aktuelle kolonner i tablewviewet og fjerner indholdet af dem
        this.orderView.getColumns().clear();

        //Opretter en ny tablekolonne med navnet Order ID
        TableColumn orderNumber = new TableColumn("Order ID");
        TableColumn orderPrice = new TableColumn("Price Total");
        TableColumn orderDate = new TableColumn("Date");

        /* setCellValueFactory sætter ingen værdier, men udtrækker værdier.
                Det bruges til at associere en kollonne i et tableview med en klasses
                egenskaber, som indeholder data. I dette tilfælde er det databasen. 
                Det fortæller altså at der skal kigges på fx. productName i et table
         */
        orderNumber.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderPrice.setCellValueFactory(new PropertyValueFactory("priceTotal"));
        orderDate.setCellValueFactory(new PropertyValueFactory("orderDate"));
        
        //Her tilføjes alle ovenstående kolonner til tableviewet
        this.orderView.getColumns().addAll(orderNumber, orderPrice, orderDate);
        
        //Viser ordrehistorikken ud fra den indlogget persons emailadresse
        try {
            this.orderView.setItems(this.orderCon.getOrderOverview(this.loggedInPerson.getEmail()));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //Denne metode opretter en order ud fra kundens kurv
    @FXML
    private void makeOrderHandler(ActionEvent event) throws SQLException {
        //Hvis ikke der er nogen produkter i tableviewet, gør knappen usynlig
        if (basketView.getItems().isEmpty()) {
            baskerOrderButton.disableProperty();
            //Ellers lav en ny ordre med kundens kurv
        } else {
            Order order = new Order(this.activeUser.getBasket());
            //Hvis ikke der er nogen indlogget person (dvs. vedkommende er besøgende)
            //Kør vinduet visitorbuyHandler og derefter betalingsvinduet
            if (this.loggedInPerson == null) {
                visitorBuyHandler(event, order);
                payHandler(event, order);
            //Ellers sæt oplysninger fra den indlogget person og kør vinduet payhandler
            } else {
                order.setAdress(this.loggedInPerson.getAddress());
                order.setCustomerId(this.loggedInPerson.getId());
                order.setEmail(this.loggedInPerson.getEmail());
                order.setName(this.loggedInPerson.getName());
                order.setPhoneNumber(this.loggedInPerson.getPhone());

                payHandler(event, order);
            }
            //Tilføj ordren til observableArrayList
            this.orderList.add(order);
        }
    }
    //Denne metode ændrer på udseendet af programmer. Synliggør og usynligør elementer
    @FXML
    private void changeSelection(ActionEvent event) throws SQLException {
        this.generalPane.setVisible(true);
        String s = (String) this.categoryDrop.getValue();
        this.catColumnsList = this.prodCon.getColumns(s);
        this.searchButton.setDisable(false);

        int columnSize = this.catColumnsList.size();
        columnSize--;

        this.column1Pane.setVisible(false);
        this.column2Pane.setVisible(false);
        this.column3Pane.setVisible(false);
        this.column4Pane.setVisible(false);
        this.column5Pane.setVisible(false);
        this.column6Pane.setVisible(false);

        switch (columnSize) {
            case 5:
                this.column1Label.setText(this.catColumnsList.get(4));
                this.column1Pane.setVisible(true);
                break;
            case 6:
                this.column1Label.setText(this.catColumnsList.get(4));
                this.column2Label.setText(this.catColumnsList.get(5));
                this.column1Pane.setVisible(true);
                this.column2Pane.setVisible(true);
                break;
            case 7:
                this.column1Label.setText(this.catColumnsList.get(4));
                this.column2Label.setText(this.catColumnsList.get(5));
                this.column3Label.setText(this.catColumnsList.get(6));
                this.column1Pane.setVisible(true);
                this.column2Pane.setVisible(true);
                this.column3Pane.setVisible(true);
                break;
            case 8:
                this.column1Label.setText(this.catColumnsList.get(4));
                this.column2Label.setText(this.catColumnsList.get(5));
                this.column3Label.setText(this.catColumnsList.get(6));
                this.column4Label.setText(this.catColumnsList.get(7));
                this.column1Pane.setVisible(true);
                this.column2Pane.setVisible(true);
                this.column3Pane.setVisible(true);
                this.column4Pane.setVisible(true);
                break;
            case 9:
                this.column1Label.setText(this.catColumnsList.get(4));
                this.column2Label.setText(this.catColumnsList.get(5));
                this.column3Label.setText(this.catColumnsList.get(6));
                this.column4Label.setText(this.catColumnsList.get(7));
                this.column5Label.setText(this.catColumnsList.get(8));
                this.column1Pane.setVisible(true);
                this.column2Pane.setVisible(true);
                this.column3Pane.setVisible(true);
                this.column4Pane.setVisible(true);
                this.column5Pane.setVisible(true);
                break;
            case 10:
                this.column1Label.setText(this.catColumnsList.get(4));
                this.column2Label.setText(this.catColumnsList.get(5));
                this.column3Label.setText(this.catColumnsList.get(6));
                this.column4Label.setText(this.catColumnsList.get(7));
                this.column5Label.setText(this.catColumnsList.get(8));
                this.column6Label.setText(this.catColumnsList.get(9));
                this.column1Pane.setVisible(true);
                this.column2Pane.setVisible(true);
                this.column3Pane.setVisible(true);
                this.column4Pane.setVisible(true);
                this.column5Pane.setVisible(true);
                this.column6Pane.setVisible(true);
                break;

        }
    }
    //Denne metode opretter en konto
    @FXML
    private void createAccount(ActionEvent event) throws SQLException {
        //Henter informationer brugeren taster ind i tekstfelterne
        String email = this.emailEntry.getText();
        String name = this.nameEntry.getText();
        String address = this.addressEntry.getText();
        String phone = this.phoneEntry.getText();
        String pw = this.pwEntry.getText();
        
        //Hvis brugeren indtaster email, navn og telefonnummer, fortsæt
        if (Checker.checkAccount(email, name, phone) == true) {
            //Hvis der allerede findes en bruger med den email, smid fejlbesked
            if (accCon.accountExists(email)) {
                this.signErrorLabel.setText("An account with this email already exists.");
            } else {
                //Ellers indsæt en ny konto i databasen med følgende parametre
                this.accCon.insertAccount(email, name, phone, address, pw);
                //Udskriver at kontoen blev oprettet
                this.signErrorLabel.setText("Your account has been created. Enjoy!");
            }
            //kommer herind hvis nogen af oplysninger er skrevet forkert. Fx. string under telefonr.
        } else {
            this.signErrorLabel.setText("Some of the entered information is invalid");
        }

    }
    //Knap til at lukke programmet
    @FXML
    private void handlerCloseProgramButton(ActionEvent event) {
        Alert closeAlert = new Alert(Alert.AlertType.WARNING);
        closeAlert.setTitle("Do you want to close the program?");
        closeAlert.setContentText("Your data will not be saved. Are you sure you want to proceed");
        ButtonType yButton = new ButtonType("Yes");
        ButtonType nButton = new ButtonType("No");
        closeAlert.getButtonTypes().setAll(yButton, nButton);
        Optional<ButtonType> result = closeAlert.showAndWait();
        if (result.get() == yButton) {
            System.exit(0);
        } else if (result.get() == nButton) {
        }
    }
    //Knap til få kort introduktion til programmet
    @FXML
    private void handlerHowToButton(ActionEvent event) {
        Text t;

        Alert closeAlert = new Alert(Alert.AlertType.NONE);
        closeAlert.setTitle("How to use the program?");
        closeAlert.setHeaderText("This is how you use the program!");
        closeAlert.setContentText("1. Search for products in browse-tab and add to basket. \n2. By clicking on update in the baske-tab you can get an overview of the selected products. \n3. Finally click order and pay to proceed. \n4 If you prefer to create an account click the signup-tab. ");
        closeAlert.setResizable(true);
        ButtonType closeButton = new ButtonType("Close");
        closeAlert.getButtonTypes().setAll(closeButton);
        Optional<ButtonType> result = closeAlert.showAndWait();
        closeAlert.getDialogPane().getButtonTypes().add(ButtonType.OK);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Ved start af programmet bliver personen sat til at være en besøgende
        this.activeUser = new Visitor();
        //Opretter forbindelse til de tre databaser
        this.accCon = new AccountsConnector("jdbc:postgresql://151.80.57.19:5432/admin_semester", "admin_willf", "2111993");
        this.prodCon = new ProductConnector("jdbc:postgresql://151.80.57.19:5432/admin_semester", "admin_willf", "2111993");
        this.orderCon = new OrderConnector("jdbc:postgresql://151.80.57.19:5432/admin_semester", "admin_willf", "2111993");
        ListProperty<String> listProperty = new SimpleListProperty<String>(FXCollections.<String>observableArrayList());
        listProperty.add("desktops");
        listProperty.add("washingmachine");
        listProperty.add("radio");
        listProperty.add("tv");
        listProperty.add("freezers");
        listProperty.add("laptops");
        this.categoryDrop.setItems(listProperty);
        this.categoryDropMan.setItems(listProperty);

        this.generalPane.setVisible(false);

        this.productTable.getColumns().clear();
        
        //Henter alle tabs og fjerner de tabs som ikke skal vises ved opstart
        this.tabPane.getTabs().removeAll(this.orderHistTab, this.prodManTab, this.employeeOrderTab, this.addProductTab);
        this.prodManCommitButton.setDisable(true);
        
        
        this.prodManView.getSelectionModel().selectedItemProperty().addListener(
                (observable) -> {
            final Product product = this.prodManView.getSelectionModel().selectedItemProperty().get();
            if (product != null) {
                this.prodManArea.clear();
                this.prodManArea.setText(product.getDescription());
                this.prodManCommitButton.setDisable(true);
            }
        }
        );

        this.prodManArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                prodManCommitButton.setDisable(false);
            }
        });

    }
    //Node finder ud af hvilke vindue actioneventet kommer fra.
    //Visitorbuyhandler er overflødig, da man laver en metode for at 
    //kalde en anden metode. 
    private void visitorBuyHandler(ActionEvent event, Order order) {
        showVisitorBuy(((Node) event.getTarget()).getScene().getWindow(), order);
    }

    private void payHandler(ActionEvent event, Order order) throws SQLException {
        showDialog(((Node) event.getTarget()).getScene().getWindow(), order);
    }

    private void showVisitorBuy(Window parent, Order order) {
        boolean answer = false;
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VisitorBuy.fxml"));
            root = loader.load();
            VisitorBuyController visitorBuyController = loader.getController();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();

            // Debug value
            visitorBuyController.setVariables(dialog, order);

            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setScene(scene);
            dialog.showAndWait();

            order = visitorBuyController.getOrder();

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showDialog(Window parent, Order order) throws SQLException {
        boolean answer = false;
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PaypalDialog.fxml"));
            root = loader.load();
            PaypalDialogController paypalDialogController = loader.getController();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();

            // Debug value
            paypalDialogController.setVariables(dialog, order);

            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setScene(scene);
            dialog.showAndWait();

            order = paypalDialogController.getOrder();

            if (!order.isIsPaid()) {
                String msg = "You didnt pay for the order";
                createModal(msg, Alert.AlertType.WARNING, true);
            } else {
                this.orderCon.insertOrder(order);
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createModal(String message, Alert.AlertType type, boolean modal) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        if (modal) {
            alert.initModality(Modality.APPLICATION_MODAL);
        }
        alert.showAndWait();
    }

    private void createModal(String message, Alert.AlertType type, boolean modal, String title) {
        Alert alert = new Alert(type);
        alert.setTitle(title);

        alert.setContentText(message);
        if (modal) {
            alert.initModality(Modality.APPLICATION_MODAL);
        }
        alert.showAndWait();
    }

    private void createModal(String message, Alert.AlertType type, boolean modal, String title, String header) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        if (modal) {
            alert.initModality(Modality.APPLICATION_MODAL);
        }
        alert.showAndWait();
    }


    @FXML
    private void handlerAddProductToDatabase(ActionEvent event) {
          try {
            //Parse metoden til en double, da addProductPriceField returnerer en double, men getText er en string
            double price = Double.parseDouble(addProductPriceField.getText());
            prodCon.addProductToDatabase(addProductNameField.getText(),
                    price, addProductDescriptionField.getText(), addProductTypeField.getText());
            
            if (addProductNameField.getText().isEmpty() && addProductTypeField.getText().isEmpty()) {
            String message = "You must enter information in all of the fields";
            createModal(message, AlertType.ERROR, true, "Error", "Error");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Something went wrong");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Something went wrong");
            ex.printStackTrace();
        }
        String message = "You have added a new product to the database";
        createModal(message, AlertType.CONFIRMATION, true, "Congratulations", "Congratulations");  
        
    }

}
