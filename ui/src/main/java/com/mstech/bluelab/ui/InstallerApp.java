package com.mstech.bluelab.ui;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

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
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Instalador");
//
//        //Image image = new Image(InstallerApp.class.getResourceAsStream("/images/bluelab-installer.png"));
//        Image image = new Image(InstallerApp.class.getResourceAsStream("/images/bluelab-installer.png"));
//
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
private TableView<Person> table = new TableView<Person>();
    private final ObservableList<Person> data =
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

    @Override
    public void start(Stage stage) {
        Image image = new Image(InstallerApp.class.getResourceAsStream("/images/bluelab-installer.png"));
        stage.getIcons().add(image);

        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(550);

        final Label label = new Label("Horário de Trabalho");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));
        firstNameCol.setCellFactory(cellFactory);
        firstNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());
                    }
                }
        );


        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));
        lastNameCol.setCellFactory(cellFactory);
        lastNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());
                    }
                }
        );

        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email"));
        emailCol.setCellFactory(cellFactory);
        emailCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmail(t.getNewValue());
                    }
                }
        );

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Last Name");
        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(emailCol.getPrefWidth());
        addEmail.setPromptText("Email");

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new Person(
                        addFirstName.getText(),
                        addLastName.getText(),
                        addEmail.getText()));
                addFirstName.clear();
                addLastName.clear();
                addEmail.clear();
            }
        });

        hb.getChildren().addAll(addFirstName, addLastName, addEmail, addButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public static class Person {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;

        private Person(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fName) {
            email.set(fName);
        }
    }

    class EditingCell extends TableCell<Person, String> {

        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                                    Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(textField.getText());
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}