package iot.app.smarthome.ui.mine;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.FileUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

import iot.app.smarthome.R;
import iot.app.smarthome.api.Api;
import iot.app.smarthome.databinding.FragmentMineBinding;
import iot.app.smarthome.model.login.LoginRequest;
import iot.app.smarthome.model.login.UserInfoVo;
import iot.app.smarthome.model.login.UserTokenVo;
import iot.app.smarthome.model.message.ResMsg;
import iot.app.smarthome.ui.login.LoginActivity;
import iot.app.smarthome.vm.mine.MineViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MineFragment extends Fragment {

    private FragmentMineBinding binding;
    private MineViewModel mineViewModel;

    private UserTokenVo userTokenVo;

//    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mineViewModel = new MineViewModel();
        binding = FragmentMineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.setMineViewModel(mineViewModel);
        //TODO：人人sharePreference亻呆存白勺亻直拿至刂token讠青求api获耳又用户亻言息
//        String token = getActivity().getSharedPreferences("KEY_TOKEN", 0).getString("KEY_TOKEN", "");
        String token = "9b47c26f80f56a30455892d9c842426b11e7f2b3ee5baa09bf25d73fd74fa3c3";
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
                    Glide.with(MineFragment.this).asBitmap().load(Api.BASE_URL + avatar).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            File avatarFile = FileUtils.saveAvatar(resource, mineViewModel.getUserInfo().getUserid() + ".png");
                            UserInfoVo userInfo = new UserInfoVo();
                            userInfo.setUsername(mineViewModel.userInfo.username.get());
                            userInfo.setUserid(mineViewModel.userInfo.userid.get());
//                            userInfo.setAvatar(avatarFile.getAbsolutePath());
                            imgView.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
                    Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResMsg<UserInfoVo>> call, Throwable t) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();

            }
        });


//

        return root;
    }

    public void onResume() {
        super.onResume();


    }

    //    @Override
    public void onRefresh() {
        //TODO: 完成刷新触发代码
//        swipeRefreshLayout.setRefreshing(false);
//        mineViewModel.refresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

