package org.ynov.jdlv_ig;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.lang3.ThreadUtils;
import org.ynov.jdlv_ig.entity.EViewFXML;
import org.ynov.jdlv_ig.utils.LoggerUtil;

import java.io.IOException;

public class StartApplication extends Application {
    /**
     * Titre de la fenêtre de l'application
     */
    private final String TITRE = "Jeu de la vie";
    /**
     * Logger pour afficher les logs relatives à cette vue.
     */
    LoggerUtil logger = new LoggerUtil();

    /**
     * Méthode de démarrage de l'application et d'initialisation de la première vue qui est la page de connexion.
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(EViewFXML.CONNEXION_VIEW.getNomView()));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(TITRE);
        this.logger.log(System.Logger.Level.INFO, "Lancement de la connexion de la Socket");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
        });
    }

    /**
     * Méthode qui permet d'arrêter l'application et de fermer les threads toujours ouverts.
     */
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
            launch();
    }
}