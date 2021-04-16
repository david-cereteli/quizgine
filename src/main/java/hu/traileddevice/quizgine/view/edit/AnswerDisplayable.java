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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class AnswerDisplayable implements CellDisplayable {
    @Getter @Setter private String answerText;
    @Getter @Setter private boolean isCorrect;

    public AnswerDisplayable(Answer answer) {
        this.answerText = answer.getAnswerText();
        this.isCorrect = answer.isCorrect();
    }

    @Override
    public String getCellContent() {
        return String.format("%s: %s", (isCorrect ? "T" : "F"), answerText);
    }

    @Override
    public String getContent() {
        return answerText;
    }
}
