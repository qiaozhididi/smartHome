package iot.app.smarthome.model.login;

import java.util.Date;

public class UserTokenVo {
    //TODO:请自行实现代码
    private String token;
    private String userId;
    private Date expiredTime;
    private Long expiredTs;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expireTime) {
        this.expiredTime = expiredTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getExpiredTs() {
        return expiredTs;
    }

    public void setExpiredTS(Long expiredTs) {
        this.expiredTs = expiredTs;
    }

}


