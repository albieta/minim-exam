package edu.upc.dsa.minim.Domain.Entity.TO;

public class UserInfo {
    private String userId;
    private String userName;
    private String userSurname;
    private String birthDate;
    private Double money;

    public UserInfo(){}

    public UserInfo(String userId, String userName, String userSurname, String birthDate, Double money){
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.birthDate = birthDate;
        this.money = money;
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
