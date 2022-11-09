package edu.upc.dsa.minim.Domain.Entity.TO;

public class ObjectInfo {
    private String objectName;
    private String description;
    private Double price;

    public ObjectInfo(){}

    public ObjectInfo(String objectName, String description, Double price){
        this.objectName = objectName;
        this.description = description;
        this.price = price;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
