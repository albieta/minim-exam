package edu.upc.dsa.minim.Domain.Entity.TO;

import edu.upc.dsa.minim.Domain.Entity.User;

public class UserInfo {
    private String userIdName;
    private String gameId;
    private int points;

    public UserInfo(){}

    public UserInfo(User user, String gameId) {
        this.userIdName = user.getUserIdName();
        this.gameId = gameId;
        this.points = user.hasPlayedGame(gameId).getPoints();
    }

    public String getUserIdName() {
        return userIdName;
    }

    public void setUserIdName(String userIdName) {
        this.userIdName = userIdName;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
