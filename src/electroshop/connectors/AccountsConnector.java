/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop.connectors;

import electroshop.Basket;
import electroshop.persons.Administrator;
import electroshop.persons.Customer;
import electroshop.persons.Employee;
import electroshop.persons.LoggedInPerson;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jakob
 */
public class AccountsConnector extends SuperDB {

    public AccountsConnector(String host, String user, String pword) {
        super(host, user, pword);
    }

    public LoggedInPerson login(String email, String pw, Basket basket) throws SQLException {
        String loginSearch = "SELECT * FROM accounts WHERE email = ? AND pw = ?;";
        //Laver et prepared Statement, som eksekverer SQL-statement. Henter forbindelsen til
        //databasen og sætter loginSearch som parameter
        PreparedStatement search = this.getCon().prepareStatement(loginSearch);
        //Sætter stringen 1 fordi det er email der står først
        search.setString(1, email);
        search.setString(2, pw);
        
        //SQL statement der læser data fra databasen skal returnere det til et resultset
        ResultSet rs = search.executeQuery();

        LoggedInPerson person = null;
        
        //Kigger på næste række i databasen og returnere true, hvis der er noget
        while (rs.next()) {
            int id = rs.getInt("id");
            String email1 = rs.getString("email");
            String name1 = rs.getString("name");
            String phone1 = rs.getString("phone");
            String address1 = rs.getString("address");
            String pw1 = rs.getString("pw");
            int sec1 = rs.getInt("sec");

            switch (sec1) {
                case 1: {
                    person = new Customer(id, email1, name1, phone1, address1, pw1, basket);
                    break;
                }
                case 2: {
                    person = new Employee(id, email1, name1, phone1, address1, pw1, basket);
                    break;
                }
                case 3: {
                    person = new Administrator(id, email1, name1, phone1, address1, pw1, basket);
                    break;
                }
            }
            person.setBasket(basket);
        }

        return (LoggedInPerson) person;
    }

    public void insertAccount(String email, String name, String phone, String address, String pw) throws SQLException {

        Boolean exists = this.accountExists(email);

        if (exists) {
            System.out.println("an account with this email already exists");
        } else {
            String addString = "insert into accounts (email, name, phone, address, pw, sec) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement addAccount = this.getCon().prepareStatement(addString);

            addAccount.setString(1, email);
            addAccount.setString(2, name);
            addAccount.setString(3, phone);
            addAccount.setString(4, address);
            addAccount.setString(5, pw);
            addAccount.setInt(6, 1);
            addAccount.execute();
        }

    }

    public Boolean accountExists(String email) throws SQLException {
        String search = "SELECT email FROM accounts WHERE email = ?;";
        PreparedStatement search2 = this.getCon().prepareStatement(search);
        search2.setString(1, email);
        ResultSet rs = search2.executeQuery();
        // Returns true if the account exists
        return rs.next();
    }
}
