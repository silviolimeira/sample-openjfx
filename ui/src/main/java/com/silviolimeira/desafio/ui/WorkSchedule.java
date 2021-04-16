package com.silviolimeira.desafio.ui;

import com.silviolimeira.desafio.model.Periodo;
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
    private TableView<Periodo> table = new TableView<Periodo>();

    private ObservableList<Periodo> data =
            FXCollections.observableArrayList();

    public WorkSchedule() {

    }

    static class EditingCell extends TableCell<Periodo, String> {

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
        TableColumn<Periodo, Void> colBtn = new TableColumn("Button Column");

        Callback<TableColumn<Periodo, Void>, TableCell<Periodo, Void>> cellFactory =
                new Callback<TableColumn<Periodo, Void>, TableCell<Periodo, Void>>() {

            public TableCell<Periodo, Void> call(final TableColumn<Periodo, Void> param) {
                final TableCell<Periodo, Void> cell = new TableCell<Periodo, Void>() {

                    private final Button btn = new Button("Action");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Periodo data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data.getEntrada());
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
                new PropertyValueFactory<Periodo, String>("entrada"));
        firstNameCol.setCellFactory(cellFactory);
        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Periodo, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Periodo, String> t) {
                        ((Periodo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEntrada(t.getNewValue());
                    }
                }
        );


        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Periodo, String>("saida"));
        lastNameCol.setCellFactory(cellFactory);
        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Periodo, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Periodo, String> t) {
                        ((Periodo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setSaida(t.getNewValue());
                    }
                }
        );

        TableColumn remove = new TableColumn("Email");
        remove.setMinWidth(200);


        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol);
        addButtonToTable();

        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Last Name");

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new Periodo(
                        addFirstName.getText(),
                        addLastName.getText()
                        ));
                addFirstName.clear();
                addLastName.clear();
            }
        });

        final HBox hb = new HBox();
        hb.getChildren().addAll(addFirstName, addLastName, addButton);
        hb.setSpacing(3);


        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        vbox.setMaxHeight(200.0);

        return vbox;

    }

}
