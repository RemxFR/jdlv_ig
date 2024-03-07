package org.ynov.jdlv_ig.utils;

import org.json.JSONObject;
import org.ynov.jdlv_ig.entity.UserDto;

/**
 * Classe pour convertir un objet json en utilisateur.
 */
public class StringToUser {
    /**
     * Constante pour le champ du mot de passe.
     */
    private static final String MDP = "mdp";
    /**
     * Constante pour le champ du login.
     */
    private static final String LOGIN = "login";
    /**
     * Constante pour le champ de l'id.
     */
    private static final String ID = "id";
    /**
     * Constante pour le champ des règles enregistrées de l'utilisateur.
     */
    private static final String REGLES_CUSTOM = "reglesCustoms";

    /**
     * Méthode qui permet de convertir un JSONObject en UserDto.
     * @param object
     * @return
     */
    public static UserDto convertirJsonObjectEnUser(JSONObject object) {
        if (!object.isEmpty()) {
            return UserDto.builder().login(object.getString(LOGIN)).mdp(object.getString(MDP)).build();
        }
        return null;
    }
}
