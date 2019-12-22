package com.dsaab.poemlearner.view;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.SongUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ModeSelectionViewController {

    private MainApp mainApp;

    @FXML
    private Label info;

    @FXML
    private void initialize() {
        
    }

    @FXML
    private void handleSearch() {
        mainApp.showSearchSelectionView();
    }

    @FXML
    private void handleStudyTask() {
        mainApp.showStudySelectionView();
    }

    @FXML
    private void handleShowProgress() {

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        info.setText(SongUtil.tangshi+"首唐诗 "+SongUtil.songshi+"首宋诗 "+SongUtil.tangshiren+"个唐诗人 "+ SongUtil.songshiren+"个宋诗人 "+mainApp.getSongList().size()+"首诗 "+ (SongUtil.songshiren+SongUtil.tangshiren)+"个诗人");
        info.setWrapText(true);
    }
}