package iot.app.smarthome.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import iot.app.smarthome.MainActivity;
import iot.app.smarthome.R;
import iot.app.smarthome.api.Api;
import iot.app.smarthome.databinding.ActivityLoginBinding;
import iot.app.smarthome.model.login.LoginRequest;
import iot.app.smarthome.model.login.UserTokenVo;
import iot.app.smarthome.model.message.ResMsg;
import iot.app.smarthome.ui.MainActivity1;
import iot.app.smarthome.vm.login.LoginViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private ProgressBar progressBar;
    private ActivityLoginBinding activityLoginBinding;


    private SharedPreferences.Editor editor;

    public interface PrefConst {

        /**
         * 默认文件名
         */
        String DEFAULT_FILE_NAME = "data";

        /**
         * 当前用户名
         */
        String KEY_CUR_USERID = "KEY_CUR_USERID";

        /**
         * 当前令牌
         */
        String KEY_TOKEN = "KEY_TOKEN";

        /**
         * 当前令牌过期时间戳
         */
        String KEY_TOKEN_EXPIRED_TS = "KEY_TOKEN_EXPIRED_TS";
    }


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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                loginBtn.setEnabled(false);
                //输入的用户名，展示ViewModel对应的用户名属性，查看是否一致。
//                Toast.makeText(LoginActivity.this, "ViewModel username:" + loginViewModel.getLoginVo().username.get(), Toast.LENGTH_SHORT).show();
                Api httpApi = Api.RETROFIT.create(Api.class);
                LoginRequest req = new LoginRequest();
                req.setUserName(loginViewModel.getLoginVo().username.get());
                req.setPassWord(loginViewModel.getLoginVo().password.get());
                Call<ResMsg<UserTokenVo>> call = httpApi.login(req);
                call.enqueue(new Callback<ResMsg<UserTokenVo>>() {
                    @Override
                    public void onResponse
                            (Call<ResMsg<UserTokenVo>> call, Response<ResMsg<UserTokenVo>> response) {
                        ResMsg<UserTokenVo> resMsg = response.body();
                        if (resMsg.success()) {
                            //TODO: 登录成功并将数据通过SharedPreferences保存到data.xml文件中
                            //实训2.1
                            UserTokenVo vo = resMsg.getData();
                            Long expiredTs = resMsg.getData().getExpiredTs();
                            editor = getSharedPreferences(PrefConst.DEFAULT_FILE_NAME, MODE_PRIVATE).edit();
                            editor.putString(PrefConst.KEY_CUR_USERID, vo.getUserId());
                            editor.putString(PrefConst.KEY_TOKEN, vo.getToken());
                            editor.putLong(PrefConst.KEY_TOKEN_EXPIRED_TS,expiredTs);
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("userid", vo.getUserId());
                            progressBar.setVisibility(View.GONE);
                            loginBtn.setEnabled(true);
                            startActivity(intent);
//                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        } else {
                            //TODO: 登录失败
                            progressBar.setVisibility(View.GONE);
                            loginBtn.setEnabled(true);
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResMsg<UserTokenVo>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "网络似乎有问题哦", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
//        自动填充按钮绑定点击事件
        Button fillBtn = (Button) findViewById(R.id.fillBtn);
        fillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //给ViewModel 的username设值，观察视图的用户名是否绑定对应的值
                loginViewModel.getLoginVo().username.set("zhangsan");
                loginViewModel.getLoginVo().password.set("123456");
            }
        });
    }

}
