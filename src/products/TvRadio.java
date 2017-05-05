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
public abstract class TvRadio extends Product{
    
    private int soundLevel;
    private String color;

    public TvRadio(int soundLevel, String color, int productId, String productName, double price, String productDesc) {
        super(productId, productName, price, productDesc);
        this.soundLevel = soundLevel;
        this.color = color;
    }

    public int getSoundLevel() {
        return soundLevel;
    }

    public void setSoundLevel(int soundLevel) {
        this.soundLevel = soundLevel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    
    
}
