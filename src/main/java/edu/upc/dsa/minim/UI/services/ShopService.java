package edu.upc.dsa.minim.UI.services;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.EmailAddressNotValidException;
import edu.upc.dsa.minim.Domain.Entity.Exceptions.UserAlreadyExistsException;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Entity.VO.Credentials;
import edu.upc.dsa.minim.Domain.Entity.VO.EmailAddress;
import edu.upc.dsa.minim.Domain.Repository.ShopManager;
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
            this.shopManager.addUser("Alba", "Roma Gómez", "23/11/2001", credentials1);
            Credentials credentials2 = new Credentials(new EmailAddress("susanagr@gmail.com"), "123test");
            this.shopManager.addUser("Susana", "Roma Gómez", "19/5/1971", credentials2);
            Credentials credentials3 = new Credentials(new EmailAddress("oriolplansponsa@gmail.com"), "123456");
            this.shopManager.addUser("Oriol", "Plans Ponsa", "11/4/1997", credentials3);

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
            @ApiResponse(code = 201, message = "Successful", response= User.class),
            @ApiResponse(code = 409, message = "Conflict, User already exists")
    })
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response registerUser(User user) {
        try{
            this.shopManager.addUser(user.getUserName(), user.getUserSurname(), user.getBirthDate(), user.getCredentials());
        } catch (UserAlreadyExistsException e) {
            return Response.status(409).entity(user).build();
        }
        return Response.status(201).entity(user).build();
    }

    @GET
    @ApiOperation(value = "get users ordered by surname and name", notes = "Get Users")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersOrdered() {
        List<User> users = this.shopManager.getUsers();

        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(201).entity(entity).build();
    }
}
