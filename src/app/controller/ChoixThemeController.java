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

public class ChoixThemeController implements Initializable{
    @FXML
    private AnchorPane pageTheme;

    @FXML
    Button btnRetour, btnSuivant;

    @FXML
    ToggleButton btnVerbes, btnAliments, btnFamille, btnQuotidien, btnMaison, btnChiffres, btnCorps;

    @FXML
    Label lblThemeChoisi;

    @FXML
    private void allerSuivant() { changerEcran(); }

    @FXML
    private void retourner() throws IOException {
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("../view/choixLangue.fxml"));
        Parent root = Loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("../styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toggleSuivant() {
        if (btnVerbes.isSelected()) {
            btnSuivant.setDisable(false);
            lblThemeChoisi.setText("Phrases");
        } else if (btnAliments.isSelected()) {
            btnSuivant.setDisable(true);
            lblThemeChoisi.setText("Aliments (bientôt disponible)");
        } else if (btnFamille.isSelected()) {
            btnSuivant.setDisable(true);
            lblThemeChoisi.setText("Famille (bientôt disponible)");
        } else if (btnChiffres.isSelected()) {
            btnSuivant.setDisable(true);
            lblThemeChoisi.setText("Chiffres (bientôt disponible)");
        } else if (btnCorps.isSelected()) {
            btnSuivant.setDisable(true);
            lblThemeChoisi.setText("Corps (bientôt disponible)");
        } else if (btnQuotidien.isSelected()) {
            btnSuivant.setDisable(true);
            lblThemeChoisi.setText("Quotidien (bientôt disponible)");
        } else {
            btnSuivant.setDisable(true);
            lblThemeChoisi.setText("Maison (bientôt disponible)");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTheme.setOpacity(0);
        ouvrirPage();
    }

    private void changerEcran() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(pageTheme);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event) -> {
            try {
                chargerFenetre();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }

    private void chargerFenetre() throws IOException {
        Stage stage = (Stage) pageTheme.getScene().getWindow();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("../view/quizVerbes.fxml"));
        Parent root = Loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("../styles.css").toExternalForm());
        QuizVerbesController quiz = Loader.getController();
        quiz.setTitreChoix(lblThemeChoisi.getText());
        stage.setScene(scene);
    }

    private void ouvrirPage() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(pageTheme);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}
