package com.dsaab.poemlearner.view;

import java.util.LinkedList;
import java.util.List;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.Song;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TagManageViewController {

    private boolean okClicked = false;
    private Song song;
    private Stage tagManageStage;
    private MainApp mainApp;

    @FXML
    private ListView<String> tagList;
    @FXML
    private TextField newTag;
    
    @FXML
    private void initialize() {

    }

    @FXML
    private void handleOK() {
        //song.tags.clear();
        //song.tags.addAll(tagList.getItems());
        List<String> userTagList = mainApp.getCurrentUser().getSongTagMap().get(song.getId());
        if(userTagList != null){
            userTagList.clear();
            userTagList.addAll(tagList.getItems());
        } else {
            List<String> list = new LinkedList<String>();
            list.addAll(tagList.getItems());
            mainApp.getCurrentUser().getSongTagMap().put(song.getId(), list);
        }
        
        okClicked = true;
        tagManageStage.close();

    }

    @FXML
    private void handleCancel() {
        tagManageStage.close();
    }

    @FXML
    private void handleAddNewTag() {
        String str = newTag.getText();
        if(str != null && str != ""){
            tagList.getItems().add(str);
        }
    }

    @FXML
    private void handleDelete() {
        int index = tagList.getSelectionModel().getSelectedIndex();

        if(index >= 0){
            tagList.getItems().remove(index);
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("未选中目标");
            alert.setContentText("请从列表中选中一个目标");

            alert.showAndWait();
        }
    }

    public void setTagManageStage(Stage tagManageStage) {
        this.tagManageStage = tagManageStage;
    }

    public void setSong(Song song) {
        this.song = song;

        List<String> l = mainApp.getCurrentUser().getSongTagMap().get(song.getId());
        if(l != null){
                tagList.getItems().addAll(l);
        } else {
            tagList.getItems().clear();
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}