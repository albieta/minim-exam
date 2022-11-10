package edu.upc.dsa.minim.Infrastructure;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.*;
import edu.upc.dsa.minim.Domain.Entity.ObjectShop;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Entity.VO.Credentials;
import edu.upc.dsa.minim.Domain.ShopManager;
import org.apache.log4j.Logger;

import java.util.*;

public class ShopManagerImpl implements ShopManager {
    private static ShopManager instance;

    protected List<ObjectShop> objects;

    protected Map<String, User> users;

    final static Logger logger = Logger.getLogger(ShopManagerImpl.class);

    public ShopManagerImpl(){
        this.objects = new ArrayList<>();
        this.users = new HashMap<>();
    }

    public static ShopManager getInstance() {
        if (instance==null) instance = new ShopManagerImpl();
        return instance;
    }

    public int size() {
        int ret = this.users.size();
        logger.info("size " + ret);

        return ret;
    }

    @Override
    public User userInfo(String userId){
        return this.users.get(userId);
    }

    @Override
    public ObjectShop objectInfo(String objectId){
        return getObject(objectId);
    }

    @Override
    public void registerUser(String name, String surname, String birthdate, Credentials credentials) throws UserAlreadyExistsException, EmailAddressNotValidException {
        logger.info("Trying register of user with information: ("+name+", "+surname+", "+birthdate+", {credentials})");
        if(userExistsByCredentials(credentials)){
            logger.warn("Register not possible, User already exists! :(");
            throw new UserAlreadyExistsException();
        }
        User user = new User(name, surname, birthdate, credentials);
        this.users.put(user.getUserId(), user);
        logger.info("Register of user: "+user+" was done!");
    }

    @Override
    public List<User> getUsers() {
        logger.info("User list was requested, proceeding to obtain it.");
        List<User> users = new ArrayList<>(this.users.values());
        users.sort((User u1, User u2)->{
            int value = u1.getUserSurname().compareToIgnoreCase(u2.getUserSurname());
            if (value==0){
                value = u1.getUserName().compareToIgnoreCase(u2.getUserName());
            }
            return value;
        });
        logger.info("User list was correctly found! :)");
        return users;
    }

    @Override
    public User loginUser(Credentials credentials) throws UserCredentialsNotValidException {
        logger.info("Login of user with email ("+credentials.getEmail()+" was requested.");
        User user = getUserByCredentials(credentials);
        if(user==null){
            logger.warn("User credentials are not valid! :(");
            throw new UserCredentialsNotValidException();
        }
        logger.info("User login was correctly done with email ("+credentials.getEmail()+")! :)");
        return user;
    }

    @Override
    public void addObject(String objectName, String description, Double price) {
        logger.info("Proceeding to add object with information ("+objectName+", "+description+", "+price+")");
        objects.add(new ObjectShop(objectName, description, price));
        logger.info("Object was correctly added");
    }

    @Override
    public List<ObjectShop> objectsByPrice() {
        logger.info("Object list was requested.");
        this.objects.sort((ObjectShop o1, ObjectShop o2)->Double.compare(o2.getPrice(),o1.getPrice()));
        logger.info("Object list was correctly found :)");
        return this.objects;
    }

    @Override
    public void objectPurchase(String userId, String objectId) throws UserDoesNotExistException, ObjectDoesNotExistException, NotEnoughMoneyException {
        logger.info("Purchase of object with id: "+objectId+" was requested from user with id: "+userId+".");
        if(!this.users.containsKey(userId)){
            logger.warn("User with id: "+userId+" does not exist");
            throw new UserDoesNotExistException();
        }
        ObjectShop object;
        if((object = getObject(objectId)) == null) {
            logger.warn("Object with id: "+objectId+" does not exist");
            throw new ObjectDoesNotExistException();
        }
        this.users.get(userId).purchaseObject(object);
        logger.info("Purchase was correctly effectuated");
    }

    @Override
    public List<ObjectShop> objectsByUser(String userId) throws UserDoesNotExistException {
        logger.info("Request of purchased objects form user with id: "+userId+".");
        User user = this.users.get(userId);
        if (user == null){
            logger.warn("User with id: "+userId+" does not exist");
            throw new UserDoesNotExistException();
        }
        logger.info("Request was correctly effectuated");
        return user.getBoughtObjects();
    }

    @Override
    public int numUsers() {
        return users.size();
    }

    @Override
    public int numObjects() {
        return objects.size();
    }

    public Boolean userExistsByCredentials(Credentials credentials) {
        for(User user : this.users.values()){
            if(user.hasEmail(credentials)){
                return true;
            }
        }
        return false;
    }

    public User getUserByCredentials(Credentials credentials) {
        return this.users.values().stream()
                .filter(x->x.getCredentials().isEqual(credentials))
                .findFirst()
                .orElse(null);
    }

    public ObjectShop getObject(String objectId) {
        return this.objects.stream()
                .filter(x->objectId.equals(x.getObjectId()))
                .findFirst()
                .orElse(null);
    }
}
