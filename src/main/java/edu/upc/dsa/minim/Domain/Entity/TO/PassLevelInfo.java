package edu.upc.dsa.minim.Domain.Entity.TO;

public class PassLevelInfo {
    private String userIdName;
    private int points;
    private String date;

    public PassLevelInfo(){
    }

    public PassLevelInfo(String userIdName, int points, String date){
        this.userIdName = userIdName;
        this.points = points;
        this.date = date;
    }

    public String getUserIdName() {
        return userIdName;
    }

    public void setUserIdName(String userIdName) {
        this.userIdName = userIdName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
