package edu.upc.dsa.minim.Domain;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.*;
import edu.upc.dsa.minim.Domain.Entity.Game;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Entity.VO.CurrentGame;
import edu.upc.dsa.minim.Domain.Entity.VO.LevelInfo;

import java.util.*;

public interface GameManager {
    public int size();

    public int numUsers();

    public int numGames();

    public void addGame(String gameId, String description, int numLevels) throws GameAlreadyExistsException;

    public void addUser(String userIdName);

    public void startGame(String userIdName, String gameId) throws GameDoesNotExistException, UserDoesNotExistException, AlreadyActiveActivityException;

    public CurrentGame currentLevel(String userIdName) throws UserDoesNotExistException, NoGameActiveException;

    public int currentPoints(String userIdName) throws UserDoesNotExistException, NoGameActiveException;

    public void passLevel(String userIdName, int points, String date) throws UserDoesNotExistException, NoGameActiveException;

    public void endGame(String userIdName) throws UserDoesNotExistException;

    public List<User> usersOrderedByPoints(String gameId) throws GameDoesNotExistException;

    public List<Game> gamesOfUser(String userIdName) throws UserDoesNotExistException;

    public List<LevelInfo> userActivity(String userIdName, String gameId) throws UserDoesNotExistException, GameDoesNotExistException;
}
