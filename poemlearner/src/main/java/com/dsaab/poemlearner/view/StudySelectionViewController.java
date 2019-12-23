package com.dsaab.poemlearner.view;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.SongUtil;

import javafx.fxml.FXML;

public class StudySelectionViewController {
    
    private MainApp mainApp;

    @FXML
    private void handleRandomStudy() {
        mainApp.showStudyView(SongUtil.randomStudyGetSong(mainApp.getSongList()));
    }

    @FXML
    private void handleTagRecmd() {
        mainApp.showTagRecmdStudyView();
    }

    @FXML
    private void handleRandomRestudy() {
        mainApp.showRandomRestudyView(SongUtil.randomRestudyGetSong(mainApp.getCurrentUser(), mainApp.getSongList()));
    }

    @FXML
    private void handleBack() {
        mainApp.showModeSelectionView();
    }

    @FXML
    private void handleStudyList() {
        mainApp.showStudyListView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}