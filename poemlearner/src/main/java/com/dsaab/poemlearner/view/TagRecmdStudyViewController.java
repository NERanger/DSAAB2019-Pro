package com.dsaab.poemlearner.view;

import java.util.List;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.Song;
import com.dsaab.poemlearner.model.SongUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TagRecmdStudyViewController {

    private MainApp mainApp;
    private List<Song> songList;
    private Song curSong;
    private int curIndex;

    @FXML
    private Label title;
    @FXML
    private Label author;
    @FXML
    private Label para;
    @FXML
    private Label tag;
    @FXML
    private Label progress;

    @FXML
    private void initialize() {
        curIndex = 0;
    }
    
    @FXML
    private void handleTagManage() {
        boolean okClicked = mainApp.showTagManageView(curSong);

        if(okClicked) {
            setContent();
        }
    }

    @FXML
    private void handleNext() {
        SongUtil.userStudyProceed(mainApp.getCurrentUser(), this.curSong, true);
        curIndex++;
        if(curIndex < songList.size()){
            setSong(songList.get(curIndex));
        } else {
            title.setText("");
            author.setText("");
            para.setText("推荐列表已完成");
        }
    }

    @FXML
    private void handleFinish() {
        SongUtil.userStudyProceed(mainApp.getCurrentUser(), this.curSong, false);
        curIndex++;
        if(curIndex < songList.size()){
            setSong(songList.get(curIndex));
        } else {
            title.setText("");
            author.setText("");
            para.setText("推荐列表已完成");
        }
    }

    @FXML
    private void handleBack() {
        mainApp.showStudySelectionView();
    }

    private void setContent() {
        title.setText(curSong.getTitle());
        title.setWrapText(true);
        author.setText(curSong.getAuthor());
        para.setText(curSong.getParagraph());
        para.setWrapText(true);

        String str = new String("标签");
        List<String> l = mainApp.getCurrentUser().getSongTagMap().get(curSong.getId());
        if(l != null){
            for(String s : l){
                str += " " + s;
            }
        }
        tag.setText(str);
        tag.setWrapText(true);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void setSong(Song song) {
        progress.setText("第" + (curIndex + 1) + "首, " + "共" + songList.size() + "首");
        this.curSong = song;
        setContent();
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
        setSong(songList.get(curIndex));
    }
}