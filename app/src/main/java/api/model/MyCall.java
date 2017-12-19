package api.model;

/**
 * 我的评论
 * 
 * @author yangshiyou
 *
 */
public class MyCall {
//{"code":200,"result":[{"icon":"6","fullname":"gg","
// create_time":"1504667661","content":"\u653e\u653e\u6b4c","litpic":"\/Uploads\/Picture\/2017-09-01\/59a91794b2b60.jpg"}]}

    private int icon;
    private String fullname;
    private String nickname;
    private long create_time;
    private String content;
    private String litpic;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
}
