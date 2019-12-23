package com.dsaab.poemlearner.view;

import java.io.File;
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
import javafx.stage.FileChooser;

public class FileSearchViewController {

    private MainApp mainApp;
    private File file;
    private List<Song> songList;

    private final int ABSTRACT_PARA_BEGIN_INDEX = 0;
    private final int ABSTRACT_PARA_END_INDEX = 5;

    @FXML
    private Label filePath;
    @FXML
    private ListView<HBox> searchResult;

    @FXML
    private void initialize() {
        filePath.setWrapText(true);
    }

    @FXML
    private void handleChooseFile() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        this.file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        filePath.setText(file.getAbsolutePath());

    }

    @FXML
    private void handleSearch() {
        if (file != null) {
            try {
                songList = SongUtil.fileSearch(mainApp.getSongList(), this.file);

                searchResult.getItems().clear();

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

                    searchResult.getItems().add(hb);
                }
            } catch (Exception e) {
                //e.printStackTrace();
                
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("读取文件出错");

                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("未选择文件");
            alert.setContentText("请先选择要加载的文件");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleBack() {
        this.mainApp.showSearchSelectionView();
    }

    @FXML
    private void handleCheckSongInfo() {
        int index = searchResult.getSelectionModel().getSelectedIndex();
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

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}