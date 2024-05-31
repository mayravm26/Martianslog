package edu.upc.dsa.martianslog.models;

public class Product {
    private String idProduct;
    private String name;
    private String description;
    private double price;
    public String url;

    public Product() {}

    public Product(String idProduct, String name, String description, double price, String url) {
        this.idProduct = idProduct;
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }





}
