package edu.upc.dsa.minim.Infrastructure;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.*;
import edu.upc.dsa.minim.Domain.Entity.ObjectShop;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Entity.VO.Credentials;
import edu.upc.dsa.minim.Domain.Entity.VO.EmailAddress;
import edu.upc.dsa.minim.Domain.Entity.VO.RandomId;
import edu.upc.dsa.minim.Domain.ShopManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ShopManagerImplTest {
    ShopManager shopManager;

    @Before
    public void setUp() throws EmailAddressNotValidException, UserAlreadyExistsException {
        this.shopManager = new ShopManagerImpl();

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

    @After
    public void tearDown() {
        this.shopManager = null;
    }

    @Test
    public void test_register_user_is_possible() throws EmailAddressNotValidException, UserAlreadyExistsException {
        Assert.assertEquals(3, this.shopManager.numUsers());
        Assert.assertEquals(5, this.shopManager.numObjects());

        Credentials credentials = new Credentials(new EmailAddress("blancaromagomez@gmail.com"), "Test123");
        this.shopManager.registerUser("Blanca", "Roma Gómez", "23/11/2000", credentials);

        Assert.assertEquals(4, this.shopManager.numUsers());
    }

    @Test
    public void test_credentials_with_invalid_email_throw_exception() {
        Assert.assertThrows(EmailAddressNotValidException.class, ()->new Credentials(new EmailAddress("blancaromagomezgmail.com"), "Test123"));
    }

    @Test
    public void test_register_user_that_already_exists_throws_exception() throws EmailAddressNotValidException {
        Credentials credentials = new Credentials(new EmailAddress("albaromagomez@gmail.com"), "Test123");

        Assert.assertThrows(UserAlreadyExistsException.class, ()->this.shopManager.registerUser("Blanca", "Roma Gómez", "23/11/2000", credentials));
    }

    @Test
    public void test_get_users(){
        List<User> users = this.shopManager.getUsers();
        Assert.assertEquals(3, users.size());
        Assert.assertEquals(User.class, users.get(0).getClass());

        Assert.assertEquals("Plans Ponsa", users.get(0).getUserSurname());
        Assert.assertEquals("Alba", users.get(1).getUserName());
        Assert.assertEquals("Susana", users.get(2).getUserName());
    }

    @Test
    public void test_login_with_invalid_credentials_throws_exception() throws EmailAddressNotValidException, UserCredentialsNotValidException {
        Credentials credentials = new Credentials(new EmailAddress("albaromagomez@gmail.com"), "Test");

        Assert.assertThrows(UserCredentialsNotValidException.class,()->this.shopManager.loginUser(credentials));
    }

    @Test
    public void test_add_object() {
        Assert.assertEquals(3, this.shopManager.numUsers());
        Assert.assertEquals(5, this.shopManager.numObjects());

        this.shopManager.addObject("Patates", "pommes de terre en frances", 2.0);

        Assert.assertEquals(6, this.shopManager.numObjects());
    }

    @Test
    public void test_objects_ordered_by_price() {
        List<ObjectShop> objects = this.shopManager.objectsByPrice();

        Assert.assertEquals((Double) 11.1, objects.get(0).getPrice());
        Assert.assertEquals((Double) 1.2, objects.get(4).getPrice());
    }

    @Test
    public void test_purchase_of_user_is_possible() throws NotEnoughMoneyException, ObjectDoesNotExistException, UserDoesNotExistException {
        List<User> users = this.shopManager.getUsers();
        List<ObjectShop> objects = this.shopManager.objectsByPrice();
        User user = users.get(0);
        ObjectShop object = objects.get(0);

        Assert.assertEquals(0, user.getBoughtObjects().size());

        this.shopManager.objectPurchase(user.getUserId(), object.getObjectId());
        User userUpdated = this.shopManager.userInfo(user.getUserId());
        Assert.assertEquals(1, userUpdated.getBoughtObjects().size());
        Assert.assertEquals(object, userUpdated.getBoughtObjects().get(0));
        Assert.assertEquals((Double) 38.9, userUpdated.getMoney());
    }

    @Test
    public void test_purchase_of_nonexisting_user_throws_exception() {
        List<ObjectShop> objects = this.shopManager.objectsByPrice();
        String objectId = objects.get(0).getObjectId();

        Assert.assertThrows(UserDoesNotExistException.class, ()-> this.shopManager.objectPurchase(RandomId.getId(), objectId));
    }

    @Test
    public void test_purchase_of_nonexisting_object_throws_exception() {
        List<User> users = this.shopManager.getUsers();
        String userId = users.get(0).getUserId();

        Assert.assertThrows(ObjectDoesNotExistException.class, ()-> this.shopManager.objectPurchase(userId, RandomId.getId()));
    }

    @Test
    public void test_purchase_with_not_enough_money_throws_exception() throws NotEnoughMoneyException, ObjectDoesNotExistException, UserDoesNotExistException {
        List<User> users = this.shopManager.getUsers();
        List<ObjectShop> objects = this.shopManager.objectsByPrice();
        User user = users.get(0);
        ObjectShop object = objects.get(0);

        this.shopManager.objectPurchase(user.getUserId(), object.getObjectId());
        this.shopManager.objectPurchase(user.getUserId(), object.getObjectId());
        this.shopManager.objectPurchase(user.getUserId(), object.getObjectId());
        this.shopManager.objectPurchase(user.getUserId(), object.getObjectId());

        Assert.assertThrows(NotEnoughMoneyException.class, ()-> this.shopManager.objectPurchase(user.getUserId(), object.getObjectId()));
    }

    @Test
    public void test_objects_by_user() throws NotEnoughMoneyException, ObjectDoesNotExistException, UserDoesNotExistException {
        List<User> users = this.shopManager.getUsers();
        List<ObjectShop> objects = this.shopManager.objectsByPrice();
        User user = users.get(0);
        ObjectShop object = objects.get(0);

        Assert.assertEquals(0, user.getBoughtObjects().size());

        this.shopManager.objectPurchase(user.getUserId(), object.getObjectId());
        List<ObjectShop> boughtObjects = this.shopManager.objectsByUser(user.getUserId());

        Assert.assertEquals(1, boughtObjects.size());
        Assert.assertEquals(object.getObjectId(), boughtObjects.get(0).getObjectId());
    }
}
