package org.ynov.jdlv_ig.http_controller;

import org.ynov.jdlv_ig.entity.ReglesCustom;
import org.ynov.jdlv_ig.entity.User;
import org.ynov.jdlv_ig.entity.UserDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ReglesHttpController {

    private final String REGLES = "regles/";
    private final String SAUVER = "sauver/";
    private final String RECUPERER = "recuperer/";

    public ReglesHttpController() {
    }

    public String sauverRegles(ReglesCustom reglesCustom, String login) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EHttpHeadersEtURI.LOCALHOST.getValeur() + this.REGLES + this.SAUVER + login))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"surPopulation\":" + "\"" + reglesCustom.getSurPopulation() + "\""
                                + ", \"sousPopulation\":" + "\"" + reglesCustom.getSousPopulation() + "\""
                                + ", \"reproduction\":" + "\"" + reglesCustom.getReproduction() + "\""
                                + ", \"tailleGrille\":" + "\"" + reglesCustom.getTailleGrille() + "\""
                                + ", \"user\":" + "\"" + reglesCustom.getUser() + "\"" + "}"))
                .header(EHttpHeadersEtURI.CONTENT_TYPE.getValeur(), EHttpHeadersEtURI.APPLICATION_JSON.getValeur())
                .header(EHttpHeadersEtURI.USER_AGENT.getValeur(), EHttpHeadersEtURI.MOZILLA_5_0.getValeur())
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String respBody = response.body();
            return respBody;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ReglesCustom> recupererReglesCustom(UserDto userDto) {
        List<ReglesCustom> reglesCustoms = null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EHttpHeadersEtURI.LOCALHOST.getValeur() + this.REGLES + this.RECUPERER + userDto.getLogin()))
                .GET()
                .header(EHttpHeadersEtURI.CONTENT_TYPE.getValeur(), EHttpHeadersEtURI.APPLICATION_JSON.getValeur())
                .header(EHttpHeadersEtURI.USER_AGENT.getValeur(), EHttpHeadersEtURI.MOZILLA_5_0.getValeur())
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String respBody = response.body();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return reglesCustoms;

    }
}
