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

package hu.traileddevice.quizgine.view.assessment;

import hu.traileddevice.quizgine.model.Question;
import hu.traileddevice.quizgine.model.Quiz;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class QuizView {
    @Getter private final ObservableList<QuestionView> questionViewList;
    private BooleanProperty isComplete;

    public QuizView(Quiz quiz) {
        questionViewList = FXCollections.observableArrayList();
        quiz.getQuestions().forEach(this::addQuestion);
    }

    public void addQuestion(Question question ) {
        questionViewList.add(new QuestionView(question));
    }

    public double getScore() {
        return questionViewList.stream()
                .mapToDouble(question -> question.getAnswers().stream().allMatch(this::isAnswerCorrect) ? 100.0 : 0.0)
                .average().orElse(0.0);
    }

    private boolean isAnswerCorrect(AnswerView answer) {
        return (answer.getIsMarked() && answer.getIsCorrect()) || (!answer.getIsMarked());
    }

    public void setIsComplete(boolean value) {
        isCompleteProperty().set(value);
    }

    public boolean getIsComplete() {
        return isCompleteProperty().get();
    }

    public BooleanProperty isCompleteProperty() {
        if (isComplete == null) isComplete = new SimpleBooleanProperty(this, "isComplete");
        return isComplete;
    }

}
