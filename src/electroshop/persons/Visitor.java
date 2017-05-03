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
public class Visitor extends Person{
    
    
    
    public Visitor(){
        this.basket = new Basket();
        this.sec = 0;
        
    }
    
    
    public void login(String email, String pw){
        
    }
    
    @Override
    public String getTitle(){
        return "(Visitor)";
    }
}
