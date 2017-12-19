package com.ysy15350.startcarunion.store_select.utils;

/**
 * Created by yangshiyou on 2017/9/5.
 */

public class SortModel {

    int id;

    boolean isSeleted;

    private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }
}
