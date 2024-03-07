package org.ynov.jdlv_ig.service;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.ynov.jdlv_ig.utils.ConvertirMessageEnInstruction;
import org.ynov.jdlv_ig.websocket.SocketClient;

/**
 * Classe qui gère la partie tchat, l'envoie, la création et la récupération des messages.
 */
public abstract class TchatService {
    /**
     * Constante relative à l'id du spinner qui contient la taille de la grille.
     */
    private static final String TAILLE_GRILLE_SPINNER = "tailleGrilleSpin";
    /**
     * Constante relative à l'id du spinner qui contient la valeur de Reproduction.
     */
    private static final String REPRO_SPINNER = "reproSpinr";
    /**
     * Constante relative à l'id du spinner qui contient la valeur de la Sur-population.
     */
    private static final String SURPO_SPINNER = "surpopSpin";
    /**
     * Constante relative à l'id du spinner qui contient la valeur de la Sous-population.
     */
    private static final String SOUSPO_SPINNER = "souspopSpin";
    /**
     * Texte du bouton NON.
     */
    private static final String NON = "Non";
    /**
     * Texte du bouton OUI.
     */
    private static final String OUI = "Oui";
    /**
     * REGEX pour séparer les valeurs des règles reçues.
     */
    private static final String VIRGULE_REGEX = ",";

    /**
     *Méthode qui permet de créer les messages et de les envoyer via la socket vers la partie backend.
     * @param client
     * @param vBox
     * @param messageAEnvoyer
     */
    public static void creerEtEnvoyerMessage(SocketClient client, VBox vBox, String messageAEnvoyer) {
        HBox hBox = new HBox();
        ElementStyleService.styliserHboxAlignementEtPadding(hBox, Pos.CENTER_RIGHT);

        Text text = new Text(messageAEnvoyer);
        ElementStyleService.styliserCouleurPoliceText(text, Color.WHITE);
        TextFlow textFlow = new TextFlow(text);
        ElementStyleService.styliserTextFlow(textFlow, ETypeMessage.ENVOYER);
        hBox.getChildren().add(textFlow);
        vBox.getChildren().add(hBox);
        client.sendMessage(messageAEnvoyer);
    }

    /**
     * Méthode qui permet d'afficher les messages reçus et de gérer la création des boutons pour accepter les règles
     * proposées par les autres joueurs.
     * @param message
     * @param vBox
     */
    public static void afficherMessageRecu(String message, VBox vBox) {
        HBox hBox = new HBox();
        Button ouiBtn = new Button(OUI);

        ElementStyleService.styliserHboxAlignementEtPadding(hBox, Pos.CENTER_LEFT);
        String regles = ConvertirMessageEnInstruction.convertirMessageEnInstruction(message);
        TextFlow textFlow = convertirMessagePourAffichage(message, regles, ouiBtn);
        ElementStyleService.styliserTextFlow(textFlow, ETypeMessage.AFFICHER);
        hBox.getChildren().add(textFlow);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
                ouiBtn.addEventHandler(ActionEvent.ACTION, event -> {
                    String[] reglesValeurs = regles.split(VIRGULE_REGEX);
                    accepterEtAppliquerRegles(
                            ouiBtn,
                            vBox,
                            Integer.parseInt(reglesValeurs[0]),
                            Integer.parseInt(reglesValeurs[1]),
                            Integer.parseInt(reglesValeurs[2]),
                            Integer.parseInt(reglesValeurs[3]));
                });
            }
        });
    }

    /**
     * Méthode pour convertir le message reçu sous forme de byte en un message affichable par l'applicaiton dans un
     * TextField.
     * @param message
     * @param regles
     * @param ouiBtn
     * @return
     */
    private static TextFlow convertirMessagePourAffichage(String message, String regles, Button ouiBtn) {
        Text text = null;
        TextFlow textFlow = null;
        TextFlow textFlowWithoutBtn;
        TextFlow textFlowWithBtn;
        Button nonBtn = new Button(NON);
        if (regles != null) {
            String reglesProposees = ConvertirMessageEnInstruction.convertirReglesRowEnProposition(regles);
            text = new Text(reglesProposees);
            textFlowWithBtn = new TextFlow(text, ouiBtn, nonBtn);
            nonBtn.setOnAction(e -> supprimerBoutonDuCommentaire(textFlowWithBtn, ouiBtn, nonBtn));
            textFlow = textFlowWithBtn;
        } else {
            text = new Text(message);
            textFlowWithoutBtn = new TextFlow(text);
            textFlow = textFlowWithoutBtn;
        }
        return textFlow;
    }

    /**
     * Méthode qui permet d'accepter les règles proposées par un autre utilisateur et de les appliquer au paramètres du
     * joueur.
     * @param button
     * @param vBox
     * @param tailleGrille
     * @param repro
     * @param surpop
     * @param souspop
     */
    private static void accepterEtAppliquerRegles(Button button, VBox vBox, int tailleGrille, int repro, int surpop, int souspop) {
        AnchorPane anchorPane = (AnchorPane) vBox.getParent().getParent().getParent().getParent();

        for (Node child : anchorPane.getChildren()) {
            if (child instanceof Spinner<?>) {
                if (child.getId().equals(TAILLE_GRILLE_SPINNER)) {
                    ((Spinner<Integer>) child).getValueFactory().setValue(tailleGrille);
                }
                if (child.getId().equals(REPRO_SPINNER)) {
                    ((Spinner<Integer>) child).getValueFactory().setValue(repro);
                }
                if (child.getId().equals(SURPO_SPINNER)) {
                    ((Spinner<Integer>) child).getValueFactory().setValue(surpop);
                }
                if (child.getId().equals(SOUSPO_SPINNER)) {
                    ((Spinner<Integer>) child).getValueFactory().setValue(souspop);
                }
            }
        }
        TextFlow textFlow = (TextFlow) button.getParent();
        Button buttonNon = (Button) textFlow.getChildren().get(2);
        supprimerBoutonDuCommentaire((TextFlow) button.getParent(), button, buttonNon);
    }

    /**
     * Méthode qui permet de supprimer les bouton OUI et NON dans le TextField du tchat
     * après acceptation ou refus des règles d'un autre joueur.
     * @param textFlow
     * @param ouiBtn
     * @param nonBtn
     */
    public static void supprimerBoutonDuCommentaire(TextFlow textFlow, Button ouiBtn, Button nonBtn) {
        textFlow.getChildren().removeAll(ouiBtn, nonBtn);
        textFlow.isResizable();
        textFlow.autosize();
    }

}
