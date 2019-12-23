package com.dsaab.poemlearner.model;

import java.util.ArrayList;

public class Song {

    public ArrayList<String> addTags(String s) {
        tags.add(s);return tags;
    }

    public ArrayList<String> removeTags(String s) {
        for (int i = 0; i < tags.size(); i++)
            if (tags.get(i).equals(s)){
                tags.remove(i);
                System.out.println("remove done!");
            }
        return tags;
    }

    @Override
    public String toString() {
        return "song{" +
                "author='" + author + '\'' +
                ", paragraph=" + paragraph +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", rhythmic='" + rhythmic + '\'' +
                ", tags=" + tags +
                '}'+"\n";
    }

    private String author;
    private String paragraph;
    private String id;
    private String title;
    private String rhythmic;
    public ArrayList<String> tags;
    private int percent;

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public Song() {
        tags=new ArrayList<>();
        percent=0;
    }

    public ArrayList<String> makeTags(ArrayList<String> s) {
        tags=s;
        return tags;
    }


    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getRhythmic() {
        return rhythmic;
    }

    public void setRhythmic(String rhythmic) {
        this.rhythmic = rhythmic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
