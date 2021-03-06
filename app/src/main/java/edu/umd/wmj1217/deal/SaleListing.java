package edu.umd.wmj1217.deal;

import java.text.NumberFormat;

/**
 * @author yoonshik
 * A SaleListing object represents an item on sale.
 */
public class SaleListing {

    public static final NumberFormat formatter = NumberFormat.getCurrencyInstance();

    private String ID;
    private String title;
    private String description;

    private String itemUrl;
    private String imageUrl;

    private double salePrice;
    private double listPrice;

    public SaleListing(String ID, String title, String description, String itemUrl, String imageUrl, double salePrice, double listPrice) {
        this.ID = ID;
        this.title = title;
        this.description = description;

        this.itemUrl = itemUrl;
        this.imageUrl = imageUrl;

        this.salePrice = salePrice;
        this.listPrice = listPrice;
    }

    public String getID(){
        return this.ID;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getItemUrl() {
        return this.itemUrl;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public double getSalePrice() {
        return this.salePrice;
    }

    public double getListPrice() {
        return this.listPrice;
    }

    public String getSalePriceText() {
        return formatter.format(salePrice);
    }

    public String getListPriceText() {
        return formatter.format(listPrice);
    }
}
