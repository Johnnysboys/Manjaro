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
public abstract class WhiteGoods extends Product {

    private int noiseLevel;
    private int energyUsage;

    public WhiteGoods(int noiseLevel, int energyUsage, int productId, String productName, double price, String productDesc) {
        super(productId, productName, price, productDesc);
        this.noiseLevel = noiseLevel;
        this.energyUsage = energyUsage;
    }

    public int getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(int noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public int getEnergyUsage() {
        return energyUsage;
    }

    public void setEnergyUsage(int energyUsage) {
        this.energyUsage = energyUsage;
    }

}
