package edu.upc.dsa.minim.Domain.Entity;

import edu.upc.dsa.minim.Domain.Entity.VO.RandomId;

public class ObjectShop {
    String objectId;
    String objectName;
    String description;
    Double price;

    public ObjectShop(){this.objectId = RandomId.getId();}

    public ObjectShop(String objectName, String description, Double price){
        this();
        this.objectName = objectName;
        this.description = description;
        this.price = price;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
