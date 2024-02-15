package org.ynov.jdlv_ig;

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
            boolean inscriptionValidee = req.inscireUser(User.builder().login(login.getText()).mdp(mdp.getText()).build());
            if (inscriptionValidee) {
                Parent gameView = FXMLLoader.load(getClass().getResource("game-view.fxml"));
                Scene gameScene = new Scene(gameView);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(gameScene);
                window.show();
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
