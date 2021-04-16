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

package hu.traileddevice.quizgine.controller.editor.listview;

import hu.traileddevice.quizgine.controller.editor.EditorController;
import hu.traileddevice.quizgine.view.edit.CellDisplayable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class TruncatedCellFactory implements Callback<ListView<CellDisplayable>, ListCell<CellDisplayable>> {
    private final EditorController editorController;

    public TruncatedCellFactory(EditorController editorController) {
        this.editorController = editorController;
    }

    @Override
    public ListCell<CellDisplayable> call(ListView<CellDisplayable> cellDisplayableListView) {
        ListCell<CellDisplayable> cell = new TruncatedListCell();
        cell.setOnMousePressed((MouseEvent event) -> {
            if (cell.isEmpty()) {
                event.consume();
                editorController.displayQuizData(null);
                cellDisplayableListView.getSelectionModel().clearSelection();
            }
        });
        return cell;
    }
}
