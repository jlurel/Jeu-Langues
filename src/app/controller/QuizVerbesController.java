package app.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import app.Main;
import app.model.Question;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class QuizVerbesController implements Initializable {

    @FXML
    private AnchorPane pageQuiz;

    @FXML
    private Button btnRetour, btnReponse1, btnReponse2, btnReponse3;

    @FXML
    private Label lblReponse, lblScore, titreChoix;

    @FXML
    private ImageView boxImage;

    @FXML
    private ProgressBar progressBarQuiz;

    static ArrayList<Question> lesQuestions;

    private static Stage currentStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File fichier = new File(getClass().getResource("../phrases.txt").getFile());
        Question.resetScore();
        Question.setIndexQuestion(0);
        lesQuestions = Question.chargerQuestions(fichier);
        Question.setBoutons(btnReponse1, btnReponse2, btnReponse3);
        lesQuestions.get(Question.getIndexQuestion()).afficherQuestions(boxImage, lblReponse);
        pageQuiz.setOpacity(0);
        ouvrirPage();
    }

    public void setTitreChoix(String themeChoisi) {
            titreChoix.setText(themeChoisi);
    }

    private void ouvrirPage() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(pageQuiz);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @FXML
    private void retourner() throws IOException {
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("../view/choixTheme.fxml"));
        Parent root = Loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("../styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void validerReponse(ActionEvent event) {
        Button boutonClique = (Button) event.getSource();
        btnReponse1.setDisable(true);
        btnReponse2.setDisable(true);
        btnReponse3.setDisable(true);
        String reponse = boutonClique.getText();
        lesQuestions.get(Question.getIndexQuestion()).verifierReponse(lesQuestions, boutonClique, lblScore);
        progressBarQuiz.setProgress(Question.getProgression());
        Timer temps = new Timer();
        temps.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    lesQuestions.get(Question.getIndexQuestion()).afficherQuestions(boxImage, lblReponse);
                    btnReponse1.setDisable(false);
                    btnReponse2.setDisable(false);
                    btnReponse3.setDisable(false);
                });
            }
        }, 1500);
    }

    public static void terminerQuiz(int score, int nbBonnesReponses, int nbQuestions, int tauxReussite) {
        Alert finQuiz = new Alert(Alert.AlertType.INFORMATION);
        finQuiz.setTitle("Fin du quiz");
        finQuiz.setHeaderText("Vous venez de finir le quiz. Votre score est : " + score);
        finQuiz.setContentText("Nombre de questions : " + nbQuestions + "\n"
                + "Nombre de bonnes réponses : " + nbBonnesReponses + "\n"
                + "Taux de réussite : " + tauxReussite + "%");
        finQuiz.showAndWait();
        Question.setIndexQuestion(-1);
        Question.resetScore();
        Stage stage = Main.getPrimaryStage();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(QuizVerbesController.class.getResource("../view/choixTheme.fxml"));
        Parent root = null;
        try {
            root = Loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add(QuizVerbesController.class.getResource("../styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
