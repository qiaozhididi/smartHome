package iot.app.smarthome.api;

import android.database.Observable;

import java.util.ArrayList;
import java.util.List;

import iot.app.smarthome.model.login.LoginRequest;
import iot.app.smarthome.model.user.UserInfoVo;
import iot.app.smarthome.model.login.UserTokenVo;
import iot.app.smarthome.model.message.ResMsg;
import iot.app.smarthome.model.device.DeviceListVo;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "http://10.0.2.2:8098";
//    String BASE_URL="http://localhost:8098";

    Retrofit RETROFIT = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();

    /**
     * 登录获取令牌
     *
     * @param loginRequest
     * @return
     */
    @POST("/signin_for_token")
    Call<ResMsg<UserTokenVo>> login(@Body LoginRequest loginRequest);

    //TODO:请参考上面的代码实现获取当前用户信息API的方法
    @GET("/curUserInfo")
    Call<ResMsg<UserInfoVo>> getUserInfo(@Query("token") String token);

    //TODO:实训3.1获取设备列表接口。
    @GET("/device_all")
    Call<ResMsg<List<DeviceListVo>>> getDeviceList(@Query("token") String token);
}
