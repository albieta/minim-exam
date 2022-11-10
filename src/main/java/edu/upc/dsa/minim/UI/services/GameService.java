package edu.upc.dsa.minim.UI.services;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.AlreadyActiveActivityException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.GameDoesNotExistException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.NoGameActiveException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.UserDoesNotExistException;
import edu.upc.dsa.minim.Domain.Entity.TO.GameInfo;
import edu.upc.dsa.minim.Domain.Entity.TO.PassLevelInfo;
import edu.upc.dsa.minim.Domain.Entity.VO.CurrentGame;
import edu.upc.dsa.minim.Domain.GameManager;
import edu.upc.dsa.minim.Infrastructure.GameManagerImpl;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/game", description = "Endpoint to Game Service")
@Path("/Game")
public class GameService {
    private GameManager gameManager;

    public GameService() {
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
    @ApiOperation(value = "create a new game", notes = "Add Game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= GameInfo.class)
    })
    @Path("/game")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addGame(GameInfo game) {
        this.gameManager.addGame(game.getGameId(), game.getDescription(), game.getNumLevels());

        return Response.status(201).entity(game).build();
    }

    @PUT
    @ApiOperation(value = "start a game", notes = "Start Game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 400, message = "Error"),
    })
    @Path("/{userId}/{objectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startGame(@PathParam("userId") String userId, @PathParam("objectId") String objectId) {
        try {
            this.gameManager.startGame(userId, objectId);
        } catch(GameDoesNotExistException| UserDoesNotExistException| AlreadyActiveActivityException| NoGameActiveException e) {
            return Response.status(400).build();
        }
        return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "get current game", notes = "Get current game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = CurrentGame.class),
            @ApiResponse(code = 400, message = "Error")
    })
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentLevel(@PathParam("userId") String userId) {
        CurrentGame currentGame;
        try{
            currentGame = this.gameManager.currentLevel(userId);
        } catch (UserDoesNotExistException| NoGameActiveException e ){
            return Response.status(400).build();
        }

        GenericEntity<CurrentGame> entity = new GenericEntity<CurrentGame>(currentGame) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get current points", notes = "Get current points")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Integer.class),
            @ApiResponse(code = 400, message = "Error")
    })
    @Path("/user/{userId}/points")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPoints(@PathParam("userId") String userId) {
        Integer points;
        try{
            points = this.gameManager.currentPoints(userId);
        } catch (UserDoesNotExistException| NoGameActiveException e ){
            return Response.status(400).build();
        }

        GenericEntity<Integer> entity = new GenericEntity<Integer>(points) {};
        return Response.status(200).entity(entity).build();
    }

    @PUT
    @ApiOperation(value = "pass level", notes = "Pass level")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 400, message = "Error"),
    })
    @Path("/user/passLevel")
    @Produces(MediaType.APPLICATION_JSON)
    public Response passLevel(PassLevelInfo info) {
        try {
            this.gameManager.passLevel(info.getUserIdName(), info.getPoints(), info.getDate());
        } catch(GameDoesNotExistException| UserDoesNotExistException| NoGameActiveException e) {
            return Response.status(400).build();
        }
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "end game", notes = "End game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 400, message = "Error"),
    })
    @Path("/user/{userId}/endGame")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endGame(@PathParam("userId") String userId) {
        try {
            this.gameManager.endGame(userId);
        } catch(UserDoesNotExistException e) {
            return Response.status(400).build();
        }
        return Response.status(201).build();
    }

}
