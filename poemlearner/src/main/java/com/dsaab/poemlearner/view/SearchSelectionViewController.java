package com.dsaab.poemlearner.view;

import com.dsaab.poemlearner.MainApp;

import javafx.fxml.FXML;

public class SearchSelectionViewController {

    private MainApp mainApp;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleEasySearch() {
        mainApp.showEasySearchView();
    }

    @FXML
    private void handleAdvancedSearch() {
        mainApp.showAdvancedSearchView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    } 
}