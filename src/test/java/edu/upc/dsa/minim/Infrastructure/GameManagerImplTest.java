package edu.upc.dsa.minim.Infrastructure;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.*;
import edu.upc.dsa.minim.Domain.Entity.Game;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Entity.VO.CurrentGame;
import edu.upc.dsa.minim.Domain.Entity.VO.LevelInfo;
import edu.upc.dsa.minim.Domain.GameManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class GameManagerImplTest {
    GameManager gameManager;

    @Before
    public void setUp() throws GameAlreadyExistsException {
        this.gameManager = new GameManagerImpl();
        this.gameManager.addUser("Alba");
        this.gameManager.addUser("Maria");
        this.gameManager.addUser("Paula");

        this.gameManager.addGame("1A", "first game", 4);
        this.gameManager.addGame("2A", "second game", 1);
        this.gameManager.addGame("3A", "third game", 6);
    }

    @After
    public void tearDown() {
        this.gameManager = null;
    }

    @Test
    public void test_creation_game_possible() throws GameAlreadyExistsException {
        Assert.assertEquals(3, this.gameManager.numGames());
        this.gameManager.addGame("4A", "4 game", 4);
        Assert.assertEquals(4, this.gameManager.numGames());
    }

    @Test
    public void test_creation_existing_game_throws_exception() {
        Assert.assertEquals(3, this.gameManager.numGames());
        Assert.assertThrows(GameAlreadyExistsException.class, ()->this.gameManager.addGame("1A", "4 game", 4));
        Assert.assertEquals(3, this.gameManager.numGames());
    }

    @Test
    public void test_start_game_non_existing_user_or_game_exception() {
        Assert.assertThrows(UserDoesNotExistException.class, ()->this.gameManager.startGame("Clara", "1A"));
        Assert.assertThrows(GameDoesNotExistException.class, ()->this.gameManager.startGame("Clara", "6A"));
    }

    @Test
    public void test_start_game_is_possible() throws UserDoesNotExistException, GameDoesNotExistException, AlreadyActiveActivityException, NoGameActiveException {
        this.gameManager.startGame("Alba", "1A");
        Assert.assertEquals("1A", this.gameManager.gamesOfUser("Alba").get(0).getGameId());
    }

    @Test
    public void test_exceptions_current_level_of_user() {
        Assert.assertThrows(NoGameActiveException.class, ()-> this.gameManager.currentLevel("Alba"));
        Assert.assertThrows(UserDoesNotExistException.class, ()-> this.gameManager.currentLevel("Clara"));
    }

    @Test
    public void test_current_level_of_user() throws UserDoesNotExistException, GameDoesNotExistException, AlreadyActiveActivityException, NoGameActiveException {
        this.gameManager.startGame("Alba", "1A");
        CurrentGame currentGame = this.gameManager.currentLevel("Alba");
        Assert.assertEquals("1A", currentGame.getGameId());
        Assert.assertEquals(1, currentGame.getLevel());
    }

    @Test
    public void test_exceptions_pass_level() {
        Assert.assertThrows(NoGameActiveException.class, ()-> this.gameManager.passLevel("Alba", 5, "11/12/2022"));
        Assert.assertThrows(UserDoesNotExistException.class, ()-> this.gameManager.passLevel("Clara", 5, "11/12/2022"));
    }

    @Test
    public void test_pass_level() throws UserDoesNotExistException, GameDoesNotExistException, AlreadyActiveActivityException, NoGameActiveException {
        this.gameManager.startGame("Alba", "1A");
        CurrentGame initialGame = this.gameManager.currentLevel("Alba");
        this.gameManager.passLevel("Alba", 5, "11/12/2022");
        CurrentGame passedGame = this.gameManager.currentLevel("Alba");
        Assert.assertEquals(1, initialGame.getLevel());
        Assert.assertEquals(2, passedGame.getLevel());
    }

    @Test
    public void test_current_points_of_user_after_passing_level() throws UserDoesNotExistException, GameDoesNotExistException, AlreadyActiveActivityException, NoGameActiveException {
        this.gameManager.startGame("Alba", "1A");
        int initialPoints = this.gameManager.currentPoints("Alba");
        this.gameManager.passLevel("Alba", 5, "11/12/2022");
        int afterPoints = this.gameManager.currentPoints("Alba");
        Assert.assertEquals(50, initialPoints);
        Assert.assertEquals(55, afterPoints);

        this.gameManager.startGame("Maria", "2A");
        int initialPoints2 = this.gameManager.currentPoints("Maria");
        this.gameManager.passLevel("Maria", 5, "11/12/2022");
        int afterPoints2 = this.gameManager.usersOrderedByPoints("2A").get(0).getPlays().get("2A").getPoints();
        Assert.assertEquals(50, initialPoints2);
        Assert.assertEquals(155, afterPoints2);
    }

    @Test
    public void test_end_game_is_possible() throws UserDoesNotExistException, GameDoesNotExistException, AlreadyActiveActivityException, NoGameActiveException {
        this.gameManager.startGame("Alba", "1A");
        CurrentGame currentGame = this.gameManager.currentLevel("Alba");
        this.gameManager.endGame("Alba");

        Assert.assertEquals("1A", currentGame.getGameId());
        Assert.assertThrows(NoGameActiveException.class, ()->this.gameManager.currentLevel("Alba"));
    }

    @Test
    public void test_exceptions_users_ordered_by_points() {
        Assert.assertThrows(GameDoesNotExistException.class, ()->this.gameManager.usersOrderedByPoints("6A"));
    }

    @Test
    public void test_users_ordered_by_points() throws GameDoesNotExistException, UserDoesNotExistException, AlreadyActiveActivityException, NoGameActiveException {
        this.gameManager.startGame("Alba", "1A");
        this.gameManager.startGame("Maria", "1A");
        this.gameManager.passLevel("Maria", 5, "11/12/2022");
        List<User> users = this.gameManager.usersOrderedByPoints("1A");
        Assert.assertEquals("Maria", users.get(0).getUserIdName());
        Assert.assertEquals("Alba", users.get(1).getUserIdName());

        this.gameManager.endGame("Alba");
        this.gameManager.endGame("Maria");

        this.gameManager.startGame("Alba", "2A");
        this.gameManager.startGame("Maria", "2A");
        this.gameManager.passLevel("Maria", 0, "11/12/2022");
        List<User> users2 = this.gameManager.usersOrderedByPoints("1A");
        Assert.assertEquals("Maria", users2.get(0).getUserIdName());
        Assert.assertEquals("Alba", users2.get(1).getUserIdName());
    }

    @Test
    public void test_games_of_user() throws UserDoesNotExistException, GameDoesNotExistException, AlreadyActiveActivityException, NoGameActiveException {
        this.gameManager.startGame("Alba", "1A");
        this.gameManager.passLevel("Alba", 5, "11/12/2022");
        this.gameManager.endGame("Alba");
        this.gameManager.startGame("Alba", "2A");
        List<Game> games = this.gameManager.gamesOfUser("Alba");

        Assert.assertEquals(2, games.size());
    }

    @Test
    public void test_user_activity() throws UserDoesNotExistException, GameDoesNotExistException, AlreadyActiveActivityException, NoGameActiveException {
        this.gameManager.startGame("Alba", "1A");
        this.gameManager.passLevel("Alba", 5, "11/12/2022");
        this.gameManager.passLevel("Alba", 18, "11/12/2022");

        this.gameManager.endGame("Alba");
        this.gameManager.startGame("Alba", "2A");
        this.gameManager.passLevel("Alba", 3, "11/12/2022");

        List<LevelInfo> infos = this.gameManager.userActivity("Alba", "1A");

        Assert.assertEquals(2, infos.size());
        Assert.assertEquals(5, infos.get(0).getPoints());
        Assert.assertEquals(18, infos.get(1).getPoints());
    }

}
