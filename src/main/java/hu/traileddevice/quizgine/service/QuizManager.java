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

package hu.traileddevice.quizgine.service;

import hu.traileddevice.quizgine.io.QuizIo;
import hu.traileddevice.quizgine.model.Answer;
import hu.traileddevice.quizgine.model.Question;
import hu.traileddevice.quizgine.model.Quiz;
import hu.traileddevice.quizgine.view.assessment.QuizView;
import hu.traileddevice.quizgine.view.edit.AnswerDisplayable;
import hu.traileddevice.quizgine.view.edit.CellDisplayable;
import hu.traileddevice.quizgine.view.edit.QuestionDisplayable;
import hu.traileddevice.quizgine.view.edit.QuizDisplayable;
import lombok.Getter;

import java.io.*;
import java.util.List;

public class QuizManager {
    private static QuizManager quizManager;
    @Getter private final String[] fileExtension;
    private final QuizIo quizIo;

    private QuizManager(QuizIo quizIo) {
        this.quizIo = quizIo;
        this.fileExtension = quizIo.getFileExtension();
    }

    public static QuizManager getInstance(QuizIo quizIo) {
        if (quizManager == null) {
            quizManager = new QuizManager(quizIo);
        }
        return quizManager;
    }

    public QuizDisplayable createNewQuizForEditing() {
        return new QuizDisplayable();
    }

    public QuizView loadQuizForAssessment(File filePath) {
        Quiz quizToLoad = loadQuiz(filePath);
        return new QuizView(quizToLoad);
    }

    public QuizDisplayable loadQuizForEditing(File filePath) {
        Quiz quizToLoad = loadQuiz(filePath);
        return new QuizDisplayable(quizToLoad);
    }

    private Quiz loadQuiz(File filePath) {
        return quizIo.load(filePath);
    }

    public void saveQuiz(QuizDisplayable quizDisplayable, File filePath) {
        Quiz quizToSave = convertEditViewToModel(quizDisplayable);
        quizIo.save(quizToSave, filePath);
    }

    public static Quiz convertEditViewToModel(QuizDisplayable quizDisplayable) {
        Quiz quizToSave = new Quiz();
        List<CellDisplayable> questionDisplayableList = quizDisplayable.getQuestions();
        for (CellDisplayable displayable : questionDisplayableList) {
            QuestionDisplayable questionDisplayable = (QuestionDisplayable) displayable;
            Question question = new Question(questionDisplayable.getQuestionText());
            List<CellDisplayable> answerDisplayableList = questionDisplayable.getAnswers();
            for (CellDisplayable cellDisplayable : answerDisplayableList) {
                AnswerDisplayable answerDisplayable = (AnswerDisplayable) cellDisplayable;
                Answer answer = new Answer(answerDisplayable.getAnswerText(), answerDisplayable.isCorrect());
                question.addAnswer(answer);
            }
            quizToSave.addQuestion(question);
        }
        return quizToSave;
    }

}
