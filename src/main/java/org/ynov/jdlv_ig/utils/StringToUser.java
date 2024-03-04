package org.ynov.jdlv_ig.utils;

import org.json.JSONObject;
import org.ynov.jdlv_ig.entity.UserDto;

public class StringToUser {

    private static final String MDP = "mdp";
    private static final String LOGIN = "login";
    private static final String ID = "id";
    private static final String REGLES_CUSTOM = "reglesCustoms";

    public static UserDto convertirJsonObjectEnUser(JSONObject object) {
        if (!object.isEmpty()) {
            return UserDto.builder().login(object.getString(LOGIN)).mdp(object.getString(MDP)).build();
        }
        return null;
    }

}
