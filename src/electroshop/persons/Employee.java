/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop.persons;

import electroshop.Basket;

/**
 *
 * @author Jakob
 */
public class Employee extends LoggedInPerson {

    public Employee(int id, String email, String name, String phone, String address, String pw, Basket basket) {
        super(id, email, name, phone, address, pw, 2, basket);
    }

    @Override
    public String getTitle() {
        return "(Employee)";
    }
}
