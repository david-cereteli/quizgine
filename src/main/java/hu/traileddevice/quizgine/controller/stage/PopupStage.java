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

package hu.traileddevice.quizgine.controller.stage;

import javafx.beans.value.ChangeListener;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupStage extends Stage {
    private double customWidth;

    public PopupStage(Stage parentStage) {
        this.setResizable(false);
        this.initModality(Modality.APPLICATION_MODAL);
        centerOnParentStage(parentStage);
    }

    public PopupStage(Stage parentStage, double width) {
        this.setResizable(false);
        this.initModality(Modality.APPLICATION_MODAL);
        this.customWidth = width;
        this.setMinWidth(width);
        centerOnParentStage(parentStage);
    }

    private void centerOnParentStage(Stage parentStage) {
        ChangeListener<Number> widthListener = (ignoredObservable, ignoredOldValue, newValue) -> {
            double currentStageWidth = customWidth != 0 ? customWidth : newValue.doubleValue();
            this.setX(parentStage.getX() + parentStage.getWidth() / 2.0 - currentStageWidth / 2.0);
        };
        ChangeListener<Number> heightListener = (ignoredObservable, ignoredOldValue, newValue) -> {
            double currentStageHeight = newValue.doubleValue();
            this.setY(parentStage.getY() + parentStage.getHeight() / 3.0 - currentStageHeight / 2.0);
        };

        this.widthProperty().addListener(widthListener);
        this.heightProperty().addListener(heightListener);

        this.setOnShown(ignoredEvent -> {
            this.widthProperty().removeListener(widthListener);
            this.heightProperty().removeListener(heightListener);
        });
    }
}
