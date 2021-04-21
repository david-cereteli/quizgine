/*
 * Quizgine is a quiz engine for building and taking quizzes.
 * Copyright (C) 2021 David Cereteli
 *
 * This file is part of Quizgine.
 *
 * Quizgine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Quizgine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Quizgine.  If not, see <https://www.gnu.org/licenses/>.
 */

package hu.traileddevice.quizgine.controller.assessment;

import com.github.mouse0w0.darculafx.DarculaFX;
import hu.traileddevice.quizgine.Main;
import hu.traileddevice.quizgine.controller.assessment.checkbox.AnswerCheckGroup;
import hu.traileddevice.quizgine.controller.assessment.changelistener.QuizCompleteListener;
import hu.traileddevice.quizgine.controller.assessment.changelistener.ShowIfSelected;
import hu.traileddevice.quizgine.controller.assessment.listview.QuestionViewCellFactory;
import hu.traileddevice.quizgine.controller.stage.ConfirmationBox;
import hu.traileddevice.quizgine.controller.stage.PopupStage;
import hu.traileddevice.quizgine.view.assessment.AnswerView;
import hu.traileddevice.quizgine.view.assessment.QuestionView;
import hu.traileddevice.quizgine.view.assessment.QuizView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AssessmentController implements Initializable {
    @Getter private QuizView quizView;
    private static final int VERTICAL_SCROLLBAR_MODIFIER = 50;
    private BooleanProperty isQuizLoaded, isQuizComplete;
    @Getter private BooleanProperty isSubmitted;
    @Getter private BooleanProperty isFirstQuestion, isLastQuestion;
    @Getter ScrollPane scrollPane;

    @FXML
    private BorderPane assessmentLayout;

    @FXML
    private Button previousQuestionButton, nextQuestionButton, submitQuizButton;

    @FXML
    public Label scoreLabel;

    @FXML
    @Getter private ListView<QuestionView> questionListView;

    private Label questionText;
    @Getter private AnswerCheckGroup answerCheckGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isQuizLoaded = new SimpleBooleanProperty(false);
        isQuizComplete = new SimpleBooleanProperty(false);
        isSubmitted = new SimpleBooleanProperty(false);
        isFirstQuestion = new SimpleBooleanProperty(false);
        isLastQuestion = new SimpleBooleanProperty(false);
        nextQuestionButton.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> !isQuizLoaded.get() || isLastQuestion.get(), isQuizLoaded, isLastQuestion));
        previousQuestionButton.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> !isQuizLoaded.get() || isFirstQuestion.get(), isQuizLoaded, isFirstQuestion));
        submitQuizButton.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> !isQuizLoaded.get() || isQuizComplete.get() || !scoreLabel.getText().isEmpty()
                        , isQuizLoaded, isQuizComplete, scoreLabel.textProperty()));
        questionText = new Label();
        questionText.setPadding(new Insets(0, 20, 50, 20));
        questionListView.setCellFactory(new QuestionViewCellFactory(this));
        questionListView.getSelectionModel().selectedItemProperty().addListener(new ShowIfSelected(this));
        answerCheckGroup = new AnswerCheckGroup(isSubmitted);
    }

    @FXML
    void exitApplication() {
        Window window = assessmentLayout.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    void openAboutWindow() throws IOException {
        Stage primaryStage = (Stage) (assessmentLayout.getScene()).getWindow();
        PopupStage aboutStage = new PopupStage(primaryStage);
        aboutStage.setTitle("About");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
        Scene scene = new Scene(root);
        DarculaFX.applyDarculaStyle(root);
        Main.applyStylePreferences(scene);
        aboutStage.setScene(scene);
        aboutStage.showAndWait();
    }

    @FXML
    void backToMain() throws IOException {
        if (rethinkChoice("Are you sure you wish to abandon your progress?")) return;
        Scene scene = assessmentLayout.getScene();
        Stage primaryStage = (Stage) scene.getWindow();
        primaryStage.setTitle("Quizgine");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        scene.setRoot(root);
        primaryStage.setScene(scene);
    }

    private boolean rethinkChoice(String confirmText) throws IOException {
        if (quizView == null || isSubmitted.get()) return false;
        Stage editorStage = (Stage) (assessmentLayout.getScene()).getWindow();
        ConfirmationBox confirmationBox = new ConfirmationBox(editorStage, confirmText, 350);
        confirmationBox.showAndWait();
        return !confirmationBox.isConfirmed();
    }

    @FXML
    void loadQuiz() throws IOException {
        if (rethinkChoice("Are you sure you wish to load a new Quiz?")) return;
        Stage editorStage = (Stage) (assessmentLayout.getScene()).getWindow();
        String directoryPath = System.getProperty("user.dir").concat(File.separator).concat("save");
        File baseDirectory = new File(directoryPath);
        baseDirectory.mkdir(); // required
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(baseDirectory);
        fileChooser.setTitle("Load Quiz");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showOpenDialog(editorStage);
        if (file != null) {
            quizView = Main.getQuizManager().loadQuizForAssessment(file);
            setupLoadedQuiz();
        }
    }

    private void setupLoadedQuiz() {
        isSubmitted.set(false);
        scoreLabel.setText("");
        questionListView.setItems(quizView.getQuestionViewList());
        for (int i = 0; i < questionListView.getItems().size(); i++) {
            QuestionView question = questionListView.getItems().get(i);
            for (int j = 0; j < question.getAnswers().size(); j++) {
                AnswerView answer = question.getAnswers().get(j);
                answer.isMarkedProperty().addListener(new QuizCompleteListener(this));
            }
        }
        isQuizComplete.bind(Bindings.createBooleanBinding(
                () -> !quizView.getIsComplete(), quizView.isCompleteProperty()
        ));
        drawCenter();
        questionText.maxWidthProperty().bind(
                Bindings.createDoubleBinding(() -> scrollPane.widthProperty().doubleValue() - VERTICAL_SCROLLBAR_MODIFIER,
                        scrollPane.widthProperty()));
        questionText.setWrapText(true);
        questionText.setAlignment(Pos.CENTER);
        questionListView.getSelectionModel().selectFirst();
        isQuizLoaded.set(true);
        answerCheckGroup.maxWidthProperty().bind(questionText.maxWidthProperty());
    }

    private void drawCenter() {
        scrollPane = new ScrollPane();
        StackPane stackPane = new StackPane();
        stackPane.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));
        stackPane.minHeightProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getHeight(), scrollPane.viewportBoundsProperty()));
        scrollPane.setContent(stackPane);
        VBox innerBox = new VBox();
        innerBox.setAlignment(Pos.CENTER);
        innerBox.getChildren().addAll(questionText, answerCheckGroup);
        stackPane.getChildren().add(innerBox);
        assessmentLayout.setCenter(scrollPane);
    }

    public void showQuestion(int questionIndex) {
        QuestionView questionView = questionListView.getItems().get(questionIndex);
        questionText.setText(questionView.getQuestionText());
        answerCheckGroup.setItems(questionView.getAnswers());
    }

    @FXML
    void nextQuestion() {
        questionListView.getSelectionModel().selectNext();
        questionListView.scrollTo(questionListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void previousQuestion() {
        questionListView.getSelectionModel().selectPrevious();
        questionListView.scrollTo(questionListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void submitQuiz() {
        double score = quizView.getScore();
        scoreLabel.setText(getPercentageScore(score));
        isSubmitted.set(true);
        questionListView.refresh();
    }

    private String getPercentageScore(double score) {
        return String.format("%.2f%%", score);
    }
}
