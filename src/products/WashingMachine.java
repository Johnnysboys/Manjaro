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
public class WashingMachine extends WhiteGoods {

    private int rpm;
    private double capacity;

    public WashingMachine(int rpm, double capacity, int noiseLevel, int energyUsage, int productId, String productName, double price, String productDesc) {
        super(noiseLevel, energyUsage, productId, productName, price, productDesc);
        this.rpm = rpm;
        this.capacity = capacity;
    }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

}
