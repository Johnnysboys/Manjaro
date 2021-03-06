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
public class Tv extends TvRadio {

    private double screenSize;
    private String resolution;
    private String panelType;

    public Tv(double screenSize, String resolution, String panelType, int soundLevel, String color, int productId, String productName, double price, String productDesc) {
        super(soundLevel, color, productId, productName, price, productDesc);
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.panelType = panelType;

    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getPanelType() {
        return panelType;
    }

    public void setPanelType(String panelType) {
        this.panelType = panelType;
    }

    @Override
    public ArrayList<String> getProductRow() {
        ArrayList<String> prodRow = new ArrayList();
        prodRow.add(this.getProductName());
        prodRow.add(String.valueOf(this.getPrice()));
        prodRow.add(this.getDescription());
        prodRow.add(String.valueOf(this.getSoundLevel()));
        prodRow.add(this.getColor());
        prodRow.add(String.valueOf(this.getScreenSize()));
        prodRow.add(this.getResolution());
        prodRow.add(this.getPanelType());

        return prodRow;
    }

}
