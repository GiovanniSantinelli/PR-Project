package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

import java.io.File;

public class Main extends Application {

    Button analizeButton;
    Button fileButton;
    String filepath;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("patternMD");
        Group root = new Group();
        Scene scene = new Scene(root, 551, 400);
        fileButton = new Button("Select a file");
        fileButton.setLayoutX(250);
        fileButton.setLayoutY(220);
        analizeButton = new Button("Start Analize");
        analizeButton.setLayoutX(150);
        analizeButton.setLayoutY(220);
        //aggiunge buttoni alla scena
        root.getChildren().addAll(analizeButton,fileButton);


        Label label = new Label("Lines:");
        TextField textField = new TextField ();
        HBox hb = new HBox();
        hb.getChildren().addAll(label, textField);
        hb.setSpacing(10);
        root.getChildren().add(hb);


        scene.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        fileButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(primaryStage);
                filepath = file.getAbsolutePath();
            }
        });

        analizeButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                String path = filepath;
                int rows = Integer.parseInt(textField.getText());


                Rengine re=new Rengine(args, false, new TextConsole());
                try {
                    ReadMidiByPkg(re,path,rows);
                    re.end();
                    System.out.println("Premi un tasto per uscire");
                    readInput();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        // Dropping over surface
        scene.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    for (File file:db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        System.out.println(filePath);
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static final String pkgName = "provaPkg";
    public static final String pkgPath = "~/R/win-library/3.6/provaPkg";
    public static final String midiPath = "C:/Users/giova/Documents/try/sothis.mid";

    public static String readInput() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = reader.readLine();
        return name;
    }

    public static void installRPkg(Rengine re, String pkgName, String pkgPath) {
        re.eval("installPkg <- function(){ if (require(provaPkg) != TRUE){ "
                + "install.packages(\"" + pkgPath + "\", repos = NULL, type=\"source\");"
                + "}"
                + "else {require(" + pkgName + ");}"
                + "}");
        re.eval("installPkg()");

    }

    //nrows = the number of the rows that getNotesByMidi() return;
    public static void ReadMidiByPkg(Rengine re,String path,int rows) throws Exception {
        installRPkg(re,pkgName,pkgPath);

        re.eval("df <- getNotesByPath(\"" + path + "\"," + rows + ")");
    }



    public static void main(String[] args) {
        Application.launch(args);
    }
}
