package org.ynov.jdlv_ig.entity;

public enum EViewFXML {
    GAME_VIEW("game-view.fxml"),
    INSCRIPTION_VIEW("inscription-view.fxml"),
    CONNEXION_VIEW("connexion-view.fxml");


    private String nomView;

    EViewFXML(String nomView) {
        this.nomView = nomView;
    }

    public String getNomView() {
        return nomView;
    }
}
