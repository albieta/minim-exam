package edu.upc.dsa.minim.Infrastructure;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.*;
import edu.upc.dsa.minim.Domain.Entity.ObjectShop;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Entity.VO.Credentials;
import edu.upc.dsa.minim.Domain.Entity.VO.Tristate;
import edu.upc.dsa.minim.Domain.Repository.ShopManager;
import org.apache.log4j.Logger;

import java.util.*;

public class ShopManagerImpl implements ShopManager {
    private static ShopManager instance;

    List<ObjectShop> objects;

    Map<String, User> users;

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
    public void registerUser(String name, String surname, String birthdate, Credentials credentials) throws UserAlreadyExistsException {
        if(!userExistsByCredentials(credentials).isNeither()){
            throw new UserAlreadyExistsException();
        }
        User user = new User(name, surname, birthdate, credentials);
        this.users.put(user.getUserId(), user);
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>(this.users.values());
        users.sort((User u1, User u2)->{
            int value = u1.getUserSurname().compareToIgnoreCase(u2.getUserSurname());
            if (value==0){
                value = u1.getUserName().compareToIgnoreCase(u2.getUserName());
            }
            return value;
        });
        return users;
    }

    @Override
    public void loginUser(Credentials credentials) throws UserCredentialsNotValidException {
        if(!userExistsByCredentials(credentials).isTrue()){
            throw new UserCredentialsNotValidException();
        }
    }

    @Override
    public void addObject(String objectName, String description, Double price) {
        objects.add(new ObjectShop(objectName, description, price));
    }

    @Override
    public List<ObjectShop> objectsByPrice() {
        this.objects.sort((ObjectShop o1, ObjectShop o2)->Double.compare(o2.getPrice(),o1.getPrice()));
        return this.objects;
    }

    @Override
    public void objectPurchase(String userId, String objectId) throws UserDoesNotExistException, ObjectDoesNotExistException, NotEnoughMoneyException {
        if(!this.users.containsKey(userId)){
            throw new UserDoesNotExistException();
        }
        ObjectShop object;
        if((object = getObject(objectId)) == null) {
            throw new ObjectDoesNotExistException();
        }
        this.users.get(userId).purchaseObject(object);
    }

    @Override
    public List<ObjectShop> objectsByUser(String userId) {
        return this.users.get(userId).getBoughtObjects();
    }

    @Override
    public int numUsers() {
        return users.size();
    }

    @Override
    public int numObjects() {
        return objects.size();
    }

    public Tristate userExistsByCredentials(Credentials credentials) {
        for(User user : this.users.values()){
            if(user.hasEmail(credentials)){
                return Tristate.fromBoolean(user.hasCredentials(credentials));
            }
        }
        return Tristate.NEITHER;
    }

    public ObjectShop getObject(String objectId) {
        for (ObjectShop element : this.objects) {
            if(Objects.equals(element.getObjectId(), objectId)) {
                return element;
            }
        }
        return null;
    }
}
