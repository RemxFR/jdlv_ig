package org.ynov.jdlv_ig.utils;

import javafx.stage.Stage;
import lombok.Getter;
import org.ynov.jdlv_ig.entity.User;
import org.ynov.jdlv_ig.entity.UserDto;

/**
 * Classe créant un singleton avec les infos utilisateurs pour les garder au travers de l'application sans avoir
 * à appeler sans cesse la bdd.
 */
public final class UserInfosSingleton {
    /**
     * Instance de la classe.
     */
    private static UserInfosSingleton instance;
    /**
     * Utilisateur à enregistrer.
     */
    @Getter
    private static UserDto userAEnregistrer;
    /**
     * Vue depuis laquelle on enregistre.
     */
    private static Stage stage;

    /**
     * Constructeur qui initialise l'utilisateur.
     * @param userDto
     */
    private UserInfosSingleton(UserDto userDto) {
        userAEnregistrer = userDto;
    }

    /**
     * Récupération ou création de l'instance de cette classe.
     * @param userDto
     * @return
     */
    public static UserInfosSingleton getInstance(UserDto userDto) {
        if (instance == null) {
            instance = new UserInfosSingleton(userDto);
        }
        return instance;
    }

    /**
     * Setter de la vue.
     * @param stageActual
     */
    public static void setStage(Stage stageActual) {
        stage = stageActual;
    }

    /**
     * Getter de la vue
     * @return
     */
    public static Stage getStage() {return stage;}

    /**
     * Getter de l'utilisateurDto.
     * @return
     */
    public static UserDto getUser() {
        return userAEnregistrer;
    }
}
