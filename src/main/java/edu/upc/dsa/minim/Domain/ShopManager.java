package edu.upc.dsa.minim.Domain;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.*;
import edu.upc.dsa.minim.Domain.Entity.ObjectShop;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Entity.VO.Credentials;

import java.util.*;

public interface ShopManager {
    public int size();

    public User userInfo(String userId);

    public ObjectShop objectInfo(String objectId);

    public void registerUser(String name, String surname, String birthdate, Credentials credentials) throws UserAlreadyExistsException;

    public List<User> getUsers();

    public User loginUser(Credentials credentials) throws UserCredentialsNotValidException;

    public void addObject(String objectName, String description, Double price);

    public List<ObjectShop> objectsByPrice();

    public void objectPurchase(String userId, String objectId) throws UserDoesNotExistException, ObjectDoesNotExistException, NotEnoughMoneyException;

    public List<ObjectShop> objectsByUser(String userId);

    public int numUsers();

    public int numObjects();
}
