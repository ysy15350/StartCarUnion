package api.model;

import java.util.List;

public class MyFootprint {
    private String date;
    private int count;
    private List<StoreInfo> list;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<StoreInfo> getList() {
        return list;
    }

    public void setList(List<StoreInfo> list) {
        this.list = list;
    }
}
