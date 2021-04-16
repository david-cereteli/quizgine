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

package hu.traileddevice.quizgine.controller.assessment.checkbox;

import hu.traileddevice.quizgine.view.assessment.AnswerView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class AnswerCheckGroup extends VBox {
    private final BooleanProperty shouldShowSolvedHighlight;
    @Getter private ObservableList<AnswerView> answers;
    private final List<CheckBox> checkBoxes;

    public AnswerCheckGroup(BooleanProperty isSubmitted) {
        checkBoxes = new ArrayList<>();
        shouldShowSolvedHighlight = new SimpleBooleanProperty();
        shouldShowSolvedHighlight.bind(isSubmitted);
        shouldShowSolvedHighlight.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                for (int rowIndex = 0; rowIndex < checkBoxes.size(); rowIndex++) {
                    CheckBox checkBox = checkBoxes.get(rowIndex);
                    AnswerView answer = answers.get(rowIndex);
                    setBackground(checkBox, answer, rowIndex);
                }
            }
        });
    }

    public void setItems(ObservableList<AnswerView> answers) {
        checkBoxes.clear();
        this.getChildren().clear();
        this.answers = answers;
        for (int rowIndex = 0; rowIndex < answers.size(); rowIndex++) {
            AnswerView answer = answers.get(rowIndex);
            CheckBox checkBox = new CheckBox(answer.getAnswerText());
            checkBox.setPadding(new Insets(5, 10, 5, 10));
            checkBox.setWrapText(true);
            checkBox.minWidthProperty().bind(this.maxWidthProperty());
            checkBox.selectedProperty().bindBidirectional(answer.isMarkedProperty());
            setBackground(checkBox, answer, rowIndex);
            checkBox.disableProperty().bind(shouldShowSolvedHighlight);
            checkBoxes.add(checkBox);
            this.getChildren().add(checkBox);
        }
    }

    private void setBackground(CheckBox checkBox, AnswerView answer, int rowIndex) {
        if (!shouldShowSolvedHighlight.get()) {
            checkBox.setStyle((rowIndex % 2 == 0)
                    ? "-fx-background-color: -dark-row-alternate-bg;"
                    : "-fx-background-color: -darcula-base;"
            );
        } else {
            if (answer.getIsCorrect() && answer.getIsMarked()) {
                checkBox.setStyle("-fx-background-color: -dark-green-bg;");
            } else if (answer.getIsMarked()) {
                checkBox.setStyle("-fx-background-color: -dark-red-bg;");
            } else if (answer.getIsCorrect() && !answer.getIsMarked()){
                checkBox.setStyle("-fx-background-color: -dark-purple-bg;");
            }
        }
    }
}
