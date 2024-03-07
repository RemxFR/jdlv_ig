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

/**
 * Classe qui gère les requêtes Http relatives aux règles de l'utilisateur.
 */
public class ReglesHttpController {
    /**
     * Chemin pour les règles.
     */
    private final String REGLES = "regles/";
    /**
     * Endpoint pour sauver les règles.
     */
    private final String SAUVER = "sauver/";
    /**
     * Endpoint pour récupérer les règles.
     */
    private final String RECUPERER = "recuperer/";
    /**
     * Champ Sur-population en JSON.
     */
    private final String SURPOP = "{\"surPopulation\":\"";
    /**
     * Champ Sous-population en JSON.
     */
    private final String SOUSPOP = "\", \"sousPopulation\":\"";
    /**
     * Champ Reproduction en JSON.
     */
    private final String REPRO = "\", \"reproduction\":\"";
    /**
     * Champ Taille grille en JSON.
     */
    private final String TAILLE_GRILLE = "\", \"tailleGrille\":\"";
    /**
     * Champ Utilisateur en JSON.
     */
    private final String USER = "\", \"user\":\"";

    /**
     * Constructeur vide.
     */
    public ReglesHttpController() {
    }

    /**
     * Méthode pour sauver les règles en les envoyant au backend pour persistence via une requête sql.
     * @param reglesCustom
     * @param login
     * @return
     */
    public String sauverRegles(ReglesCustom reglesCustom, String login) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EHttpHeadersEtURI.LOCALHOST.getValeur() + this.REGLES + this.SAUVER + login))
                .POST(HttpRequest.BodyPublishers.ofString(
                        SURPOP + reglesCustom.getSurPopulation() + SOUSPOP + reglesCustom.getSousPopulation()
                                + REPRO + reglesCustom.getReproduction() + TAILLE_GRILLE + reglesCustom.getTailleGrille()
                                + USER + reglesCustom.getUser() + EHttpHeadersEtURI.END.getValeur()))
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

    /**
     * Méthode pour récupérer les règles enregistrées en bdd, depuis le backend via une requête sql.
     * @param userDto
     * @return
     */
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
