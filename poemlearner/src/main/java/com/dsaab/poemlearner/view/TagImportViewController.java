package com.dsaab.poemlearner.view;

import java.io.File;
import java.io.IOException;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.SongUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class TagImportViewController {

    private MainApp mainApp;
    private File file;

    @FXML
    private Label filePath;
    @FXML
    private Label tips;

    @FXML
    private void initialize() {
        filePath.setWrapText(true);
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
    private void handleBack() {
        this.mainApp.showModeSelectionView();
    }

    @FXML
    private void handleImport() {
        if(file != null){
            try {
                SongUtil.fileTags(this.mainApp.getSongList(), this.mainApp.getCurrentUser(), this.file);
                tips.setText("标签已导入");
            } catch (IOException e) {
                e.printStackTrace();
                    
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("读取文件出错");
    
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("未选择文件");
            alert.setContentText("请先选择要加载的文件");

            alert.showAndWait();
        }
        
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}