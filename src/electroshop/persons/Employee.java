/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop.persons;

/**
 *
 * @author Jakob
 */
public class Employee extends Person {

    public Employee(String email, String name, String phone, String address, String pw) {

        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.pw = pw;
        this.sec = 2;

    }
    
    @Override
    public String getTitle(){
        return "(Employee)";
    }
}
