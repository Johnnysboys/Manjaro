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
public class Fridge extends WhiteGoods {

    private double volumne;
    private int shelfs;

    public Fridge(double volumne, int shelfs, int noiseLevel, int energyUsage, int productId, String productName, double price, String productDesc) {
        super(noiseLevel, energyUsage, productId, productName, price, productDesc);
        this.volumne = volumne;
        this.shelfs = shelfs;
    }

    public double getVolumne() {
        return volumne;
    }

    public void setVolumne(double volumne) {
        this.volumne = volumne;
    }

    public int getShelfs() {
        return shelfs;
    }

    public void setShelfs(int shelfs) {
        this.shelfs = shelfs;
    }

}
