package org.ynov.jdlv_ig;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.ynov.jdlv_ig.logique.Cellule;
import org.ynov.jdlv_ig.logique.Matrice;
import org.ynov.jdlv_ig.logique.User;
import org.ynov.jdlv_ig.service.TchatService;
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
                    TchatService.creerEtEnvoyerMessage(this.client, this.tchatVBox, this.tchatTextField.getText());
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
                root.setStyle("-fx-: black;");
            }
        }
    }

    @FXML
    private void proposerRegles() {
        int tailleGrille = this.tailleGrilleSpin.getValueFactory().getValue();
        int repro = this.reproSpin.getValueFactory().getValue();
        int surPop = this.surPoSpin.getValueFactory().getValue();
        int sousPop = this.sousPoSpin.getValueFactory().getValue();
        String reglesProposees = "->r:[%s,%s,%s,%s]";
        String reglesOk = String.format(reglesProposees, tailleGrille, repro, surPop, sousPop);
        TchatService.creerEtEnvoyerMessage(this.client, this.tchatVBox, reglesOk);
    }

}
