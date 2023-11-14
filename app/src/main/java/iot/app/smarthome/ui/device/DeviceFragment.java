package iot.app.smarthome.ui.device;


import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


import iot.app.smarthome.R;
import iot.app.smarthome.api.Api;
import iot.app.smarthome.model.device.DeviceListVo;
import iot.app.smarthome.model.message.ResMsg;
//import iot.app.smarthome.databinding.FragmentDeviceBinding;
import iot.app.smarthome.model.device.DeviceListVo;
import iot.app.smarthome.model.user.UserInfoVo;
import iot.app.smarthome.vm.device.DeviceViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceFragment extends Fragment {
    //    private FragmentDeviceBinding binding;
    private DeviceViewModel mViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<DeviceListVo> reDeviceList = new ArrayList();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DeviceViewModel.class);
//        binding = FragmentDeviceBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_device, container, false);
        recyclerView = root.findViewById(R.id.dev_list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout = root.findViewById(R.id.device_swipe_view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRemoteDeviceList();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                    getRemoteDeviceList();
                    Toast.makeText(getActivity(), "刷新成功，同步网络数据", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //如果不下拉刷新就显示本地数据
        getCachedDeviceList();
        Toast.makeText(getActivity(), "本地数据", Toast.LENGTH_SHORT).show();


        return root;

    }


    /**
     * 获取缓存在Sqlite的用户的设备信息
     */
    private void getCachedDeviceList() {
        //TODO:实验3.3 从Sqlite 读取用户设备信息，并绑定到recyclerView
        reDeviceList = LitePal.findAll(DeviceListVo.class);
        DeviceAdapter adapter = new DeviceAdapter(reDeviceList);
        recyclerView.setAdapter(adapter);

    }

    /**
     * 通过远程 API 获取用户设备列表信息
     */
    private void getRemoteDeviceList() {
        //TODO:实训3.2 通过远程API，获取设备列表，并绑定到 recyclerView
        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String token = pref.getString("KEY_TOKEN", "");
        Api httpApi = Api.RETROFIT.create(Api.class);
        Call<ResMsg<List<DeviceListVo>>> call = httpApi.getDeviceList(token);
        call.enqueue(new Callback<ResMsg<List<DeviceListVo>>>() {
            @Override
            public void onResponse
                    (Call<ResMsg<List<DeviceListVo>>> call, Response<ResMsg<List<DeviceListVo>>> response) {
                ResMsg<List<DeviceListVo>> resMsg = response.body();
                if (resMsg.success()) {
                    reDeviceList = resMsg.getData();
                    DeviceAdapter adapter = new DeviceAdapter(reDeviceList);
                    recyclerView.setAdapter(adapter);
                    insertCurDeviceListIntoDB(reDeviceList);
                }
            }

            @Override
            public void onFailure(Call<ResMsg<List<DeviceListVo>>> call, Throwable t) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void insertCurDeviceListIntoDB(List<DeviceListVo> reDeviceList) {
        LitePal.initialize(getActivity());
//        LitePal.deleteAll("DeviceListVo", null);
        LitePal.saveAll(reDeviceList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
