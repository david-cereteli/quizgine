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

import hu.traileddevice.quizgine.view.edit.AnswerDisplayable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class EditAnswer implements Initializable, EditorLink {
    @FXML
    VBox editLayout;

    @FXML
    private TextArea answerText;

    @FXML
    private CheckBox isCorrect;

    private AnswerDisplayable answerDisplayable;

    private EditorController editorController;

    @Override
    public void setEditorController(EditorController editorController) {
        this.editorController = editorController;
    }

    @FXML
    public void modifyAnswer(ActionEvent actionEvent) {
        answerDisplayable.setAnswerText(answerText.getText());
        answerDisplayable.setCorrect(isCorrect.isSelected());
        editorController.answerListView.refresh();
        editorController.backToEditorFromEditOrAdd(answerDisplayable);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ChangeListener<Number> isRenderedListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                answerDisplayable = (AnswerDisplayable) editorController.answerListView.getSelectionModel().getSelectedItem();
                answerText.setText(answerDisplayable.getAnswerText());
                isCorrect.setSelected(answerDisplayable.isCorrect());
                editLayout.widthProperty().removeListener(this);
            }
        };

        editLayout.widthProperty().addListener(isRenderedListener);
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        editorController.backToEditorFromEditOrAdd(answerDisplayable);
    }
}
