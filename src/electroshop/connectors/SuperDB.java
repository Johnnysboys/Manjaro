/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop.connectors;

import java.sql.Connection;

/**
 *
 * @author Jakob
 */
public abstract class SuperDB {
    String hostName;
    String userName;
    String password;
    Connection con;
}
