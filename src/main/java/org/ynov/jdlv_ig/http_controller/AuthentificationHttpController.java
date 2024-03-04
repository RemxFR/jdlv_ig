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

public class AuthentificationHttpController {

    private final String CONNEXION = "connexion/";
    private final String INSCRIPTION = "inscription";
    private final String AUTH = "auth";
    public AuthentificationHttpController() {
    }

    public void connect(String url) throws IOException {
        URL url1 = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        urlConnection.disconnect();
    }

    public boolean inscireUser(UserDto userDto) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EHttpHeadersEtURI.LOCALHOST.getValeur() + CONNEXION + INSCRIPTION))
                .POST(HttpRequest.BodyPublishers.ofString("{\"login\":" + "\"" + userDto.getLogin() + "\"" + ",\"mdp\":" + "\"" + userDto.getMdp() + "\"" + "}"))
                .header(EHttpHeadersEtURI.CONTENT_TYPE.getValeur(), EHttpHeadersEtURI.APPLICATION_JSON.getValeur())
                .header(EHttpHeadersEtURI.USER_AGENT.getValeur(), EHttpHeadersEtURI.MOZILLA_5_0.getValeur())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String respBody = response.body();
        return response.statusCode() == 200;
    }

    public Map<UserDto, Integer> seConnecter(User user) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EHttpHeadersEtURI.LOCALHOST.getValeur() + CONNEXION + AUTH))
                .POST(HttpRequest.BodyPublishers.ofString("{\"login\":" + "\"" + user.getLogin() + "\"" + ",\"mdp\":" + "\"" + user.getMdp() + "\"" + "}"))
                .header("Content-Type", "application/json")
                .header("User-Agent", "Mozilla/5.0")
                .build();

        try {
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
        } catch (EOFException e) {
            e.printStackTrace();
        }
        return null;
    }
}
