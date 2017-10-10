/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop.connectors;

import electroshop.Checker;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import products.Desktop;
import products.Fridge;
import products.Laptop;
import products.Product;
import products.Radio;
import products.Tv;
import products.WashingMachine;

/**
 *
 * @author Jakob
 */
public class ProductConnector extends SuperDB {

    public ProductConnector(String host, String user, String pword) {
        super(host, user, pword);
    }

    public ObservableList getCategories() throws SQLException {

        ListProperty<String> listProperty = new SimpleListProperty<>(FXCollections.<String>observableArrayList());

        String cat = "SELECT table_name\n"
                + "   FROM information_schema.tables\n"
                + "   WHERE table_schema='public'\n"
                + "   AND table_type='BASE TABLE'\n"
                + "   AND NOT table_name='accounts'\n"
                + "   AND NOT table_name='products';";
        PreparedStatement catSearch = this.getCon().prepareStatement(cat);
        ResultSet rs = catSearch.executeQuery();

        while (rs.next()) {
            String tableName = rs.getString("table_name");
            listProperty.add(tableName);
        }
        return listProperty;
    }

    public ArrayList getColumns(String tableName) throws SQLException {
        ArrayList<String> list = new ArrayList<>();

        String col = "SELECT column_name FROM information_schema.columns WHERE table_name   = ?";
        PreparedStatement colSearch = this.getCon().prepareStatement(col);
        colSearch.setString(1, tableName);

        ResultSet rs = colSearch.executeQuery();

        while (rs.next()) {
            list.add(rs.getString("column_name"));
        }
        return list;
    }

    public void changeProductDesc(int id, String desc) throws SQLException {
        String query = "UPDATE products set description = ? WHERE id = ?";
        PreparedStatement descChange = this.getCon().prepareStatement(query);
        descChange.setString(1, desc);
        descChange.setInt(2, id);

        descChange.execute();
    }

    

    public ArrayList findProductsByName(String category, String name) throws SQLException {
        ArrayList pList;
        switch (category) {
            case "desktops":
                pList = new ArrayList<Desktop>();
                break;
            case "laptops":
                pList = new ArrayList<Laptop>();
                break;
            case "washingmachine":
                pList = new ArrayList<WashingMachine>();
                break;
            case "freezers":
                pList = new ArrayList<Fridge>();
                break;
            case "tv":
                pList = new ArrayList<Tv>();
                break;
            case "radio":
                pList = new ArrayList<Radio>();
                break;
            default:
                pList = new ArrayList<Product>();
                break;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(category);
        sb.append(" WHERE ");
        sb.append("name ILIKE '%").append(name).append("%'");

        PreparedStatement ps = this.getCon().prepareStatement(sb.toString());
        ResultSet rs = ps.executeQuery();
        int q = 0;
        while (rs.next() && q < 10) {
            Product prod = null;
            switch (category) {
                case "desktops":
                    prod = new Desktop(rs.getString("formfactor"), rs.getBoolean("integratedwifi"), rs.getDouble("processor"), rs.getInt("ram"), rs.getInt("harddrive"), rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
                    Desktop desk = (Desktop) prod;
                    pList.add(desk);
                    break;
                case "laptops":
                    prod = new Laptop(rs.getDouble("screensize"), rs.getDouble("weight"), rs.getInt("battery"), rs.getDouble("processor"), rs.getInt("ram"), rs.getInt("harddrive"), rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
                    Laptop lap = (Laptop) prod;
                    pList.add(lap);
                    break;
                case "washingmachine":
                    prod = new WashingMachine(rs.getInt("rpm"), rs.getDouble("capacity"), rs.getInt("noiselevel"), rs.getInt("energyusage"), rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
                    WashingMachine wash = (WashingMachine) prod;
                    pList.add(wash);
                    break;
                case "freezers":
                    prod = new Fridge(rs.getDouble("volume"), rs.getInt("shelf"), rs.getInt("noiselevel"), rs.getInt("energyusage"), rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
                    Fridge fridge = (Fridge) prod;
                    pList.add(fridge);
                    break;
                case "tv":
                    prod = new Tv(rs.getDouble("screensize"), rs.getString("resolution"), rs.getString("paneltype"), rs.getInt("soundlevel"), rs.getString("color"), rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
                    Tv tv = (Tv) prod;
                    pList.add(tv);
                    break;
                case "radio":
                    prod = new Radio(rs.getInt("battery"), rs.getInt("effect"), rs.getInt("soundlevel"), rs.getString("color"), rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
                    Radio rad = (Radio) prod;
                    pList.add(rad);
                    break;
                default:
                    break;
            }
        }

        return pList;

    }

    public ArrayList findProducts(String category, ArrayList<String> columns, String name, String price, String col1, String col2, String col3, String col4, String col5, String col6) throws SQLException {
        ArrayList pList;
        switch (category) {
            case "desktops":
                pList = new ArrayList<Desktop>();
                break;
            case "laptops":
                pList = new ArrayList<Laptop>();
                break;
            case "washingmachine":
                pList = new ArrayList<WashingMachine>();
                break;
            case "freezers":
                pList = new ArrayList<Fridge>();
                break;
            case "tv":
                pList = new ArrayList<Tv>();
                break;
            case "radio":
                pList = new ArrayList<Radio>();
                break;
            default:
                pList = new ArrayList<Product>();
                break;
        }
        int cSize = columns.size();
        cSize--;

        ArrayList<String> colList = new ArrayList();

        colList.add(col1);
        colList.add(col2);
        colList.add(col3);
        colList.add(col4);
        colList.add(col5);
        colList.add(col6);

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * FROM ").append(category);
        sb.append(" WHERE ");
        sb.append("name ILIKE '%").append(name).append("%'");
        if (Checker.checkDouble(price)) {
            double p = Double.parseDouble(price);
            double priceMin = p * 0.75;
            double priceMax = p * 1.25;
            sb.append("AND price BETWEEN ").append(priceMin).append(" AND ").append(priceMax);

        }

        for (int i = 0; i < cSize - 4; i++) {
            String columnName = columns.get(i + 4);
            String colSearch = colList.get(i).toLowerCase();

            if (colSearch.equals("")) {
                //Skipping search attribute

            } else if (Checker.checkInt(colSearch)) {
                int e = Integer.parseInt(colSearch);
                int eMin = (int) (e * 0.75);
                int eMax = (int) (e * 1.25);
                sb.append(" AND ").append(columnName).append(" BETWEEN ").append(eMin).append(" AND ").append(eMax);
            } else if (Checker.checkDouble(colSearch)) {
                double d = Double.parseDouble(colSearch);
                double dMin = d * 0.75;
                double dMax = d * 1.25;
                sb.append(" AND ").append(columnName).append(" BETWEEN ").append(dMin).append(" AND ").append(dMax);
            } else if (columnName.equals("integratedwifi")) { // BOOLEAN COLUMN-NAME-CHECK
                switch (colSearch) {
                    case "yes":
                        sb.append(" AND ").append(columnName).append(" = true");
                        break;
                    case "no":
                        sb.append(" AND ").append(columnName).append(" = false");
                        break;
                    default:
                        sb.append(" AND ").append(columnName).append(" = false");
                        break;
                }
            } else {
                sb.append(" AND ").append(columnName).append(" ILIKE '%").append(colSearch).append("%' ");
            }

        }

        PreparedStatement ps = this.getCon().prepareStatement(sb.toString());
        ResultSet rs = ps.executeQuery();
        int q = 0;
        while (rs.next() && q < 10) {
            Product prod = null;
            switch (category) {
                case "desktops":
                    prod = new Desktop(rs.getString("formfactor"), rs.getBoolean("integratedwifi"), rs.getDouble("processor"), rs.getInt("ram"), rs.getInt("harddrive"), rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
                    Desktop desk = (Desktop) prod;
                    pList.add(desk);
                    break;
                case "laptops":
                    prod = new Laptop(rs.getDouble("screensize"), rs.getDouble("weight"), rs.getInt("battery"), rs.getDouble("processor"), rs.getInt("ram"), rs.getInt("harddrive"), rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
                    Laptop lap = (Laptop) prod;
                    pList.add(lap);
                    break;
                case "washingmachine":
                    prod = new WashingMachine(rs.getInt("rpm"), rs.getDouble("capacity"), rs.getInt("noiselevel"), rs.getInt("energyusage"), rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
                    WashingMachine wash = (WashingMachine) prod;
                    pList.add(wash);
                    break;
                case "freezers":
                    prod = new Fridge(rs.getDouble("volume"), rs.getInt("shelf"), rs.getInt("noiselevel"), rs.getInt("energyusage"), rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
                    Fridge fridge = (Fridge) prod;
                    pList.add(fridge);
                    break;
                case "tv":
                    prod = new Tv(rs.getDouble("screensize"), rs.getString("resolution"), rs.getString("paneltype"), rs.getInt("soundlevel"), rs.getString("color"), rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
                    Tv tv = (Tv) prod;
                    pList.add(tv);
                    break;
                case "radio":
                    prod = new Radio(rs.getInt("battery"), rs.getInt("effect"), rs.getInt("soundlevel"), rs.getString("color"), rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("description"));
                    Radio rad = (Radio) prod;
                    pList.add(rad);
                    break;
                default:
                    break;
            }
        }

        return pList;
    }
    public void addProductToDatabase(String name, double price, String description, String productType) throws SQLException {
        //Her laves et preparedStatement, som eksekverer SQL-kode
        PreparedStatement statement = getCon().prepareStatement("INSERT INTO products (name,price,description, producttype) VALUES (?,?,?,?)");
        //SetString bruges til at sætte værdierne på spørgsmålstegnene
        //Den kigger på name og finder ud af det er en string. 
        statement.setString(1, name);
        statement.setDouble(2, price);
        statement.setString(3, description);
        statement.setString(4, productType);
        statement.executeUpdate();
        //Husker at lukke forbindelse og statement, så der ikke kommer SQL-injections
        getCon().close(); 
        statement.close();

    }

}
