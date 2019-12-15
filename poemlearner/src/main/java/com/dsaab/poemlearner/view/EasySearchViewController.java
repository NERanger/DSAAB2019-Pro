package com.dsaab.poemlearner.view;

import java.util.LinkedList;
import java.util.List;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.Song;
import com.dsaab.poemlearner.model.SongUtil;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

//https://segmentfault.com/a/1190000011860499
public class EasySearchViewController {

    private MainApp mainApp;
    private List<String> searchResult;

    @FXML
    private TextField searchBar;
    @FXML
    private ListView<String> resultList;

    @FXML
    private void initialize() {
        searchResult = new LinkedList<String>();
    }

    @FXML
    private void handleSearch() {
        String target = searchBar.getText();
        List<Song> songList = SongUtil.simpleSearch(mainApp.getSongList(), target);

        for(Song song : songList) {
            this.searchResult.add(song.getTitle() + " " + song.getAuthor() + " " + song.getParagraph());
        }
        resultList.getItems().clear();
        resultList.getItems().addAll(this.searchResult);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}