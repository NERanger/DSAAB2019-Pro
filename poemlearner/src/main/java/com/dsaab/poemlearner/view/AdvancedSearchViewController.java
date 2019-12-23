package com.dsaab.poemlearner.view;

import java.util.LinkedList;
import java.util.List;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.AdvancedSearchConstrain;
import com.dsaab.poemlearner.model.FieldChoice;
import com.dsaab.poemlearner.model.Song;
import com.dsaab.poemlearner.model.SongUtil;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AdvancedSearchViewController {

    private MainApp mainApp;
    private List<AdvancedSearchConstrain> constrainList;
    private int gpRowIndex;
    private List<Song> songList;
    

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

    @FXML
    private void handleBack() {
        this.mainApp.showSearchSelectionView();
    }

    @FXML
    private void handleSearch() {
        //List<String> keywords = new ArrayList<String>();
        //List<Boolean> logic = new ArrayList<Boolean>();
        //List<String> field = new ArrayList<String>();

        int len = constrainList.size();

        String[] keywords = new String[len];
        String[] field = new String[len];
        Boolean[] logic = new Boolean[len - 1];

        for(int i = 0; i < len; i++) {
            //keywords.add(cons.getTextField().getText());
            keywords[i] = constrainList.get(i).getTextField().getText();
            //field.add(FieldChoice.getKeyByName(cons.getFieldChoiceBox().getSelectionModel().getSelectedItem()));
            field[i] = FieldChoice.getKeyByName(constrainList.get(i).getFieldChoiceBox().getSelectionModel().getSelectedItem());
            ChoiceBox<String> lcb = constrainList.get(i).getLogicChoiceBox();
            if(lcb != null){
                if(lcb.getSelectionModel().getSelectedItem().equals(FieldChoice.OR.getName())) {
                    logic[i - 1] = true;
                } else {
                    logic[i - 1] = false;
                }
            }
        }

        songList = SongUtil.keywordSearch(mainApp.getSongList(), keywords, logic, field);
        mainApp.showAdvancedSearchResultView(songList);

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


}