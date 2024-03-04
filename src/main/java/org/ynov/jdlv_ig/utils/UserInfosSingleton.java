package org.ynov.jdlv_ig.utils;

import javafx.stage.Stage;
import lombok.Getter;
import org.ynov.jdlv_ig.entity.User;
import org.ynov.jdlv_ig.entity.UserDto;

public final class UserInfosSingleton {

    private static UserInfosSingleton instance;
    @Getter
    private static UserDto userAEnregistrer;
    private static Stage stage;


    private UserInfosSingleton(UserDto userDto) {
        userAEnregistrer = userDto;
    }

    public static UserInfosSingleton getInstance(UserDto userDto) {
        if (instance == null) {
            instance = new UserInfosSingleton(userDto);
        }
        return instance;
    }
    public static void setStage(Stage stageActual) {
        stage = stageActual;
    }
    public static UserDto getUser() {
        return userAEnregistrer;
    }
    public static Stage getStage() {return stage;}
}
