package edu.upc.dsa.minim.Domain.Entity;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.NoGameActiveException;
import edu.upc.dsa.minim.Domain.Entity.VO.UserHistory;

import java.util.*;

public class Game {
    private String gameId;
    private String description;
    private int numLevels;
    private List<UserHistory> usersHistory;

    public Game(){
        this.usersHistory = new LinkedList<>();
    }

    public Game( String gameId, String description, int numLevels){
        this();
        this.gameId = gameId;
        this.description = description;
        this.numLevels = numLevels;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumLevels() {
        return numLevels;
    }

    public void setNumLevels(int numLevels) {
        this.numLevels = numLevels;
    }

    public List<UserHistory> getUsersHistory() {
        return usersHistory;
    }

    public void setUsersHistory(List<UserHistory> usersHistory) {
        this.usersHistory = usersHistory;
    }

    public void sort() {
        this.usersHistory.sort((UserHistory p1, UserHistory p2)->(p1.getPoints() - p2.getPoints()));
    }

    public void addUserHistory(User user) throws NoGameActiveException {
        this.usersHistory.add(new UserHistory(user.getUserIdName(),user.getPoints()));
    }
}
