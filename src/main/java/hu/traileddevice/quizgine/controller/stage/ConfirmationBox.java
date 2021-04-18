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

import com.github.mouse0w0.darculafx.DarculaFX;
import hu.traileddevice.quizgine.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationBox extends PopupStage implements Initializable {
    @Getter private boolean isConfirmed;
    private final String askText;

    @FXML
    private Text questionText;

    public ConfirmationBox(Stage parentStage, String text) throws IOException {
        super(parentStage);
        this.askText = text;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirmation-box.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        DarculaFX.applyDarculaStyle(root);
        Main.applyStylePreferences(scene);
        this.setTitle("Confirmation");
        this.setScene(scene);
    }

    public ConfirmationBox(Stage parentStage, String text, double width) throws IOException {
        super(parentStage, width);
        this.askText = text;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirmation-box.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        DarculaFX.applyDarculaStyle(root);
        Main.applyStylePreferences(scene);
        this.setTitle("Confirmation");
        this.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionText.setText(askText);
    }

    @FXML
    private void onYes() {
        isConfirmed = true;
        this.close();
    }

    @FXML
    private void onNo() {
        isConfirmed = false;
        this.close();
    }

}
