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

    private static final String col1 = "A";
    private static final String col2 = "B";
    private static final String col3 = "C";
    private static final String col4 = "D";
    private static final String col5 = "E";
    private static final String col6 = "F";
    private static final String col7 = "G";
    private static final String col8 = "H";
    private static final String col9 = "I";

    public static Boolean checkAccount(String email, String name, String phone) {
        Boolean accountGood = false;

        if (checkEmail(email) == true || checkName(name) == true || checkPhone(phone) == true) {
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

    public static Boolean checkDouble(String c) {
        Boolean typeChecksOut = true;
        double checkDouble = 0;
        if (!c.equals("")) {
            if (Character.isWhitespace(c.charAt(0))) {
                // System.out.println("Doubles cannot start with a space");
                typeChecksOut = false;
            } else {

                try {
                    checkDouble = Double.parseDouble(c);
                    // System.out.println("Type is true (Double)");
                } catch (NumberFormatException err) {
                    // System.out.println("Wrong input type:");
                    // System.out.println(err.getMessage());
                    // System.out.println("Expected double");

                    typeChecksOut = false;
                }

            }
        } else {
            // System.out.println("String was empty");
            typeChecksOut = false;
        }
        return typeChecksOut;
    }

    public static Boolean checkString(String c) {
        Boolean nameFlag = true;
        if (!c.equals("")) {
            if (Character.isWhitespace(c.charAt(0))) {
                // System.out.println("Name cannot start with a space");
                nameFlag = false;
            } else {
                // System.out.println("Name is good");
            }
        } else {
            //  System.out.println("Name is needed");
            nameFlag = false;
        }
        return nameFlag;
    }

    public static Boolean checkInt(String c) {
        Boolean typeChecksOut = true;
        int checkInt = 0;
        if (!c.equals("")) {
            if (Character.isWhitespace(c.charAt(0))) {
                //System.out.println("Integers cannot start with a space");
                typeChecksOut = false;
            } else {

                try {
                    checkInt = Integer.parseInt(c);
                    // System.out.println("Type is true (Int)");
                } catch (NumberFormatException err) {
                    // System.out.println("Wrong input type:");
                    // System.out.println(err.getMessage());
                    //  System.out.println("Expected integer");

                    typeChecksOut = false;
                }

            }
        } else {
            // System.out.println("String was empty");
            typeChecksOut = false;
        }
        return typeChecksOut;
    }
    
    public static String getColumn(int i){
        if(i == 1){
            return col1;
        } else if (i== 2){
            return col2;
        } else if (i== 3){
            return col3;
        }else if (i== 4){
            return col4;
        }else if (i== 5){
            return col5;
        }else if (i== 6){
            return col6;
        }else if (i== 7){
            return col7;
        }else if (i== 8){
            return col8;
        }else if (i== 9){
            return col9;
        } else {
            return null;
        }
    }
}
