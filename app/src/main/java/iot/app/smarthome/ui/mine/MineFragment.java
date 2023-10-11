package iot.app.smarthome.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
        //TODO: 调用curUserInfo接口获取用户信息
//        String token = getIntent().getStringExtra("token");
        String token = "002f9183a235e51ff2134d4dcbb3d4ff8913ec79106d6ffdc3437dc5bb3bd51e";
        Api httpApi = Api.RETROFIT.create( Api.class);
        Call<ResMsg<UserInfoVo>> call = httpApi.getUserInfo(token);
        call.enqueue(new Callback<ResMsg<UserInfoVo>>() {
                         @Override
                         public void onResponse(Call<ResMsg<UserInfoVo>> call, Response<ResMsg<UserInfoVo>> response) {
                             ResMsg<UserInfoVo> resMsg = response.body();
                             if (resMsg.success()) {
                                 UserInfoVo vo = resMsg.getData();
                                 mineViewModel.setUserInfo(vo);
                                 Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();

                             }
                         }

                         @Override
                         public void onFailure(Call<ResMsg<UserInfoVo>> call, Throwable t) {
                             Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();

                         }
                     }
        );

        return root;
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

