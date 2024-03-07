package org.ynov.jdlv_ig.http_controller;


import org.json.JSONObject;
import org.ynov.jdlv_ig.entity.User;
import org.ynov.jdlv_ig.entity.UserDto;
import org.ynov.jdlv_ig.utils.StringToUser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe qui permet de gérer les requêtes Http.
 */
public class AuthentificationHttpController {
    /**
     * Constante relative à l'url pour les requêtes de connexion.
     */
    private final String CONNEXION = "connexion/";
    /**
     * Constante relative à l'url pour les requêtes d'inscription.
     */
    private final String INSCRIPTION = "inscription";
    /**
     * Constante relative à l'url pour les requêtes d'authentification'.
     */
    private final String AUTH = "auth";
    /**
     * Constructeur de la classe.
     */
    public AuthentificationHttpController() {
    }

    /**
     * Méthode pour procéder à l'inscription d'un utilisateur via requête Http vers le backend.
     * @param userDto
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean inscireUser(UserDto userDto) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EHttpHeadersEtURI.LOCALHOST.getValeur() + CONNEXION + INSCRIPTION))
                .POST(HttpRequest.BodyPublishers.ofString( EHttpHeadersEtURI.LOGIN.getValeur()+ userDto.getLogin()
                        + EHttpHeadersEtURI.MDP.getValeur()  + userDto.getMdp() + EHttpHeadersEtURI.END.getValeur()))
                .header(EHttpHeadersEtURI.CONTENT_TYPE.getValeur(), EHttpHeadersEtURI.APPLICATION_JSON.getValeur())
                .header(EHttpHeadersEtURI.USER_AGENT.getValeur(), EHttpHeadersEtURI.MOZILLA_5_0.getValeur())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String respBody = response.body();
        return response.statusCode() == 200;
    }

    /**
     * Méthode de connexion à l'application via une requête Http vers le backend pour vérifier le login et le mot
     * de passe utilisateur.
     * @param user
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Map<UserDto, Integer> seConnecter(User user) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EHttpHeadersEtURI.LOCALHOST.getValeur() + CONNEXION + AUTH))
                .POST(HttpRequest.BodyPublishers.ofString(EHttpHeadersEtURI.LOGIN.getValeur() + user.getLogin()
                        + EHttpHeadersEtURI.MDP.getValeur() + user.getMdp() + EHttpHeadersEtURI.END.getValeur()))
                .header(EHttpHeadersEtURI.CONTENT_TYPE.getValeur(), EHttpHeadersEtURI.APPLICATION_JSON.getValeur())
                .header(EHttpHeadersEtURI.USER_AGENT.getValeur(), EHttpHeadersEtURI.MOZILLA_5_0.getValeur())
                .build();
        try {
            return getUserDtoMap(client, request);
        } catch (EOFException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Méthode pour transformer le body json reçu en UtilisateurDto, afin d'y avoir accès dans notre application.
     * @param client
     * @param request
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private static Map<UserDto, Integer> getUserDtoMap(HttpClient client, HttpRequest request)
            throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String respBody = response.body();
        Map<UserDto, Integer> responseMap = new HashMap<>();
        if (respBody != null || !respBody.equals("")) {
            JSONObject userJson = new JSONObject(respBody);
            UserDto userConnected = StringToUser.convertirJsonObjectEnUser(userJson);
            responseMap.put(userConnected, response.statusCode());
            return responseMap;
        }
        responseMap.put(null, null);
        return responseMap;
    }
}
