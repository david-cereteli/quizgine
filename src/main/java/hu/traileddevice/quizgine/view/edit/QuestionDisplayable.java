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

package hu.traileddevice.quizgine.view.edit;

import hu.traileddevice.quizgine.model.Answer;
import hu.traileddevice.quizgine.model.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class QuestionDisplayable implements CellDisplayable {
    @Getter @Setter private String questionText;
    @Getter @Setter private List<CellDisplayable> answers;

    public QuestionDisplayable() {
        answers = new ArrayList<>();
    }

    public QuestionDisplayable(String questionText) {
        this();
        this.questionText = questionText;
    }

    public QuestionDisplayable(Question question) {
        this();
        this.questionText = question.getQuestionText();
        List<Answer> answers = question.getAnswers();
        for (Answer currentAnswer : answers) {
            this.answers.add(new AnswerDisplayable(currentAnswer));
        }
    }

    @Override
    public String getContent() {
        return questionText;
    }
}
