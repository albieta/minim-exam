package edu.upc.dsa.minim.UI.services;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.*;
import edu.upc.dsa.minim.Domain.Entity.Game;
import edu.upc.dsa.minim.Domain.Entity.TO.PassLevelInfo;
import edu.upc.dsa.minim.Domain.Entity.TO.Points;
import edu.upc.dsa.minim.Domain.Entity.TO.UserInfo;
import edu.upc.dsa.minim.Domain.Entity.VO.CurrentGame;
import edu.upc.dsa.minim.Domain.Entity.VO.LevelInfo;
import edu.upc.dsa.minim.Domain.GameManager;
import edu.upc.dsa.minim.Infrastructure.GameManagerImpl;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

import static edu.upc.dsa.minim.Domain.Entity.TO.TransferMethod.UserToUserInfo.userInfoList;

@Api(value = "/dsaGame", description = "Endpoint to Game Service")
@Path("/dsaGame")
public class GameService {
    private GameManager gameManager;

    public GameService() throws GameAlreadyExistsException {
        this.gameManager = GameManagerImpl.getInstance();
        if (gameManager.size()==0) {
            this.gameManager.addUser("Alba");
            this.gameManager.addUser("Maria");
            this.gameManager.addUser("Paula");

            this.gameManager.addGame("1A", "first game", 4);
            this.gameManager.addGame("2A", "second game", 1);
            this.gameManager.addGame("3A", "third game", 6);
        }
    }

    @POST
    @ApiOperation(value = "Create a new game", notes = "Add Game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= Game.class),
            @ApiResponse(code = 409, message = "Conflict, game already exists!")
    })
    @Path("/game")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addGame(Game game) {
        try{
            this.gameManager.addGame(game.getGameId(), game.getDescription(), game.getNumLevels());
        } catch (GameAlreadyExistsException e) {
            return Response.status(409).entity(game).build();
        }
        return Response.status(201).entity(game).build();
    }

    @PUT
    @ApiOperation(value = "Start a game by a User", notes = "Start Game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User or game Not found"),
            @ApiResponse(code = 409, message = "Conflict, game is already active!")
    })
    @Path("/{userId}/{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startGame(@PathParam("userId") String userId, @PathParam("gameId") String gameId) {
        try {
            this.gameManager.startGame(userId, gameId);
        } catch(GameDoesNotExistException | UserDoesNotExistException e) {
            return Response.status(404).build();
        } catch(AlreadyActiveActivityException e) {
            return Response.status(409).build();
        }
        return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "Get current game and level", notes = "Get current game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = CurrentGame.class),
            @ApiResponse(code = 404, message = "No active game or user found")
    })
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentLevel(@PathParam("userId") String userId) {
        CurrentGame currentGame;
        try{
            currentGame = this.gameManager.currentLevel(userId);
        } catch (UserDoesNotExistException| NoGameActiveException e ){
            return Response.status(404).build();
        }
        GenericEntity<CurrentGame> entity = new GenericEntity<CurrentGame>(currentGame) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "Get current points of user current game", notes = "Get current points")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Points.class),
            @ApiResponse(code = 404, message = "No active game or user found")
    })
    @Path("/user/{userId}/points")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPoints(@PathParam("userId") String userId) {
        Integer points;
        try{
            points = this.gameManager.currentPoints(userId);
        } catch (UserDoesNotExistException| NoGameActiveException e ){
            return Response.status(404).build();
        }
        GenericEntity<Points> entity = new GenericEntity<Points>(new Points(points)) {};
        return Response.status(200).entity(entity).build();
    }

    @PUT
    @ApiOperation(value = "Pass current level", notes = "Pass level")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "No active game or user found")
    })
    @Path("/user/passLevel")
    @Produces(MediaType.APPLICATION_JSON)
    public Response passLevel(PassLevelInfo info) {
        try {
            this.gameManager.passLevel(info.getUserIdName(), info.getPoints(), info.getDate());
        } catch(UserDoesNotExistException| NoGameActiveException e) {
            return Response.status(404).build();
        }
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "End current game", notes = "End game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "No user found")
    })
    @Path("/user/{userId}/endGame")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endGame(@PathParam("userId") String userId) {
        try {
            this.gameManager.endGame(userId);
        } catch(UserDoesNotExistException e) {
            return Response.status(404).build();
        }
        return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "Get users of game ordered by score", notes = "Get users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = UserInfo.class),
            @ApiResponse(code = 404, message = "No active game or user found")
    })
    @Path("/users/{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersOrdered(@PathParam("gameId") String gameId) {
        List<UserInfo> users;
        try{
            users = userInfoList(this.gameManager.usersOrderedByPoints(gameId), gameId);
        } catch (GameDoesNotExistException e ){
            return Response.status(404).build();
        }
        GenericEntity<List<UserInfo>> entity = new GenericEntity<List<UserInfo>>(users) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "Get games played by User", notes = "Get games")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Game.class),
            @ApiResponse(code = 404, message = "No user found!")
    })
    @Path("/games/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGamesByUser(@PathParam("userId") String userId) {
        List<Game> games;
        try{
            games = this.gameManager.gamesOfUser(userId);
        } catch (UserDoesNotExistException e ){
            return Response.status(404).build();
        }
        GenericEntity<List<Game>> entity = new GenericEntity<List<Game>>(games) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "Get game activity of User", notes = "Get game activity")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = LevelInfo.class),
            @ApiResponse(code = 404, message = "No user or game found!")
    })
    @Path("/game/{userId}/{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameActivity(@PathParam("userId") String userId, @PathParam("gameId") String gameId) {
        List<LevelInfo> gameActivity;
        try{
            gameActivity = this.gameManager.userActivity(userId, gameId);
        } catch (UserDoesNotExistException | GameDoesNotExistException e ){
            return Response.status(404).build();
        }
        GenericEntity<List<LevelInfo>> entity = new GenericEntity<List<LevelInfo>>(gameActivity) {};
        return Response.status(200).entity(entity).build();
    }

}
