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
public class Laptop extends Computer {

    private double screenSize;
    private double weight;
    private int batteryLife;

    public Laptop(double screenSize, double weight, int batteryLife, double processor, 
            int ram, int harddrivesSize, int productId, String productName, double price, String productDesc) {
        super(processor, ram, harddrivesSize, productId, productName, price, productDesc);
        this.screenSize = screenSize;
        this.weight = weight;
        this.batteryLife = batteryLife;

    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
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
        prodRow.add(String.valueOf(this.getScreenSize()));
        prodRow.add(String.valueOf(this.getWeight()));
        prodRow.add(String.valueOf(this.getBatteryLife()));

        return prodRow;
    }

}
