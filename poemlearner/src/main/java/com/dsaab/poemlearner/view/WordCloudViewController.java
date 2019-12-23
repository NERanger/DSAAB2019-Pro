package com.dsaab.poemlearner.view;

import java.io.File;
import java.net.MalformedURLException;

import com.dsaab.poemlearner.MainApp;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WordCloudViewController {
    private MainApp mainApp;
    private Image image;

    @FXML
    ImageView imgv;

    @FXML
    private void initialize() {
        File file = new File("src\\main\\java\\com\\dsaab\\poemlearner\\image\\Worldcloud.png");
        try {
            image = new Image(file.toURI().toURL().toString());
            imgv.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        this.mainApp.showModeSelectionView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}