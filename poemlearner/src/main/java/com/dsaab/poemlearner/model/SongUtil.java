package com.dsaab.poemlearner.model;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.hankcs.hanlp.HanLP;

import java.io.File;
import java.io.FileInputStream;
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
            String infile="src\\main\\java\\com\\dsaab\\poemlearner\\data\\json\\poet.song."+String.valueOf(i)+".json";
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

    private static ArrayList<String > parseTags(JsonReader jsonReader){
        ArrayList<String > p =new ArrayList<>();
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