package com.dsaab.poemlearner.view;

import java.util.List;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.Song;
import com.dsaab.poemlearner.model.SongUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RandomStudyViewController {
    
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
    private Label exp;

    @FXML
    private void initialize() {

    }
    
    @FXML
    private void handleTagManage() {
        boolean okClicked = mainApp.showTagManageView(song);

        if(okClicked) {
            setContent();
        }
    }

    @FXML
    private void handleNext() {
        SongUtil.userStudyProceed(mainApp.getCurrentUser(), this.song, true, mainApp.getCurrentDay());
        Song nextSong = SongUtil.randomStudyGetSong(mainApp.getSongList());

        mainApp.showStudyView(nextSong);
    }

    @FXML
    private void handleFinish() {
        SongUtil.userStudyProceed(mainApp.getCurrentUser(), this.song, false, mainApp.getCurrentDay());
        Song nextSong = SongUtil.randomStudyGetSong(mainApp.getSongList());

        mainApp.showStudyView(nextSong);
    }

    @FXML
    private void handleBack() {
        mainApp.showStudySelectionView();
    }

    private void setContent() {
        title.setText(song.getTitle());
        title.setWrapText(true);
        author.setText(song.getAuthor());
        para.setText(song.getParagraph());
        para.setWrapText(true);

        String str = new String("标签");
        List<String> l = mainApp.getCurrentUser().getSongTagMap().get(song.getId());
        if(l != null){
            for(String s : l){
                str += " " + s;
            }
        }
        tag.setText(str);
        tag.setWrapText(true);

        exp.setText("熟练度-" + mainApp.getCurrentUser().getExpById(song.getId()));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setSong(Song song) {
        this.song = song;
        setContent();
    }

}