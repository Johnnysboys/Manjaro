/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jakob
 */
public abstract class SuperDB {
    private String hostName;
    private String userName;
    private String password;
    private Connection con;
    
    public SuperDB(String host, String user, String pword) {
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
    
    
    public Connection getCon(){
        return this.con;
    }
    public void closeConnection() throws SQLException{
        this.con.close();
    }
}
