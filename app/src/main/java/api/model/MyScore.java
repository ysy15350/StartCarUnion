package api.model;

/**
 * 我的评论
 *
 * @author yangshiyou
 */
public class MyScore {

//    {"code":200,"result":[{"id":"2","uid":"4","number":"10","create_time":"1504669729"}
// ,{"id":"1","uid":"4","number":"10","create_time":"1504511038"}]}

    private int id;
    private int uid;
    private int number;
    private int point;
    private long create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
}
