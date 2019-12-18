package com.dsaab.poemlearner.view;

import java.io.File;

import com.dsaab.poemlearner.MainApp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

public class FileSearchViewController {

    private MainApp mainApp;
    private File file;

    @FXML
    private Label filePath;
    @FXML
    private ListView<String> searchResult;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleChooseFile() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        this.file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        filePath.setText(file.getAbsolutePath());

    }

    @FXML
    private void handleSearch() {
        if (file != null) {
            //TODO
        }
    }

    @FXML
    private void handleBack() {
        this.mainApp.showSearchSelectionView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}