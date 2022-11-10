package edu.upc.dsa.minim.Domain.Entity.VO;

public class CurrentGame {
    private String gameId;
    private int level;

    public CurrentGame(){}

    public CurrentGame(String gameId, int level){
        this.gameId = gameId;
        this.level = level;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
