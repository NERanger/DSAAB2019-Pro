package com.dsaab.poemlearner.model;

import java.io.Serializable;
import java.util.LinkedList;

public class User implements Serializable{

    private static final long serialVersionUID = 1L;


    private String userName;
    private String password;

    private LinkedList<String> learning;//存正在学习的诗歌的id
    private LinkedList<Integer> ilearning;//存正在学习的诗歌的熟练度
    private LinkedList<Song> alllearning;//存正在学习的诗

    private LinkedList<String> learned;
    private LinkedList<Integer> ilearned;
    private LinkedList<Song> alllearned;//存完成学习的诗歌
    private LinkedList<Song> tagsRecommend;//根据tags推荐诗


    private int plannew;//计划今日新学
    private int planreview;//计划今日复习


    public LinkedList<Song> getAlllearning() {
        return alllearning;
    }

    public void setAlllearning(LinkedList<Song> alllearning) {
        this.alllearning = alllearning;
    }

    public LinkedList<Song> getAlllearned() {
        return alllearned;
    }

    public void setAlllearned(LinkedList<Song> alllearned) {
        this.alllearned = alllearned;
    }


    public LinkedList<String> getLearning() {
        return learning;
    }

    public void setLearning(LinkedList<String> learning) {
        this.learning = learning;
    }

    public LinkedList<Integer> getIlearning() {
        return ilearning;
    }

    public void setIlearning(LinkedList<Integer> ilearning) {
        this.ilearning = ilearning;
    }

    public LinkedList<String> getLearned() {
        return learned;
    }

    public void setLearned(LinkedList<String> learned) {
        this.learned = learned;
    }

    public LinkedList<Integer> getIlearned() {
        return ilearned;
    }

    public void setIlearned(LinkedList<Integer> ilearned) {
        this.ilearned = ilearned;
    }

    public int getPlannew() {
        return plannew;
    }

    public void setPlannew(int plannew) {
        this.plannew = plannew;
    }

    public int getPlanreview() {
        return planreview;
    }

    public void setPlanreview(int planreview) {
        this.planreview = planreview;
    }

    public void setTagsRecommend(LinkedList<Song> tagsRecommend) {
        this.tagsRecommend = tagsRecommend;
    }

    public LinkedList<Song> getTagsRecommend() {
        return tagsRecommend;
    }

    public User() {
        this(null, null);
        learned=new LinkedList<>();
        learning=new LinkedList<>();
        ilearned=new LinkedList<>();
        ilearning=new LinkedList<>();
        alllearned=new LinkedList<>();
        alllearning=new LinkedList<>();
        plannew=10;
        planreview=10;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        learned=new LinkedList<>();
        learning=new LinkedList<>();
        ilearned=new LinkedList<>();
        ilearning=new LinkedList<>();
        alllearned=new LinkedList<>();
        alllearning=new LinkedList<>();
        plannew=10;
        planreview=10;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}