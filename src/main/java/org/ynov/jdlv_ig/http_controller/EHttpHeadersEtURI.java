package org.ynov.jdlv_ig.http_controller;

/**
 * Enum qui définie les différents paramètres et url des requêtes Http.
 */
public enum EHttpHeadersEtURI {
    /**
     * Content-Type
     */
    CONTENT_TYPE("Content-Type"),
    /**
     * application/json
     */
    APPLICATION_JSON("application/json"),
    /**
     * User-Agent
     */
    USER_AGENT("User-Agent"),
    /**
     * Mozilla/5.0
     */
    MOZILLA_5_0("Mozilla/5.0"),
    /**
     * Url du backend.
     */
    LOCALHOST("http://localhost:8081/"),
    GET("GET"),
    POST("POST"),
    /**
     * JSON pour le login.
     */
    LOGIN("{\"login\":" + "\""),
    /**
     * JSON pour le mdp.
     */
    MDP("\",\"mdp\":\""),
    /**
     * Fermeture du JSON.
     */
    END("\"}");

    /**
     * Valeur du champ.
     */
    private String valeur;

    /**
     * Constructeur de l'enum avec la valeur.
     * @param valeur
     */
    EHttpHeadersEtURI(String valeur) {
        this.valeur = valeur;
    }

    /**
     * Getter de la valeur du champ.
     * @return
     */
    public String getValeur() {
        return valeur;
    }
}
