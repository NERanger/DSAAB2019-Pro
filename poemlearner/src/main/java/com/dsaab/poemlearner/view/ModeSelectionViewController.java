package com.dsaab.poemlearner.view;

import com.dsaab.poemlearner.MainApp;

import javafx.fxml.FXML;

public class ModeSelectionViewController {

    private MainApp mainApp;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleSearch() {
        mainApp.showSearchSelectionView();
    }

    @FXML
    private void handleStudyTask() {

    }

    @FXML
    private void handleShowProgress() {

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}