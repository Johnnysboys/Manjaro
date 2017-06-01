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
import java.util.Date;
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
    private void handleBasketUpdate(ActionEvent event) {
        this.updateBasket();
    }

    @FXML
    private void clearBasket(ActionEvent event) {
        activeUser.getBasket().emptyBasket();
        this.updateBasket();
    }

    private void updateBasket() {
        basketView.getColumns().clear();

        TableColumn<Map.Entry<Product, String>, String> column1 = new TableColumn<>("Product Name");
        column1.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<Product, String>, String> p) -> new SimpleStringProperty(p.getValue().getKey().getProductName()));

        TableColumn<Map.Entry<Product, String>, String> column2 = new TableColumn<>("Price");
        column2.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<Product, String>, String> p) -> new SimpleStringProperty(String.valueOf(p.getValue().getKey().getPrice())));

        TableColumn<Map.Entry<String, String>, String> column3 = new TableColumn<>("Quantity");
        column3.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) -> new SimpleStringProperty(String.valueOf(p.getValue().getValue())));

        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(activeUser.getBasket().getProductMap().entrySet());

        column1.setMaxWidth(200);
        column2.setMaxWidth(75);
        column3.setMaxWidth(75);
        basketView.setMaxWidth(350);

        basketView.getColumns().setAll(column1, column2, column3);

        basketView.setItems(items);
        totalLabel.setText(String.valueOf(activeUser.getBasket().getTotal()));
    }

    @FXML
    private void handleLogin(ActionEvent event) throws SQLException {
        String email = emailField.getText();
        String pw = pwField.getText();

        loggedInPerson = accCon.login(email, pw, activeUser.getBasket());

        if (loggedInPerson == null) {
            createModal("Unknown user or password, try again.", AlertType.ERROR, true, "Login", "Login error");
        } else {
            activeUser = loggedInPerson;
            loginPane.setVisible(false);

            StringBuilder sb = new StringBuilder();

            sb.append(loggedInPerson.getEmail()).append(" ");
            sb.append(loggedInPerson.getTitle());
            userInfoLabel.setText(sb.toString());
            loggedInPane.setVisible(true);
            createModal("Logged in as " + sb.toString(), AlertType.CONFIRMATION, true, "Login", "Login successful");

            if (activeUser.getSec() == 1) {         // Is a Customer
                tabPane.getTabs().add(orderHistTab);
                tabPane.getTabs().remove(signUpTab);
            } else if (activeUser.getSec() == 2) {  // Is an Employee
                tabPane.getTabs().add(employeeOrderTab);
                tabPane.getTabs().remove(signUpTab);

            } else if (activeUser.getSec() == 3) {  // Is an Administrator
                tabPane.getTabs().remove(signUpTab);
                tabPane.getTabs().add(prodManTab);

            }

        }

    }

    @FXML
    private void handleLogout(ActionEvent event) {
        loggedInPane.setVisible(false);
        loginPane.setVisible(true);
        signUpTab.setDisable(false);

        activeUser = new Visitor(activeUser.getBasket());
        tabPane.getTabs().remove(orderHistTab);
        tabPane.getTabs().remove(employeeOrderTab);
        tabPane.getTabs().remove(prodManTab);
        tabPane.getTabs().add(signUpTab);
        loggedInPerson = null;
    }

    @FXML
    private void handleManCommit(ActionEvent event) throws SQLException {
        int id = prodManView.getSelectionModel().getSelectedItem().getProductId();
        String newDesc = prodManArea.getText();
        prodCon.changeProductDesc(id, newDesc);
        System.out.println("");
        prodManArea.clear();
        prodManCommitButton.setDisable(true);
        this.prodManSearchHandle(event);
    }

    @FXML
    private void prodManSearchHandle(ActionEvent event) throws SQLException {
        String searchQuery = prodManSearchField.getText();
        String category = (String) categoryDropMan.getValue();
        ArrayList<String> columns = prodCon.getColumns(category);

        prodManView.getColumns().clear();

        prodManSearch = FXCollections.observableArrayList(prodCon.findProductsByName(category, searchQuery));

        switch (category) {
            case "desktops": {
                TableColumn c1 = new TableColumn(columns.get(1));
                TableColumn c2 = new TableColumn(columns.get(2));
                TableColumn c4 = new TableColumn(columns.get(4));
                TableColumn c5 = new TableColumn(columns.get(5));
                TableColumn c6 = new TableColumn(columns.get(6));
                TableColumn c7 = new TableColumn(columns.get(7));
                TableColumn c8 = new TableColumn(columns.get(8));

                prodManView.getColumns().addAll(c1, c2, c4, c5, c6, c7, c8);

                c1.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<Product, String>("processor"));
                c5.setCellValueFactory(new PropertyValueFactory<Product, String>("ram"));
                c6.setCellValueFactory(new PropertyValueFactory<Product, String>("harddrivesSize"));
                c7.setCellValueFactory(new PropertyValueFactory<Product, String>("formfactor"));
                c8.setCellValueFactory(new PropertyValueFactory<Product, String>("integratedWifi"));
                break;
            }
            case "washingmachine": {
                TableColumn c1 = new TableColumn(columns.get(1));
                TableColumn c2 = new TableColumn(columns.get(2));
                TableColumn c4 = new TableColumn(columns.get(4));
                TableColumn c5 = new TableColumn(columns.get(5));
                TableColumn c6 = new TableColumn(columns.get(6));
                TableColumn c7 = new TableColumn(columns.get(7));

                prodManView.getColumns().addAll(c1, c2, c4, c5, c6, c7);
                c1.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<Product, String>("noiseLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<Product, String>("energyUsage"));
                c6.setCellValueFactory(new PropertyValueFactory<Product, String>("rpm"));
                c7.setCellValueFactory(new PropertyValueFactory<Product, String>("capacity"));
                break;
            }
            case "freezers": {
                TableColumn c1 = new TableColumn(columns.get(1));
                TableColumn c2 = new TableColumn(columns.get(2));
                TableColumn c4 = new TableColumn(columns.get(4));
                TableColumn c5 = new TableColumn(columns.get(5));
                TableColumn c6 = new TableColumn(columns.get(6));
                TableColumn c7 = new TableColumn(columns.get(7));

                prodManView.getColumns().addAll(c1, c2, c4, c5, c6, c7);
                c1.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<Product, String>("noiseLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<Product, String>("energyUsage"));
                c6.setCellValueFactory(new PropertyValueFactory<Product, String>("volume"));
                c7.setCellValueFactory(new PropertyValueFactory<Product, String>("shelfs"));
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

                prodManView.getColumns().addAll(c1, c2, c4, c5, c6, c7, c8);
                c1.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<Product, String>("soundLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<Product, String>("color"));
                c6.setCellValueFactory(new PropertyValueFactory<Product, String>("screenSize"));
                c7.setCellValueFactory(new PropertyValueFactory<Product, String>("resolution"));
                c8.setCellValueFactory(new PropertyValueFactory<Product, String>("panelType"));
                break;
            }
            case "radio": {
                TableColumn c1 = new TableColumn(columns.get(1));
                TableColumn c2 = new TableColumn(columns.get(2));
                TableColumn c4 = new TableColumn(columns.get(4));
                TableColumn c5 = new TableColumn(columns.get(5));
                TableColumn c6 = new TableColumn(columns.get(6));
                TableColumn c7 = new TableColumn(columns.get(7));

                prodManView.getColumns().addAll(c1, c2, c4, c5, c6, c7);
                c1.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<Product, String>("soundLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<Product, String>("color"));
                c6.setCellValueFactory(new PropertyValueFactory<Product, String>("batteryLife"));
                c7.setCellValueFactory(new PropertyValueFactory<Product, String>("effect"));
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

                prodManView.getColumns().addAll(c1, c2, c4, c5, c6, c7, c8, c9);
                c1.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<Product, String>("processor"));
                c5.setCellValueFactory(new PropertyValueFactory<Product, String>("ram"));
                c6.setCellValueFactory(new PropertyValueFactory<Product, String>("harddrivesSize"));
                c7.setCellValueFactory(new PropertyValueFactory<Product, String>("screenSize"));
                c8.setCellValueFactory(new PropertyValueFactory<Product, String>("weight"));
                c9.setCellValueFactory(new PropertyValueFactory<Product, String>("batteryLife"));
                break;
            }
            default:
                System.out.println("Category not recognized.");
                break;
        }

        prodManView.setItems(prodManSearch);
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
        if (prodSearch == null) {
            String msg = "No products found";
            createModal(msg, AlertType.WARNING, true, "Warning", "Warning");
            return;
        }

        productTable.setEditable(true);
        int columnAmount = catColumnsList.size();
        columnAmount--;
        productTable.getColumns().clear();
        System.out.println(category);
        System.out.println(catColumnsList.toString());
        switch (category) {
            case "desktops": {
                TableColumn c1 = new TableColumn(catColumnsList.get(1));
                TableColumn c2 = new TableColumn(catColumnsList.get(2));
                TableColumn c4 = new TableColumn(catColumnsList.get(4));
                TableColumn c5 = new TableColumn(catColumnsList.get(5));
                TableColumn c6 = new TableColumn(catColumnsList.get(6));
                TableColumn c7 = new TableColumn(catColumnsList.get(7));
                TableColumn c8 = new TableColumn(catColumnsList.get(8));

                productTable.getColumns().addAll(c1, c2, c4, c5, c6, c7, c8);

                c1.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<Product, String>("processor"));
                c5.setCellValueFactory(new PropertyValueFactory<Product, String>("ram"));
                c6.setCellValueFactory(new PropertyValueFactory<Product, String>("harddrivesSize"));
                c7.setCellValueFactory(new PropertyValueFactory<Product, String>("formfactor"));
                c8.setCellValueFactory(new PropertyValueFactory<Product, String>("integratedWifi"));
                break;
            }
            case "washingmachine": {
                TableColumn c1 = new TableColumn(catColumnsList.get(1));
                TableColumn c2 = new TableColumn(catColumnsList.get(2));
                TableColumn c4 = new TableColumn(catColumnsList.get(4));
                TableColumn c5 = new TableColumn(catColumnsList.get(5));
                TableColumn c6 = new TableColumn(catColumnsList.get(6));
                TableColumn c7 = new TableColumn(catColumnsList.get(7));

                productTable.getColumns().addAll(c1, c2, c4, c5, c6, c7);
                c1.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<Product, String>("noiseLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<Product, String>("energyUsage"));
                c6.setCellValueFactory(new PropertyValueFactory<Product, String>("rpm"));
                c7.setCellValueFactory(new PropertyValueFactory<Product, String>("capacity"));
                break;
            }
            case "freezers": {
                TableColumn c1 = new TableColumn(catColumnsList.get(1));
                TableColumn c2 = new TableColumn(catColumnsList.get(2));
                TableColumn c4 = new TableColumn(catColumnsList.get(4));
                TableColumn c5 = new TableColumn(catColumnsList.get(5));
                TableColumn c6 = new TableColumn(catColumnsList.get(6));
                TableColumn c7 = new TableColumn(catColumnsList.get(7));
                System.out.println("in freeze");
                productTable.getColumns().addAll(c1, c2, c4, c5, c6, c7);
                c1.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<Product, String>("noiseLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<Product, String>("energyUsage"));
                c6.setCellValueFactory(new PropertyValueFactory<Product, String>("volume"));
                c7.setCellValueFactory(new PropertyValueFactory<Product, String>("shelfs"));
                break;
            }
            case "tv": {
                TableColumn c1 = new TableColumn(catColumnsList.get(1));
                TableColumn c2 = new TableColumn(catColumnsList.get(2));
                TableColumn c4 = new TableColumn(catColumnsList.get(4));
                TableColumn c5 = new TableColumn(catColumnsList.get(5));
                TableColumn c6 = new TableColumn(catColumnsList.get(6));
                TableColumn c7 = new TableColumn(catColumnsList.get(7));
                TableColumn c8 = new TableColumn(catColumnsList.get(8));

                productTable.getColumns().addAll(c1, c2, c4, c5, c6, c7, c8);
                c1.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<Product, String>("soundLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<Product, String>("color"));
                c6.setCellValueFactory(new PropertyValueFactory<Product, String>("screenSize"));
                c7.setCellValueFactory(new PropertyValueFactory<Product, String>("resolution"));
                c8.setCellValueFactory(new PropertyValueFactory<Product, String>("panelType"));
                break;
            }
            case "radio": {
                TableColumn c1 = new TableColumn(catColumnsList.get(1));
                TableColumn c2 = new TableColumn(catColumnsList.get(2));
                TableColumn c4 = new TableColumn(catColumnsList.get(4));
                TableColumn c5 = new TableColumn(catColumnsList.get(5));
                TableColumn c6 = new TableColumn(catColumnsList.get(6));
                TableColumn c7 = new TableColumn(catColumnsList.get(7));

                productTable.getColumns().addAll(c1, c2, c4, c5, c6, c7);
                c1.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<Product, String>("soundLevel"));
                c5.setCellValueFactory(new PropertyValueFactory<Product, String>("color"));
                c6.setCellValueFactory(new PropertyValueFactory<Product, String>("batteryLife"));
                c7.setCellValueFactory(new PropertyValueFactory<Product, String>("effect"));
                break;
            }
            case "laptops": {
                TableColumn c1 = new TableColumn(catColumnsList.get(1));
                TableColumn c2 = new TableColumn(catColumnsList.get(2));
                TableColumn c4 = new TableColumn(catColumnsList.get(4));
                TableColumn c5 = new TableColumn(catColumnsList.get(5));
                TableColumn c6 = new TableColumn(catColumnsList.get(6));
                TableColumn c7 = new TableColumn(catColumnsList.get(7));
                TableColumn c8 = new TableColumn(catColumnsList.get(8));
                TableColumn c9 = new TableColumn(catColumnsList.get(9));

                productTable.getColumns().addAll(c1, c2, c4, c5, c6, c7, c8, c9);
                c1.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
                c2.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
                c4.setCellValueFactory(new PropertyValueFactory<Product, String>("processor"));
                c5.setCellValueFactory(new PropertyValueFactory<Product, String>("ram"));
                c6.setCellValueFactory(new PropertyValueFactory<Product, String>("harddrivesSize"));
                c7.setCellValueFactory(new PropertyValueFactory<Product, String>("screenSize"));
                c8.setCellValueFactory(new PropertyValueFactory<Product, String>("weight"));
                c9.setCellValueFactory(new PropertyValueFactory<Product, String>("batteryLife"));
                break;
            }
            default:
                System.out.println("Category not recognized.");
                break;
        }

        //Button column
        TableColumn actionCol = new TableColumn(" ");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactory
                = //
                new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
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

        productTable.getColumns().add(actionCol);

        //Row Hover Mechanic
        productTable.setRowFactory(tableView -> {
            final TableRow<Product> row = new TableRow<>();

            row.hoverProperty().addListener((observable) -> {
                final Product product = row.getItem();
                productDescArea.clear();
                if (row.isHover() && product != null) {
                    productDescArea.setText(product.getDescription());
                } else {
                    productDescArea.setText("Hover over a product to see its description.");
                }
            });

            return row;
        });

        productTable.setItems(prodSearch);
    }

    @FXML
    private void searchForOrders() throws SQLException {

        String searchCriteria = empOrderSearchField.getText();

        if (searchCriteria.equals("")) {
            return;
        } else {
            System.out.println(searchCriteria);
            empOrderList = orderCon.getOrderOverview(searchCriteria);

            empOrderView.getColumns().clear();
            TableColumn orderNumber = new TableColumn("Order ID");
            TableColumn orderPrice = new TableColumn("Price Total");
            TableColumn orderDate = new TableColumn("Date");

            orderNumber.setCellValueFactory(new PropertyValueFactory<>("orderID"));
            orderPrice.setCellValueFactory(new PropertyValueFactory("priceTotal"));
            orderDate.setCellValueFactory(new PropertyValueFactory("orderDate"));

            empOrderView.getColumns().addAll(orderNumber, orderPrice, orderDate);

            empOrderView.setItems(empOrderList);

        }

    }

    @FXML
    private void showOrderHistory() {
        orderView.getColumns().clear();

        TableColumn orderNumber = new TableColumn("Order ID");
        TableColumn orderPrice = new TableColumn("Price Total");
        TableColumn orderDate = new TableColumn("Date");

        orderNumber.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderPrice.setCellValueFactory(new PropertyValueFactory("priceTotal"));
        orderDate.setCellValueFactory(new PropertyValueFactory("orderDate"));

        orderView.getColumns().addAll(orderNumber, orderPrice, orderDate);

        try {
            orderView.setItems(orderCon.getOrderOverview(loggedInPerson.getEmail()));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void makeOrderHandler(ActionEvent event) throws SQLException {
        if (basketView.getItems().isEmpty()) {
            baskerOrderButton.disableProperty();
        } else {
            Order order = new Order(activeUser.getBasket());

            if (loggedInPerson == null) {
                visitorBuyHandler(event, order);
                payHandler(event, order);
            } else {
                order.setAdress(loggedInPerson.getAddress());
                order.setCustomerId(loggedInPerson.getId());
                order.setEmail(loggedInPerson.getEmail());
                order.setName(loggedInPerson.getName());
                order.setPhoneNumber(loggedInPerson.getPhone());

                payHandler(event, order);
            }
            orderList.add(order);
        }
    }

    @FXML
    private void changeSelection(ActionEvent event) throws SQLException {
        generalPane.setVisible(true);
        String s = (String) categoryDrop.getValue();
        catColumnsList = prodCon.getColumns(s);
        searchButton.setDisable(false);

        int columnSize = catColumnsList.size();
        columnSize--;

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
    private void createAccount(ActionEvent event) throws SQLException {
        String email = emailEntry.getText();
        String name = nameEntry.getText();
        String address = addressEntry.getText();
        String phone = phoneEntry.getText();
        String pw = pwEntry.getText();

        if (Checker.checkAccount(email, name, phone) == true) {
            if (accCon.accountExists(email)) {
                signErrorLabel.setText("An account with this email already exists.");
            } else {
                accCon.insertAccount(email, name, phone, address, pw);
                signErrorLabel.setText("Your account has been created. Enjoy!");
            }

        } else {
            signErrorLabel.setText("Some of the entered information is invalid");
        }

    }

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
        activeUser = new Visitor();
        accCon = new AccountsConnector("jdbc:postgresql://151.80.57.19:5432/admin_semester", "admin_willf", "2111993");
        prodCon = new ProductConnector("jdbc:postgresql://151.80.57.19:5432/admin_semester", "admin_willf", "2111993");
        orderCon = new OrderConnector("jdbc:postgresql://151.80.57.19:5432/admin_semester", "admin_willf", "2111993");
        ListProperty<String> listProperty = new SimpleListProperty<String>(FXCollections.<String>observableArrayList());
        listProperty.add("desktops");
        listProperty.add("washingmachine");
        listProperty.add("radio");
        listProperty.add("tv");
        listProperty.add("freezers");
        listProperty.add("laptops");
        categoryDrop.setItems(listProperty);
        categoryDropMan.setItems(listProperty);

        generalPane.setVisible(false);

        productTable.getColumns().clear();

        tabPane.getTabs().removeAll(orderHistTab, prodManTab, employeeOrderTab);
        prodManCommitButton.setDisable(true);

        prodManView.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            final Product product = prodManView.getSelectionModel().selectedItemProperty().get();
            if (product != null) {
                prodManArea.clear();
                prodManArea.setText(product.getDescription());
                prodManCommitButton.setDisable(true);
            }
        });

        prodManArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                prodManCommitButton.setDisable(false);
            }
        });

    }

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
                orderCon.insertOrder(order);
                System.out.println(order.getOrderDate());
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

}
