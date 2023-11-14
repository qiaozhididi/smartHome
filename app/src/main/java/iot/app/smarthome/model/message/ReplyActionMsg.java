package iot.app.smarthome.model.message;

import iot.app.smarthome.model.message.BaseActionMsg;
import iot.app.smarthome.model.message.ResMsg;

public class ReplyActionMsg extends BaseActionMsg {

  private ResMsg resMsg;

  public ResMsg getResMsg() {
    return resMsg;
  }

  public void setResMsg(ResMsg resMsg) {
    this.resMsg = resMsg;
  }
}
