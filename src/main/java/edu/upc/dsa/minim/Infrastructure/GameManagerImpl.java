package edu.upc.dsa.minim.Infrastructure;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.*;
import edu.upc.dsa.minim.Domain.Entity.Game;
import edu.upc.dsa.minim.Domain.Entity.GamePlay;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Entity.VO.CurrentGame;
import edu.upc.dsa.minim.Domain.Entity.VO.LevelInfo;
import edu.upc.dsa.minim.Domain.GameManager;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

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
    public void addGame(String gameId, String description, int numLevels) throws GameAlreadyExistsException {
        logger.info("Adding new game with info ("+gameId+", "+description+", "+numLevels+")");
        if(getGame(gameId)!=null) {
            logger.warn("This game already exists, cannot be created!");
            throw new GameAlreadyExistsException();
        }
        this.games.add(new Game(gameId, description, numLevels));
        logger.info("Game was correctly created!");
    }

    @Override
    public void addUser(String userIdName) {
        this.users.put(userIdName, new User(userIdName));
    }

    @Override
    public void startGame(String userIdName, String gameId) throws GameDoesNotExistException, UserDoesNotExistException, AlreadyActiveActivityException {
        logger.info("Starting a new game with userId: "+userIdName+" and gameId: "+gameId);
        Game game = getGame(gameId);
        User user = getUser(userIdName);
        isGameNull(game);
        isUserNull(user);
        user.startGame(game);
        logger.info("The game was correctly started!");
    }

    @Override
    public CurrentGame currentLevel(String userIdName) throws UserDoesNotExistException, NoGameActiveException {
        logger.info("Getting current game of User with id: "+userIdName);
        User user = getUser(userIdName);
        isUserNull(user);
        logger.info("OK");
        CurrentGame currentGame = new CurrentGame(user.getCurrentGame().getGameId(),user.getLevel());
        logger.info("Current level is: "+currentGame);
        return currentGame;
    }

    @Override
    public int currentPoints(String userIdName) throws UserDoesNotExistException, NoGameActiveException {
        logger.info("Getting current points of game of userId: "+ userIdName);
        User user = getUser(userIdName);
        isUserNull(user);
        int points = user.getPoints();
        logger.info("User current points are: "+points);
        return points;
    }

    @Override
    public void passLevel(String userIdName, int points, String date) throws UserDoesNotExistException, NoGameActiveException {
        logger.info("Passing level of userId: "+userIdName+" with information ("+points+", "+date+")");
        User user = getUser(userIdName);
        isUserNull(user);
        user.passLevel(points, date);
        logger.info("Level Passing was correctly effectuated!");
    }

    @Override
    public void endGame(String userIdName) throws UserDoesNotExistException {
        logger.info("Ending current game of userId: "+userIdName);
        User user = getUser(userIdName);
        isUserNull(user);
        user.endGame();
        logger.info("End of game was correctly effectuated!");
    }

    @Override
    public List<User> usersOrderedByPoints(String gameId) throws GameDoesNotExistException {
        logger.info("Getting Users ordered by Points of gameId: "+gameId);
        Game game = getGame(gameId);
        isGameNull(game);
        logger.info("Users were correctly obtained!");
        return getUsersOrderedByPoints(gameId);
    }

    @Override
    public List<Game> gamesOfUser(String userIdName) throws UserDoesNotExistException {
        logger.info("Getting games of User with id: "+ userIdName);
        User user = getUser(userIdName);
        isUserNull(user);
        List<Game> gamesPlayed = new ArrayList<>();
        for(String gamePlayed : user.getGamesPlayed().keySet()){
            gamesPlayed.add(getGame(gamePlayed));
        }
        logger.info("Games of User were correctly obtained!");
        return gamesPlayed;
    }

    @Override
    public List<LevelInfo> userActivity(String userIdName, String gameId) throws UserDoesNotExistException, GameDoesNotExistException {
        logger.info("Getting user activity of userId: "+userIdName+" and gameId: "+gameId);
        Game game = getGame(gameId);
        User user = getUser(userIdName);
        isGameNull(game);
        isUserNull(user);
        GamePlay gamePlayed = getUser(userIdName).hasPlayedGame(gameId);
        if(gamePlayed==null){
            return new ArrayList<>();
        }
        List<LevelInfo> userActivity = gamePlayed.getLevels();
        logger.info("User Activity was correctly obtained!");
        return userActivity;
    }

    public List<User> getUsersOrderedByPoints(String gameId) {
        return this.users.values().stream()
                .filter(x -> (x.hasPlayedGame(gameId)!=null))
                .sorted((User u1, User u2) -> (u2.hasPlayedGame(gameId).getPoints() - u1.hasPlayedGame(gameId).getPoints()))
                .collect(Collectors.toList());
    }

    public Game getGame(String gameId) {
        return this.games.stream()
                .filter(x->gameId.equals(x.getGameId()))
                .findFirst()
                .orElse(null);
    }

    public User getUser(String userIdName) {
        return this.users.get(userIdName);
    }

    public void isGameNull(Game game) throws GameDoesNotExistException {
        if(game==null) {
            logger.warn("Game does not exist EXCEPTION");
            throw new GameDoesNotExistException();
        }
    }

    public void isUserNull(User user) throws UserDoesNotExistException {
        if(user==null) {
            logger.warn("User does not exist EXCEPTION");
            throw new UserDoesNotExistException();
        }
    }

    public int numUsers(){
        return this.users.size();
    }

    public int numGames(){
        return this.games.size();
    }
}
