package com.dsaab.poemlearner.view;

import java.io.File;
import java.net.MalformedURLException;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.SongUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ModeSelectionViewController {

    private MainApp mainApp;

    @FXML
    private Label info;
    @FXML
    private Label image;

    @FXML
    private void initialize() {
        File file = new File("src\\main\\java\\com\\dsaab\\poemlearner\\image\\background.jpg");
        try {
            Image im = new Image(file.toURI().toURL().toString());
            image.setGraphic(new ImageView(im));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        info.setWrapText(true);
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
        mainApp.showProgressView();
    }

    @FXML
    private void handleTagImport() {
        mainApp.showTagImportView();

    }

    @FXML
    private void handlePK() {
        mainApp.showPKView();
    }

    @FXML
    private void handleWordCloud() {
        mainApp.showWordCloudView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        info.setText(SongUtil.tangshi+"首唐诗 "+SongUtil.songshi+"首宋诗 "+SongUtil.tangshiren+"个唐诗人 "+ SongUtil.songshiren+"个宋诗人 "+mainApp.getSongList().size()+"首诗 "+ (SongUtil.songshiren+SongUtil.tangshiren)+"个诗人");
        info.setWrapText(true);
    }
}