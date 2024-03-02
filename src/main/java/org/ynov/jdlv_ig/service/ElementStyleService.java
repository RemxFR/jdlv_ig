package org.ynov.jdlv_ig.service;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public abstract class ElementStyleService {

    private static final String FONT_COLOR = "-fx-color: rgb(239,242, 255); ";
    private static final String ENVOYER_BACKGROUND_COLOR = "-fx-background-color:rgb(233,233, 235); ";
    private static final String AFFICHER_BACKGROUND_COLOR = "-fx-background-color:rgb(15,125,242); ";
    private static final String BACKGROUND_RADIUS = "-fx-background-radius: 20px;";
    private static final int INSET_5 = 5;
    private static final int INSET_10 = 10;

    public static void styliserTextFlow(TextFlow textFlow, ETypeMessage eTypeMessage) {
        if (eTypeMessage.equals(ETypeMessage.AFFICHER)) {
        textFlow.setStyle(FONT_COLOR + ENVOYER_BACKGROUND_COLOR + BACKGROUND_RADIUS);
        }
        if (eTypeMessage.equals(ETypeMessage.ENVOYER)) {
            textFlow.setStyle(FONT_COLOR + AFFICHER_BACKGROUND_COLOR + BACKGROUND_RADIUS);
        }
        textFlow.setPadding(new Insets(INSET_5, INSET_10, INSET_5, INSET_10));
    }

    public static void styliserHboxAlignementEtPadding(HBox hBox, Pos centerLeft) {
        hBox.setAlignment(centerLeft);
        hBox.setPadding(new Insets(INSET_5, INSET_5, INSET_5, INSET_10));
    }

    public static void styliserCouleurPoliceText(Text text, Color color) {
        text.setFill(color);
    }
}
