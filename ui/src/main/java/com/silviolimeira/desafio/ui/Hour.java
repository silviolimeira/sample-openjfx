package com.silviolimeira.desafio.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Hour {

    private HBox hbox = new HBox();
    final TextField hh = new TextField();
    final TextField mm = new TextField();
    Boolean isValidHour = false;
    Boolean isValidMinute = false;

    public Hour() {
        super();
    }

    public HBox getComponent(String title) {

        final Label fieldTitle = new Label(title);
        fieldTitle.setFont(new Font("Arial", 16));

        //final TextField hh = new TextField();
        // force the field to be numeric only
        hh.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (hh.getText().length() > 2) {
                    hh.setText(hh.getText().substring(0, 2));
                }
                if (!newValue.matches("\\d*")) {
                    hh.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (hh.getText().length() > 0) {
                    if (Integer.parseInt("0" + hh.getText()) > 23) {
                        hh.setStyle("-fx-text-inner-color: red;");
                        isValidHour = false;
                    } else {
                        hh.setStyle("-fx-text-inner-color: black;");
                        isValidHour = true;
                    }
                }
            }
        });
        hh.setPromptText("");
        hh.setMaxWidth(30);

        final Label label = new Label(":");
        label.setFont(new Font("Arial", 20));

        //final TextField mm = new TextField();
        // force the field to be numeric only
        mm.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (mm.getText().length() > 2) {
                    mm.setText(mm.getText().substring(0, 2));
                }
                if (!newValue.matches("\\d*")) {
                    mm.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (mm.getText().length() > 0) {
                    if (Integer.parseInt("0" + mm.getText()) > 59) {
                        mm.setStyle("-fx-text-inner-color: red;");
                        isValidMinute = false;
                    } else {
                        mm.setStyle("-fx-text-inner-color: black;");
                        isValidMinute = true;
                    }
                }

            }
        });
        mm.setPromptText("");
        mm.setMaxWidth(30);


        hbox.getChildren().addAll(fieldTitle, hh, label, mm);
        hbox.setSpacing(3);

        return hbox;
    }

    public Boolean isValid() {
        Boolean cnd = isValidHour == true && isValidMinute == true;
        System.out.println(cnd);
        return (isValidHour == true && isValidMinute == true);
    }

    public String toString() {
        return String.format("%02d", Integer.parseInt(hh.getText()))
                + ":"
                + String.format("%02d", Integer.parseInt(mm.getText()));
    }

    public void clear() {
        hh.setText("");
        mm.setText("");
        hh.setStyle("-fx-text-inner-color: black;");
        mm.setStyle("-fx-text-inner-color: black;");
    }

    public void invalidate() {
        hh.setStyle("-fx-text-inner-color: red;");
        mm.setStyle("-fx-text-inner-color: red;");
    }

}
