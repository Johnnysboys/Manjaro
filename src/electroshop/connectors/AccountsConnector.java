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
import electroshop.persons.Person;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jakob
 */
public class AccountsConnector extends SuperDB {

    public AccountsConnector(String host, String user, String pword) {
        this.hostName = host;
        this.userName = user;
        this.password = pword;

        try {
            con = DriverManager.getConnection(hostName, userName, password);
            System.out.println("Opened database successfully");

        } catch (SQLException err) {
            System.out.println(err.getMessage());
            System.out.println("Could not connect to database.");
        }

    }

    public Person login(String email, String pw, Basket basket) throws SQLException {
        String loginSearch = "SELECT * FROM accounts WHERE email = ? AND pw = ?;";
        PreparedStatement search = con.prepareStatement(loginSearch);

        search.setString(1, email);
        search.setString(2, pw);

        ResultSet rs = search.executeQuery();

        Person person = null;

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
                    person = new Customer(email1, name1, phone1, address1, pw1);
                    break;
                }
                case 2: {
                    person = new Employee(email1, name1, phone1, address1, pw1);
                    break;
                }
                case 3: {
                    person = new Administrator(email1, name1, phone1, address1, pw1);
                    break;
                }
            }
            person.setBasket(basket);
        }

        return person;
    }

    public void insertAccount(String email, String name, String phone, String address, String pw) throws SQLException {

        Boolean exists = this.accountExists(email);

        if (exists == true) {
            System.out.println("an account with this email already exists");
        } else {
            System.out.println("Trying to add account");
            String addString = "insert into accounts (email, name, phone, address, pw, sec) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement addAccount = con.prepareStatement(addString);

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
        PreparedStatement search2 = con.prepareStatement(search);

        search2.setString(1, email);
        ResultSet rs = search2.executeQuery();
        if (rs.next() == true) {
            return true;
        } else {
            return false;
        }

    }

}
