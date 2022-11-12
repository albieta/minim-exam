package edu.upc.dsa.minim.Domain.Entity.TO.TransferMethod;

import edu.upc.dsa.minim.Domain.Entity.TO.UserInfo;
import edu.upc.dsa.minim.Domain.Entity.User;

import java.util.*;

public class UserToUserInfo {
    public static List<UserInfo> userInfoList(List<User> users, String gameId) {
        List<UserInfo> userInfoList = new ArrayList<>();
        for(User user : users) {
            userInfoList.add(new UserInfo(user, gameId));
        }
        return userInfoList;
    }
}
