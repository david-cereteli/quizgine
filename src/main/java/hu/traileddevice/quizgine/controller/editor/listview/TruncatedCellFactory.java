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

package hu.traileddevice.quizgine.controller.editor.listview;

import hu.traileddevice.quizgine.controller.editor.EditorController;
import hu.traileddevice.quizgine.view.edit.AnswerDisplayable;
import hu.traileddevice.quizgine.view.edit.CellDisplayable;
import hu.traileddevice.quizgine.view.edit.QuestionDisplayable;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class TruncatedCellFactory implements Callback<ListView<CellDisplayable>, ListCell<CellDisplayable>> {
    private static final String CORRECT_STYLE = "correctAnswer";
    private static final String INCOMPLETE_STYLE = "incompleteQuestion";
    private static final String[] STYLE_CLASSES = {CORRECT_STYLE, INCOMPLETE_STYLE};
    private final EditorController editorController;

    public TruncatedCellFactory(EditorController editorController) {
        this.editorController = editorController;
    }

    @Override
    public ListCell<CellDisplayable> call(ListView<CellDisplayable> cellDisplayableListView) {
        ListCell<CellDisplayable> cell = new ListCell<>();
        cell.itemProperty().addListener((observableValue, oldItem, newItem) -> {
            if (newItem != null) {
                cell.prefWidthProperty()
                        .bind(DoubleBinding.doubleExpression(cellDisplayableListView.maxWidthProperty()).add(-1));
                cell.setText(generateContent(cell, newItem));
                String styleToApply = generateBackground(newItem);
                if (styleToApply != null) {
                    cell.getStyleClass().add(styleToApply);
                } else {
                    cell.getStyleClass().removeAll(STYLE_CLASSES);
                }
            }
            refreshQuestionListIfAnswersChanged(oldItem, newItem);
        });
        cell.emptyProperty().addListener((observableValue, wasEmpty, isEmpty) -> {
            if (isEmpty) {
                cell.setText(null);
                cell.getStyleClass().removeAll(STYLE_CLASSES);
            }
        });
        cell.setOnMousePressed((MouseEvent event) -> {
            if (cell.isEmpty()) {
                event.consume();
                editorController.displayQuizData(null);
                cellDisplayableListView.getSelectionModel().clearSelection();
            }
        });
        return cell;
    }

    private String generateContent(ListCell<CellDisplayable> cell, CellDisplayable newItem) {
        String noLineBreak = newItem.getContent().replaceAll("\\R+", "");
        String numberedText = (cell.getIndex() + 1 + ". ").concat(noLineBreak);
        cell.setTextOverrun(OverrunStyle.ELLIPSIS);
        return numberedText;
    }

    private String generateBackground(CellDisplayable cellDisplayable) {
        if (cellDisplayable instanceof AnswerDisplayable) {
            AnswerDisplayable answer = (AnswerDisplayable) cellDisplayable;
            if (answer.isCorrect()) {
                return CORRECT_STYLE;
            }
        } else if (cellDisplayable instanceof QuestionDisplayable) {
            QuestionDisplayable question = (QuestionDisplayable) cellDisplayable;
            if (question.getAnswers().stream().noneMatch(answer -> ((AnswerDisplayable)answer).isCorrect())) {
                return INCOMPLETE_STYLE;
            }
        }
        return null;
    }

    private void refreshQuestionListIfAnswersChanged(CellDisplayable oldItem, CellDisplayable newItem) {
        if (newItem instanceof AnswerDisplayable || oldItem instanceof AnswerDisplayable) {
            editorController.getQuestionListView().refresh();
        }
    }

}

