# Quizgine

Quizgine is a quiz engine for building and taking quizzes, developed using Java and the JavaFX framework, created for the purpose of coding practice.


## Table of Contents

* [How to use](#How-to-use)
    * [1. Editor](#Editor-module)
    * [2. Assessment](#Assessment-module)
* [How to run](#How-to-run)
    * [1. IDE](#1-IDE)
    * [2. Command line](#2-Command-line)
    * [3. Jar](#3-Jar)
* [Dependencies](#Dependencies)
* [License](#License)


## How to use

Use the `File` menu in the upper left corner to open either module.

### Editor module

Use the `File` menu in the upper left corner to create a new quiz (or load an existing one).

You can add(`+`), edit(`✎`) or remove(`-`) questions with the left hand side buttons. The questions will be displayed in the list below them. The edit and remove buttons interact with the question selected (indicated by the outline around it).

Once a question is selected you can add answers to it with the right hand side buttons. When creating answers, don't forget to tick the correct answer to the question below the answer's textbox. (Multiple correct answers are allowed.)

Keep in mind that the buttons interact with the selected item in the list. Selection of answers will change automatically as you navigate to different questions.

Once you are satisfied with your quiz, you may save it by clicking on the `Save Quiz` menu item in the `File` menu, entering a file name for it in the popup and hitting the `Save` button.

### Assessment module

Use the `File`  menu in the upper left corner to load a quiz.

Use right(`>`) or left(`<`) arrows on the left to navigate between questions. (Clicking with the mouse is also allowed.)

Indicate your answer to the question by ticking the checkbox next to it.

Once you have selected at least one answer to each question, the `End test` button will be activated. Pressing it immediately calculates your score in a percentage format. (A question is considered correctly answered if all the right answers are ticked, and none of the wrong ones. No partial points.)

When the `End test` button is clicked, changing answers is no longer possible. However, you may go back to any question to review it. Correct answers are highlighted with a green background. (Red for wrong, purple for right answers that you missed.)


## How to run

[OpenJDK 11](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot) and [Maven](https://maven.apache.org/download.cgi) is required! 

Tested on Windows 10.

### 1. IDE

- Import the project in your IDE with the pom.xml to let maven gather the dependencies.
- Compile.
- Use the `App` class as the entry point.

### 2. Command line

From the project root folder in command line execute:
```console
$ mvn compile
```
```console
$ mvn exec:java -Dexec.mainClass=hu.traileddevice.quizgine.App
```

### 3. Jar

From the project root folder in command line execute:
```console
$ mvn clean install
```

Change into the `target` folder and execute:
```console
$ java -jar quizgine-1.0.jar
```


## Dependencies

* **[JavaFX Controls](https://mvnrepository.com/artifact/org.openjfx/javafx-controls/17-ea+6) 17-ea+6**
* **[JavaFX FXML](https://mvnrepository.com/artifact/org.openjfx/javafx-fxml/17-ea+6) 17-ea+6**
* **[Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.6) 2.8.6**
* **[JavaFX Maven Plugin Maven Mojo](https://mvnrepository.com/artifact/org.openjfx/javafx-maven-plugin/0.0.5) 0.0.5**
* **[Apache Maven Compiler Plugin](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-compiler-plugin/3.8.0) 3.8.0**
* **[Apache Maven Assembly Plugin](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-assembly-plugin)**
* **[Project Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.18) 1.18.18**
* **[DarculaFX](https://mvnrepository.com/artifact/com.github.mouse0w0/darculafx/1.0.0) 1.0.0**


## License

The source code is available under the **[GNU AGPLv3+](https://www.gnu.org/licenses/agpl-3.0.html)** license.

See the [COPYING](COPYING) and [LICENSE](LICENSE) files for details.