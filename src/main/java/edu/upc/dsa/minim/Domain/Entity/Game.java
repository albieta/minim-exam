package edu.upc.dsa.minim.Domain.Entity;

public class Game {
    private String gameId;
    private String description;
    private int numLevels;

    public Game(){}

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
}
