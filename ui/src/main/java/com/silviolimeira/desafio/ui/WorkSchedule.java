package com.silviolimeira.desafio.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class WorkSchedule extends VBox {

    private VBox vbox = new VBox();
    private TableView<InstallerApp.Person> table = new TableView<InstallerApp.Person>();

    private ObservableList<InstallerApp.Person> data =
            FXCollections.observableArrayList();

    public WorkSchedule() {

    }

    static class EditingCell extends TableCell<InstallerApp.Person, String> {

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


    private void addButtonToTable() {
        TableColumn<InstallerApp.Person, Void> colBtn = new TableColumn("Button Column");

        Callback<TableColumn<InstallerApp.Person, Void>, TableCell<InstallerApp.Person, Void>> cellFactory =
                new Callback<TableColumn<InstallerApp.Person, Void>, TableCell<InstallerApp.Person, Void>>() {

            public TableCell<InstallerApp.Person, Void> call(final TableColumn<InstallerApp.Person, Void> param) {
                final TableCell<InstallerApp.Person, Void> cell = new TableCell<InstallerApp.Person, Void>() {

                    private final Button btn = new Button("Action");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            InstallerApp.Person data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data.getFirstName());
                            getTableView().getItems().remove(getIndex());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);

    }

    public VBox getInstance() {

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
                new PropertyValueFactory<InstallerApp.Person, String>("firstName"));
        firstNameCol.setCellFactory(cellFactory);
        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<InstallerApp.Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<InstallerApp.Person, String> t) {
                        ((InstallerApp.Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());
                    }
                }
        );


        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<InstallerApp.Person, String>("lastName"));
        lastNameCol.setCellFactory(cellFactory);
        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<InstallerApp.Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<InstallerApp.Person, String> t) {
                        ((InstallerApp.Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());
                    }
                }
        );

        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<InstallerApp.Person, String>("email"));
        emailCol.setCellFactory(cellFactory);
        emailCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<InstallerApp.Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<InstallerApp.Person, String> t) {
                        ((InstallerApp.Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmail(t.getNewValue());
                    }
                }
        );

        TableColumn remove = new TableColumn("Email");
        remove.setMinWidth(200);


        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        addButtonToTable();

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
                data.add(new InstallerApp.Person(
                        addFirstName.getText(),
                        addLastName.getText(),
                        addEmail.getText()));
                addFirstName.clear();
                addLastName.clear();
                addEmail.clear();
            }
        });

        final HBox hb = new HBox();
        hb.getChildren().addAll(addFirstName, addLastName, addEmail, addButton);
        hb.setSpacing(3);


        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        vbox.setMaxHeight(200.0);

        return vbox;

    }

}
