package com.dsaab.poemlearner.view;

import java.util.Calendar;
import java.util.List;

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
    private Label tips;
    @FXML
    private Label exp;
    @FXML
    private Label latestStudyDate;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleAddToLearning() {
        mainApp.getCurrentUser().getLearning().add(song.getId());
        mainApp.getCurrentUser().getIlearning().add(0);
        mainApp.getCurrentUser().getAlllearning().add(song.getId());

        tips.setText("已加入学习列表");
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
        List<String> l = mainApp.getCurrentUser().getSongTagMap().get(song.getId());
        if(l != null){
            for(String s : l){
                str += " " + s;
            }
        }
        tag.setText(str);
        tag.setWrapText(true);

        exp.setText("熟练度 - " + mainApp.getCurrentUser().getExpById(song.getId()));

        Calendar day = mainApp.getCurrentUser().getSongDateMap().get(song.getId());
        if(day != null) {
            latestStudyDate.setText("最近学习日期: " + mainApp.getStringDate(day));
            //System.out.println(mainApp.getCurrentUser().getSongDateMap().toString());
            //mainApp.getCurrentUser().printStudyDate();
        } else {
            latestStudyDate.setText("最近学习日期: -");
        }
        
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setSong(Song song) {
        this.song = song;
        setContent();
    }
}