package edu.umd.wmj1217.deal;

/**
 * Created by yoonshik on 4/27/16.
 */
public class SaleListing {
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


}
