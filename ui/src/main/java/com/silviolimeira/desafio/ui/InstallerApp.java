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
import javafx.scene.control.ScrollPane;
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

    WorkSchedule horarioTrabalho;
    WorkSchedule marcacoes;
    WorkScheduleReport atraso;
    WorkScheduleReport horaExtra;
    CalculaHorarioDeTrabalho calculaHorarioDeTrabalho = new CalculaHorarioDeTrabalho();

    private boolean cancelTreads = false;

    private TableView<Periodo> table = new TableView<Periodo>();
    private final ObservableList<Periodo> data =
            FXCollections.observableArrayList();
    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    public Task taskSubtracaoEntreHorarios() {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                String workDir = System.getProperty("user.dir");
                System.out.println("User dir: " + workDir);
                int cnt = 0;
                while (!cancelTreads) {
                    System.out.print(".");
                    Thread.sleep(1000);
                    //System.out.println(horarioTrabalho.getTable().getItems().size());

                    if (horarioTrabalho.isUpdated() || marcacoes.isUpdated()) {
                        Platform.runLater(() -> {
                            try {
                                System.out.println("#");
                                horarioTrabalho.setUpdated(false);
                                marcacoes.setUpdated(false);

                                int max = horarioTrabalho.getTableAsList().size();
                                System.out.println("Horario Trabalho: ### " + max);

                                horaExtra.getTable().getItems().clear();
                                atraso.getTable().getItems().clear();

                                calculaHorarioDeTrabalho.calculaHoraExtraAtraso(horaExtra,atraso,horarioTrabalho,marcacoes);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    cnt++;
                    if (cnt > 10) {
                        cnt = 0;
                        horarioTrabalho.setUpdated(true);
                        marcacoes.setUpdated(true);
                        System.out.print("#");
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
        //stage.setWidth(800);
        //stage.setHeight(600);

        Group root = new Group();

        Scene scene = new Scene(root, 600, 600, Color.LIGHTGREY);

        BorderPane mainPane = new BorderPane();

        ScrollPane scrollPanePane = new ScrollPane();
        scrollPanePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPanePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPanePane.setPrefViewportWidth(580);
        scrollPanePane.setFitToWidth(true);
        scrollPanePane.setFitToHeight(true);

        scrollPanePane.setContent(mainPane);

        root.getChildren().add(scrollPanePane);

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