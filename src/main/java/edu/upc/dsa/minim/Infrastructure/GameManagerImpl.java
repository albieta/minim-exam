package edu.upc.dsa.minim.Infrastructure;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.AlreadyActiveActivityException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.GameDoesNotExistException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.NoGameActiveException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.UserDoesNotExistException;
import edu.upc.dsa.minim.Domain.Entity.Game;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Entity.VO.CurrentGame;
import edu.upc.dsa.minim.Domain.Entity.VO.LevelInfo;
import edu.upc.dsa.minim.Domain.Entity.VO.UserHistory;
import edu.upc.dsa.minim.Domain.GameManager;
import org.apache.log4j.Logger;

import java.util.*;

public class GameManagerImpl implements GameManager {

    private static GameManager instance;

    protected List<Game> games;

    protected Map<String, User> users;

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    public GameManagerImpl(){
        this.games = new ArrayList<>();
        this.users = new HashMap<>();
    }

    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }

    @Override
    public int size() {
        int ret = this.users.size();
        logger.info("size " + ret);

        return ret;
    }

    @Override
    public void addGame(String gameId, String description, int numLevels) {
        this.games.add(new Game(gameId, description, numLevels));
    }

    @Override
    public void addUser(String userIdName) {
        this.users.put(userIdName, new User(userIdName));
    }

    @Override
    public void startGame(String gameId, String userIdName) throws GameDoesNotExistException, UserDoesNotExistException, AlreadyActiveActivityException, NoGameActiveException {
        Game game = getGame(gameId);
        User user = getUser(userIdName);
        user.startGame(game);
        game.addUserHistory(user);
    }

    @Override
    public CurrentGame currentLevel(String userIdName) throws UserDoesNotExistException, NoGameActiveException {
        User user = getUser(userIdName);
        return new CurrentGame(user.getCurrentGame().getGameId(),user.getLevel());
    }

    @Override
    public int currentPoints(String userIdName) throws UserDoesNotExistException, NoGameActiveException {
        User user = getUser(userIdName);
        return user.getPoints();
    }

    @Override
    public void passLevel(String userIdName, int points, String date) throws UserDoesNotExistException, NoGameActiveException, GameDoesNotExistException {
        User user = getUser(userIdName);
        user.passLevel(points, date);
        getGame(user.getCurrentGame().getGameId()).addUserHistory(user);
    }

    @Override
    public void endGame(String userIdName) throws UserDoesNotExistException {
        User user = getUser(userIdName);
        user.endGame();
    }

    @Override
    public List<User> usersOrderedByPoints(String gameId) throws GameDoesNotExistException, UserDoesNotExistException {
        Game game = getGame(gameId);
        game.sort();
        List<UserHistory> users = game.getUsersHistory();
        return UsersHistoryToUser(users);
    }

    @Override
    public List<Game> gamesOfUser(String userIdName) throws UserDoesNotExistException, GameDoesNotExistException {
        Collection<String> gamesPlayed = getUser(userIdName).getGamesPlayed().keySet();
        List<Game> games = new ArrayList<>();
        for(String game : gamesPlayed){
            games.add(getGame(game));
        }
        return games;
    }

    @Override
    public List<LevelInfo> userActivity(String userIdName, String gameId) throws UserDoesNotExistException {
        return getUser(userIdName).getGamesPlayed().get(gameId);
    }

    public Game getGame(String gameId) throws GameDoesNotExistException {
        Game game = this.games.stream()
                .filter(x->gameId.equals(x.getGameId()))
                .findFirst()
                .orElse(null);
        if (game == null){
            throw new GameDoesNotExistException();
        }
        return game;
    }

    public User getUser(String userIdName) throws UserDoesNotExistException {
        User user = this.users.get(userIdName);
        if(user == null) {
            throw new UserDoesNotExistException();
        }
        return user;
    }

    public List<User> UsersHistoryToUser(List<UserHistory> usersHistory) throws UserDoesNotExistException {
        List<User> users = new ArrayList<>();
        for(UserHistory userHistory: usersHistory){
            users.add(getUser(userHistory.getUserId()));
        }
        return users;
    }

    public int numUsers(){
        return this.users.size();
    }

    public int numGames(){
        return this.games.size();
    }

}
