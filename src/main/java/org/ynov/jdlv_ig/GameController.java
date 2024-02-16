package org.ynov.jdlv_ig;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.ynov.jdlv_ig.logique.Cellule;
import org.ynov.jdlv_ig.logique.Matrice;
import org.ynov.jdlv_ig.logique.User;
import org.ynov.jdlv_ig.utils.ConvertirMessageEnInstruction;
import org.ynov.jdlv_ig.utils.UserInfosSingleton;
import org.ynov.jdlv_ig.websocket.SocketClient;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private SpinnerValueFactory.IntegerSpinnerValueFactory grilleSpinValeur;
    Matrice matrice;
    public static Circle[][] circles;
    public final int tempo = 100;
    public Timeline chronologie;
    private int espace = 20;
    private int taille;
    private double nb;
    @Getter
    @Setter
    private String userName;
    private Timeline petitCycle;

    @FXML
    private Group group;

    @FXML
    private TextField tchatTextField;

    @FXML
    private VBox tchatVBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button propReglesBtn;

    @FXML
    private Spinner<Integer> sousPoSpin;
    @FXML
    Spinner<Integer> surPoSpin;
    @FXML
    Spinner<Integer> reproSpin;
    @FXML
    Spinner<Integer> tailleGrilleSpin = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100));
    private CheckBox tdgCB, tdgSrP, tdgRepro, tdgSsP;
    @Getter
    private static int tgs;
    private SocketClient client;
    private final String TAILLE_GRILLE_SPINNER = "tailleGrilleSpin";
    private final String REPRO_SPINNER = "reproSpinr";
    private final String SURPO_SPINNER = "surpopSpin";
    private final String SOUSPO_SPINNER = "souspopSpin";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Socket socket = new Socket("localhost", 1234);
            User user = UserInfosSingleton.getUser();
            this.setUserName(user.getLogin());
            this.client = new SocketClient(socket, this.getUserName());
            this.propReglesBtn.setText("Proposer\nRègles");
            this.propReglesBtn.setTextAlignment(TextAlignment.CENTER);
            taille = 100;
            nb = 0.1;
            chronologie = new Timeline();
            int largeur = 500;
            espace = largeur / taille;
            this.tailleGrilleSpin.getValueFactory().setValue(taille);
            this.sousPoSpin.getValueFactory().setValue(Cellule.sousPopulation);
            this.surPoSpin.getValueFactory().setValue(Cellule.surPopulation);
            this.reproSpin.getValueFactory().setValue(Cellule.minPopulationRegeneratrice);
        } catch (IOException e) {
            e.printStackTrace();
        }


        tchatVBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });
        this.client.listenForMessage(tchatVBox);
    }

    @FXML
    protected void lancerjdlv() {
        taille = tailleGrilleSpin.getValueFactory().getValue();
        this.grilleSpinValeur.setValue(taille);
        System.out.println("taille: " + this.grilleSpinValeur.getValue() + "...");
        Cellule.sousPopulation = this.sousPoSpin.getValueFactory().getValue();
        Cellule.surPopulation = this.surPoSpin.getValueFactory().getValue();
        Cellule.minPopulationRegeneratrice = this.reproSpin.getValueFactory().getValue();
        matrice = new Matrice(taille, nb);
        GameController.circles = new Circle[taille][taille];
        group.setStyle("-fx-background-color: black;");
        dessinMatrice(group);
        petitCycle = new Timeline(new KeyFrame(Duration.millis(tempo), actionEvent -> {
            matrice.copierGrille();
            matrice.animGrille();
        }));
        petitCycle.setCycleCount(Timeline.INDEFINITE);
        petitCycle.play();
    }

    @FXML
    protected void arreterJdlv() {
        this.petitCycle.stop();
        this.group.getChildren().clear();
    }


    @FXML
    protected void ecrireTchat(KeyEvent event) {
        if (!tchatTextField.getText().isEmpty() || !tchatTextField.getText().isBlank()) {
            tchatTextField.setOnKeyPressed(event1 -> {
                if (event1.getCode() == KeyCode.ENTER) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(tchatTextField.getText());
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-color: rgb(239,242, 255);" +
                            "-fx-background-color:rgb(15,125,242);" +
                            "-fx-background-radius: 20px");
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.WHITE);
                    hBox.getChildren().add(textFlow);
                    tchatVBox.getChildren().add(hBox);
                    this.client.sendMessage(tchatTextField.getText());
                    tchatTextField.clear();
                }
            });
        }
    }


    private void dessinMatrice(Group root) {
        Cellule[][] grille = matrice.getGrille();
        int rayon = espace / 2;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Cellule cell = grille[i][j];
                circles[i][j] = new Circle((j * espace + rayon), (i * espace + rayon), rayon);
                if (cell.isEstVivante()) {
                    circles[i][j].setFill(Cellule.coulActive);
                } else {
                    circles[i][j].setFill(Cellule.coulDesactive);
                }
                cell.setCircle(circles[i][j]);
                root.getChildren().add(circles[i][j]);
                root.setStyle("-fx-background-color: black;");
            }
        }
    }

    public void afficherMessageRecu(String message, VBox vBox) {
        Text text = null;
        TextFlow textFlow = null;
        TextFlow textFlowWithoutBtn;
        TextFlow textFlowWithBtn;
        String[] reglesValues = null;
        Button ouiBtn = new Button("Oui");
        Button nonBtn = new Button("Non");

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        String regles = ConvertirMessageEnInstruction.convertirMessageEnInstruction(message);
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

        textFlow.setStyle("-fx-color: rgb(239,242, 255);" +
                "-fx-background-color:rgb(233,233, 235);" +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);


        Platform.runLater(new Runnable() {
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
    }

    @FXML
    private void proposerRegles() {
        int tailleGrille = this.tailleGrilleSpin.getValueFactory().getValue();
        int repro = this.reproSpin.getValueFactory().getValue();
        int surPop = this.surPoSpin.getValueFactory().getValue();
        int sousPop = this.sousPoSpin.getValueFactory().getValue();
        String reglesProposees = "->r:[%s,%s,%s,%s]";
        String reglesOk = String.format(reglesProposees, tailleGrille, repro, surPop, sousPop);
        System.out.println(reglesOk);
        this.client.sendMessage(reglesOk);
    }

    private void accepterEtAppliquerRegles(Button button, VBox vBox, int tailleGrille, int repro, int surpop, int souspop) {
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

    private void supprimerBoutonDuCommentaire(TextFlow textFlow, Button ouiBtn, Button nonBtn) {
        textFlow.getChildren().removeAll(ouiBtn, nonBtn);
        textFlow.isResizable();
        textFlow.autosize();
    }
}
