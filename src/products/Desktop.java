/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;

import java.util.ArrayList;

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

        @Override
    public ArrayList<String> getProductRow() {
        ArrayList<String> prodRow = new ArrayList();
        prodRow.add(this.getProductName());
        prodRow.add(String.valueOf(this.getPrice()));
        prodRow.add(this.getDescription());
        prodRow.add(String.valueOf(this.getProcessor()));
        prodRow.add(String.valueOf(this.getRam()));
        prodRow.add(String.valueOf(this.getHarddrivesSize()));
        prodRow.add(this.getFormfactor());
        prodRow.add(String.valueOf(this.integratedWifi));
                
        return prodRow;
    }
    
    @Override
    public String toString(){
        return "Desktop: "+ this.getProductName()+ " PROCESSOR: " +this.getProcessor();
    }
    
}
