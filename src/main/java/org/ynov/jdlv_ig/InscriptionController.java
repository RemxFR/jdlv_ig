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

/**
 * Classe en charge de gérer la partie inscription de l'utilisateur au niveau de l'affichage et de l'appel
 * des méthodes dédiées à cet effet.
 */
public class InscriptionController {

    /**
     * Champ pour rentrer le login utilisateur
     */
    @FXML
    private TextField login;
    /**
     * Champ pour rentrer le mot de passe utilisateur
     */
    @FXML
    private PasswordField mdp;

    /**
     * Méthode permettant à l'utilisateur de créer son profil dans le Jeu de la vie, en entrant son login
     * et son mot de passe.
     * Une fois cela fait et validé par la partie backend, cette méthode redirige automatiquement l'utilsateur
     * vers la page du jeu.
     * @param event
     * @throws IOException
     * @throws InterruptedException
     */
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

    /**
     * Méthode qui permet de retourner à l'accueil, qui est la page de connexion, si l'utilisateur ne souahite
     * plus s'inscrire ou s'est retrouvé sur la page d'inscription par erreur.
     * @param event
     * @throws IOException
     */
    @FXML
    public void retourAccueil(ActionEvent event) throws IOException {
        Parent connexionView = FXMLLoader.load(getClass().getResource(EViewFXML.CONNEXION_VIEW.getNomView()));
        Scene connexionScene = new Scene(connexionView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(connexionScene);
        window.show();
    }

}
