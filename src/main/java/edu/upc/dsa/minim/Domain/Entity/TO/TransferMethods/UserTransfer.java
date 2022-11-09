package edu.upc.dsa.minim.Domain.Entity.TO.TransferMethods;

import edu.upc.dsa.minim.Domain.Entity.TO.UserInfo;
import edu.upc.dsa.minim.Domain.Entity.User;

import java.util.*;

public class UserTransfer {
    public static List<UserInfo> userInfoList(List<User> users){
        List<UserInfo> userInfoList = new ArrayList<>();
        for(User user : users){
            userInfoList.add(new UserInfo(user.getUserId(), user.getUserName(), user.getUserSurname(), user.getBirthDate(), user.getMoney()));
        }
        return userInfoList;
    }
}
