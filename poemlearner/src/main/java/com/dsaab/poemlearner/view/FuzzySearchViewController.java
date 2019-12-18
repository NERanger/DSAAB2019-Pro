package com.dsaab.poemlearner.view;

import com.dsaab.poemlearner.MainApp;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FuzzySearchViewController {

    private MainApp mainApp;
    //private List<String> searchResult;

    @FXML
    private TextField searchBar;
    @FXML
    private ListView<String> resultList;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleSearch() {
        //TODO
    }

    @FXML
    private void handleBack() {
        this.mainApp.showSearchSelectionView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}