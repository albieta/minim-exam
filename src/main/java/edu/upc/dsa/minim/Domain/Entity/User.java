package edu.upc.dsa.minim.Domain.Entity;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.NotEnoughMoneyException;
import edu.upc.dsa.minim.Domain.Entity.VO.Credentials;
import edu.upc.dsa.minim.Domain.Entity.VO.RandomId;

import java.util.*;

public class User {
    String userId;
    String userName;
    String userSurname;
    String birthDate;
    Credentials credentials;
    Double money;
    List<ObjectShop> boughtObjects;

    public User(){
        this.userId = RandomId.getId();
        this.money = 50.0;
        this.boughtObjects = new LinkedList<>();
    }

    public User(String userName, String userSurname, String birthDate, Credentials credentials){
        this();
        this.userName = userName;
        this.userSurname = userSurname;
        this.birthDate = birthDate;
        this.credentials = credentials;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Boolean hasCredentials(Credentials credentials){
        return this.credentials.isEqual(credentials);
    }

    public Boolean hasEmail(Credentials credentials) {
        return this.credentials.getEmail().isEqual(credentials.getEmail());
    }

    public List<ObjectShop> getBoughtObjects() {
        return boughtObjects;
    }

    public void setBoughtObjects(List<ObjectShop> objects) {this.boughtObjects = objects;}

    public void addBoughtObject(ObjectShop object) {
        this.boughtObjects.add(object);
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public void purchaseObject(ObjectShop object) throws NotEnoughMoneyException {
        if(object.getPrice()>this.money){
            throw new NotEnoughMoneyException();
        }

        this.money = this.money - object.getPrice();
        boughtObjects.add(object);
    }
}
