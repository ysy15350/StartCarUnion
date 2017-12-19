package api.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangshiyou on 2017/9/4.
 */

public class StoreInfo implements Serializable {

    //	{"code":200,"result":{"id":"4","fullname":"gg","ad_img":"","content":"","tell":"","address":"ggg","qq":"","email":null,"atlas":"","pid":"0"}}

    private String date;
    private int count;

    private int id;
    private String fullname;
    private String litpic;
    private String ad_img;
    private List<String> ad_img_litpic;
    private String icon;
    private String mobile;
    private String content;
    private String tell;
    private String mobile_list;
    private String address;
    private String qq;
    private String email;
    private String atlas;//"38,39"
    private List<String> atlas_litpic;
    private String pid;
    private String pid_title;
    private int coll;
    private int city_id;
    private String city_name;
    private long update_time;

    private long create_time;
    private String type_info;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getAd_img() {
        return ad_img;
    }

    public void setAd_img(String ad_img) {
        this.ad_img = ad_img;
    }

    public List<String> getAd_img_litpic() {
        return ad_img_litpic;
    }

    public void setAd_img_litpic(List<String> ad_img_litpic) {
        this.ad_img_litpic = ad_img_litpic;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getMobile_list() {
        return mobile_list;
    }

    public void setMobile_list(String mobile_list) {
        this.mobile_list = mobile_list;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAtlas() {
        return atlas;
    }

    public void setAtlas(String atlas) {
        this.atlas = atlas;
    }

    public List<String> getAtlas_litpic() {
        return atlas_litpic;
    }

    public void setAtlas_litpic(List<String> atlas_litpic) {
        this.atlas_litpic = atlas_litpic;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid_title() {
        return pid_title;
    }

    public void setPid_title(String pid_title) {
        this.pid_title = pid_title;
    }

    public int getColl() {
        return coll;
    }

    public void setColl(int coll) {
        this.coll = coll;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getType_info() {
        return type_info;
    }

    public void setType_info(String type_info) {
        this.type_info = type_info;
    }
}
