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
public abstract class Radio extends TvRadio {

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

}
