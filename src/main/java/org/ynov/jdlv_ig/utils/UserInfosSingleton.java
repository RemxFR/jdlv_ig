package org.ynov.jdlv_ig.utils;

import lombok.Getter;
import org.ynov.jdlv_ig.logique.User;

public final class UserInfosSingleton {

    private static UserInfosSingleton instance;
    @Getter
    private static User userAEnregistrer;


    private UserInfosSingleton(User user) {
        userAEnregistrer = user;
    }

    public static UserInfosSingleton getInstance(User user) {
        if (instance == null) {
            instance = new UserInfosSingleton(user);
        }
        return instance;
    }

    public static User getUser() {
        return userAEnregistrer;
    }
}
