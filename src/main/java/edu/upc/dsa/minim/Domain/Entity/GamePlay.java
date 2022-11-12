package edu.upc.dsa.minim.Domain.Entity;

import edu.upc.dsa.minim.Domain.Entity.VO.LevelInfo;

import java.util.*;

public class GamePlay {
    private Game game;
    private int points;
    private List<LevelInfo> levels;

    public GamePlay(){}

    public GamePlay(Game game){
        this.game = game;
        this.points = 50;
        this.levels = new ArrayList<>();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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
        if(this.levels.size()==this.game.getNumLevels()){
            this.points = this.points + 100;
            return true;
        }
        return false;
    }
}
