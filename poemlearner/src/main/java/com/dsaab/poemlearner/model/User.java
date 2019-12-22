package com.dsaab.poemlearner.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    private String userName;
    private String password;

    private LinkedList<String> learning;//存正在学习的诗歌的id
    private LinkedList<Integer> ilearning;//存正在学习的诗歌的熟练度
    private LinkedList<String> alllearning;//存正在学习的诗

    private LinkedList<String> learned;
    private LinkedList<Integer> ilearned;
    private LinkedList<String> alllearned;//存完成学习的诗歌
    private LinkedList<String> tagsRecommend;//根据tags推荐诗

    private Map<String, List<String>> songTagMap;


    private int plannew;//计划今日新学
    private int planreview;//计划今日复习

    public LinkedList<String> getAlllearning() {
        return alllearning;
    }

    public void setAlllearning(LinkedList<String> alllearning) {
        this.alllearning = alllearning;
    }

    public LinkedList<String> getAlllearned() {
        return alllearned;
    }

    public void setAlllearned(LinkedList<String> alllearned) {
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

    public void setTagsRecommend(LinkedList<String> tagsRecommend) {
        this.tagsRecommend = tagsRecommend;
    }

    public LinkedList<String> getTagsRecommend() {
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
        songTagMap = new HashMap<String, List<String>>();
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
        songTagMap = new HashMap<String, List<String>>();
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

    public Map<String, List<String>> getSongTagMap() {
        return songTagMap;
    }

    public void setSongTagMap(Map<String, List<String>> songTagMap) {
        this.songTagMap = songTagMap;
    }

    public Integer getExpById(String id) {

        //System.out.println(this.learning);

        if(this.learning.size() != 0) {
            for(int i = 0; i < this.learning.size(); i++){
                if(this.learning.get(i).equals(id)){
                    return this.ilearning.get(i);
                }
            }
        }
        return 0;
    }

    public Integer getIndexById(List<String> idList, String id) {
        for(String str : idList){
            if(str.equals(id)){
                return idList.indexOf(str);
            }
        }
        return null;
    }
}