package iot.app.smarthome.model.device;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class DeviceListVo extends LitePalSupport {
    public final static String DEV_TYPE_LOCK="lock";
    public final static String DEV_TYPE_THERMOMETER="thermometer";
    public final static String DEV_TYPE_BULB="bulb";

    @Column(unique = true)
    private String iotId;
    private String devName;
    private String userId;
    private String devType;
    private String status;
    private String description;

    public String getIotId() {
        return iotId;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}