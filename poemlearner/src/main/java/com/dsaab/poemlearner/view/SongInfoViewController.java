package com.dsaab.poemlearner.view;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.Song;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SongInfoViewController {

    private MainApp mainApp;
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
        boolean okClicked = mainApp.showTagManageView(song);

        if(okClicked) {
            setContent();
        }
    }

    private void setContent() {
        title.setText(song.getTitle());
        title.setWrapText(true);
        author.setText(song.getAuthor());
        para.setText(song.getParagraph());
        para.setWrapText(true);

        String str = new String("标签");
        for(String s : song.tags){
            str += " " + s;
        }
        tag.setText(str);
        tag.setWrapText(true);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setSong(Song song) {
        this.song = song;
        setContent();
    }
}