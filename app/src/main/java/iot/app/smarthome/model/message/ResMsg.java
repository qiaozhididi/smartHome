package iot.app.smarthome.model.message;

import iot.app.smarthome.api.Api;
import iot.app.smarthome.model.login.LoginRequest;
import iot.app.smarthome.model.login.UserTokenVo;
import retrofit2.Call;

/**
 * 响应消息
 *
 * @param <T>
 */
public class ResMsg<T> {
    private String errcode = "0";
    private String errmsg;
    private T data;

    public ResMsg() {

    }

    public ResMsg(String errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public boolean success() {
        return "0".equals(errcode);
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Call<ResMsg<UserTokenVo>> login() {
        Api httpApi = Api.RETROFIT.create(Api.class);
        LoginRequest req = new LoginRequest();
//        req.setUserName(loginVo.username.get();
//        req.setPassWord(loginVo.password.get());
        Call<ResMsg<UserTokenVo>> call = httpApi.login(req);
        return call;
    }
}

