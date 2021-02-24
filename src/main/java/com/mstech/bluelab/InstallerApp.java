package com.mstech.bluelab;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * JavaFX App
 */
public class InstallerApp extends Application {

    int litros;
    int fileSize;
    double preciofinal;
    public Label precio = new Label();
    TextField txtlitros;
    //tu tarea hacer
    Task copyWorker;
    Task copyClientWorker;
    boolean cancelTreads = false;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Instalador");

        //Image image = new Image(InstallerApp.class.getResourceAsStream("/images/bluelab-installer.png"));
        Image image = new Image(InstallerApp.class.getResourceAsStream("/images/bluelab-installer.png"));

        primaryStage.getIcons().add(image);

        Group root = new Group();
        Scene scene = new Scene(root, 330, 120, Color.WHITE);

        BorderPane mainPane = new BorderPane();
        root.getChildren().add(mainPane);

        final Label label = new Label("Progresso");
        precio.setText("Progresso...");
        final ProgressBar progressBar = new ProgressBar(0);
        final HBox h1 = new HBox();
        h1.setSpacing(5);
        h1.setAlignment(Pos.CENTER);
        h1.getChildren().addAll(precio);
        mainPane.setTop(h1);


        final Label litroslbl = new Label("Litros de gasolina:");
        txtlitros = new TextField("");
        final Button startButton = new Button("Iniciar");
        final Button cancelButton = new Button("Cancelar");
        final HBox hb2 = new HBox();
        hb2.setSpacing(5);
        hb2.setAlignment(Pos.CENTER);
        //hb2.getChildren().addAll(litroslbl,txtlitros,startButton, cancelButton);
        hb2.getChildren().addAll(startButton, cancelButton);
        mainPane.setBottom(hb2);
        //mainPane.setTop(hb2);

        final HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        //hb.getChildren().addAll(label, progressBar);
        hb.getChildren().addAll(progressBar);
        mainPane.setCenter(hb);


        startButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                //limpias el diseño al empezar la acción del botón
                startButton.setDisable(true);
                progressBar.setProgress(0);
                cancelButton.setDisable(false);
                copyWorker = createWorker();

                progressBar.progressProperty().unbind();
                progressBar.progressProperty().bind(copyWorker.progressProperty());

                copyWorker.messageProperty().addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        System.out.println(newValue);

                    }
                });

                new Thread(copyWorker).start();
                preciofinal = Double.parseDouble(txtlitros.getText()) * 20;
                precio.setText("$" + preciofinal);

            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                startButton.setDisable(false);
                cancelButton.setDisable(true);
                copyWorker.cancel(true);

                cancelTreads = true;
                copyClientWorker.cancel(true);

                progressBar.progressProperty().unbind();
                progressBar.setProgress(0);
                System.out.println("cancelado");
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                copyClientWorker = copyClientWorker();
                Thread copyClientWorkerThread = new Thread(copyClientWorker);
                copyClientWorkerThread.start();

                synchronized(copyClientWorkerThread){
                    try{
                        System.out.println("Aguardando o b completar...");
                        copyClientWorkerThread.wait();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println("Terminado.");
                    updateProgress(1L, 1L);
                }

                return true;
            }
        };
    }

    public Task copyClientWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                String workDir = System.getProperty("user.dir");
                System.out.println("User dir: " + workDir);


//                System.out.println("Litros:"+litros);
//                for (int i = 0; i < (litros/2); i++) {
//                    Thread.sleep(1000);
////                    updateMessage("2000 milliseconds");
//                    updateProgress(i + 1, litros/2);
//                    System.out.println(i);
//
//                }

                //refs: https://www.baeldung.com/java-compress-and-uncompress

                File destDir = new File("E:\\Program Files\\mstech\\bluelab");

                byte[] buffer = new byte[1024];
                ZipInputStream zis = new
                        ZipInputStream(new FileInputStream(workDir + File.separator + "installer.zip"));
                ZipEntry zipEntry = zis.getNextEntry();
                while ((zipEntry != null) && (!cancelTreads)) {
                    File newFile = newFile(destDir, zipEntry);
                    if (zipEntry.isDirectory()) {
                        if (!newFile.isDirectory() && !newFile.mkdirs()) {
                            throw new IOException("Failed to create directory " + newFile);
                        }
                    } else {
                        // fix for Windows-created archives
                        File parent = newFile.getParentFile();
                        if (!parent.isDirectory() && !parent.mkdirs()) {
                            throw new IOException("Failed to create directory " + parent);
                        }

                        // write file content
                        FileOutputStream fos = new FileOutputStream(newFile);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                    }
                    zipEntry = zis.getNextEntry();
                }
                zis.closeEntry();
                zis.close();

                if (cancelTreads)
                    return false;

                return true;
            }
        };
    }


    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

}