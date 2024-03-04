package org.ynov.jdlv_ig.http_controller;

public enum EHttpHeadersEtURI {

    CONTENT_TYPE("Content-Type"),
    APPLICATION_JSON("application/json"),
    USER_AGENT("User-Agent"),
    MOZILLA_5_0("Mozilla/5.0"),
    LOCALHOST("http://localhost:8081/");

    private String valeur;

    EHttpHeadersEtURI(String valeur) {
        this.valeur = valeur;
    }

    public String getValeur() {
        return valeur;
    }
}
