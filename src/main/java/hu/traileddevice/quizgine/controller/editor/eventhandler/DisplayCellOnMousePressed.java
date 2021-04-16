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

package hu.traileddevice.quizgine.controller.editor.eventhandler;

import hu.traileddevice.quizgine.controller.editor.EditorController;
import hu.traileddevice.quizgine.view.edit.CellDisplayable;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class DisplayCellOnMousePressed implements EventHandler<MouseEvent> {
    private final EditorController editorController;

    public DisplayCellOnMousePressed(EditorController editorController) {
        this.editorController = editorController;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        ListView<CellDisplayable> focusedListview =
                (ListView<CellDisplayable>) editorController.getEditorLayout().getScene().getFocusOwner();
        CellDisplayable cellDisplayable = focusedListview.getSelectionModel().getSelectedItem();
        editorController.displayQuizData(cellDisplayable);
    }
}
