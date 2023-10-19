package iot.app.smarthome.vm.device;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;

public class DeviceViewModel extends ViewModel {
    // TODO: 显示设备列表。
    String deviceName;
    String description;
    String devType;

    //    public DeviceViewModel(@NonNull String deviceName, @NonNull String description, @NonNull String devType) {
//        this.deviceName = deviceName;
//        this.description = description;
//        this.devType = devType;
//    }
//
//    @Bindable
//    public String getDeviceName() {
//        return deviceName;
//    }
//
//    public void setDeviceName(String deviceName) {
//        this.deviceName = deviceName;
//    }
//
//    @Bindable
//    public String getDescription() {
//        return description;
//    }
//
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    @Bindable
//    public String getDevType() {
//        return devType;
//    }
//
//    @BindingAdapter("bind:devType")
//    public void setDevType(ImageView iv, String devType) {
//        Glide.with(iv.getContext()).load(devType).into(iv);
//        this.devType = devType;
//    }
    public DeviceViewModel() {
        super();
    }

}
