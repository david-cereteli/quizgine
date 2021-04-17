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

package hu.traileddevice.quizgine.controller.editor;

import com.github.mouse0w0.darculafx.DarculaFX;
import hu.traileddevice.quizgine.Main;
import hu.traileddevice.quizgine.controller.editor.eventhandler.DisplayCellOnKeyMove;
import hu.traileddevice.quizgine.controller.editor.eventhandler.DisplayCellOnMousePressed;
import hu.traileddevice.quizgine.controller.editor.listview.TruncatedCellFactory;
import hu.traileddevice.quizgine.view.edit.AnswerDisplayable;
import hu.traileddevice.quizgine.view.edit.CellDisplayable;
import hu.traileddevice.quizgine.view.edit.QuestionDisplayable;
import hu.traileddevice.quizgine.controller.stage.ConfirmationBox;
import hu.traileddevice.quizgine.controller.stage.PopupStage;
import hu.traileddevice.quizgine.view.edit.QuizDisplayable;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditorController implements Initializable {
    @Getter private static final double NEGATIVE_SCROLL_BAR_WIDTH = -10;
    private QuizDisplayable quizDisplayable;
    ObservableList<CellDisplayable> questions;
    ObservableList<CellDisplayable> answers;
    private BooleanProperty isQuizLoaded, isInAddOrEdit, isQuestionSelected, isAnswerSelected;

    @FXML
    @Getter
    BorderPane editorLayout;

    @FXML
    private Button addQuestionButton, editQuestionButton, deleteQuestionButton,
            addAnswerButton, editAnswerButton, deleteAnswerButton;

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    @Getter
    ListView<CellDisplayable> questionListView;

    @FXML
    @Getter
    ListView<CellDisplayable> answerListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isQuizLoaded = new SimpleBooleanProperty(false);
        isQuestionSelected = new SimpleBooleanProperty(false);
        isAnswerSelected = new SimpleBooleanProperty(false);
        isInAddOrEdit = new SimpleBooleanProperty(false);

        addQuestionButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> !isQuizLoaded.get() || isInAddOrEdit.get(),
                isQuizLoaded, isInAddOrEdit));
        editQuestionButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> !isQuizLoaded.get() || isInAddOrEdit.get() || !isQuestionSelected.get(),
                isQuizLoaded, isQuestionSelected, isInAddOrEdit));
        deleteQuestionButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> !isQuizLoaded.get() || isInAddOrEdit.get() || !isQuestionSelected.get(),
                isQuizLoaded, isQuestionSelected, isInAddOrEdit));
        addAnswerButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> !isQuizLoaded.get() || isInAddOrEdit.get() || !isQuestionSelected.get(),
                isQuizLoaded, isQuestionSelected, isInAddOrEdit));
        editAnswerButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> !isQuizLoaded.get() || isInAddOrEdit.get() || !isQuestionSelected.get() || !isAnswerSelected.get(),
                isQuizLoaded, isQuestionSelected, isInAddOrEdit, isAnswerSelected));
        deleteAnswerButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> !isQuizLoaded.get() || isInAddOrEdit.get() || !isQuestionSelected.get() || !isAnswerSelected.get(),
                isQuizLoaded, isQuestionSelected, isInAddOrEdit, isAnswerSelected));

        questionListView.disableProperty().bind(Bindings.createBooleanBinding(() -> isInAddOrEdit.get(), isInAddOrEdit));
        answerListView.disableProperty().bind(Bindings.createBooleanBinding(() -> isInAddOrEdit.get(), isInAddOrEdit));

        saveMenuItem.disableProperty().bind(Bindings.createBooleanBinding(() -> !isQuizLoaded.get(), isQuizLoaded));

        questionListView.setCellFactory(new TruncatedCellFactory(this));

        questionListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CellDisplayable>() {
            @Override
            public void changed(ObservableValue<? extends CellDisplayable> observableValue,
                                CellDisplayable oldValue, CellDisplayable newValue) {
                if (newValue != null) {
                    answers = FXCollections.observableList(((QuestionDisplayable) newValue).getAnswers());
                    isQuestionSelected.set(true);
                } else {
                    answers = FXCollections.emptyObservableList();
                    isQuestionSelected.set(false);
                }
                answerListView.setItems(answers);
                if (!answers.isEmpty()) {
                    answerListView.getSelectionModel().selectFirst();
                }
            }
        });

        questionListView.setOnMousePressed(new DisplayCellOnMousePressed(this));
        questionListView.setOnKeyPressed(new DisplayCellOnKeyMove(this));

        answerListView.setCellFactory(new TruncatedCellFactory(this));

        answerListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CellDisplayable>() {
            @Override
            public void changed(ObservableValue<? extends CellDisplayable> observableValue,
                                CellDisplayable oldValue, CellDisplayable newValue) {
                isAnswerSelected.set(newValue != null);
            }
        });

        answerListView.setOnMousePressed(new DisplayCellOnMousePressed(this));
        answerListView.setOnKeyPressed(new DisplayCellOnKeyMove(this));
    }

    @FXML
    private void exitApplication() {
        Platform.exit();
    }

    @FXML
    private void openAboutWindow() throws IOException {
        Stage primaryStage = (Stage) (editorLayout.getScene()).getWindow();
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
    private void backToMain() throws IOException {
        Scene scene = editorLayout.getScene();
        Stage primaryStage = (Stage) scene.getWindow();
        primaryStage.setTitle("Quizgine");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        scene.setRoot(root);
        primaryStage.setScene(scene);
    }

    public void focusAndDisplay(CellDisplayable cellDisplayable) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (cellDisplayable instanceof QuestionDisplayable) {
                    questionListView.getSelectionModel().select(cellDisplayable);
                    questionListView.requestFocus();
                    displayQuizData(cellDisplayable);
                } else if (cellDisplayable instanceof AnswerDisplayable) {
                    answerListView.getSelectionModel().select(cellDisplayable);
                    answerListView.requestFocus();
                    displayQuizData(cellDisplayable);
                }
            }
        });
    }

    public void displayQuizData(CellDisplayable cellDisplayable) {
        if (cellDisplayable == null) {
            editorLayout.setCenter(null);
            return;
        }

        ScrollPane scrollPane = new ScrollPane();
        StackPane stackPane = new StackPane();
        stackPane.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));
        stackPane.minHeightProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getHeight(), scrollPane.viewportBoundsProperty()));
        scrollPane.setContent(stackPane);
        VBox innerBox = new VBox();
        innerBox.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(innerBox);

        Label label = new Label(cellDisplayable.getContent());
        label.maxWidthProperty().bind(Bindings.add(stackPane.getMaxWidth() - 10, stackPane.widthProperty()));
        label.setBorder(new Border(
                new BorderStroke(Color.web("#45494a"), BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(5))
        ));
        label.setBackground(new Background(
                new BackgroundFill(Color.web("#45494a"), new CornerRadii(5), new Insets(5, 5, 5, 5))
        ));
        label.setWrapText(true);
        innerBox.getChildren().add(label);
        editorLayout.setCenter(scrollPane);
    }

    void backToEditorFromEditOrAdd(CellDisplayable cellDisplayable) {
        editorLayout.setCenter(null);
        isInAddOrEdit.set(false);
        focusAndDisplay(cellDisplayable);
    }

    @FXML
    public void addQuestion() throws IOException {
        loadAddOrEdit("/fxml/editor/add-question.fxml");
    }

    @FXML
    public void editQuestion() throws IOException {
        loadAddOrEdit("/fxml/editor/edit-question.fxml");
    }

    @FXML
    public void deleteQuestion() throws IOException {
        int questionIndexToDelete = questionListView.getSelectionModel().getSelectedIndex();
        ConfirmationBox confirmationController = getDeleteConfirmationPopup(questionIndexToDelete, "question");
        if (confirmationController.isConfirmed()) {
            questions.remove(questionIndexToDelete);
            editorLayout.setCenter(null);
        } else {
            focusAndDisplay(questionListView.getItems().get(questionIndexToDelete));
        }
    }

    @FXML
    public void addAnswer() throws IOException {
        loadAddOrEdit("/fxml/editor/add-answer.fxml");
    }

    @FXML
    public void editAnswer() throws IOException {
        loadAddOrEdit("/fxml/editor/edit-answer.fxml");
    }

    @FXML
    public void deleteAnswer() throws IOException {
        int answerIndexToDelete = answerListView.getSelectionModel().getSelectedIndex();
        ConfirmationBox confirmationController = getDeleteConfirmationPopup(answerIndexToDelete, "answer");
        if (confirmationController.isConfirmed()) {
            answers.remove(answerIndexToDelete);
            editorLayout.setCenter(null);
        } else {
            focusAndDisplay(answerListView.getItems().get(answerIndexToDelete));
        }
    }

    private ConfirmationBox getDeleteConfirmationPopup(int indexToDelete, String type) throws IOException {
        Stage editorStage = (Stage) (editorLayout.getScene()).getWindow();
        ConfirmationBox confirmationController =
                new ConfirmationBox(editorStage, String.format("Delete %s #%d ?", type, indexToDelete + 1));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirmation-box.fxml"));
        loader.setController(confirmationController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        DarculaFX.applyDarculaStyle(root);
        Main.applyStylePreferences(scene);
        confirmationController.setTitle("Confirmation");
        confirmationController.setScene(scene);
        confirmationController.showAndWait();
        return confirmationController;
    }

    private void loadAddOrEdit(String fxmlPath) throws IOException {
        isInAddOrEdit.set(true);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        VBox centerPane = fxmlLoader.load();
        editorLayout.setCenter(centerPane);
        EditorLink controller = fxmlLoader.getController();
        controller.setEditorController(this);
    }

    @FXML
    private void createNewQuiz() {
        quizDisplayable = Main.getQuizManager().createNewQuizForEditing();
        setupLoadedQuiz();
    }

    private void setupLoadedQuiz() {
        editorLayout.setCenter(null);
        questions = FXCollections.observableList(quizDisplayable.getQuestions()); // original list is updated!
        questionListView.setItems(questions);
        isQuizLoaded.set(true);
    }

    @FXML
    public void loadQuiz() {
        Stage editorStage = (Stage) (editorLayout.getScene()).getWindow();
        String directoryPath = System.getProperty("user.dir").concat(File.separator).concat("save");
        File baseDirectory = new File(directoryPath);
        baseDirectory.mkdir(); // this IS required
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(baseDirectory);
        fileChooser.setTitle("Load Quiz");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(Main.getQuizManager().getFileExtension()[0],
                        Main.getQuizManager().getFileExtension()[1]));
        File file = fileChooser.showOpenDialog(editorStage);
        if (file != null) {
            quizDisplayable = Main.getQuizManager().loadQuizForEditing(file);
            setupLoadedQuiz();
        }
    }

    @FXML
    public void saveQuiz() {
        Stage editorStage = (Stage) (editorLayout.getScene()).getWindow();
        String directoryPath = System.getProperty("user.dir").concat(File.separator).concat("save");
        File baseDirectory = new File(directoryPath);
        baseDirectory.mkdir(); // only creates it if not exists
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(baseDirectory);
        fileChooser.setTitle("Save Quiz");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(Main.getQuizManager().getFileExtension()[0],
                        Main.getQuizManager().getFileExtension()[1]));
        File file = fileChooser.showSaveDialog(editorStage);
        if (quizDisplayable != null && file != null) {
            Main.getQuizManager().saveQuiz(quizDisplayable, file);
        }
    }

}
