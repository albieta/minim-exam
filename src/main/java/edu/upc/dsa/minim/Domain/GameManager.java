package edu.upc.dsa.minim.Domain;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.AlreadyActiveActivityException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.GameDoesNotExistException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.NoGameActiveException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.UserDoesNotExistException;
import edu.upc.dsa.minim.Domain.Entity.Game;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Entity.VO.CurrentGame;
import edu.upc.dsa.minim.Domain.Entity.VO.LevelInfo;

import java.util.*;

public interface GameManager {
    public int size();

    public int numUsers();

    public int numGames();

    public void addGame(String gameId, String description, int numLevels);

    public void addUser(String userIdName);

    public void startGame(String gameId, String userIdName) throws GameDoesNotExistException, UserDoesNotExistException, AlreadyActiveActivityException, NoGameActiveException;

    public CurrentGame currentLevel(String userIdName) throws UserDoesNotExistException, NoGameActiveException;

    public int currentPoints(String userIdName) throws UserDoesNotExistException, NoGameActiveException;

    public void passLevel(String userIdName, int points, String date) throws UserDoesNotExistException, NoGameActiveException, GameDoesNotExistException;

    public void endGame(String userIdName) throws UserDoesNotExistException;

    public List<User> usersOrderedByPoints(String gameId) throws GameDoesNotExistException, UserDoesNotExistException;

    public List<Game> gamesOfUser(String userIdName) throws UserDoesNotExistException, GameDoesNotExistException;

    public List<LevelInfo> userActivity(String userIdName, String gameId) throws UserDoesNotExistException;
}
