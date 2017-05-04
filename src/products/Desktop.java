/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;

/**
 *
 * @author Jakob
 */
public class Desktop extends Computer {

    private String formfactor;
    private boolean integratedWifi;

    public Desktop(String formfactor, boolean integratedWifi, double processor, int ram, int harddrivesSize, int productId, String productName, double price, String productDesc) {
        super(processor, ram, harddrivesSize, productId, productName, price, productDesc);
        this.formfactor = formfactor;
        this.integratedWifi = integratedWifi;
    }

    public String getFormfactor() {
        return formfactor;
    }

    public void setFormfactor(String formfactor) {
        this.formfactor = formfactor;
    }

    public boolean isIntegratedWifi() {
        return integratedWifi;
    }

    public void setIntegratedWifi(boolean integratedWifi) {
        this.integratedWifi = integratedWifi;
    }

}
