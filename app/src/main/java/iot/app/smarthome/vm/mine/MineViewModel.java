package iot.app.smarthome.vm.mine;

import androidx.lifecycle.ViewModel;

import iot.app.smarthome.model.user.ObservableUserInfo;

public class MineViewModel extends ViewModel {

    public ObservableUserInfo userInfo;
    public MineViewModel(){
        this.userInfo=new ObservableUserInfo();
    }

}
