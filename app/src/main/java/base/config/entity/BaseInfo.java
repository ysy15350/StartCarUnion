package base.config.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by yangshiyou on 2017/9/12.
 */

@Table(name = "baseinfo")
public class BaseInfo {

    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "token")
    private String token;

    /**
     * token写入时间戳
     */
    @Column(name = "tokenCreateTime")
    private long tokenCreateTime;

    @Column(name = "uid")
    private int uid;

    @Column(name = "versionCode")
    private int versionCode;


    @Column(name = "versionName")
    private String versionName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTokenCreateTime() {
        return tokenCreateTime;
    }

    public void setTokenCreateTime(long tokenCreateTime) {
        this.tokenCreateTime = tokenCreateTime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
