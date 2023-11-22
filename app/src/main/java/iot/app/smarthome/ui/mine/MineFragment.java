package iot.app.smarthome.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.litepal.LitePal;

import java.io.File;

import iot.app.smarthome.MainActivity;
import iot.app.smarthome.R;
import iot.app.smarthome.api.Api;
import iot.app.smarthome.databinding.FragmentMineBinding;
import iot.app.smarthome.model.user.UserInfoVo;
import iot.app.smarthome.model.message.ResMsg;
import iot.app.smarthome.model.utils.FileUtils;
import iot.app.smarthome.ui.device.TempHumStatisticActivity;
import iot.app.smarthome.ui.device.ThermometerActivity;
import iot.app.smarthome.ui.login.LoginActivity;
import iot.app.smarthome.vm.mine.MineViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MineFragment extends Fragment {

    private FragmentMineBinding binding;
    private MineViewModel mineViewModel;


    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mineViewModel = new MineViewModel();
        binding = FragmentMineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.setMineViewModel(mineViewModel);
        //TODO：从sharePreference保存的值拿到token请求API获取的用户信息
        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String token = pref.getString("KEY_TOKEN", "");
//        String token = "9b47c26f80f56a30455892d9c842426b11e7f2b3ee5baa09bf25d73fd74fa3c3";
        Api httpApi = Api.RETROFIT.create(Api.class);
        Call<ResMsg<UserInfoVo>> call = httpApi.getUserInfo(token);
        call.enqueue(new Callback<ResMsg<UserInfoVo>>() {
            @Override
            public void onResponse
                    (Call<ResMsg<UserInfoVo>> call, Response<ResMsg<UserInfoVo>> response) {
                ResMsg<UserInfoVo> resMsg = response.body();
                if (resMsg.success()) {
                    //显示获取的用户信息
                    mineViewModel.userInfo.userid.set(resMsg.getData().getUserid());
                    mineViewModel.userInfo.username.set(resMsg.getData().getUsername());
                    mineViewModel.userInfo.useravatar.set(resMsg.getData().getAvatar());
                    //获取头像
                    ImageView imgView = root.findViewById(R.id.myAvatar);
                    String avatar = mineViewModel.userInfo.useravatar.get();
                    Glide.with(MineFragment.this).asBitmap().load(Api.BASE_URL + avatar).into(imgView);
//                    使用Bitmap作为图片格式，并保存到本地。
                    Glide.with(MineFragment.this).asBitmap().load(Api.BASE_URL + avatar).placeholder(R.drawable.refs).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            File avatarFile = FileUtils.saveAvatar(resource, mineViewModel.userInfo.userid.get() + ".png");
                            imgView.setImageBitmap(resource);
                            UserInfoVo userInfo = new UserInfoVo();
                            userInfo.setUsername(mineViewModel.userInfo.username.get());
                            userInfo.setUserid(mineViewModel.userInfo.userid.get());
                            userInfo.setAvatar(avatarFile.getAbsolutePath());
                            insertCurUserIntoDB(userInfo);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
                    initView();
                }
            }

            @Override
            public void onFailure(Call<ResMsg<UserInfoVo>> call, Throwable t) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();

            }
        });
        //实训2.2退出登录
        Button logoutBtn = root.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                Toast.makeText(getActivity(), "退出登录成功", Toast.LENGTH_SHORT).show();
//                getActivity().finish();
                startActivity(intent);
            }
        });

        Button Chart1 = root.findViewById(R.id.tempBtn1);
        Chart1.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          Intent intent = new Intent(getActivity(), ThermometerActivity.class);
                                          startActivity(intent);
                                      }

                                  }
        );
        Button Chart2 = root.findViewById(R.id.tempBtn2);
        Chart2.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          Intent intent = new Intent(getActivity(), TempHumStatisticActivity.class);
                                          startActivity(intent);
                                      }

                                  }
        );

        return root;
    }

    public void insertCurUserIntoDB(UserInfoVo userInfo) {
        LitePal.deleteAll(UserInfoVo.class);
        userInfo.save();
    }

    //刷新用户信息
    public void initView() {
        swipeRefreshLayout = binding.getRoot().findViewById(R.id.mine_swipe_view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO:完成刷新触发代码
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);
                Toast.makeText(getActivity(), "刷新用户信息成功", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

