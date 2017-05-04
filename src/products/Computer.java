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
public abstract class Computer extends Product {

    private double processor;
    private int ram;
    private int harddrivesSize;

    public Computer(double processor, int ram, int harddrivesSize, int productId, String productName, double price, String productDesc) {
        super(productId, productName, price, productDesc);
        this.processor = processor;
        this.ram = ram;
        this.harddrivesSize = harddrivesSize;
    }

    public double getProcessor() {
        return processor;
    }

    public void setProcessor(double processor) {
        this.processor = processor;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getHarddrivesSize() {
        return harddrivesSize;
    }

    public void setHarddrivesSize(int harddrivesSize) {
        this.harddrivesSize = harddrivesSize;
    }

}
