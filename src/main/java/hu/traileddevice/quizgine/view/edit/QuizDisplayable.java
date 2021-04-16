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

import hu.traileddevice.quizgine.model.Question;
import hu.traileddevice.quizgine.model.Quiz;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class QuizDisplayable {
    @Getter private final List<CellDisplayable> questions;

    public QuizDisplayable() {
        questions = new ArrayList<>();
    }

    public QuizDisplayable(Quiz quiz) {
        this();
        List<Question> questions = quiz.getQuestions();
        for (Question question : questions) {
            QuestionDisplayable questionDisplayable = new QuestionDisplayable(question);
            addQuestion(questionDisplayable);
        }
    }

    public void addQuestion(QuestionDisplayable questionDisplayable) {
        questions.add(questionDisplayable);
    }
}