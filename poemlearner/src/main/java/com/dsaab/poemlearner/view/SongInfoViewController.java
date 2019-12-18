package com.dsaab.poemlearner.view;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.Song;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SongInfoViewController {

    private MainApp mainApp;
    private Stage songInfoStage;
    private Song song;

    @FXML
    private Label title;
    @FXML
    private Label author;
    @FXML
    private Label para;
    @FXML
    private Label tag;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleAddToLearning() {

    }

    @FXML
    private void handleTagManage() {

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        this.songInfoStage = stage;
    }

    public void setSong(Song song) {
        this.song = song;

        this.title.setText(song.getTitle());
        this.author.setText(song.getAuthor());
        this.para.setText(song.getParagraph());
        this.para.setWrapText(true);
    }
}