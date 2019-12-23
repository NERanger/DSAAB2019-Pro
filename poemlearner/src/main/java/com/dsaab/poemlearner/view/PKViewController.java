package com.dsaab.poemlearner.view;

import java.util.LinkedList;
import java.util.List;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.SongUtil;
import com.dsaab.poemlearner.model.User;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class PKViewController {

    private MainApp mainApp;
    //private List<String> searchResult;
    //private List<HBox> searchResult;
    private List<User> rankList;

    @FXML
    private ListView<HBox> resultList;

    @FXML
    private void initialize() {
        rankList = new LinkedList<User>();
    }

    private void setContent() {
        //songList = mainApp.getCurrentUser().getLearning();
        rankList = SongUtil.ranked_users(this.mainApp.getUserData(), 1);

        resultList.getItems().clear();

        // Label rankTitle = new Label("排名");
        // Label nameTitle = new Label("用户名");
        // Label learningTitle = new Label("正在学习");
        // Label learnedTitle = new Label("已学习");
        // HBox title = new HBox();
        // title.setAlignment(Pos.BOTTOM_LEFT);
        // title.setPadding(new Insets(2,5,2,5));
        // title.setSpacing(15);
        // title.getChildren().addAll(rankTitle, nameTitle, learningTitle, learnedTitle);

        // resultList.getItems().add(title);

        for(User user : rankList) {
            //this.searchResult.add(song.getTitle() + " " + song.getAuthor() + " " + song.getParagraph());
            Label rank = new Label("排名：" + Integer.toString(rankList.indexOf(user)));
            //rank.setFont(new Font(30));
            Label name = new Label("用户名：" + user.getUserName());
            //name.setFont(new Font(20));
            //Label paragraph = new Label(song.getParagraph().substring(this.ABSTRACT_PARA_BEGIN_INDEX, this.ABSTRACT_PARA_END_INDEX) + " ...");
            //paragraph.setFont(new Font(15));
            Label learning = new Label("正在学习：" + user.getLearning().size());
            Label learned = new Label("已学习：" + user.getLearned().size());

            HBox hb = new HBox();
            hb.setAlignment(Pos.BOTTOM_LEFT);
            hb.setPadding(new Insets(2,5,2,5));
            hb.setSpacing(15);
            hb.getChildren().addAll(rank, name, learning, learned);

            resultList.getItems().add(hb);
        }
        
        //resultList.getItems().addAll(this.searchResult);
    }

    @FXML
    private void handleBack() {
        this.mainApp.showModeSelectionView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        setContent();
    }
}