package edu.upc.dsa.minim.Infrastructure;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.*;
import edu.upc.dsa.minim.Domain.Entity.Game;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.GameManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class GameManagerImplTest {
    GameManager gameManager;

    @Before
    public void setUp() {
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
    public void test_creation_game_possible(){
        Assert.assertEquals(3, this.gameManager.numGames());
        this.gameManager.addGame("4A", "4 game", 4);
        Assert.assertEquals(4, this.gameManager.numGames());
    }

    @Test
    public void test_start_game_non_existing_user_exception() {
        Assert.assertThrows(UserDoesNotExistException.class, ()->this.gameManager.startGame("1A", "clara"));
    }

    @Test
    public void test_start_game_is_possible() throws UserDoesNotExistException, GameDoesNotExistException, AlreadyActiveActivityException, NoGameActiveException {
        this.gameManager.startGame("1A", "Alba");
        Assert.assertEquals("1A", this.gameManager.gamesOfUser("Alba"));
    }

}
