package com.silviolimeira.desafio.ui;

import com.silviolimeira.desafio.business.CalculaHorarioDeTrabalho;
import com.silviolimeira.desafio.model.Periodo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * JavaFX App
 */
public class InstallerApp extends Application {

//    int litros;
//    int fileSize;
//    double preciofinal;
//    public Label precio = new Label();
//    TextField txtlitros;
//    //tu tarea hacer
//    Task copyWorker;
//    Task copyClientWorker;
//    boolean cancelTreads = false;
//
//
//    public static void main(String[] args) {
//        Application.launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStageprimaryStage) {
//        primaryStage.setTitle("Instalador");
//
//        //Image image = new Image(InstallerApp.class.getResourceAsStream("/images/logo.png"));
//        Image image = new Image(InstallerApp.class.getResourceAsStream("/images/logo.png"));
//        primaryStage.getIcons().add(image);
//
//        Group root = new Group();
//        Scene scene = new Scene(root, 330, 120, Color.WHITE);
//
//        BorderPane mainPane = new BorderPane();
//        root.getChildren().add(mainPane);
//
//        final Label label = new Label("Progresso");
//        precio.setText("Progresso...");
//        final ProgressBar progressBar = new ProgressBar(0);
//        final HBox h1 = new HBox();
//        h1.setSpacing(5);
//        h1.setAlignment(Pos.CENTER);
//        h1.getChildren().addAll(precio);
//        mainPane.setTop(h1);
//
//
//        final Label litroslbl = new Label("Litros de gasolina:");
//        txtlitros = new TextField("");
//        final Button startButton = new Button("Iniciar");
//        final Button cancelButton = new Button("Cancelar");
//        final HBox hb2 = new HBox();
//        hb2.setSpacing(5);
//        hb2.setAlignment(Pos.CENTER);
//        //hb2.getChildren().addAll(litroslbl,txtlitros,startButton, cancelButton);
//        hb2.getChildren().addAll(startButton, cancelButton);
//        mainPane.setBottom(hb2);
//        //mainPane.setTop(hb2);
//
//        final HBox hb = new HBox();
//        hb.setSpacing(5);
//        hb.setAlignment(Pos.CENTER);
//        //hb.getChildren().addAll(label, progressBar);
//        hb.getChildren().addAll(progressBar);
//        mainPane.setCenter(hb);
//
//
//        startButton.setOnAction(new EventHandler<ActionEvent>() {
//
//            public void handle(ActionEvent event) {
//                //limpias el diseño al empezar la acción del botón
//                startButton.setDisable(true);
//                progressBar.setProgress(0);
//                cancelButton.setDisable(false);
//                copyWorker = createWorker();
//
//                progressBar.progressProperty().unbind();
//                progressBar.progressProperty().bind(copyWorker.progressProperty());
//
//                copyWorker.messageProperty().addListener(new ChangeListener<String>() {
//                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                        System.out.println(newValue);
//
//                    }
//                });
//
//                new Thread(copyWorker).start();
//                preciofinal = Double.parseDouble(txtlitros.getText()) * 20;
//                precio.setText("$" + preciofinal);
//
//            }
//        });
//        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent event) {
//                startButton.setDisable(false);
//                cancelButton.setDisable(true);
//                copyWorker.cancel(true);
//
//                cancelTreads = true;
//                copyClientWorker.cancel(true);
//
//                progressBar.progressProperty().unbind();
//                progressBar.setProgress(0);
//                System.out.println("cancelado");
//            }
//        });
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public Task createWorker() {
//        return new Task() {
//            @Override
//            protected Object call() throws Exception {
//
//                copyClientWorker = copyClientWorker();
//                Thread copyClientWorkerThread = new Thread(copyClientWorker);
//                copyClientWorkerThread.start();
//
//                synchronized(copyClientWorkerThread){
//                    try{
//                        System.out.println("Aguardando o b completar...");
//                        copyClientWorkerThread.wait();
//                    }catch(InterruptedException e){
//                        e.printStackTrace();
//                    }
//                    System.out.println("Terminado.");
//                    updateProgress(1L, 1L);
//                }
//
//                Tools tools = new Tools();
//                tools.hello();
//
//                return true;
//            }
//        };
//    }
//
//    public Task copyClientWorker() {
//        return new Task() {
//            @Override
//            protected Object call() throws Exception {
//
//                String workDir = System.getProperty("user.dir");
//                System.out.println("User dir: " + workDir);
//
//
////                System.out.println("Litros:"+litros);
////                for (int i = 0; i < (litros/2); i++) {
////                    Thread.sleep(1000);
//////                    updateMessage("2000 milliseconds");
////                    updateProgress(i + 1, litros/2);
////                    System.out.println(i);
////
////                }
//
//                //refs: https://www.baeldung.com/java-compress-and-uncompress
//
//                File destDir = new File("E:\\Program Files\\mstech\\bluelab");
//
//                byte[] buffer = new byte[1024];
//                ZipInputStream zis = new
//                        ZipInputStream(new FileInputStream(workDir + File.separator + "installer.zip"));
//                ZipEntry zipEntry = zis.getNextEntry();
//                while ((zipEntry != null) && (!cancelTreads)) {
//                    File newFile = newFile(destDir, zipEntry);
//                    if (zipEntry.isDirectory()) {
//                        if (!newFile.isDirectory() && !newFile.mkdirs()) {
//                            throw new IOException("Failed to create directory " + newFile);
//                        }
//                    } else {
//                        // fix for Windows-created archives
//                        File parent = newFile.getParentFile();
//                        if (!parent.isDirectory() && !parent.mkdirs()) {
//                            throw new IOException("Failed to create directory " + parent);
//                        }
//
//                        // write file content
//                        FileOutputStream fos = new FileOutputStream(newFile);
//                        int len;
//                        while ((len = zis.read(buffer)) > 0) {
//                            fos.write(buffer, 0, len);
//                        }
//                        fos.close();
//                    }
//                    zipEntry = zis.getNextEntry();
//                }
//                zis.closeEntry();
//                zis.close();
//
//                if (cancelTreads)
//                    return false;
//
//                return true;
//            }
//        };
//    }
//
//
//    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
//        File destFile = new File(destinationDir, zipEntry.getName());
//
//        String destDirPath = destinationDir.getCanonicalPath();
//        String destFilePath = destFile.getCanonicalPath();
//
//        if (!destFilePath.startsWith(destDirPath + File.separator)) {
//            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
//        }
//
//        return destFile;
//    }

    WorkSchedule horarioTrabalho;
    WorkSchedule marcacoes;
    WorkScheduleReport atraso;
    WorkScheduleReport horaExtra;
    CalculaHorarioDeTrabalho calculaHorarioDeTrabalho = new CalculaHorarioDeTrabalho();

    private boolean cancelTreads = false;

    private TableView<Periodo> table = new TableView<Periodo>();
    private final ObservableList<Periodo> data =
            FXCollections.observableArrayList(
//                    new Person("Jacob", "Smith", "jacob.smith@example.com"),
//                    new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
//                    new Person("Ethan", "Williams", "ethan.williams@example.com"),
//                    new Person("Emma", "Jones", "emma.jones@example.com"),
//                    new Person("Michael", "Brown", "michael.brown@example.com")
            );
    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

//    public void subtracaoEntreHorarios(WorkSchedule horarioTrabalho, WorkSchedule marcacoes, WorkScheduleReport atraso, WorkScheduleReport horaExtra) {
//
////        for (Periodo s : horarioTrabalho.getTableAsList()) {
////            System.out.println(">" + s.toString());
////        }
//        Periodo p1 = null;
//        Periodo p2 = null;
//        Periodo p3 = null;
//        //System.out.println("***" + horarioTrabalho.getTable().getItems().size());
//        if (horarioTrabalho.getTable().getItems().size() >=1) {
//            p1 = horarioTrabalho.getTable().getItems().get(0);
//            //System.out.println("p1:" + p1.toString());
//        }
//        if (horarioTrabalho.getTable().getItems().size() >=2) {
//            p2 = horarioTrabalho.getTable().getItems().get(1);
//            //System.out.println("p2:" + p2.toString());
//        }
//        if (horarioTrabalho.getTable().getItems().size() ==3) {
//            p3 = horarioTrabalho.getTable().getItems().get(2);
//            //System.out.println("p3:" + p3.toString());
//        }
//
//
//        int max = horarioTrabalho.getTable().getItems().size();
//        for (int i = 0; i < max; i++) {
//            Periodo periodo = (Periodo)horarioTrabalho.getTable().getItems().get(i);
//            //horaExtra.getTable().getItems().add(txt);
//            horaExtra.getTable().getItems().add(periodo);
//
//            int maxMarcacoes = marcacoes.getTable().getItems().size();
//            for (int n = 0; n < maxMarcacoes; n++) {
//                Periodo marcacao = (Periodo)marcacoes.getTable().getItems().get(i);
//                horaExtra.getTable().getItems().add(marcacao);
//
//            }
//
//        }
//
//    }

    public Task taskSubtracaoEntreHorarios() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                String workDir = System.getProperty("user.dir");
                System.out.println("User dir: " + workDir);

                while (!cancelTreads) {
                    System.out.print(".");
                    Thread.sleep(1000);
                    //System.out.println(horarioTrabalho.getTable().getItems().size());

                    if (horarioTrabalho.isUpdated() || marcacoes.isUpdated()) {
                        System.out.println("#");
                        horarioTrabalho.setUpdated(false);
                        marcacoes.setUpdated(false);

                        int max = horarioTrabalho.getTableAsList().size();
                        System.out.println("Horario Trabalho: ### " + max);

//                        calculaHorarioDeTrabalho.subtracaoEntreHorarios(
//                                horarioTrabalho,
//                                marcacoes,
//                                atraso,
//                                horaExtra
//                        );

                        calculaHorarioDeTrabalho.calcPeriodosHoraExtra(horarioTrabalho.getData());

                        horaExtra.getTable().getItems().clear();
                        calculaHorarioDeTrabalho.calculaHoraExtraAtraso(horaExtra,atraso,horarioTrabalho,marcacoes);
                        //subtracaoEntreHorarios(horarioTrabalho, marcacoes, atraso, horaExtra);
                        //horaExtra.getTable().getItems().add(new Periodo("00:00", "12:22"));
                        //System.out.println(horarioTrabalho.getTableAsList().add(new Periodo("00:00", "12:22")));
                    }

                }

                if (cancelTreads)
                    return false;

                return true;
            }
        };
    }


    @Override
    public void start(Stage stage) {
        Image image = new Image(InstallerApp.class.getResourceAsStream("/images/logo.png"));
        stage.getIcons().add(image);
        stage.setTitle("Desafio");
        stage.setWidth(800);
        stage.setHeight(600);

        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);

        BorderPane mainPane = new BorderPane();
        root.getChildren().add(mainPane);

        horarioTrabalho = new WorkSchedule();
        marcacoes = new WorkSchedule();
        atraso = new WorkScheduleReport();
        horaExtra = new WorkScheduleReport();

        mainPane.setTop(horarioTrabalho.getComponent("Horário de Trabalho", 3));
        mainPane.setCenter(marcacoes.getComponent("Marcações Feitas"));

        final HBox hb = new HBox();
        hb.getChildren().addAll(atraso.getComponent("Atraso"), horaExtra.getComponent("Hora Extra"));
        hb.setSpacing(3);
        mainPane.setBottom(hb);

        //((Group) scene.getRoot()).getChildren().addAll(workSchedule.getInstance(), workSchedule1.getInstance());
        //System.out.println("subtracao");

        new Thread(taskSubtracaoEntreHorarios()).start();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                cancelTreads = true;
                Platform.exit();
                System.exit(0);
            }
        });

        stage.setScene(scene);
        stage.show();
    }

}