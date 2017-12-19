package api.model;

/**
 * 我的评论
 *
 * @author yangshiyou
 */
public class MyComment {
//{"code":200,"result":[{"icon":"6","fullname":"gg","
// create_time":"1504667661","content":"\u653e\u653e\u6b4c","litpic":"\/Uploads\/Picture\/2017-09-01\/59a91794b2b60.jpg"}]}

    private int id;
    private int uid;
    private int sid;
    private int icon;
    private String fullname;
    private long create_time;
    private String content;
    private String litpic;
    private String uid_icon;
    private String sid_icon;

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

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getUid_icon() {
        return uid_icon;
    }

    public void setUid_icon(String uid_icon) {
        this.uid_icon = uid_icon;
    }

    public String getSid_icon() {
        return sid_icon;
    }

    public void setSid_icon(String sid_icon) {
        this.sid_icon = sid_icon;
    }
}
