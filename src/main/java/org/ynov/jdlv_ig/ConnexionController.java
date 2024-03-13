package org.ynov.jdlv_ig;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import org.ynov.jdlv_ig.entity.EViewFXML;
import org.ynov.jdlv_ig.entity.UserDto;
import org.ynov.jdlv_ig.http_controller.AuthentificationHttpController;
import org.ynov.jdlv_ig.entity.User;
import org.ynov.jdlv_ig.utils.UserInfosSingleton;

import java.io.IOException;
import java.util.Map;

/**
 * Classe qui gère la vue relative à la page de connexion
 */
public class ConnexionController {

    /**
     * Login du joueur.
     */
    private String loginUser;
    /**
     * Bouton qui renvoie vers la page d'inscription.
     */
    @FXML
    private Label inscriptionBtnText;
    /**
     * Champ de texte pour le login.
     */
    @FXML
    private TextField loginField;
    /**
     * Champ de texte pour le mot de passe.
     */
    @FXML
    private PasswordField mdpField;

    /**
     * Méthode qui permet d'enclencher le processus de connexion et en cas de succès, qui initialise et affiche la vue
     * du jeu lui même.
     * @param event
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    protected void seConnecter(ActionEvent event) throws IOException, InterruptedException {
        if ((loginField.getText() != null && !loginField.getText().equals(""))
        && mdpField.getText() != null && !mdpField.getText().equals("")) {

            AuthentificationHttpController connexionBackend = new AuthentificationHttpController();
            Map<UserDto, Integer> response = connexionBackend.seConnecter(User.builder().login(loginField.getText()).mdp(mdpField.getText()).build());
            if(!response.isEmpty()) {
                UserDto userDto = response.keySet().iterator().next();
                UserInfosSingleton.getInstance(userDto);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(EViewFXML.GAME_VIEW.getNomView()));
                Scene gameScene = new Scene(root);
                window.setScene(gameScene);
                UserInfosSingleton.setStage(window);
                UserInfosSingleton.getStage();
                window.show();
                window.setOnCloseRequest(e -> Platform.exit());
            }
        } else {
            Popup erreurAuthentification = new Popup();
            erreurAuthentification.setWidth(300);
            erreurAuthentification.setHeight(300);
            erreurAuthentification.getContent().addAll(new TextField("Erreur d'authentification"), new Button("OK"));
        }
    }
    /**
     * Méthode qui permet de changer la couleur du bouton inscription au survol.
     */
    @FXML
    protected void onInscriptionFieldBtnHover() {
        inscriptionBtnText.setTextFill(new Color(0.3059, 0.4118, 0.4353, 1.0));
        inscriptionBtnText.setCursor(Cursor.HAND);

    }
    /**
     * Méthode qui permet de rétablir la couleur du bouton inscription quand on sort du survol.
     */
    @FXML
    void onInscriptionFieldBtnOut() {
        inscriptionBtnText.setTextFill(Color.BLACK);
        inscriptionBtnText.setCursor(Cursor.DEFAULT);
    }
    /**
     * Méthode qui charge la vue Inscription et l'affiche lorsque l'on clic sur le bouton inscription.
     * @param event
     * @throws IOException
     */
    @FXML
    void onInscriptionFieldBtnClick(MouseEvent event) throws IOException {
        Parent inscriptionView = FXMLLoader.load(getClass().getResource(EViewFXML.INSCRIPTION_VIEW.getNomView()));
        Scene inscriptionScene = new Scene(inscriptionView);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(inscriptionScene);
        window.show();
    }

    /**
     * Setter du login utilisateur.
     * @param loginUser
     */
    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    /**
     * Getter du login utilisateur.
     * @return
     */
    public String getLoginUser() {
        return loginUser;
    }
}