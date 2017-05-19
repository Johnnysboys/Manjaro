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
public class Fridge extends WhiteGoods {

    private double volume;
    private int shelfs;

    public Fridge(double volume, int shelfs, int noiseLevel, int energyUsage, int productId, String productName, double price, String productDesc) {
        super(noiseLevel, energyUsage, productId, productName, price, productDesc);
        this.volume = volume;
        this.shelfs = shelfs;

    }

    public double getVolumne() {
        return volume;
    }

    public void setVolumne(double volumne) {
        this.volume = volumne;
    }

    public int getShelfs() {
        return shelfs;
    }

    public void setShelfs(int shelfs) {
        this.shelfs = shelfs;
    }

    @Override
    public ArrayList<String> getProductRow() {
        ArrayList<String> prodRow = new ArrayList();
        prodRow.add(this.getProductName());
        prodRow.add(String.valueOf(this.getPrice()));
        prodRow.add(this.getDescription());
        prodRow.add(String.valueOf(this.getNoiseLevel()));
        prodRow.add(String.valueOf(this.getEnergyUsage()));
        prodRow.add(String.valueOf(this.getVolumne()));
        prodRow.add(String.valueOf(this.getShelfs()));

        return prodRow;
    }

}
