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

public abstract class TchatService {

    private static final String TAILLE_GRILLE_SPINNER = "tailleGrilleSpin";
    private static final String REPRO_SPINNER = "reproSpinr";
    private static final String SURPO_SPINNER = "surpopSpin";
    private static final String SOUSPO_SPINNER = "souspopSpin";

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

    public static void afficherMessageRecu(String message, VBox vBox) {
        HBox hBox = new HBox();
        Button ouiBtn = new Button("Oui");

        ElementStyleService.styliserHboxAlignementEtPadding(hBox, Pos.CENTER_LEFT);
        String regles = ConvertirMessageEnInstruction.convertirMessageEnInstruction(message);
        TextFlow textFlow = convertirMessagePourAffichage(message, regles, ouiBtn);
        ElementStyleService.styliserTextFlow(textFlow, ETypeMessage.AFFICHER);
        hBox.getChildren().add(textFlow);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
                ouiBtn.addEventHandler(ActionEvent.ACTION, event -> {
                    String[] reglesValeurs = regles.split(",");
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
        thread.start();
    }

    private static TextFlow convertirMessagePourAffichage(String message, String regles, Button ouiBtn) {
        Text text = null;
        TextFlow textFlow = null;
        TextFlow textFlowWithoutBtn;
        TextFlow textFlowWithBtn;
        Button nonBtn = new Button("Non");
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

    public static void supprimerBoutonDuCommentaire(TextFlow textFlow, Button ouiBtn, Button nonBtn) {
        textFlow.getChildren().removeAll(ouiBtn, nonBtn);
        textFlow.isResizable();
        textFlow.autosize();
    }

}
