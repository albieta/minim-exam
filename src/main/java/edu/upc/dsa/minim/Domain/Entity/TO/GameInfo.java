package edu.upc.dsa.minim.Domain.Entity.TO;

import java.util.LinkedList;

public class GameInfo {
    private String gameId;
    private String description;
    private int numLevels;

    public GameInfo(){
    }

    public GameInfo( String gameId, String description, int numLevels){
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
}
