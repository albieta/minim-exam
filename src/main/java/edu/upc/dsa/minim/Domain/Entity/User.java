package edu.upc.dsa.minim.Domain.Entity;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.AlreadyActiveActivityException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.NoGameActiveException;
import edu.upc.dsa.minim.Domain.Entity.VO.*;

import java.util.*;

public class User {
    private String userIdName;
    private int points;
    private int level;
    private Game currentGame;
    private Map<String, List<LevelInfo>> gamesPlayed;

    public User(){
        this.gamesPlayed = new HashMap<>();
    }

    public User(String userIdName) {
        this();
        this.userIdName = userIdName;
        this.points = 0;
    }

    public String getUserIdName() {
        return userIdName;
    }

    public void setUserIdName(String userIdName) {
        this.userIdName = userIdName;
    }

    public int getPoints() throws NoGameActiveException {
        if(this.currentGame == null){
            throw new NoGameActiveException();
        }
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Game getCurrentGame() throws NoGameActiveException {
        if(this.currentGame == null){
            throw new NoGameActiveException();
        }
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public Map<String, List<LevelInfo>> getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Map<String, List<LevelInfo>> gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void startGame(Game game) throws AlreadyActiveActivityException {
        if(currentGame!=null){
            throw new AlreadyActiveActivityException();
        }
        this.currentGame = game;
        this.level = 1;
        this.points = 50;
    }

    public void passLevel(int points, String date) throws NoGameActiveException {
        if(this.currentGame == null){
            throw new NoGameActiveException();
        }
        int maxLevel = this.currentGame.getNumLevels();
        if (this.level==1){
            this.gamesPlayed.put(currentGame.getGameId(), new ArrayList<>());
        }
        List<LevelInfo> list = this.gamesPlayed.get(currentGame.getGameId());
        list.add(new LevelInfo(this.level, points, date));
        this.gamesPlayed.put(currentGame.getGameId(), list);
        this.level = this.level + 1;
        this.points = this.points + points;
        if(this.level==maxLevel){
            this.currentGame = null;
        }
    }

    public void endGame() {
        this.currentGame = null;
    }
}
