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

package hu.traileddevice.quizgine;

import com.github.mouse0w0.darculafx.DarculaFX;
import hu.traileddevice.quizgine.io.QuizIoGson;
import hu.traileddevice.quizgine.service.QuizManager;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class Main extends Application {
    @Getter private static HostServices services;
    @Getter private static final QuizManager quizManager = QuizManager.getInstance(new QuizIoGson());
    @Getter @Setter private static boolean justStarted = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        services = getHostServices();
        primaryStage.setTitle("Quizgine");
        Scene scene = new Scene(root);
        DarculaFX.applyDarculaStyle(scene);
        applyStylePreferences(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void applyStylePreferences(Scene scene) {
        scene.getStylesheets().add(Main.class.getResource("/css/preferences.css").toExternalForm());
    }

}
