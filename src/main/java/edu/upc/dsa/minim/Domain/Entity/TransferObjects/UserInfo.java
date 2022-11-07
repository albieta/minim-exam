package edu.upc.dsa.minim.Domain.Entity.TransferObjects;

import edu.upc.dsa.minim.Domain.Entity.VO.Credentials;

public class UserInfo {
    private String userName;
    private String userSurname;
    private String birthDate;
    private Credentials credentials;

    public UserInfo(){}

    public UserInfo(String userName, String userSurname, String birthDate, Credentials credentials){
        this.userName = userName;
        this.userSurname = userSurname;
        this.birthDate = birthDate;
        this.credentials = credentials;
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
}
