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
public class Radio extends TvRadio {

    private int batteryLife;
    private int effect;

    public Radio(int batteryLife, int effect, int soundLevel, String color, int productId, String productName, double price, String productDesc) {
        super(soundLevel, color, productId, productName, price, productDesc);
        this.batteryLife = batteryLife;
        this.effect = effect;

    }

    public int getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    @Override
    public ArrayList<String> getProductRow() {
        ArrayList<String> prodRow = new ArrayList();
        prodRow.add(this.getProductName());
        prodRow.add(String.valueOf(this.getPrice()));
        prodRow.add(this.getDescription());
        prodRow.add(String.valueOf(this.getSoundLevel()));
        prodRow.add(this.getColor());
        prodRow.add(String.valueOf(this.getBatteryLife()));
        prodRow.add(String.valueOf(this.getEffect()));

        return prodRow;
    }

}
