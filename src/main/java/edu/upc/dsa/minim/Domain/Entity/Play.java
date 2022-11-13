package edu.upc.dsa.minim.Domain.Entity;

import edu.upc.dsa.minim.Domain.Entity.VO.LevelInfo;

import java.util.*;

public class Play {
    private String gameId;
    private int numLevels;
    private int points;
    private List<LevelInfo> levels;

    public Play(){}

    public Play(Game game){
        this.gameId = game.getGameId();
        this.points = 50;
        this.numLevels = game.getNumLevels();
        this.levels = new ArrayList<>(game.getNumLevels());
    }

    public String getGame() {
        return gameId;
    }

    public void setGame(String gameId) {
        this.gameId = gameId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<LevelInfo> getLevels() {
        return levels;
    }

    public Integer getCurrentLevel() {
        return levels.size()+1;
    }

    public void setLevels(List<LevelInfo> levels) {
        this.levels = levels;
    }

    public Boolean passLevel(int points, String date) {
        int currentLevel = this.levels.size()+1;
        levels.add(new LevelInfo(currentLevel, points, date));
        this.points = this.points + points;
        if(this.levels.size()==this.numLevels){
            this.points = this.points + 100;
            return true;
        }
        return false;
    }
}
