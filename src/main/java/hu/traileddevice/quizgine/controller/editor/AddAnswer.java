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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

public class AddAnswer implements EditorLink{

    @FXML
    private TextArea answerText;

    @FXML
    private CheckBox isCorrect;

    private EditorController editorController;

    @Override
    public void setEditorController(EditorController editorController) {
        this.editorController = editorController;
    }

    @FXML
    public void createAnswer(ActionEvent ignoredEvent) {
        AnswerDisplayable answerDisplayable = new AnswerDisplayable(answerText.getText(), isCorrect.isSelected());
        editorController.answers.add(answerDisplayable);
        editorController.backToEditorFromEditOrAdd(answerDisplayable);
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        editorController.backToEditorFromEditOrAdd(null);
    }
}
