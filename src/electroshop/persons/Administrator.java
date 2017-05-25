/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop.persons;

import electroshop.Basket;
import electroshop.FXMLDocumentController;

/**
 *
 * @author Jakob
 */
public class Administrator extends LoggedInPerson {
    
    FXMLDocumentController controller; 

    public Administrator(int id, String email, String name, String phone, String address, String pw, Basket basket) {
        super(id, email, name, phone, address, pw, 3, basket);

    }

    @Override
    public String getTitle() {
        return "(Administrator)";
    }
    
    
}
