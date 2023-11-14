package iot.app.smarthome.model.message;

import iot.app.smarthome.model.message.BaseActionMsg;

public class ResvActionMsg extends BaseActionMsg {

  protected Object[] params;

  public Object[] getParams() {
    return params;
  }

  public void setParams(Object[] params) {
    this.params = params;
  }
}
