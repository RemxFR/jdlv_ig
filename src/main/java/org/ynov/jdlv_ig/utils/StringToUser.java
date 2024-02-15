package org.ynov.jdlv_ig.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ynov.jdlv_ig.logique.ReglesCustom;
import org.ynov.jdlv_ig.logique.User;

import java.util.List;

public class StringToUser {

    private static final String MDP = "mdp";
    private static final String LOGIN = "login";
    private static final String ID = "id";
    private static final String REGLES_CUSTOM = "reglesCustoms";

    public static User convertirJsonObjectEnUser(JSONObject object) {
        if(!object.isEmpty()) {
            JSONArray reglesCustomList = object.getJSONArray(REGLES_CUSTOM);
            reglesCustomList.iterator().forEachRemaining(items -> {
                System.out.println(items);
            });
            return User.builder().login(object.getString(LOGIN)).mdp(object.getString(MDP)).build();
        }
        return null;
    }

}
