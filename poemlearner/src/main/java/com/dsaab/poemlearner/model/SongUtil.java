package com.dsaab.poemlearner.model;

import com.dsaab.poemlearner.MainApp;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.hankcs.hanlp.HanLP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SongUtil {

    public static int tangshi=57100,tangshiren=0,songshi=254100,songshiren=0;
    public static String  stangshiren="",ssongshiren="";
    public static MainApp mainApp;

    public static void setMainApp(MainApp main) {
        mainApp = main;
    }

    public static void parseJSONSongs(LinkedList<Song> list){
        //LinkedList<Song> list = new LinkedList<>();

        //System.out.println(System.getProperty("user.dir"));
        Song s = new Song();

        for (int i = 1000; i < 204000; i+=1000) {
            String infile="src\\main\\java\\com\\dsaab\\poemlearner\\data\\json\\poet.song." + String.valueOf(i) + ".json";
            System.out.println(infile);
            
            //InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(infile);
            //System.out.println(is == null);
            try {
                File f = new File(infile);
                InputStream is = new FileInputStream(f);

                InputStreamReader in = new InputStreamReader(is, "utf-8");
                JsonReader reader = new JsonReader(in);
                reader.beginArray();

                while (reader.hasNext()) {
                    s = parseSong(reader);
                    list.add(s);
                    if(!(s.getAuthor().equals(ssongshiren))) {
                        songshiren++;
                    }
                    ssongshiren=s.getAuthor();
//                    System.out.println(list.getLast().getAuthor());
                }

                reader.endArray();
                in.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        for (int i = 1000; i < 57100; i+=1000) {
            String infile="src\\main\\java\\com\\dsaab\\poemlearner\\data\\json\\poet.tang." + String.valueOf(i) + ".json";
            System.out.println(infile);
            
            //InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(infile);
            //System.out.println(is == null);
            try {
                File f = new File(infile);
                InputStream is = new FileInputStream(f);

                InputStreamReader in = new InputStreamReader(is, "utf-8");
                JsonReader reader = new JsonReader(in);
                reader.beginArray();

                while (reader.hasNext()) {
                    s = parseSong(reader);
                    list.add(s);
                    if(!(s.getAuthor().equals(stangshiren))){
                        tangshiren++;
                    }
                    stangshiren=s.getAuthor();
//                    System.out.println(list.getLast().getAuthor());
                }

                reader.endArray();
                in.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static LinkedList<Song> keywordSearch(LinkedList<Song> linkedList, String[] keywords, Boolean[] or,String[] field) {
        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = HanLP.convertToTraditionalChinese(keywords[i]);
        }
        LinkedList<LinkedList<String>> str = new LinkedList<>();
        LinkedList<String> str1 = new LinkedList<>();
        str1.add(keywords[0]);
        str.add(str1);
        LinkedList<LinkedList<String>> str_f = new LinkedList<>();
        LinkedList<String> str1_f = new LinkedList<>();
        str1_f.add(field[0]);
        str_f.add(str1_f);
 
        int jie = 0;
        for (int i = 0; i < or.length; i++) {
            LinkedList<String> str_temp = new LinkedList<>();
            LinkedList<String> str_temp_f = new LinkedList<>();
            if (or[i]) {
                str_temp.add(keywords[i + 1]);
                str_temp_f.add(field[i + 1]);
                str.add(str_temp);
                str_f.add(str_temp_f);
                jie++;
            } else {
                str.get(jie).add(keywords[i + 1]);
                str_f.get(jie).add(field[i + 1]);
            }
        }
        LinkedList<LinkedList<Song>> result_temp = new LinkedList<>();
        for (int k=0;k<str.size();k++) {
            LinkedList<Song> temp = judgeSort(linkedList, str.get(k).get(0),str_f.get(k).get(0));
            if (str.get(k).size() != 1) {
                for (int i = 1; i < str.get(k).size(); i++) {
                    temp = judgeSort(temp, str.get(k).get(i),str_f.get(k).get(i));
                }
            }
            result_temp.add(temp);
        }
        LinkedList<Song> result = new LinkedList<>();
        for(LinkedList<Song> te : result_temp){
            result.addAll(te);
        }
        return result;
    }

    public static LinkedList<Song> judgeSort(LinkedList<Song> linkedList, String aimconv,String type) {
        String aim = HanLP.convertToTraditionalChinese(aimconv);
        LinkedList<Song> searchList = new LinkedList<>();
        if (type.equals("Author"))
        for (Song song : linkedList) {
            if (song.getAuthor() != null && song.getAuthor().contains(aim)) {
                searchList.add(song);
            }
 
        }
        if (type.equals("Rythmic"))
            for (Song song : linkedList) {
 
                if (song.getRhythmic() != null && song.getRhythmic().contains(aim)) {
                    searchList.add(song);
                }
 
            }
        if (type.equals("Title"))
            for (Song song : linkedList) {
 
                if (song.getTitle() != null && song.getTitle().contains(aim)) {
                    searchList.add(song);
                }
 
            }
        if (type.equals("Paragraph"))
            for (Song song : linkedList) {
 
                if (song.getParagraph() != null && song.getParagraph().contains(aim)) {
                    searchList.add(song);
                }
 
            }
        if (type.equals("Tags"))
            for (Song song : linkedList) {
 
                if (song.tags != null && song.tags.contains(aim)) {
                    searchList.add(song);
                }
            }
 
        return searchList;
    }

    public static LinkedList<Song> tagSearch(LinkedList<Song> linkedList, User user, String aimconv){
        //User's own input does not need convert
        //String aim=HanLP.convertToTraditionalChinese(aimconv);

        LinkedList<Song> searchList=new LinkedList<>();

        // for (Song song : linkedList) {
        //     if (song.tags != null && song.tags.contains(aim)) {
        //         searchList.add(song);
        //     }
        // }

        for(Map.Entry<String, List<String>> entry : user.getSongTagMap().entrySet()){
            for(String str : entry.getValue()){
                if(str.contains(aimconv)){
                    Song song = getSongById(linkedList, entry.getKey());
                    if(song != null){
                        searchList.add(song);
                    }
                }
            }
        }
        
        return searchList;
    }

    public static LinkedList<Song> fileSearch(LinkedList<Song> linkedList, File file) throws IOException {
        LinkedList<Song> searchList = new LinkedList<>();
        InputStreamReader is = new InputStreamReader(new FileInputStream(file), "utf-8");
        BufferedReader bf = new BufferedReader(is);
        String textLine;
        String str = "";
        while ((textLine = bf.readLine()) != null) {
            
            str = HanLP.convertToTraditionalChinese(textLine);
            String[] numbs = str.split("\\s+");//0-num,10-title,11-parag,2-author
            String[] parag = numbs[1].split("[()/]");
            String title = parag[0], para = parag[1], author = numbs[2];
            System.out.println(title + para + author);
//            out.println(title + para + author);
//            out.flush();
            for (Song tmp : linkedList) {
                if (tmp.getAuthor() != null && tmp.getAuthor().contains(author)
                        && tmp.getTitle() != null && tmp.getTitle().contains(title)
                        && tmp.getParagraph() != null && tmp.getParagraph().contains(para)) {
                    searchList.add(tmp);
                }
            }
        }
        bf.close();
        return searchList;
    }

    public static LinkedList<Song> fuzzySearch(LinkedList<Song> linkedList,String aimconv){
        String aim = HanLP.convertToTraditionalChinese(aimconv);
        LinkedList<Song> searchList=new LinkedList<>();
        char ch=' ';
        String c=" ";
        for (int j = 0; j < aim.length(); j++) {
            ch=aim.charAt(j);
            c=String.valueOf(ch);
//            System.out.println(c);
            for (Song song : linkedList) {
                if (song.getAuthor() != null && song.getAuthor().contains(c)) {
                    searchList.add(song);
                    continue;
                }
                if (song.getId() != null && song.getId().contains(c)) {
                    searchList.add(song);
                    continue;
                }
                if (song.getRhythmic() != null && song.getRhythmic().contains(c)) {
                    searchList.add(song);
                    continue;
                }
                if (song.getTitle() != null && song.getTitle().contains(c)) {
                    searchList.add(song);
                    continue;
                }
                if (song.getParagraph() != null && song.getParagraph().contains(c)) {
                    searchList.add(song);
                    continue;
                }
                if (song.tags != null && song.tags.contains(c)) {
                    searchList.add(song);
                    continue;
                }
            }
        }
        return searchList;
    }

    public static LinkedList<Song> authorSearch(LinkedList<Song> linkedList,String aimconv){
        String aim=HanLP.convertToTraditionalChinese(aimconv);
        LinkedList<Song> searchList=new LinkedList<>();
        for (Song song : linkedList) {
            if (song.getAuthor() != null && song.getAuthor().contains(aim)) {
                searchList.add(song);
            }

        }
        return searchList;
    }

    public static LinkedList<Song> simpleSearch(LinkedList<Song> linkedList, String aimconv) {
        String aim = HanLP.convertToTraditionalChinese(aimconv);
        LinkedList<Song> searchList = new LinkedList<>();
        for (Song song : linkedList) {
            if (song.getAuthor() != null && song.getAuthor().contains(aim)) {
                searchList.add(song);
            }
            if (song.getId() != null && song.getId().contains(aim)) {
                searchList.add(song);
            }
            if (song.getRhythmic() != null && song.getRhythmic().contains(aim)) {
                searchList.add(song);
            }
            if (song.getTitle() != null && song.getTitle().contains(aim)) {
                searchList.add(song);
            }
            if (song.getParagraph() != null && song.getParagraph().contains(aim)) {
                searchList.add(song);
            }
            if (song.tags != null && song.tags.contains(aim)) {
                searchList.add(song);
            }
        }
        return searchList;
    }


    private static Song parseSong(JsonReader jsonReader)throws IOException{
        Song s=new Song();
        try {
            jsonReader.beginObject();

            while(jsonReader.hasNext()){
                String attrsong=jsonReader.nextName();
                if ("title".equals(attrsong))s.setTitle(jsonReader.nextString());
                else if("paragraphs".equals(attrsong)&&jsonReader.peek()!= JsonToken.NULL)
                    s.setParagraph(parseParagraph(jsonReader));
                else if("id".equals(attrsong))s.setId(jsonReader.nextString());
                else if("tags".equals(attrsong)&&jsonReader.peek()!= JsonToken.NULL){
                    List<String> tagList = parseTags(jsonReader);
                    for(User user : mainApp.getUserData()){
                        for(String tag : tagList) {
                            user.addTag(s.getId(), tag);
                        }
                        
                    }
                }
                    //s.makeTags(parseTags(jsonReader));
                else if("author".equals(attrsong))s.setAuthor(jsonReader.nextString());
                else if("rhythmic".equals(attrsong))s.setRhythmic(jsonReader.nextString());
                else jsonReader.skipValue();
            }

            jsonReader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }


    private static String parseParagraph(JsonReader jsonReader){
        String p ="" ;
        try {
            jsonReader.beginArray();

            while(jsonReader.hasNext()){

                p+=jsonReader.nextString();
            }

            jsonReader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p;
    }

    private static ArrayList<String> parseTags(JsonReader jsonReader){
        ArrayList<String> p = new ArrayList<>();
        try {
            jsonReader.beginArray();

            while(jsonReader.hasNext()){
                p.add(jsonReader.nextString());
            }

            jsonReader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p;
    }

    public static Song getSongById(List<Song> linkedList, String id) {
        for(Song song : linkedList){
            if(song.getId().equals(id)){
                return song;
            }
        }
        return null;
    }

    public static Song randomStudyGetSong(LinkedList<Song> linkedList) {
        //int i=0;
        Random r=new Random();
        int num;
        Song s;
        while (true) {
            num=r.nextInt(linkedList.size());
             s= linkedList.get(num);
            if (mainApp.getCurrentUser().getExpById(s.getId()) == 0) {
                break;
            }
        }
        return s;
    }

    public static Song randomGetSong(LinkedList<Song> linkedList) {
        Random r=new Random();
        int num;
        Song s;
        num=r.nextInt(linkedList.size());
        s= linkedList.get(num);
        return s;
    }

    //两个按钮：true即继续学习（加入复习列表），false即完成学习（不再出现）
    public static void userStudyProceed(User kevin,Song s,boolean continue_Or_finish, Calendar day) {

        Random rand = new Random();

        if (continue_Or_finish) {
            kevin.updateDateMap(s.getId(), day);
            //学习列表存入id，熟练度，整首诗歌
            kevin.getLearning().add(s.getId());
            kevin.getIlearning().add(rand.nextInt(25));
            kevin.getAlllearning().add(s.getId());
        }
        else {
            kevin.updateDateMap(s.getId(), day);
            //完成列表存入id，熟练度，整首诗歌
            kevin.getIlearned().add(100);
            kevin.getLearned().add(s.getId());
            kevin.getAlllearned().add(s.getId());
        }
    }

    //根据tags推荐诗词，搜索已学习诗词中的tags,找到有相同tags的诗，并输出规定数量的诗
    //若根据上述tags搜索推荐的诗未达到目标数量，用随机搜素补全
    //输入：poems(诗库) u(用户) number(推荐诗词的目标数量)
    //输出：推荐诗词（LinkedList）
    public static LinkedList<Song> tagRecommend(LinkedList<Song> poems,User u, int number){

        LinkedList<Song> poems_learned = new LinkedList<Song>();
        for(String str : u.getAlllearned()){
            poems_learned.add(getSongById(poems, str));
        }

        
        LinkedList<Song> poems_recommend = new LinkedList<>();
        LinkedList<String>  tags= new LinkedList<>();
        System.out.print("Tags:");
        for(Song poem_temp:poems_learned){
            if(poem_temp.tags!=null){
                for(String tag_temp:poem_temp.tags){
                    if(!tags.contains(tag_temp)){
                        tags.add(tag_temp);
                        System.out.print(" " + tag_temp);
                    }
                }
            }
        }
        System.out.println();


        int numberOfrecommend=0;
        for(Song poem_tem:poems){
            if(poem_tem.tags!=null){
                for(String tag_temp:poem_tem.tags){
                    if(tags.contains(tag_temp)){
                        if(!poems_learned.contains(poem_tem)){
                            poems_recommend.add(poem_tem);
                            numberOfrecommend++;
                        }
                        break;
                    }
                }
            }
            if(numberOfrecommend>=number){
                break;
            }
        }

        if(numberOfrecommend<number){
            System.out.println("已学习诗词中，包含的Tags过少，不足以生成目标数量推荐诗词！");
            System.out.println("已用随机推荐补全推荐诗词");
        }

        while(numberOfrecommend<number){
            Song poem_temps=randomStudyGetSong(poems);
            if(!poems_recommend.contains(poem_temps)){
                poems_recommend.add(poem_temps);
                numberOfrecommend++;
            }
        }
        return poems_recommend;
    }

    public static Song randomRestudyGetSong(User kevin, LinkedList<Song> linkedList) {
        Random r=new Random();
        if(kevin.getLearning().size() != 0){
            int num=r.nextInt(kevin.getLearning().size());
            String id = kevin.getLearning().get(num);
            Song ks= getSongById(linkedList, id);
            return ks;
        }

        return null;
        
    }

    public static void randomRestudyUserProceed(User kevin, Song ks, boolean continue_Or_finish, Calendar day) {
        if (continue_Or_finish){
            //熟练度+10
            //ks.setPercent(ks.getPercent()+10);
            kevin.setExpById(ks.getId(), kevin.getExpById(ks.getId()) + 10);
            kevin.updateDateMap(ks.getId(), day);
            //加满则移除
            if (kevin.getExpById(ks.getId())>=100){
                kevin.getIlearned().add(100);
                kevin.getLearned().add(ks.getId());
                kevin.getAlllearned().add(ks.getId());
                kevin.getLearning().remove(ks.getId());
                kevin.getIlearning().remove(kevin.getIndexById(kevin.getLearning(), ks.getId()));
                kevin.getAlllearning().remove(ks.getId());
            }
        }
        else {
            kevin.updateDateMap(ks.getId(), day);
            //移出学习列表，加入完成列表
            kevin.getIlearned().add(100);
            kevin.getLearned().add(ks.getId());
            kevin.getLearning().remove(ks.getId());
            kevin.getIlearning().remove(kevin.getIndexById(kevin.getLearning(), ks.getId()));
            kevin.getAlllearning().remove(ks.getId());
            kevin.getAlllearned().add(ks.getId());

        }
    }

    public static void fileTags(LinkedList<Song> linkedList, User user,File file) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String textLine;
        String str = "";
        while ((textLine = bf.readLine()) != null) {
            str = textLine;
            String[] numbs = str.split("\\s+");//0-num,10-title,11-parag,2-author
            String[] parag = numbs[1].split("\\（|\\）|/");
            String title=parag[0],para=parag[1],author=numbs[2];
            for (Song song : linkedList) {
                if (song.getAuthor() != null && song.getAuthor().contains(author)
                        && song.getTitle() != null && song.getTitle().contains(title)
                        && song.getParagraph() != null && song.getParagraph().contains(para)) {
                            user.addTag(song.getId(), file.getName());
                }
            }
        }
        bf.close();
    }

    public static List<User> ranked_users(List<User> userList,int k) {
        LinkedList<User> users = new LinkedList<User>();
        users.addAll(userList);
        LinkedList<User> r_users=new LinkedList<>();
        switch (k) {
            case 1:
                rankrefresh(users,r_users,1);

            case 2:
                rankrefresh(users,r_users,2);
            case 3:
                rankrefresh(users,r_users,3);
        }
        LinkedList<User> ranked_users=new LinkedList<>();
        while(!r_users.isEmpty()){
            ranked_users.add(r_users.getLast());
            r_users.removeLast();
        }

        return  ranked_users;
   }

    public static void rankrefresh(List<User> users,List<User> new_users,int k ){
        if(users.isEmpty()){
            return;
        }
        int a;
        int i;
        switch (k) {
            case 1:
                a = Integer.MAX_VALUE;
                i = 0;
                if(users.isEmpty()){break;}
                for (int j = 0; j < users.size(); j++) {
                    if (users.get(j).number_alllearned() <= a) {
                        a = users.get(j).number_alllearned();
                        i = j;
                    }
                }

                users.get(i).rank_learned = users.size();
                new_users.add(users.get(i));
                users.remove(i);
                rankrefresh(users, new_users,k);
            case 2:
                a = Integer.MAX_VALUE;
                i = 0;
                if(users.isEmpty()){break;}
                for (int j = 0; j < users.size(); j++) {
                    if (users.get(j).number_alllearning() <= a) {
                        a = users.get(j).number_alllearning();
                        i = j;
                    }
                }
                users.get(i).rank_learning = users.size();
                new_users.add(users.get(i));
                users.remove(i);
                rankrefresh(users, new_users,k);
            case 3:
                a = Integer.MAX_VALUE;
                i = 0;
                if(users.isEmpty()){break;}
                for (int j = 0; j < users.size(); j++) {
                    if (users.get(j).number_todaylearned() <= a) {
                        a = users.get(j).number_todaylearned();
                        i = j;
                    }
                }
                users.get(i).rank_todaylearned = users.size();
                new_users.add(users.get(i));
                users.remove(i);
                rankrefresh(users, new_users,k);
        }
    }

    private static List<Song> idListToSongList(List<String> idList, List<Song> songList) {
        List<Song> list = new LinkedList<Song>();

        for(String id : idList){
            list.add(getSongById(songList, id));
        }

        return list;
    }

    public static void generateWordCloud(List<String> idList) {
        List<Song> songList = idListToSongList(idList, mainApp.getSongList());

        Map<String, Integer> map = new LinkedHashMap<>();
        for (Song p : songList)
                map.put(p.getAuthor(), map.getOrDefault(p.getAuthor(), mainApp.getCurrentUser().getExpById(p.getId())) + 1);

        Worldcloud worldcloud = new Worldcloud();
        worldcloud.wordcloud(map);
        worldcloud.save_img("src\\main\\java\\com\\dsaab\\poemlearner\\image\\Worldcloud.png");
    }
        
}