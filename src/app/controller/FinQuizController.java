package app.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class FinQuizController implements Initializable {

    @FXML
    private AnchorPane pageFin;

    @FXML
    private Label lblBonnesReponses, lblNbQuestions, lblTauxReussite;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageFin.setOpacity(0);
        ouvrirPage();
    }

    private void ouvrirPage() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(pageFin);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public void setLabels(int nbBonnesReponses, int nbQuestions, float tauxReussite) {
        lblBonnesReponses.setText("Nombre de bonnes réponses : " + String.valueOf(nbBonnesReponses));
        lblNbQuestions.setText("Nombre de questions : " + String.valueOf(nbQuestions));
        lblTauxReussite.setText("Taux de réussite : " + String.valueOf(tauxReussite));
    }
}
