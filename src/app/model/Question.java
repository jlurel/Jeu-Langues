package app.model;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import app.controller.QuizVerbesController;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Question {
    static ArrayList<Button> boutons;
    static int indexQuestion = 0;
    static int nbBonnesReponses = 0;
    static int nbQuestions = 1;
    static int nbQuestionsTotales = 0;
    static double progression = 0;
    static Random random = new Random();

    static int score = 0;

    String image;
    String question;
    String bonneReponse;
    ArrayList<String> mauvaisesReponses;
    Button btnVrai;

    public Question(String lImage, String laQuestion, String laBonneReponse, String mauvaiseReponse1, String mauvaiseReponse2) {
        this.image = lImage;
        this.question = laQuestion;
        this.bonneReponse = laBonneReponse;
        this.mauvaisesReponses = new ArrayList<>();
        this.mauvaisesReponses.add(mauvaiseReponse1);
        this.mauvaisesReponses.add(mauvaiseReponse2);
    }

    public static ArrayList<Question> chargerQuestions(File fichier) {
        ArrayList<Question> lesQuestions = new ArrayList<>();
        //Essaie de récupérer les questions contenues dans le fichier défini
        try {
            Scanner sc = new Scanner (fichier);
            while(sc.hasNext()) {
                sc.useDelimiter(";");
                String lImage = sc.next();
                String laQuestion = sc.next();
                String laBonneReponse = sc.next();
                String mauvaiseReponse1 = sc.next();
                String mauvaiseReponse2 = sc.next();
                lesQuestions.add(new Question(lImage, laQuestion, laBonneReponse, mauvaiseReponse1, mauvaiseReponse2));
                if (sc.hasNext("fin")) sc.nextLine();
            }
        } catch (IOException e) {
            Alert fichierIntrouvable = new Alert(Alert.AlertType.ERROR);
            fichierIntrouvable.setTitle("Fichier de questions introuvable");
            fichierIntrouvable.setContentText("Impossible de trouver le fichier des questions ! Vérifiez le chemin du fichier correspondant.");
            fichierIntrouvable.show();
        }
        //vérifie que les questions ont bien été chargées
        if (lesQuestions.isEmpty()) {
            Alert aucuneQuestion = new Alert(Alert.AlertType.WARNING);
            aucuneQuestion.setTitle("Aucune question");
            aucuneQuestion.setHeaderText("Aucune question trouvée.");
            aucuneQuestion.setContentText("Vérifiez que le fichier " + fichier.getName() + " contient des questions.");
            aucuneQuestion.show();
        }
        nbQuestionsTotales = lesQuestions.size();
        return lesQuestions;
    }

    public static void setBoutons(Button...lesBoutons) {
        boutons = new ArrayList<> (Arrays.asList(lesBoutons));
    }

    public void afficherQuestions(ImageView ivQuestion, Label lblQuestion) {
        Image imgQuestion = new Image(Question.class.getResourceAsStream(this.image));
        ivQuestion.setImage(imgQuestion);
        lblQuestion.setText(this.question);
        ArrayList<Button> lesBoutons = new ArrayList<>(boutons);

        for (Button unBouton : lesBoutons) {
            unBouton.getStyleClass().removeAll("btnVrai", "btnFaux");
        }

        //on choisit un bouton au hasard pour lui attribuer la bonne reponse
        int reponseRandom = random.nextInt(3);
        btnVrai = lesBoutons.get(reponseRandom);
        lesBoutons.get(reponseRandom).setText(this.bonneReponse);
        lesBoutons.remove(lesBoutons.get(reponseRandom));

        //on mélange les mauvaises reponses et les affecte chacune à un bouton
        Collections.shuffle(this.mauvaisesReponses);
        for (Button unBouton : lesBoutons) {
            unBouton.setText(this.mauvaisesReponses.get(lesBoutons.indexOf(unBouton)));
        }
    }

    public void verifierReponse(ArrayList<Question> lesQuestions, Button leBouton, Label lblScore) {
        if(leBouton == this.btnVrai) {
            leBouton.getStyleClass().add("btnVrai");
            score += 5;
            nbBonnesReponses += 1;
        } else {
            this.btnVrai.getStyleClass().add("btnVrai");
            leBouton.getStyleClass().add("btnFaux");
            score -= 2;
        }
        lblScore.setText("Score : " + score);
        int tauxReussite = (int) ((double)nbBonnesReponses/(double)nbQuestions*100);
        if(nbQuestions == lesQuestions.size()) {
            QuizVerbesController.terminerQuiz(score, nbBonnesReponses, nbQuestions, tauxReussite);
            score = 0;
            nbQuestions = 0;
            nbBonnesReponses = 0;
            tauxReussite = 0;
        }
        indexQuestion += 1;
        nbQuestions += 1;
        progression = (double) nbQuestions/nbQuestionsTotales;
        System.out.println(progression);
    }

    public static int getIndexQuestion() {
        return indexQuestion;
    }

    public static void setIndexQuestion(int indexQuestion) {
        Question.indexQuestion = indexQuestion;
    }

    public static void resetScore() {
        Question.score = 0;
    }

    public static double getProgression() {
        return progression;
    }
}
