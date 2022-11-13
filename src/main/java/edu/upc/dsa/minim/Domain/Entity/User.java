package edu.upc.dsa.minim.Domain.Entity;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.AlreadyActiveActivityException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.NoGameActiveException;

import java.util.*;

public class User {
    private String userIdName;
    private Boolean active;
    private String mostRecentPlay;
    private Map<String, Play> plays;

    public User(){
        this.active = false;
        this.plays = new HashMap<>();
    }

    public User(String userIdName) {
        this();
        this.userIdName = userIdName;
    }

    public String getUserIdName() {
        return userIdName;
    }

    public void setUserIdName(String userIdName) {
        this.userIdName = userIdName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Map<String, Play> getPlays() {
        return plays;
    }

    public void setPlays(Map<String, Play> plays) {
        this.plays = plays;
    }

    public int getPoints() throws NoGameActiveException {
        if(!this.active){
            throw new NoGameActiveException();
        }
        return currentGamePlay().getPoints();
    }

    public String getCurrentGame() throws NoGameActiveException {
        if(!this.active){
            throw new NoGameActiveException();
        }
        return currentGamePlay().getGame();
    }

    public int getLevel() {
        return currentGamePlay().getCurrentLevel();
    }

    public void startGame(Game game) throws AlreadyActiveActivityException {
        if(this.active){
            throw new AlreadyActiveActivityException();
        }
        this.plays.put(game.getGameId(), new Play(game));
        this.mostRecentPlay = game.getGameId();
        this.active = true;
    }

    public void passLevel(int points, String date) throws NoGameActiveException {
        if(!this.active){
            throw new NoGameActiveException();
        }
        if(currentGamePlay().passLevel(points, date)){
            this.active = false;
        }
    }

    public void endGame() {
        this.active = false;
    }

    public Play currentGamePlay() {
        return this.plays.get(this.mostRecentPlay);
    }

    public Play hasPlayedGame(String gameId) {
        return this.plays.get(gameId);
    }
}
