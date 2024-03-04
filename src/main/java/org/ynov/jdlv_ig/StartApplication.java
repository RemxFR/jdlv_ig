package org.ynov.jdlv_ig;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.lang3.ThreadUtils;
import org.ynov.jdlv_ig.utils.LoggerUtil;

import java.io.IOException;

public class StartApplication extends Application {
    LoggerUtil logger = new LoggerUtil();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("connexion-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Jeu de la vie");
        this.logger.log(System.Logger.Level.INFO, "Lancement de la connexion de la Socket");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
        });
    }

    @Override
    public void stop() {

        for (Thread t : ThreadUtils.getAllThreads()) {
            if (t.getState().equals(Thread.State.RUNNABLE)) {
                t.interrupt();
            }
            if (t.getState().equals(Thread.State.WAITING)) {
                try {
                    t.run();
                    t.interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        while (!Thread.currentThread().isInterrupted()) {
            launch();
        }
    }
}