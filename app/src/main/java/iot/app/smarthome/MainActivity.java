package iot.app.smarthome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Date;

import iot.app.smarthome.R;
import iot.app.smarthome.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

//    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实验5 .2
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        long tokenExpiredTs = pref.getLong("KEY_TOKEN_EXPIRED_TS", 0L);
        String token = pref.getString("KEY_TOKEN", "");
        if (!"".equals(token) && new Date().getTime() < tokenExpiredTs) {
            //如果令牌不为空，而且令牌没过期，正常启动 MainActivity
//            Intent intent = getIntent();
//            String userid = intent.getStringExtra("userid");
            //从SharePreference中获取userid
            String userid = pref.getString("KEY_CUR_USERID", "");
            Toast.makeText(this, "欢迎您" + userid, Toast.LENGTH_SHORT).show();
        } else {
            //否则跳转到 LoginActivity 重新登录。
            Toast.makeText(this, "您的登录信息已过期，请重新登录！", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_device, R.id.navigation_mine)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


}
