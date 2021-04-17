package com.silviolimeira.desafio.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Hour extends HBox {

    private HBox hbox = new HBox();

    public Hour() {

    }

    public HBox getInstance(String title) {

        final Label fieldTitle = new Label(title);
        fieldTitle.setFont(new Font("Arial", 16));

        final TextField hh = new TextField();
        // force the field to be numeric only
        hh.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (hh.getText().length() > 2) {
                    String s = hh.getText().substring(0, 2);
                    hh.setText(s);
                }
                if (!newValue.matches("\\d*")) {
                    hh.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        hh.setPromptText("");
        hh.setMaxWidth(30);

        final Label label = new Label(":");
        label.setFont(new Font("Arial", 20));

        final TextField mm = new TextField();
        // force the field to be numeric only
        mm.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (mm.getText().length() > 2) {
                    String s = mm.getText().substring(0, 2);
                    mm.setText(s);
                }
                if (!newValue.matches("\\d*")) {
                    mm.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        mm.setPromptText("");
        mm.setMaxWidth(30);


        hbox.getChildren().addAll(fieldTitle, hh, label, mm);
        hbox.setSpacing(3);

        return hbox;
    }

}
