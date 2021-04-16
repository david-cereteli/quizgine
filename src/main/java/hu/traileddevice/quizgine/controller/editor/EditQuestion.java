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

import hu.traileddevice.quizgine.view.edit.QuestionDisplayable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class EditQuestion implements Initializable, EditorLink {
    @FXML
    VBox editLayout;

    @FXML
    TextArea questionText;

    private QuestionDisplayable questionDisplayable;

    private EditorController editorController;

    @Override
    public void setEditorController(EditorController editorController) {
        this.editorController = editorController;
    }

    @FXML
    public void modifyQuestion(ActionEvent actionEvent) {
        questionDisplayable.setQuestionText(questionText.getText());
        editorController.questionListView.refresh();
        editorController.backToEditorFromEditOrAdd(questionDisplayable);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ChangeListener<Number> isRenderedListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number aNumber, Number t1) {
                questionDisplayable = (QuestionDisplayable) editorController.questionListView.getSelectionModel().getSelectedItem();
                questionText.setText(questionDisplayable.getQuestionText());
                editLayout.widthProperty().removeListener(this);
            }
        };

        editLayout.widthProperty().addListener(isRenderedListener);
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        editorController.backToEditorFromEditOrAdd(questionDisplayable);
    }
}
