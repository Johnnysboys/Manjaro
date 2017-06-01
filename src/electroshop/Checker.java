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

        if (checkEmail(email) == true && checkName(name) == true && checkPhone(phone) == true) {
            accountGood = true;
        }
        return accountGood;
    }

    public static Boolean checkEmail(String c) {
        Boolean isEmail = false;
        if (c.contains("@")) {
            isEmail = true;
        }
        return isEmail;
    }

    public static Boolean checkPhone(String c) {
        Boolean isPhone = false;
        if (c.length() == 8) {
            isPhone = true;
        }
        return isPhone;
    }

    public static Boolean checkName(String c) {
        Boolean isName = true;
        if (c.equals("")) {
            isName = false;
        } else if (Character.isWhitespace(c.charAt(0))) {
            isName = false;
        }
        return isName;
    }

    public static Boolean checkDouble(String c) {
        Boolean typeChecksOut = true;
        double checkDouble = 0;
        if (!c.equals("")) {
            if (Character.isWhitespace(c.charAt(0))) {
                typeChecksOut = false;
            } else {

                try {
                    checkDouble = Double.parseDouble(c);
                } catch (NumberFormatException err) {

                    typeChecksOut = false;
                }

            }
        } else {
            typeChecksOut = false;
        }
        return typeChecksOut;
    }

    public static Boolean checkString(String c) {
        Boolean nameFlag = true;
        if (!c.equals("")) {
            if (Character.isWhitespace(c.charAt(0))) {
                nameFlag = false;
            }
        } else {
            nameFlag = false;
        }
        return nameFlag;
    }

    public static Boolean checkInt(String c) {
        Boolean typeChecksOut = true;
        int checkInt = 0;
        if (!c.equals("")) {
            if (Character.isWhitespace(c.charAt(0))) {
                typeChecksOut = false;
            } else {
                try {
                    checkInt = Integer.parseInt(c);
                } catch (NumberFormatException err) {
                    typeChecksOut = false;
                }
            }
        } else {
            typeChecksOut = false;
        }
        return typeChecksOut;
    }

    public static String getColumn(int i) {
        switch (i) {
            case 1:
                return col1;
            case 2:
                return col2;
            case 3:
                return col3;
            case 4:
                return col4;
            case 5:
                return col5;
            case 6:
                return col6;
            case 7:
                return col7;
            case 8:
                return col8;
            case 9:
                return col9;
            default:
                return null;
        }
    }
}
