package com.dsaab.poemlearner.view;

import java.util.List;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.Song;
import com.dsaab.poemlearner.model.SongUtil;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

//https://segmentfault.com/a/1190000011860499
public class EasySearchViewController {

    private MainApp mainApp;
    //private List<String> searchResult;
    //private List<HBox> searchResult;
    private List<Song> songList;

    private final int ABSTRACT_PARA_BEGIN_INDEX = 0;
    private final int ABSTRACT_PARA_END_INDEX = 5;

    @FXML
    private TextField searchBar;
    @FXML
    private ListView<HBox> resultList;

    @FXML
    private void initialize() {
        //searchResult = new LinkedList<String>();
        //searchResult = new LinkedList<HBox>();
    }

    @FXML
    private void handleSearch() {
        String target = searchBar.getText();
        songList = SongUtil.simpleSearch(mainApp.getSongList(), target);

        resultList.getItems().clear();

        try {
            for(Song song : songList) {
                //this.searchResult.add(song.getTitle() + " " + song.getAuthor() + " " + song.getParagraph());
                Label title = new Label(song.getTitle());
                title.setFont(new Font(30));
                Label author = new Label(song.getAuthor());
                author.setFont(new Font(20));
                Label paragraph = new Label(song.getParagraph().substring(this.ABSTRACT_PARA_BEGIN_INDEX, this.ABSTRACT_PARA_END_INDEX) + " ...");
                paragraph.setFont(new Font(15));
    
                HBox hb = new HBox();
                hb.setAlignment(Pos.BOTTOM_LEFT);
                hb.setPadding(new Insets(2,5,2,5));
                hb.setSpacing(15);
                hb.getChildren().addAll(title, author, paragraph);
    
                resultList.getItems().add(hb);
            }
        } catch (Exception e) {
            
        }

        
        
        //resultList.getItems().addAll(this.searchResult);
    }

    @FXML
    private void handleCheckSongInfo() {
        int index = resultList.getSelectionModel().getSelectedIndex();
        if(index >= 0){
            this.mainApp.showSongInfoView(this.songList.get(index));
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("未选中目标");
            alert.setContentText("请从列表中选中一个目标");

            alert.showAndWait();
        }
        
    }

    @FXML
    private void handleBack() {
        this.mainApp.showSearchSelectionView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}