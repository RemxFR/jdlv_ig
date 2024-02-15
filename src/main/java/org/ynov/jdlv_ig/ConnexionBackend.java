package org.ynov.jdlv_ig;


import org.json.JSONObject;
import org.ynov.jdlv_ig.logique.User;
import org.ynov.jdlv_ig.utils.StringToUser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.*;
import java.util.HashMap;
import java.util.Map;

public class ConnexionBackend {

    public ConnexionBackend() {
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
        System.out.println("responseCode: " + urlConnection.getResponseCode());
        System.out.println("responseMessage: " + content);
    }

    public boolean inscireUser(User user) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/connexion/inscription"))
                .POST(HttpRequest.BodyPublishers.ofString("{\"login\":" + "\"" + user.getLogin() + "\"" +",\"mdp\":" + "\"" + user.getMdp() + "\"" + "}"))
                .header("Content-Type", "application/json")
                .header("User-Agent", "Mozilla/5.0")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String respBody = response.body();
        System.out.println("response après post: " + respBody);
        return response.statusCode() == 200;
    }

    public Map<User, Integer> seConnecter(User user) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/connexion/auth"))
                .POST(HttpRequest.BodyPublishers.ofString("{\"login\":" + "\"" + user.getLogin() + "\"" +",\"mdp\":" + "\"" + user.getMdp() + "\"" + "}"))
                .header("Content-Type", "application/json")
                .header("User-Agent", "Mozilla/5.0")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String respBody = response.body();
        Map<User, Integer> responseMap = new HashMap<>();
        if(respBody != null || !respBody.equals("")) {
            System.out.println(respBody);
            JSONObject userJson = new JSONObject(respBody);
            User userConnected = StringToUser.convertirJsonObjectEnUser(userJson);
            responseMap.put(userConnected, response.statusCode());
            return responseMap;
        }
        responseMap.put(null, null);
        return responseMap;
    }
}
