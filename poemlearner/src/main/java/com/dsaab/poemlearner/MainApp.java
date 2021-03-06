package com.dsaab.poemlearner;

import com.dsaab.poemlearner.model.Song;
import com.dsaab.poemlearner.model.SongUtil;
import com.dsaab.poemlearner.model.User;
import com.dsaab.poemlearner.model.UserListWrapper;
import com.dsaab.poemlearner.view.AdvancedSearchResultViewController;
import com.dsaab.poemlearner.view.AdvancedSearchViewController;
import com.dsaab.poemlearner.view.AuthorSearchViewController;
import com.dsaab.poemlearner.view.EasySearchViewController;
import com.dsaab.poemlearner.view.FileSearchViewController;
import com.dsaab.poemlearner.view.FuzzySearchViewController;
import com.dsaab.poemlearner.view.LoginController;
import com.dsaab.poemlearner.view.ModeSelectionViewController;
import com.dsaab.poemlearner.view.PKViewController;
import com.dsaab.poemlearner.view.ProgressViewController;
import com.dsaab.poemlearner.view.RandomRestudyViewController;
import com.dsaab.poemlearner.view.RandomStudyViewController;
import com.dsaab.poemlearner.view.SearchSelectionViewController;
import com.dsaab.poemlearner.view.SongInfoViewController;
import com.dsaab.poemlearner.view.StudyListViewController;
import com.dsaab.poemlearner.view.StudySelectionViewController;
import com.dsaab.poemlearner.view.TagImportViewController;
import com.dsaab.poemlearner.view.TagManageViewController;
import com.dsaab.poemlearner.view.TagRecmdStudyViewController;
import com.dsaab.poemlearner.view.TagSearchViewController;
import com.dsaab.poemlearner.view.WordCloudViewController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private List<User> userData = new ArrayList<User>();
    private final String userDataFilePath = "src\\main\\java\\com\\dsaab\\poemlearner\\data\\userData";
    private File userDataFile;
    private LinkedList<Song> songList;
    private User currentUser;
    //private String currentDay="20191201";
    private SimpleDateFormat format_day = new SimpleDateFormat("yyyyMMdd");
    private Calendar currentDay = Calendar.getInstance();;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        SongUtil.setMainApp(this);
        loadSongData();

        setUserDataFilePath(this.userDataFilePath);
        loadUserData();

        initRootLayout();
        showLogin();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
    }

    public String getStringDate(Calendar day){
        return format_day.format(day.getTime());
    }

    public void setDay(String day) throws ParseException {
        this.currentDay.setTime(format_day.parse(day));
    }

    public Calendar getCurrentDay() {
        return this.currentDay;
    }

    private void loadSongData() {
        System.out.print("Loading song data...");
        songList = new LinkedList<Song>();
        SongUtil.parseJSONSongs(songList);
        System.out.print("Done\n");
        //System.out.println(songList == null);

    }

    public LinkedList<Song> getSongList() {
        return this.songList;
    }

    public List<User> getUserData() {
        return this.userData;
    }

    private void setUserDataFilePath(String filePath) {
        this.userDataFile = new File(filePath);
    }

    private void loadUserData() {
        if(this.userDataFile.exists()) {
            this.loadUserDataFromFile(this.userDataFile);
        }else {
            try {
                this.userDataFile.createNewFile();
                this.userData.add(new User("admin", "admin"));
                this.userData.add(new User("a", "a"));
                this.userData.add(new User("b", "b"));

                Random ran = new Random();

                for(User user : this.userData) {
                    this.setCurrentUser(user);
                    for(int i = 0; i < 100; i++){
                        String day = randomDate();
                        setDay(day);
                        Song s = SongUtil.randomStudyGetSong(this.songList);
                        boolean b = ran.nextBoolean();
                        SongUtil.userStudyProceed(user, s, b, this.currentDay);
                        System.out.println(user.getUserName() + " " + s.getId() + " " + b + this.getStringDate(this.currentDay));
                        user.addTag(s.getId(), b ? "学习中":"已完成");
                    }
                }
                

                this.saveUserDataToFile(this.userDataFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String randomDate(){
        Random rnd=new Random();

        String monthStr = new String();
        String dayStr = new String();

        int year=rnd.nextInt(18)+2010;  //生成[2000,2017]的整数；年
        int month=rnd.nextInt(12)+1;   //生成[1,12]的整数；月 
        int day=rnd.nextInt(30)+1;       //生成[1,30)的整数；日

        if(month < 10) {
            monthStr = "0" + month;
        }else{
            monthStr = Integer.toString(month);
        }
        
        if(day < 10) {
            dayStr = "0" + day;
        }else{
            dayStr = Integer.toString(day);
        }
        return year+monthStr+dayStr;
    }

    public boolean isWordCloudExsit() {
        File image = new File("src\\main\\java\\com\\dsaab\\poemlearner\\image\\Worldcloud.png");
        return image.exists();
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showWordCloudView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/WordCloudView.fxml"));
            AnchorPane WordCloudView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(WordCloudView);

            WordCloudViewController controller = loader.getController();
            if(!isWordCloudExsit()){
                List<String> list = new LinkedList<String>();
                list.addAll(this.getCurrentUser().getLearning());
                list.addAll(this.getCurrentUser().getLearned());
                SongUtil.generateWordCloud(this.getCurrentUser().getLearning());
            }
            
            controller.setMainApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPKView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PKView.fxml"));
            AnchorPane PKView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(PKView);

            PKViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProgressView.fxml"));
            AnchorPane ProgressView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(ProgressView);

            ProgressViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showTagImportView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TagImportView.fxml"));
            AnchorPane TagImportView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(TagImportView);

            TagImportViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRandomRestudyView(Song song) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RandomRestudyView.fxml"));
            AnchorPane RandomRestudyView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(RandomRestudyView);

            RandomRestudyViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setSong(song);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showStudyListView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/StudyListView.fxml"));
            AnchorPane StudyListView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(StudyListView);

            StudyListViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.generateSongList(150);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showTagRecmdStudyView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TagRecmdStudyView.fxml"));
            AnchorPane TagRecmdStudyView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(TagRecmdStudyView);

            TagRecmdStudyViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setSongList(SongUtil.tagRecommend(this.getSongList(), this.getCurrentUser(), 10));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showStudyView(Song song) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RandomStudyView.fxml"));
            AnchorPane RandomStudyView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(RandomStudyView);

            RandomStudyViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setSong(song);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showStudySelectionView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/StudySelectionView.fxml"));
            AnchorPane StudySelectionView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(StudySelectionView);

            StudySelectionViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean showTagManageView(Song song) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TagManageView.fxml"));
            AnchorPane TagManageView = (AnchorPane) loader.load();
            
            Stage tagManageStage = new Stage();
            tagManageStage.setTitle("Detailed Information");
            tagManageStage.initModality(Modality.WINDOW_MODAL);
            tagManageStage.initOwner(primaryStage);

            Scene scene = new Scene(TagManageView);
            tagManageStage.setScene(scene);

            TagManageViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setTagManageStage(tagManageStage);
            controller.setSong(song);
            

            tagManageStage.show();

            return controller.isOkClicked();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showAdvancedSearchResultView(List<Song> songList) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AdvancedSearchResultView.fxml"));
            AnchorPane AdvancedSearchResultView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setRight(AdvancedSearchResultView);

            AdvancedSearchResultViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setSongList(songList);
            controller.setResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSongInfoView(Song song) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SongInfoView.fxml"));
            AnchorPane SongInfoView = (AnchorPane) loader.load();
            
            Stage songInfoStage = new Stage();
            songInfoStage.setTitle("Detailed Information");
            songInfoStage.initModality(Modality.WINDOW_MODAL);
            songInfoStage.initOwner(primaryStage);

            Scene scene = new Scene(SongInfoView);
            songInfoStage.setScene(scene);

            SongInfoViewController controller = loader.getController();
            controller.setMainApp(this);
            //controller.setStage(songInfoStage);
            controller.setSong(song);

            songInfoStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showTagSearchView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TagSearchView.fxml"));
            AnchorPane TagSearchView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(TagSearchView);

            TagSearchViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showFuzzySearchView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FuzzySearchView.fxml"));
            AnchorPane FuzzySearchView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(FuzzySearchView);

            FuzzySearchViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showFileSearchView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FileSearchView.fxml"));
            AnchorPane FileSearchView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(FileSearchView);

            FileSearchViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAuthorSearchView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AuthorSearchView.fxml"));
            AnchorPane AuthorSearchView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(AuthorSearchView);

            AuthorSearchViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAdvancedSearchView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AdvancedSearchView.fxml"));
            AnchorPane AdvancedSearchView = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(AdvancedSearchView);

            AdvancedSearchViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLogin() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Login.fxml"));
            AnchorPane Login = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(Login);

            LoginController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showModeSelectionView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ModeSelectionView.fxml"));
            AnchorPane ModeSelectionView = (AnchorPane) loader.load();
            
            rootLayout.setCenter(ModeSelectionView);

            ModeSelectionViewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSearchSelectionView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SearchSelectionView.fxml"));
            AnchorPane SearchSelectionView = (AnchorPane) loader.load();
            
            rootLayout.setCenter(SearchSelectionView);

            SearchSelectionViewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEasySearchView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EasySearchView.fxml"));
            AnchorPane EasySearchView = (AnchorPane) loader.load();
            
            rootLayout.setCenter(EasySearchView);

            EasySearchViewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //reference: https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    public void loadUserDataFromFile(File file) {
        try {
            FileInputStream fi = new FileInputStream(file);
			ObjectInputStream oi = new ObjectInputStream(fi);
    
            // Reading XML from the file and unmarshalling.
            UserListWrapper wrapper = (UserListWrapper) oi.readObject();
    
            userData.clear();
            userData.addAll(wrapper.getUsers());

            //System.out.println(wrapper.getUsers().toString());

            oi.close();
			fi.close();
    
            // Save the file path to the registry.
            setPersonFilePath(file);
    
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    //reference: https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    public void saveUserDataToFile(File file) {
        try {
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
    
            // Wrapping our person data.
            UserListWrapper wrapper = new UserListWrapper();
            wrapper.setUsers(userData);

            o.writeObject(wrapper);

            o.close();
            f.close();
    
            // Save the file path to the registry.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception

            e.printStackTrace();

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
    
            // Update the stage title.
            primaryStage.setTitle("MainApp - " + file.getName());
        } else {
            prefs.remove("filePath");
    
            // Update the stage title.
            primaryStage.setTitle("MainApp");
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void clearFileContent(File file) {
        try {
            if(file.exists()) {
                FileWriter fileWriter =new FileWriter(file);
                fileWriter.write("");     
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    } 

    @Override
    public void stop(){
        System.out.print("App is closing, Saving user data...");
        this.clearFileContent(this.userDataFile);

        this.saveUserDataToFile(this.userDataFile);

        System.out.print("Done\n");
    }
}