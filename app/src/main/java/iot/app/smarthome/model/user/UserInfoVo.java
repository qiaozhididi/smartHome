package iot.app.smarthome.model.user;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.Collection;

public class UserInfoVo extends LitePalSupport {
    private int id;
    @Column(unique = true, defaultValue = "")
    private String userid;
    private String username;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
