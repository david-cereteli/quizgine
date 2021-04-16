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

package hu.traileddevice.quizgine.controller.assessment.changelistener;

import hu.traileddevice.quizgine.controller.assessment.AssessmentController;
import hu.traileddevice.quizgine.view.assessment.AnswerView;
import hu.traileddevice.quizgine.view.assessment.QuestionView;
import hu.traileddevice.quizgine.view.assessment.QuizView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class QuizCompleteListener implements ChangeListener<Boolean> {
    private final AssessmentController assessmentController;

    public QuizCompleteListener(AssessmentController assessmentController) {
        this.assessmentController = assessmentController;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        QuizView quizView = assessmentController.getQuizView();
        QuestionView currentQuestion = assessmentController.getQuestionListView().getSelectionModel().getSelectedItem();
        if (newValue) {
            currentQuestion.setIsComplete(true);
            quizView.setIsComplete(assessmentController.getQuestionListView().getItems().stream()
                    .allMatch(QuestionView::getIsComplete));
        } else {
            boolean isAtLeastOneAnswerMarked = assessmentController.getAnswerCheckGroup().getAnswers().stream()
                    .anyMatch(AnswerView::getIsMarked);
            if (!isAtLeastOneAnswerMarked) {
                currentQuestion.setIsComplete(false);
                quizView.setIsComplete(false);
            }
        }
    }
}
