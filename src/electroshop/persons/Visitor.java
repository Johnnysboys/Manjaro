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
public class Visitor extends Person {

    public Visitor() {
        super(0, new Basket());
    }
    
    public Visitor(Basket basket) {
        super(0, basket);
    }

    public void login(String email, String pw) {

    }

    @Override
    public String getTitle() {
        return "(Visitor)";
    }
}
