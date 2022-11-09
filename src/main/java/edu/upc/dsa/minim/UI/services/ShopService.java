package edu.upc.dsa.minim.UI.services;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.*;
import edu.upc.dsa.minim.Domain.Entity.ObjectShop;
import edu.upc.dsa.minim.Domain.Entity.TO.ObjectInfo;
import edu.upc.dsa.minim.Domain.Entity.TO.TransferMethods.UserTransfer;
import edu.upc.dsa.minim.Domain.Entity.TO.UserInfo;
import edu.upc.dsa.minim.Domain.Entity.TO.UserRegister;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Entity.VO.Credentials;
import edu.upc.dsa.minim.Domain.Entity.VO.EmailAddress;
import edu.upc.dsa.minim.Domain.ShopManager;
import edu.upc.dsa.minim.Infrastructure.ShopManagerImpl;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;



@Api(value = "/shop", description = "Endpoint to Shop Service")
@Path("/shop")
public class ShopService {
    private ShopManager shopManager;

    public ShopService() throws UserAlreadyExistsException, EmailAddressNotValidException {
        this.shopManager = ShopManagerImpl.getInstance();
        if (shopManager.size()==0) {
            Credentials credentials1 = new Credentials(new EmailAddress("albaromagomez@gmail.com"), "Test123");
            this.shopManager.registerUser("Alba", "Roma Gómez", "23/11/2001", credentials1);
            Credentials credentials2 = new Credentials(new EmailAddress("susanagr@gmail.com"), "123test");
            this.shopManager.registerUser("Susana", "Roma Gómez", "19/5/1971", credentials2);
            Credentials credentials3 = new Credentials(new EmailAddress("oriolplansponsa@gmail.com"), "123456");
            this.shopManager.registerUser("Oriol", "Plans Ponsa", "11/4/1997", credentials3);

            this.shopManager.addObject("Pa Bimbo", "un pa molt bo", 2.3);
            this.shopManager.addObject("Talla ungles", "talla ungles per quan les tens llargues", 6.1);
            this.shopManager.addObject("Cocacola", "es molt bona", 1.2);
            this.shopManager.addObject("Llibreta", "per escriure algo", 4.9);
            this.shopManager.addObject("Anell", "per posar en el dit", 11.1);
        }
    }

    @POST
    @ApiOperation(value = "register a new user", notes = "Register User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= UserRegister.class),
            @ApiResponse(code = 409, message = "Conflict, User already exists")
    })
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response registerUser(UserRegister user) {
        try{
            this.shopManager.registerUser(user.getUserName(), user.getUserSurname(), user.getBirthDate(), user.getCredentials());
        } catch (UserAlreadyExistsException e) {
            return Response.status(409).entity(user).build();
        }

        return Response.status(201).entity(user).build();
    }

    @POST
    @ApiOperation(value = "login of a user", notes = "Login User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 401, message = "Unauthorized, Invalid credentials")
    })
    @Path("/user/login")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response loginUser(Credentials credentials) {
        try{
            this.shopManager.loginUser(credentials);
        } catch (UserCredentialsNotValidException e) {
            return Response.status(401).build();
        }
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "perform an object purchase from user", notes = "Purchase")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 403, message = "Not enough money"),
            @ApiResponse(code = 400, message = "User or Object do not exist")
    })
    @Path("/{userId}/{objectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response objectPurchase(@PathParam("userId") String userId, @PathParam("objectId") String objectId) {
        try {
            this.shopManager.objectPurchase(userId, objectId);
        } catch (NotEnoughMoneyException e) {
            return Response.status(403).build();
        } catch (UserDoesNotExistException | ObjectDoesNotExistException e) {
            return Response.status(400).build();
        }
        return Response.status(201).build();
    }

    @POST
    @ApiOperation(value = "add a new Object", notes = "Add Object")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= ObjectInfo.class)
    })
    @Path("/object")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addObject(ObjectInfo object) {
        this.shopManager.addObject(object.getObjectName(), object.getDescription(), object.getPrice());
        return Response.status(201).entity(object).build();
    }

    @GET
    @ApiOperation(value = "get users ordered by surname and name", notes = "Get Users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = UserInfo.class, responseContainer="List"),
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersOrdered() {
        List<User> users = this.shopManager.getUsers();

        GenericEntity<List<UserInfo>> entity = new GenericEntity<List<UserInfo>>(UserTransfer.userInfoList(users)) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get objects ordered by price", notes = "Get Objects")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = ObjectShop.class, responseContainer="List"),
    })
    @Path("/objects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjectsOrdered() {
        List<ObjectShop> objects = this.shopManager.objectsByPrice();

        GenericEntity<List<ObjectShop>> entity = new GenericEntity<List<ObjectShop>>(objects) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get objects purchased by user", notes = "Get Orders By User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = ObjectShop.class, responseContainer="List"),
            @ApiResponse(code = 400, message = "User does not exist")
    })
    @Path("/object/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response objectsByUser(@PathParam("userId") String userId) {
        try{
            List<ObjectShop> objects = this.shopManager.objectsByUser(userId);
            GenericEntity<List<ObjectShop>> entity = new GenericEntity<List<ObjectShop>>(objects) {};
            return Response.status(200).entity(entity).build();
        } catch (UserDoesNotExistException e) {
            return Response.status(400).build();
        }
    }
}
