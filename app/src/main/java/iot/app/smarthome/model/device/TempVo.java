package iot.app.smarthome.model.device;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class TempVo extends LitePalSupport {
    private int id;
    @Column(unique = true)
    private int dateHour;//默认格式yyyyMMddhh
    private float degree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDateHour() {
        return dateHour;
    }

    public float genDateHourF() {
        return dateHour % 100;
    }

    public void setDateHour(int dateHour) {
        this.dateHour = dateHour;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }
}
