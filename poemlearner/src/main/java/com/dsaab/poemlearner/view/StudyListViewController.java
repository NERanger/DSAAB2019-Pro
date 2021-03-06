package com.dsaab.poemlearner.view;

import java.util.LinkedList;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class StudyListViewController {

    private MainApp mainApp;
    private List<Song> songList;

    private final int ABSTRACT_PARA_BEGIN_INDEX = 0;
    private final int ABSTRACT_PARA_END_INDEX = 5;

    @FXML
    private ListView<HBox> studyList;
    @FXML
    private Label label;

    @FXML
    private void initialize() {
        songList = new LinkedList<Song>();
    }

    @FXML
    private void handleBack() {
        mainApp.showStudySelectionView();
    }

    @FXML
    private void handleCheckSongInfo() {
        int index = studyList.getSelectionModel().getSelectedIndex();
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

    public void generateSongList(int num) {

        studyList.getItems().clear();

        for(int i = 0; i < num; i++){
            Song song = SongUtil.randomGetSong(mainApp.getSongList());

            songList.add(song);

            try {
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

                studyList.getItems().add(hb);
            } catch (Exception e) {
                
            }

            
        }

        label.setText("共显示" + songList.size() + "首诗词");
        label.setWrapText(true);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}