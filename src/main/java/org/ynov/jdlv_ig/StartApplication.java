package org.ynov.jdlv_ig;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ynov.jdlv_ig.websocket.SocketClient;

import java.io.IOException;
import java.net.Socket;

public class StartApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("connexion-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Jeu de la vie");
        stage.setScene(scene);
        stage.show();
        System.out.println("Lancement de la connexion de la Socket");
    }

    public static void main(String[] args) {
        launch();
    }
}