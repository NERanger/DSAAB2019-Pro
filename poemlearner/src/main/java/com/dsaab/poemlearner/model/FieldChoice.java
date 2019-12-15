package com.dsaab.poemlearner.model;

public enum FieldChoice {
    TITLE("题目", 0), AUTHOR("作者", 1), CONTENT("内容", 2), AND("与", 3), OR("或", 4);

    private String name;
    private int index;

    private FieldChoice(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

}