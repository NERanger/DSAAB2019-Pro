package com.dsaab.poemlearner.model;

public enum FieldChoice {
    TITLE("题目", "Title", 0), AUTHOR("作者", "Author", 1), CONTENT("内容", "Paragraph", 2), TAG("标签", "Tags", 3), AND("与", "and", 4), OR("或", "or", 5);

    private String name;
    private String searchKey;
    private int index;

    private FieldChoice(String name, String searchKey, int index) {
        this.name = name;
        this.searchKey = searchKey;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public int getIndex() {
        return index;
    }

    static public String getKeyByName(String name) {
        for(FieldChoice fc : FieldChoice.values()){
            if(fc.getName().equals(name)) {
                return fc.getSearchKey();
            }
        }

        return null;
    }

}