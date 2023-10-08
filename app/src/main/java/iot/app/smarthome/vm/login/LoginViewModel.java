package iot.app.smarthome.vm.login;

import androidx.lifecycle.ViewModel;

//import iot.app.smarthome.api.ApiService;
//import iot.app.smarthome.api.ResMsg;
//import iot.app.smarthome.model.login.LoginRequest;
import iot.app.smarthome.model.login.ObservableLoginInfo;
//import iot.app.smarthome.model.login.UserTokenVo;
//import retrofit2.Call;

public class LoginViewModel extends ViewModel {

    private ObservableLoginInfo loginVo;

    public LoginViewModel() {
        loginVo = new ObservableLoginInfo();
    }

    public ObservableLoginInfo getLoginVo() {
        return loginVo;
    }

    public void setLoginVo(ObservableLoginInfo loginVo) {
        this.loginVo = loginVo;
    }

}
