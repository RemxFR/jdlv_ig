package org.ynov.jdlv_ig;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.ynov.jdlv_ig.logique.Cellule;
import org.ynov.jdlv_ig.logique.Matrice;
import org.ynov.jdlv_ig.websocket.SocketClient;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

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
    private SocketClient client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Socket socket = new Socket("localhost", 1234);

            this.client = new SocketClient(socket, "user" + this.getUserName());
            System.out.println(this.getUserName());
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
        taille = 100;
        nb = 0.1;
        chronologie = new Timeline();
        int largeur = 500;
        espace = largeur / taille;

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
                    this.client.sendMessage(tchatTextField);
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
                    circles[i][j].setFill(Cellule.coulVersActive);
                } else {
                    circles[i][j].setFill(Cellule.coulDesactive);
                }
                cell.setCircle(circles[i][j]);
                root.getChildren().add(circles[i][j]);
            }
        }
    }

    public static void afficherMessageRecu(String message, VBox vBox) {

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
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
            }
        });

    }
}
