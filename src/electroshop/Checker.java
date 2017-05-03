/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electroshop;

/**
 *
 * @author Jakob
 */
public class Checker {
    
    public static Boolean checkAccount(String email, String name, String phone){
        Boolean accountGood = false;
        
        if(checkEmail(email) == true || checkName(name) == true || checkPhone(phone) == true ){
            accountGood = true;
            
        }
        
        return accountGood;
        
    }

    public static Boolean checkEmail(String c) {
        Boolean isEmail = false;

        if (c.contains("@")) {
            System.out.println("Email good");
            isEmail = true;
        }

        return isEmail;
    }

    public static Boolean checkPhone(String c) {
        Boolean isPhone = false;

        if (c.length() == 8) {
            System.out.println("Phone good");
            isPhone = true;
        }

        return isPhone;
    }

    public static Boolean checkName(String c) {
        Boolean isName = true;

        if (c.equals("")) {
            isName = false;
            System.out.println("name is empty - WRONG");
        } else if (Character.isWhitespace(c.charAt(0))) {
            isName = false;
            System.out.println("name starts with blank - WRONG");
        }

        return isName;
    }

}
