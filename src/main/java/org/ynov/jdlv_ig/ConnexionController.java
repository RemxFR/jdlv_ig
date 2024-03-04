package org.ynov.jdlv_ig;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.ynov.jdlv_ig.entity.EViewFXML;
import org.ynov.jdlv_ig.entity.UserDto;
import org.ynov.jdlv_ig.http_controller.AuthentificationHttpController;
import org.ynov.jdlv_ig.entity.User;
import org.ynov.jdlv_ig.utils.UserInfosSingleton;

import java.io.IOException;
import java.util.Map;

public class ConnexionController {

    private String loginUser;

    @FXML
    private Label inscriptionBtnText;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField mdpField;

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


        }
    }

    @FXML
    protected void onInscriptionFieldBtnHover() {
        inscriptionBtnText.setTextFill(new Color(0.3059, 0.4118, 0.4353, 1.0));
        inscriptionBtnText.setCursor(Cursor.HAND);

    }

    @FXML
    void onInscriptionFieldBtnOut() {
        inscriptionBtnText.setTextFill(Color.BLACK);
        inscriptionBtnText.setCursor(Cursor.DEFAULT);
    }

    @FXML
    void onInscriptionFieldBtnClick(MouseEvent event) throws IOException {
        Parent inscriptionView = FXMLLoader.load(getClass().getResource(EViewFXML.INSCRIPTION_VIEW.getNomView()));
        Scene inscriptionScene = new Scene(inscriptionView);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(inscriptionScene);
        window.show();
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginUser() {
        return loginUser;
    }
}