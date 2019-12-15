package com.dsaab.poemlearner.view;

import java.util.LinkedList;
import java.util.List;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.AdvancedSearchConstrain;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AdvancedSearchViewController {

    private MainApp mainApp;
    private List<AdvancedSearchConstrain> constrainList;
    private int gpRowIndex;
    

    @FXML
    private ChoiceBox<String> firstCB;
    @FXML
    private TextField firstTF;
    @FXML
    private GridPane gridPane;

    @FXML
    private void initialize() {
        gpRowIndex = 0;
        constrainList = new LinkedList<AdvancedSearchConstrain>();

        AdvancedSearchConstrain constrain = new AdvancedSearchConstrain(null, firstCB, firstTF);
        constrainList.add(constrain);
    }

    @FXML
    private void handleAddRow() {
        HBox hb = new HBox();
        ChoiceBox<String> logicCB = new ChoiceBox<String>();
        ChoiceBox<String> fieldCB = new ChoiceBox<String>();
        Label label = new Label("包含");
        TextField tf = new TextField();

        AdvancedSearchConstrain constrain = new AdvancedSearchConstrain(logicCB, fieldCB, tf);
        constrainList.add(constrain);

        hb.getChildren().addAll(logicCB, fieldCB, label, tf);
        hb.setSpacing(20);

        gpRowIndex += 1;
        gridPane.setVgap(15);
        gridPane.addRow(gpRowIndex, hb);

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


}