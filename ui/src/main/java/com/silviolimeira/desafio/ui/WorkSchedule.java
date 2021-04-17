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

public class WorkSchedule {

    private VBox vbox = new VBox();
    private TableView<Periodo> table = new TableView<Periodo>();

    private ObservableList<Periodo> data =
            FXCollections.observableArrayList();
    private int maxLines = 3;

    public WorkSchedule() {

    }

    public ObservableList<Periodo> getData() {
        return this.data;
    }

    public TableView<Periodo> getTable() {
        return this.table;
    }

    private void addButtonToTable() {
        TableColumn<Periodo, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Periodo, Void>, TableCell<Periodo, Void>> cellFactory =
                new Callback<TableColumn<Periodo, Void>, TableCell<Periodo, Void>>() {

            public TableCell<Periodo, Void> call(final TableColumn<Periodo, Void> param) {
                final TableCell<Periodo, Void> cell = new TableCell<Periodo, Void>() {

                    private final Button btn = new Button("Deletar");

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

    public VBox getComponent(String title) {
        return this.getComponent(title, Integer.MAX_VALUE);
    }

    public VBox getComponent(String title, int maxLines) {

        this.maxLines = maxLines;

        final Label label = new Label(title);
        label.setFont(new Font("Arial", 20));

        table.setEditable(false);
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };

        TableColumn entradaCol = new TableColumn("Entrada");
        entradaCol.setMinWidth(100);
        entradaCol.setCellValueFactory(
                new PropertyValueFactory<Periodo, String>("entrada"));
        entradaCol.setCellFactory(cellFactory);
        entradaCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Periodo, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Periodo, String> t) {
                        ((Periodo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEntrada(t.getNewValue());
                    }
                }
        );


        TableColumn saidaCol = new TableColumn("Saída");
        saidaCol.setMinWidth(100);
        saidaCol.setCellValueFactory(
                new PropertyValueFactory<Periodo, String>("saida"));
        saidaCol.setCellFactory(cellFactory);
        saidaCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Periodo, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Periodo, String> t) {
                        ((Periodo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setSaida(t.getNewValue());
                    }
                }
        );

        table.setItems(data);
        table.getColumns().addAll(entradaCol, saidaCol);
        addButtonToTable();

        final TextField addEntrada = new TextField();
        // force the field to be numeric only
        addEntrada.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (addEntrada.getText().length() > 5) {
                    String s = addEntrada.getText().substring(0, 5);
                    addEntrada.setText(s);
                }
                if (!newValue.matches("\\d*")) {
                    addEntrada.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        addEntrada.setPromptText("Entrada");
        addEntrada.setMaxWidth(entradaCol.getPrefWidth());

        final TextField addSaida = new TextField();
        addSaida.setMaxWidth(saidaCol.getPrefWidth());
        addSaida.setPromptText("Saída");

        final Button addButton = new Button("Adicionar");

        //final HBox hb = new HBox();
        //hb.getChildren().addAll(addEntrada, addSaida, addButton);
        //hb.setSpacing(3);

        Hour entrada = new Hour();
        Hour saida = new Hour();
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (table.getItems().size() >= maxLines) return;
                if (entrada.isValid() && saida.isValid()) {
                    data.add(new Periodo(
                            entrada.toString(),
                            saida.toString()
                    ));
                    entrada.clear();
                    saida.clear();
                }
            }
        });

        final HBox hb = new HBox();
        hb.setSpacing(10);
        hb.getChildren().addAll(entrada.getComponent("Entrada:"), saida.getComponent("Saída:"), addButton);

        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        //vbox.getChildren().addAll(label, table, hb);
        vbox.getChildren().addAll(label, table, hb);

        vbox.setMaxHeight(200.0);

        return vbox;

    }

}
