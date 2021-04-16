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

package hu.traileddevice.quizgine.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hu.traileddevice.quizgine.model.Quiz;

import java.io.*;

public class QuizIoGson implements QuizIo {
    private final Gson gson;
    private static final String[] fileExtension = {"JSON", "*.json"};

    public QuizIoGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void save(Quiz quizToSave, File filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            gson.toJson(quizToSave, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Quiz load(File filePath) {
        Quiz fileToLoad = null;
        try {
            FileReader fileReader = new FileReader(filePath);
            fileToLoad = gson.fromJson(fileReader, Quiz.class);
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileToLoad;
    }

    @Override
    public String[] getFileExtension() {
        return fileExtension;
    }
}
