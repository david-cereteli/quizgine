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

package hu.traileddevice.quizgine.controller;

import com.github.mouse0w0.darculafx.DarculaFX;
import hu.traileddevice.quizgine.Main;
import hu.traileddevice.quizgine.controller.stage.PopupStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainLayout;

    @FXML
    private void exitApplication() {
        Platform.exit();
    }

    @FXML
    private void openAboutWindow() throws IOException {
        Stage primaryStage = (Stage) (mainLayout.getScene()).getWindow();
        PopupStage aboutStage = new PopupStage(primaryStage);
        aboutStage.setTitle("About");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
        Scene scene = new Scene(root);
        DarculaFX.applyDarculaStyle(root);
        Main.applyStylePreferences(scene);
        aboutStage.setScene(scene);
        aboutStage.showAndWait();
    }

    @FXML
    private void openQuizEditor() throws IOException {
        loadInSameStage("/fxml/editor/editor.fxml", "Quiz Editor");
    }

    @FXML
    private void openAssessment() throws IOException {
        loadInSameStage("/fxml/assessment/assessment.fxml", "Quiz Assessment");
    }

    private void loadInSameStage(String url, String title) throws IOException {
        Scene scene = mainLayout.getScene();
        Stage primaryStage = (Stage) scene.getWindow();
        primaryStage.setTitle(title);
        Parent root = FXMLLoader.load(getClass().getResource(url));
        scene.setRoot(root);
        primaryStage.setScene(scene);
    }
}
