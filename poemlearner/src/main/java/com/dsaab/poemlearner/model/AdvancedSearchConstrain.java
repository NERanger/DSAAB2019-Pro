package com.dsaab.poemlearner.model;

import java.util.Arrays;
import java.util.List;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AdvancedSearchConstrain {
    private ChoiceBox<String> fieldChoiceBox;
    private ChoiceBox<String> logicChoiceBox;
    private TextField textField;

    public AdvancedSearchConstrain( ChoiceBox<String> logicChoiceBox, ChoiceBox<String> fieldChoiceBox, TextField textField) {
        List<String> fieldChoice = Arrays.asList(FieldChoice.TITLE.getName(), FieldChoice.AUTHOR.getName(), FieldChoice.CONTENT.getName(), FieldChoice.TAG.getName());
        fieldChoiceBox.getItems().addAll(fieldChoice);

        if(logicChoiceBox != null){
            List<String> logicChoice = Arrays.asList(FieldChoice.AND.getName(), FieldChoice.OR.getName());
            logicChoiceBox.getItems().addAll(logicChoice);
        }

        this.fieldChoiceBox = fieldChoiceBox;
        this.textField = textField;
        this.logicChoiceBox = logicChoiceBox;
    }

    public ChoiceBox<String> getFieldChoiceBox() {
        return fieldChoiceBox;
    }

    public void setFieldChoiceBox(ChoiceBox<String> fieldChoiceBox) {
        this.fieldChoiceBox = fieldChoiceBox;
    }

    public ChoiceBox<String> getLogicChoiceBox() {
        return logicChoiceBox;
    }

    public void setLogicChoiceBox(ChoiceBox<String> logicChoiceBox) {
        this.logicChoiceBox = logicChoiceBox;
    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }
}