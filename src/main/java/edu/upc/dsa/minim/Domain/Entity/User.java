package edu.upc.dsa.minim.Domain.Entity;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.AlreadyActiveActivityException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.NoGameActiveException;

import java.util.*;

public class User {
    private String userIdName;
    private Boolean activeGame;
    private String mostRecentGame;
    private Map<String, GamePlay> gamesPlayed;

    public User(){
        this.activeGame = false;
        this.gamesPlayed = new HashMap<>();
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

    public Boolean getActiveGame() {
        return activeGame;
    }

    public void setActiveGame(Boolean activeGame) {
        this.activeGame = activeGame;
    }

    public Map<String, GamePlay> getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Map<String, GamePlay> gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getPoints() throws NoGameActiveException {
        if(!this.activeGame){
            throw new NoGameActiveException();
        }
        return currentGamePlay().getPoints();
    }

    public Game getCurrentGame() throws NoGameActiveException {
        if(!this.activeGame){
            throw new NoGameActiveException();
        }
        return currentGamePlay().getGame();
    }

    public int getLevel() {
        return currentGamePlay().getCurrentLevel();
    }

    public void startGame(Game game) throws AlreadyActiveActivityException {
        if(this.activeGame){
            throw new AlreadyActiveActivityException();
        }
        this.gamesPlayed.put(game.getGameId(), new GamePlay(game));
        this.mostRecentGame = game.getGameId();
        this.activeGame = true;
    }

    public void passLevel(int points, String date) throws NoGameActiveException {
        if(!this.activeGame){
            throw new NoGameActiveException();
        }
        if(currentGamePlay().passLevel(points, date)){
            this.activeGame = false;
        }
    }

    public void endGame() {
        this.activeGame = false;
    }

    public GamePlay currentGamePlay() {
        return this.gamesPlayed.get(this.mostRecentGame);
    }

    public GamePlay hasPlayedGame(String gameId) {
        return this.gamesPlayed.get(gameId);
    }
}
