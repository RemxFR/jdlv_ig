package org.ynov.jdlv_ig;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.ynov.jdlv_ig.entity.*;
import org.ynov.jdlv_ig.http_controller.ReglesHttpController;
import org.ynov.jdlv_ig.service.TchatService;
import org.ynov.jdlv_ig.utils.UserInfosSingleton;
import org.ynov.jdlv_ig.websocket.SocketClient;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Classe qui est en charge de gérer les contrôles de l'affichage qui concerne l'écran de jeu en lui même,
 * y compris l'appel aux méthodes relatives à la partie tchat.
 */
public class GameController implements Initializable {

    /**
     * Regex qui représentent comment les règles sont formatées au moment de leur envoie.
     */
    private final String REGLES_REGEX = "->r:[%s,%s,%s,%s]";
    /**
     * Couleur noire pour background.
     */
    private final String BLACK_BACKGROUND = "-fx-fill: black;";
    /**
     * Classe en charge de générer la simulation du jeu de la vie dans une matrice aux valeurs prédéfinies
     * ou définies par le joueur.
     */
    Matrice matrice;
    /**
     * Objet cercle, affiché comme une cellule dans la grille et possédant une position x et y.
     */
    public static Circle[][] circles;
    /**
     * Vitesse de la simulation.
     */
    public final int tempo = 100;
    /**
     * Permet de générer à partir de cette valeur la position des cellules.
     */
    private int espace = 20;
    /**
     * Défini la taille de la grille.
     */
    private int taille;
    /**
     * Défini la densité de la grille.
     */
    private double densite;
    /**
     * Défini le login de l'utilisateur et permet de le récupérer pour l'envoie des messages
     * et l'enregistrement des règles par exemple.
     */
    @Getter
    @Setter
    private String userName;
    /**
     * Objet permettant d'implémenter le cycle de la simulation, de la démarrer et de l'arrêter.
     */
    private Timeline petitCycle;
    /**
     * Groupe rassemblant les objets à afficher dans la grille.
     */
    @FXML
    private Group group;
    /**
     * Wrapper pour les messages affichés dans le tchat.
     */
    @FXML
    private TextField tchatTextField;
    /**
     *Box verticale contenant la partie tchat.
     */
    @FXML
    private VBox tchatVBox;
    /**
     * Scroller permettant de scroller dans le tchat.
     */
    @FXML
    private ScrollPane scrollPane;
    /**
     * Bouton pour envoyer les règles aux autres joueurs au travers du tchat.
     */
    @FXML
    private Button propReglesBtn;
    /**
     * Liste déroulant permettant de changer la valeur du paramètre Sous-population.
     */
    @FXML
    private Spinner<Integer> sousPoSpin;
    /**
     * Liste déroulant permettant de changer la valeur du paramètre Sur-population.
     */
    @FXML
    Spinner<Integer> surPoSpin;
    /**
     * Liste déroulant permettant de changer la valeur du paramètre Reproduction.
     */
    @FXML
    Spinner<Integer> reproSpin;
    /**
     * Spinner qui wrap les valeurs qui définissent la taille de la grille du jeu.
     */
    @FXML
    Spinner<Integer> tailleGrilleSpin = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100));
    /**
     * Valeurs affichées dans la liste déroulante en charge de définir la taille de la grille du jeu.
     */
    @FXML
    private SpinnerValueFactory.IntegerSpinnerValueFactory grilleSpinValeur;
    /**
     * Table qui permet d'afficher les règles enregistrées par l'utilisateur.
     */
    @FXML
    TableView<ReglesCustomDto> tableView;
    @FXML
    TableColumn<ReglesCustomDto, Integer> grilleTailleColonne;
    @FXML
    TableColumn<ReglesCustomDto, Integer> reproColonne;
    @FXML
    TableColumn<ReglesCustomDto, Integer> surpopColonne;
    @FXML
    TableColumn<ReglesCustomDto, Integer> souspopColonne;
    /**
     * Socket qui permet de se connecter à la partie backend pour le tchat.
     */
    private SocketClient client;
    /**
     * UserDto, classe équivalente à la classe utilisateur mais sans la liste des règles enregistrées
     * afin de faciliter le transfer des données sans surcharger les requêtes.
     */
    private UserDto userDto;
    /**
     * Contrôleur qui permet de faire les requêtes Http afin de gérer les différentes méthodes de connexion,
     * de persistence des données et d'inscription.
     */
    private ReglesHttpController reglesHttpController = new ReglesHttpController();

    /**
     * Méthode d'initialisation de la vue du jeu, des valeurs des différents paramètres et lancement de l'écoute
     * au niveau du tchat.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            try {
                Socket socket = new Socket("localhost", 1234);
                this.userDto = UserInfosSingleton.getUser();
                this.setUserName(userDto.getLogin());
                this.client = new SocketClient(socket, this.getUserName());
                this.propReglesBtn.setText("Proposer\nRègles");
                this.propReglesBtn.setTextAlignment(TextAlignment.CENTER);
                taille = 100;
                densite = 0.1;
                int largeur = 500;
                espace = largeur / taille;
                this.tailleGrilleSpin.getValueFactory().setValue(taille);
                this.sousPoSpin.getValueFactory().setValue(Cellule.sousPopulation);
                this.surPoSpin.getValueFactory().setValue(Cellule.surPopulation);
                this.reproSpin.getValueFactory().setValue(Cellule.minPopulationRegeneratrice);
                this.afficherReglesEnregistrees(userDto.getReglesCustomList());
                this.client.listenForMessage(tchatVBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        fixerScrollTchatAuDernierMessageReçu();
    }

    /**
     * Méthode qui permet de d'afficher la vue du tchat au dernier message reçu à partir du moment où les messages reçus
     * activent le scroll. On verra toujours les derniers, tandis que les premiers ne seront plus visibles si
     * la taille totale du nombre de message dépasse la hauteur du tchat.
     */
    private void fixerScrollTchatAuDernierMessageReçu() {
        tchatVBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });
    }

    /**
     * Méthode qui permet de lancer une simulation du jeu de la vie.
     */
    @FXML
    protected void lancerjdlv() {
        this.group.getChildren().clear();
        taille = tailleGrilleSpin.getValueFactory().getValue();
        this.grilleSpinValeur.setValue(taille);
        Cellule.sousPopulation = this.sousPoSpin.getValueFactory().getValue();
        Cellule.surPopulation = this.surPoSpin.getValueFactory().getValue();
        Cellule.minPopulationRegeneratrice = this.reproSpin.getValueFactory().getValue();
        matrice = new Matrice(taille, densite);
        GameController.circles = new Circle[taille][taille];
        dessinMatrice(group);
        petitCycle = new Timeline(new KeyFrame(Duration.millis(tempo), actionEvent -> {
            matrice.copierGrille();
            matrice.animGrille();
        }));
        petitCycle.setCycleCount(Timeline.INDEFINITE);
        petitCycle.play();
    }

    /**
     * Méthode qui permet d'arrêter la simulation en cours.
     */
    @FXML
    protected void arreterJdlv() {
        this.petitCycle.stop();
        this.group.getChildren().clear();
    }

    /**
     * Méthode qui permet d'écrire dans le tchat et d'envoyer un message lorsque l'on clic sur la touche
     * Entrée.
     * @param event
     */
    @FXML
    protected void ecrireTchat(KeyEvent event) {
        if (!tchatTextField.getText().isEmpty() || !tchatTextField.getText().isBlank()) {
            tchatTextField.setOnKeyPressed(event1 -> {
                if (event1.getCode() == KeyCode.ENTER) {
                    TchatService.creerEtEnvoyerMessage(this.client, this.tchatVBox, this.tchatTextField.getText());
                    tchatTextField.clear();
                }
            });
        }
    }

    /**
     * Méthode qui génère/dessine la matrice de la simulation.
     * @param root
     */
    private void dessinMatrice(Group root) {
        Cellule[][] grille = matrice.getGrille();
        int rayon = espace / 2;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Cellule cell = grille[i][j];
                circles[i][j] = new Circle((j * espace + rayon), (i * espace + rayon), rayon);
                if (cell.isEstVivante()) {
                    circles[i][j].setFill(Cellule.coulActive);
                } else {
                    circles[i][j].setFill(Cellule.coulDesactive);
                }
                cell.setCircle(circles[i][j]);
                root.getChildren().add(circles[i][j]);
                root.setStyle(BLACK_BACKGROUND);
            }
        }
    }

    /**
     * Méthode qui permet d'envoyer les règles du joueur dans le tchat sous forme de proposition aux autres
     * joueurs.
     */
    @FXML
    private void proposerRegles() {
        int tailleGrille = this.tailleGrilleSpin.getValueFactory().getValue();
        int repro = this.reproSpin.getValueFactory().getValue();
        int surPop = this.surPoSpin.getValueFactory().getValue();
        int sousPop = this.sousPoSpin.getValueFactory().getValue();
        String reglesProposees = REGLES_REGEX;
        String reglesOk = String.format(reglesProposees, tailleGrille, repro, surPop, sousPop);
        TchatService.creerEtEnvoyerMessage(this.client, this.tchatVBox, reglesOk);
    }

    /**
     * Méthode qui permet de sauvegarder les règles du joueur.
     */
    @FXML
    private void sauvegarderRegles() {
        ReglesCustom reglesCustom = new ReglesCustom(
                this.surPoSpin.getValueFactory().getValue(),
                this.sousPoSpin.getValueFactory().getValue(),
                this.reproSpin.getValueFactory().getValue(),
                this.tailleGrilleSpin.getValueFactory().getValue());
        reglesHttpController.sauverRegles(reglesCustom, this.userDto.getLogin());
        tableView.getItems().clear();
        this.afficherReglesEnregistrees(this.recupererRegles());
    }

    /**
     * Méthode qui permet de récupérer et d'afficher la liste des règles enregistrées par le joueur.
     */
    private void afficherReglesEnregistrees(List<ReglesCustomDto> reglesCustomDtoList) {
        List<ReglesCustomDto> reglesCustomList = reglesCustomDtoList;
        ObservableList<ReglesCustomDto> reglesCustoms = FXCollections.observableArrayList(reglesCustomList);
        grilleTailleColonne.setCellValueFactory(new PropertyValueFactory<ReglesCustomDto, Integer>("tailleGrille"));
        reproColonne.setCellValueFactory(new PropertyValueFactory<ReglesCustomDto, Integer>("reproduction"));
        souspopColonne.setCellValueFactory(new PropertyValueFactory<ReglesCustomDto, Integer>("sousPopulation"));
        surpopColonne.setCellValueFactory(new PropertyValueFactory<ReglesCustomDto, Integer>("surPopulation"));
        tableView.setItems(reglesCustoms);
    }

    /**
     * Méthode qui retourne depuis le backend la liste de règles enregistrées par le joueur.
     * @return
     */
    private List<ReglesCustomDto> recupererRegles() {
        return reglesHttpController.recupererReglesCustom(this.userDto);
    }

}
