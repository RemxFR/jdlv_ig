package org.ynov.jdlv_ig.service;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Classe qui permet de gérer le style de différents éléments des vues.
 */
public abstract class ElementStyleService {
    /**
     * Couleur de la police en bleu clair.
     */
    private static final String FONT_COLOR = "-fx-color: rgb(239,242,255); ";
    /**
     * Couleur de background gris.
     */
    private static final String ENVOYER_BACKGROUND_COLOR = "-fx-background-color:rgb(233,233,235); ";
    /**
     * Couleur de background bleu.
     */
    private static final String AFFICHER_BACKGROUND_COLOR = "-fx-background-color:rgb(15,125,242); ";
    /**
     * Border Radius de 20px.
     */
    private static final String BACKGROUND_RADIUS = "-fx-background-radius: 20px;";
    /**
     * Padding de 5.
     */
    private static final int INSET_5 = 5;
    /**
     * Padding de 10.
     */
    private static final int INSET_10 = 10;

    /**
     * Méthode pour styliser les TextFlows, qui contiennent les messages envoyés et reçus.
     * @param textFlow
     * @param eTypeMessage
     */
    public static void styliserTextFlow(TextFlow textFlow, ETypeMessage eTypeMessage) {
        if (eTypeMessage.equals(ETypeMessage.AFFICHER)) {
        textFlow.setStyle(FONT_COLOR + ENVOYER_BACKGROUND_COLOR + BACKGROUND_RADIUS);
        }
        if (eTypeMessage.equals(ETypeMessage.ENVOYER)) {
            textFlow.setStyle(FONT_COLOR + AFFICHER_BACKGROUND_COLOR + BACKGROUND_RADIUS);
        }
        textFlow.setPadding(new Insets(INSET_5, INSET_10, INSET_5, INSET_10));
    }

    /**
     * Méthode pour styliser l'alignement et le padding des messages.
     * @param hBox
     * @param centerLeft
     */
    public static void styliserHboxAlignementEtPadding(HBox hBox, Pos centerLeft) {
        hBox.setAlignment(centerLeft);
        hBox.setPadding(new Insets(INSET_5, INSET_5, INSET_5, INSET_10));
    }

    /**
     * Méthode pour styliser la couleur de la police.
     * @param text
     * @param color
     */
    public static void styliserCouleurPoliceText(Text text, Color color) {
        text.setFill(color);
    }
}
