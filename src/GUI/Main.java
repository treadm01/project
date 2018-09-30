package GUI;

import ArithmeticCoder.AdaptiveArithmeticCompress;
import ArithmeticCoder.AdaptiveArithmeticDecompress;
import GrammarCoder.*;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main extends Application {
    private String PATH = System.getProperty("user.dir");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final Rule[] searchFileRule = new Rule[1];
        primaryStage.setTitle("ProjectGC");
        InputOutput io = new InputOutput();

        // next to it is output for search
        TextArea textOutput = new TextArea();
        textOutput.textProperty().addListener((observable, oldValue, newValue) -> {
            textOutput.setText(newValue);
        });

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(PATH));

        BorderPane border = new BorderPane();

        ChoiceBox choiceBox = new ChoiceBox();

        choiceBox.getItems().add("Search");
        choiceBox.getItems().add("Decompress");
        choiceBox.getItems().add("Compress (With Edits)");
        choiceBox.getItems().add("Compress");

        choiceBox.setValue("Compress");

        Button compressButton = new Button("Select File");
        compressButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                if (choiceBox.getValue() == "Compress" || choiceBox.getValue() == "Compress (With Edits)" ) {
                    Compress c = new Compress();
                    long startTime = System.currentTimeMillis();
                    c.processInput(io.readFile(selectedFile), choiceBox.getValue() != "Compress");
                    ImplicitEncoder ie = new ImplicitEncoder(c.getFirstRule());
                    try {
                        AdaptiveArithmeticCompress aac = new AdaptiveArithmeticCompress(ie.highestRule, ie.getSymbolList());
                        textOutput.setText(aac.constructCompressionOutput(selectedFile.length()));

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    long stopTime = System.currentTimeMillis();
                    long elapsedTime = stopTime - startTime;
                    System.out.println("time " + elapsedTime);
                } else if (choiceBox.getValue() == "Decompress") {
                    try {
                        AdaptiveArithmeticDecompress aad = new AdaptiveArithmeticDecompress(selectedFile);
                        Decompress d = new Decompress();
                        // split into local variables
                        d.writeToFile(d.decompress(d.buildGrammar(aad.getImplicitEncoding())));
                        textOutput.setText("File decompressed");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    //todo keep the rule returned from decompressing, when press search button init search
                    try {
                        // currently decompressing completely todo how to search accessed file
                        AdaptiveArithmeticDecompress aad = new AdaptiveArithmeticDecompress(selectedFile);
                        Decompress d = new Decompress();
                        searchFileRule[0] = d.buildGrammar(aad.getImplicitEncoding());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        // search bar
        TextField searchField = new TextField();

        Button searchButton = new Button("Search");

        searchButton.setOnAction(value ->  {
            if (searchFileRule[0] != null) {
                Search s = new Search(searchFileRule[0]);
                textOutput.setText(String.valueOf(s.search(String.valueOf(searchField.getCharacters()))));
            }
        });

        HBox hBox = new HBox(choiceBox, compressButton, searchField, searchButton);
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);
        border.setTop(hBox);

        HBox fileAndOutput = new HBox(textOutput);
        fileAndOutput.setSpacing(10);

        VBox vBox = new VBox(fileAndOutput);
        vBox.setPadding(new Insets(0, 12, 15, 12));
        vBox.setSpacing(10);

        border.setCenter(vBox);

        Scene scene = new Scene(border, 560, 250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}