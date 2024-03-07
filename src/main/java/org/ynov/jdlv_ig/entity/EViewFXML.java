package org.ynov.jdlv_ig.entity;

/**
 * Enum qui gère les noms des différentes vues de l'application.
 */
public enum EViewFXML {
    /**
     * Vue du jeu en lui même.
     */
    GAME_VIEW("game-view.fxml"),
    /**
     * Vue de la page inscription.
     */
    INSCRIPTION_VIEW("inscription-view.fxml"),
    /**
     * Vue de la page connexion.
     */
    CONNEXION_VIEW("connexion-view.fxml");
    /**
     * Paramètre du nom de la vue.
     */
    private String nomView;

    /**
     * Constructeur avec le nom de la vue.
     * @param nomView
     */
    EViewFXML(String nomView) {
        this.nomView = nomView;
    }

    /**
     * Getter du nom de la vue.
     * @return
     */
    public String getNomView() {
        return nomView;
    }
}
