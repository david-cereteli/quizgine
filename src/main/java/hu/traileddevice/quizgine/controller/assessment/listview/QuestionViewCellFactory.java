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

package hu.traileddevice.quizgine.controller.assessment.listview;

import hu.traileddevice.quizgine.controller.assessment.AssessmentController;
import hu.traileddevice.quizgine.view.assessment.QuestionView;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class QuestionViewCellFactory implements Callback<ListView<QuestionView>, ListCell<QuestionView>> {
    private static final String INCORRECT_STYLE = "incorrectQuestion";
    private static final String COMPLETE_STYLE = "correctAnswer";
    private static final String[] STYLE_CLASSES = {INCORRECT_STYLE, COMPLETE_STYLE};
    private final AssessmentController assessmentController;

    public QuestionViewCellFactory(AssessmentController assessmentController) {
        this.assessmentController = assessmentController;
    }

    @Override
    public ListCell<QuestionView> call(ListView<QuestionView> questionViewList) {
        ListCell<QuestionView> cell = new ListCell<>();
        cell.itemProperty().addListener((observableValue, oldItem, newItem) -> {
            if (newItem != null) {
                cell.prefWidthProperty()
                        .bind(DoubleBinding.doubleExpression(questionViewList.maxWidthProperty()).add(-1));
                String noLineBreak = newItem.getQuestionText().replaceAll("\\R+", "");
                String numberedText = (cell.getIndex() + 1 + ". ").concat(noLineBreak);
                cell.setTextOverrun(OverrunStyle.ELLIPSIS);
                cell.setText(numberedText);
                String styleToApply = generateBackground(newItem);
                if (styleToApply != null) {
                    cell.getStyleClass().add(styleToApply);
                } else {
                    cell.getStyleClass().removeAll(STYLE_CLASSES);
                }
            }
        });
        cell.emptyProperty().addListener((observableValue, wasEmpty, isEmpty) -> {
            if (isEmpty) {
                cell.setText(null);
                cell.getStyleClass().removeAll(STYLE_CLASSES);
            }
        });
        return cell;
    }

    private String generateBackground(QuestionView questionView) {
        if (assessmentController.getIsSubmitted().get()) {
            if (!questionView.isAnsweredCorrectly()) return INCORRECT_STYLE;
        } else {
            if (questionView.getIsComplete()) return COMPLETE_STYLE;
        }
        return null;
    }
}
