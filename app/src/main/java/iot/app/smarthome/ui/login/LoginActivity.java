package iot.app.smarthome.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import iot.app.smarthome.R;
import iot.app.smarthome.api.Api;
import iot.app.smarthome.databinding.ActivityLoginBinding;
import iot.app.smarthome.model.login.LoginRequest;
import iot.app.smarthome.model.login.UserTokenVo;
import iot.app.smarthome.model.message.ResMsg;
import iot.app.smarthome.vm.login.LoginViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从视图中获取绑定对象
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        //设置绑定对象的生命周期拥有者为本对象
        activityLoginBinding.setLifecycleOwner(this);
        //创建 ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //绑定对象设值 ViewModel
        activityLoginBinding.setLoginViewModel(loginViewModel);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //输入的用户名，展示ViewModel对应的用户名属性，查看是否一致。
                Toast.makeText(LoginActivity.this, "ViewModel username:" + loginViewModel.getLoginVo().username.get(), Toast.LENGTH_SHORT).show();
            }
        });
        //自动填充按钮绑定点击事件
        Button fillBtn = (Button) findViewById(R.id.fillBtn);
        fillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //给ViewModel 的username设值，观察视图的用户名是否绑定对应的值
                loginViewModel.getLoginVo().username.set("zhangsan");
            }
        });


        Api httpApi = Api.RETROFIT.create(Api.class);
        LoginRequest req = new LoginRequest();
        req.setUsername("");
        req.setPassword("");
        Call<ResMsg<UserTokenVo>> call = httpApi.login(req);
        call.enqueue(new Callback<ResMsg<UserTokenVo>>() {
            @Override
            public void onResponse
                    (Call<ResMsg<UserTokenVo>> call, Response<ResMsg<UserTokenVo>> response) {
                ResMsg<UserTokenVo> resMsg = response.body();
                if (resMsg.success()) {
                    //TODO: 登录成功
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO: 登录失败
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResMsg<UserTokenVo>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "网络似乎有问题哦", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
