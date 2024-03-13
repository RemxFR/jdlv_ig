package org.ynov.jdlv_ig.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ynov.jdlv_ig.entity.ReglesCustomDto;
import org.ynov.jdlv_ig.entity.UserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour convertir un objet json en utilisateur.
 */
public class JsonConverter {
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
    private static final String REGLES_CUSTOM = "reglesCustomList";

    /**
     * Méthode qui permet de convertir un JSONObject en UserDto.
     * @param object
     * @return
     */
    public static UserDto convertirJsonObjectEnUser(JSONObject object) {
        if (!object.isEmpty()) {

            JSONArray jsonArray = object.getJSONArray(REGLES_CUSTOM);
            List<ReglesCustomDto> reglesCustomDtoList = mapReglesCustomDtos(jsonArray);
            return UserDto.builder().login(object.getString(LOGIN)).reglesCustomList(reglesCustomDtoList).build();
        }
        return null;
    }

    /**
     * Méthode qui permet de convertir un JSONArray des règles customisées en liste de règles customisées DTOs.
     * @param jsonArray
     * @return
     */
    public static List<ReglesCustomDto> mapReglesCustomDtos(JSONArray jsonArray) {
        List<ReglesCustomDto> reglesCustomDtoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ReglesCustomDto reglesCustomDto = ReglesCustomDto.builder()
                    .reproduction(jsonObject.getInt("reproduction"))
                    .sousPopulation(jsonObject.getInt("sousPopulation"))
                    .surPopulation(jsonObject.getInt("surPopulation"))
                    .tailleGrille(jsonObject.getInt("tailleGrille"))
                    .build();
            reglesCustomDtoList.add(reglesCustomDto);
        }
        return reglesCustomDtoList;
    }
}
