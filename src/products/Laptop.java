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
public class Laptop extends Computer {

    private double screenSize;
    private int weight;
    private int batteryLife;

    public Laptop(double screenSize, int weight, int batteryLife, double processor, int ram, int harddrivesSize, int productId, String productName, double price, String productDesc) {
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

    public int getWeight() {
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

}
