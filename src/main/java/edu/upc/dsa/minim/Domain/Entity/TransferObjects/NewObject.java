package edu.upc.dsa.minim.Domain.Entity.TransferObjects;

import edu.upc.dsa.minim.Domain.Entity.VO.RandomId;

public class NewObject {
    private String objectName;
    private String description;
    private Double price;

    public NewObject(){}

    public NewObject(String objectName, String description, Double price){
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
