package iot.app.smarthome.model.device;

public class BulbDevShadowStateVo {
    public static final String SWITCH_STATE_ON="on";
    public static final String SWITCH_STATE_OFF="off";
    private String switchState;
    private Long updateTime;

    public String getSwitchState() {
        return switchState;
    }

    public void setSwitchState(String switchState) {
        this.switchState = switchState;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

}
