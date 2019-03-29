package app.controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChoixLangueController implements Initializable {
    @FXML
    private AnchorPane pageLangue;

    @FXML
    private ToggleButton btnAnglais, btnAllemand, btnEspagnol;

    @FXML
    private Label lblLangueChoisie;

    @FXML
    private Button btnSuivant;

    @FXML
    private void allerSuivant() throws IOException {
        changerEcran();
    }

    @FXML
    private void toggleSuivant() {
        if (btnAnglais.isSelected()) {
            btnSuivant.setDisable(false);
            lblLangueChoisie.setText("Anglais");
        } else if (btnAllemand.isSelected()) {
            btnSuivant.setDisable(true);
            lblLangueChoisie.setText("Allemand (bientôt disponible)");
        } else {
            btnSuivant.setDisable(true);
            lblLangueChoisie.setText("Espagnol (bientôt disponible)");
        }

    }

    @FXML
    private void retourner() throws IOException {
        Stage stage = (Stage) pageLangue.getScene().getWindow();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("../view/startJeu.fxml"));
        Parent root = Loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("../styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageLangue.setOpacity(0);
        ouvrirPage();
    }

    private void changerEcran() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(pageLangue);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event) -> {
            try {
                chargerFenetreSuivante();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }

    private void chargerFenetreSuivante() throws IOException {
        Stage stage = (Stage) pageLangue.getScene().getWindow();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("../view/choixTheme.fxml"));
        Parent root = Loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("../styles.css").toExternalForm());
        stage.setScene(scene);
    }

    private void ouvrirPage() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(pageLangue);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}
