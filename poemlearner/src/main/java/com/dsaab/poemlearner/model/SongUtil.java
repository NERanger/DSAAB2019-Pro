package com.dsaab.poemlearner.model;

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
import java.util.LinkedList;

public class SongUtil {

    public static void parseJSONSongs(LinkedList<Song> list){
        //LinkedList<Song> list = new LinkedList<>();

        //System.out.println(System.getProperty("user.dir"));

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
                    list.add(parseSong(reader));
//                    System.out.println(list.getLast().getAuthor());
                }

                reader.endArray();
                in.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //return list;
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

    public static LinkedList<Song> tagSearch(LinkedList<Song> linkedList,String aimconv){
        String aim=HanLP.convertToTraditionalChinese(aimconv);
        LinkedList<Song> searchList=new LinkedList<>();
        for (Song song : linkedList) {
            if (song.tags != null && song.tags.contains(aim)) {
                searchList.add(song);
            }
 
        }
        return searchList;
    }

    public static LinkedList<Song> fileSearch(LinkedList<Song> linkedList, File file) throws IOException {
        LinkedList<Song> searchList = new LinkedList<>();
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String textLine;
        String str = "";
        while ((textLine = bf.readLine()) != null) {
            str = textLine;
            String[] numbs = str.split("\\s+");//0-num,10-title,11-parag,2-author
            String[] parag = numbs[1].split("[（）/]");
            String title = parag[0], para = parag[1], author = numbs[2];
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
        LinkedList<Song> searchList=new LinkedList<>();
        char ch=' ';
        String c=" ";
        for (int j = 0; j < aimconv.length(); j++) {
            ch=aimconv.charAt(j);
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
                else if("tags".equals(attrsong)&&jsonReader.peek()!= JsonToken.NULL)
                    s.makeTags(parseTags(jsonReader));
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
}