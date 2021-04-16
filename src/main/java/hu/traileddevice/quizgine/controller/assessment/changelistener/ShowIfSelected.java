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
import hu.traileddevice.quizgine.view.assessment.QuestionView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public class ShowIfSelected implements ChangeListener<QuestionView> {
    private final AssessmentController assessmentController;

    public ShowIfSelected(AssessmentController assessmentController) {
        this.assessmentController = assessmentController;
    }

    @Override
    public void changed(ObservableValue<? extends QuestionView> observableValue, QuestionView oldValue, QuestionView newValue) {
        if (newValue != null) {
            ObservableList<QuestionView> items = assessmentController.getQuestionListView().getItems();
            if (items.size() == 1) {
                assessmentController.getIsFirstQuestion().set(true);
                assessmentController.getIsLastQuestion().set(true);
            } else if (newValue.equals(items.get(0))) {
                assessmentController.getIsFirstQuestion().set(true);
                assessmentController.getIsLastQuestion().set(false);
            } else if (newValue.equals(items.get(items.size() - 1))) {
                assessmentController.getIsLastQuestion().set(true);
                assessmentController.getIsFirstQuestion().set(false);
            } else {
                assessmentController.getIsFirstQuestion().set(false);
                assessmentController.getIsLastQuestion().set(false);
            }
            assessmentController.showQuestion(items.indexOf(newValue));
            assessmentController.getScrollPane().setVvalue(0); // scroll to top
        }
    }
}
