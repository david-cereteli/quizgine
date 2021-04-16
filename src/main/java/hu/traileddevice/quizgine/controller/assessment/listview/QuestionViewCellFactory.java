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

package hu.traileddevice.quizgine.controller.assessment.listview;

import hu.traileddevice.quizgine.view.assessment.QuestionView;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class QuestionViewCellFactory implements Callback<ListView<QuestionView>, ListCell<QuestionView>> {

    @Override
    public ListCell<QuestionView> call(ListView<QuestionView> questionViewList) {
        ListCell<QuestionView> cell = new QuestionViewCell();
        cell.setOnMousePressed((MouseEvent event) -> {
            if (cell.isEmpty()) {
                event.consume();
                questionViewList.getSelectionModel().clearSelection();
            }
        });
        return cell;
    }
}

