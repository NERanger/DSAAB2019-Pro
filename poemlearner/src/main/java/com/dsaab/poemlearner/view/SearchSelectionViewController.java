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

    @FXML
    private void handleBack() {
        mainApp.showModeSelectionView();
    }

    @FXML
    private void handleAuthorSearch() {
        mainApp.showAuthorSearchView();
    }

    @FXML
    private void handleFileSearch() {
        mainApp.showFileSearchView();
    }

    @FXML
    private void handleFuzzySearch() {
        mainApp.showFuzzySearchView();
    }

    @FXML
    private void handleTagSearch() {
        mainApp.showTagSearchView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    } 
}