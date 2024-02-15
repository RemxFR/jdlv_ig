package org.ynov.jdlv_ig;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.ynov.jdlv_ig.logique.User;
import org.ynov.jdlv_ig.utils.UserInfosSingleton;

import java.io.IOException;

public class InscriptionController {

    @FXML
    private TextField login;
    @FXML
    private PasswordField mdp;

    @FXML
    public void inscription(ActionEvent event) throws IOException, InterruptedException {
        if ((login != null && !login.equals("")) && (mdp != null && !mdp.equals(""))) {
            ConnexionBackend req = new ConnexionBackend();
            User newUser = User.builder().login(login.getText()).mdp(mdp.getText()).build();
            boolean inscriptionValidee = req.inscireUser(newUser);
            if (inscriptionValidee) {
                UserInfosSingleton.getInstance(newUser);
                Parent gameView = FXMLLoader.load(getClass().getResource("game-view.fxml"));
                Scene gameScene = new Scene(gameView);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(gameScene);
                window.show();
                window.setOnCloseRequest(e -> Platform.exit());
            }
        }
    }

    @FXML
    public void retourAccueil(ActionEvent event) throws IOException {
        Parent connexionView = FXMLLoader.load(getClass().getResource("connexion-view.fxml"));
        Scene connexionScene = new Scene(connexionView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(connexionScene);
        window.show();
    }

}
