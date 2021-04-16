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

import hu.traileddevice.quizgine.model.Answer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AnswerView {
    private StringProperty answerText;
    private BooleanProperty isCorrect;
    private BooleanProperty isMarked;

    public AnswerView(Answer answer) {
        setAnswerText(answer.getAnswerText());
        setIsCorrect(answer.isCorrect());
    }

    public void setIsMarked(Boolean value) {
        isMarkedProperty().set(value);
    }

    public boolean getIsMarked() {
        return isMarkedProperty().get();
    }

    public BooleanProperty isMarkedProperty() {
        if (isMarked == null) isMarked = new SimpleBooleanProperty(this, "answerText");
        return isMarked;
    }


    public void setAnswerText(String value) {
        answerTextProperty().set(value);
    }

    public String getAnswerText() {
        return answerTextProperty().get();
    }

    public StringProperty answerTextProperty() {
        if (answerText == null) answerText = new SimpleStringProperty(this, "answerText");
        return answerText;
    }

    public void setIsCorrect(Boolean value) {
        isCorrectProperty().set(value);
    }

    public Boolean getIsCorrect() {
        return isCorrectProperty().get();
    }

    public BooleanProperty isCorrectProperty() {
        if (isCorrect == null) isCorrect = new SimpleBooleanProperty(this, "isCorrect");
        return isCorrect;
    }
}
