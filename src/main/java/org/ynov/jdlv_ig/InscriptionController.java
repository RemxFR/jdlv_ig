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
import org.ynov.jdlv_ig.entity.EViewFXML;
import org.ynov.jdlv_ig.entity.UserDto;
import org.ynov.jdlv_ig.http_controller.AuthentificationHttpController;
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
            AuthentificationHttpController req = new AuthentificationHttpController();
            UserDto newUserDto = UserDto.builder().login(login.getText()).mdp(mdp.getText()).build();
            boolean inscriptionValidee = req.inscireUser(newUserDto);
            if (inscriptionValidee) {
                UserInfosSingleton.getInstance(newUserDto);
                Parent gameView = FXMLLoader.load(getClass().getResource(EViewFXML.GAME_VIEW.getNomView()));
                Scene gameScene = new Scene(gameView);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(gameScene);
                UserInfosSingleton.setStage(window);
                window.show();

                window.setOnCloseRequest(e -> Platform.exit());
            }
        }
    }

    @FXML
    public void retourAccueil(ActionEvent event) throws IOException {
        Parent connexionView = FXMLLoader.load(getClass().getResource(EViewFXML.CONNEXION_VIEW.getNomView()));
        Scene connexionScene = new Scene(connexionView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(connexionScene);
        window.show();
    }

}
