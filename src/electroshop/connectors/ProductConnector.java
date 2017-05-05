/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop.connectors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jakob
 */
public class ProductConnector extends SuperDB {

    public ProductConnector(String host, String user, String pword) {
        super(host, user, pword);
    }

    public ObservableList getCategories() throws SQLException {

        ListProperty<String> listProperty = new SimpleListProperty<String>(FXCollections.<String>observableArrayList());

        String cat = "SELECT table_name\n"
                + "  FROM information_schema.tables\n"
                + " WHERE table_schema='public'\n"
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
        
        String col = "SELECT column_name\n"
                + "FROM information_schema.columns\n"
                + "WHERE table_name   = ?";
        PreparedStatement colSearch = this.getCon().prepareStatement(col);
        colSearch.setString(1, tableName);
        
        ResultSet rs = colSearch.executeQuery();
        
        while(rs.next()){
            list.add(rs.getString("column_name"));
            System.out.println(rs.getString("column_name"));
        }
        return list;
    }

}
