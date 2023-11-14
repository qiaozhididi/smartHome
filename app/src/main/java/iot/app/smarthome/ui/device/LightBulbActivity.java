package iot.app.smarthome.ui.device;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

import iot.app.smarthome.R;
import iot.app.smarthome.mqtt.SmartMqtt;

public class LightBulbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String iotId = getIntent().getStringExtra("iotid");
        //绑定显示activity_light_bulb.xml页面
        setContentView(R.layout.activity_light_bulb);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //获取Intent过来的IoTID并放在input框里
        TextView input = findViewById(R.id.iotIdText);
        input.setText(iotId);
        MQTTinit();
        super.onCreate(savedInstanceState);

//    实现开关灯按钮的功能
        Button bulbOnBtn = findViewById(R.id.bulbOnBtn);
        Button bulbOffBtn = findViewById(R.id.bulbOffBtn);
        bulbOnBtn.setVisibility(View.VISIBLE);
        bulbOffBtn.setVisibility(View.VISIBLE);
        //主题和消息定义
        String PubTopic = "/iot/cloud/" + iotId + "/receive"; // MQTT主题
        String eventId = "0759";
        String eventName = "updateDevShadow";
        long eventTime = System.currentTimeMillis();
        long updateTime = System.currentTimeMillis();
        bulbOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String switchState = "on";
                String data = "{\"switchState\":\"" + switchState + "\",\"updateTime\":" + updateTime + "}";
                String msg = "{\"eventId\":\"" + eventId + "\",\"eventName\":\"" + eventName + "\",\"eventTime\":" + eventTime + ",\"data\":" + data + "}";
                SmartMqtt mqttClient = SmartMqtt.getInstance();
                mqttClient.sendData(PubTopic, msg.getBytes(StandardCharsets.UTF_8), new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.i("MQTT", "send Data success");
                        Toast.makeText(LightBulbActivity.this, "发送开灯指令成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.e("MQTT", "send Data fail");
                        Toast.makeText(LightBulbActivity.this, "发送开灯指令失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        bulbOffBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              String switchState = "off";
                                              String data = "{\"switchState\":\"" + switchState + "\",\"updateTime\":" + updateTime + "}";
                                              String msg = "{\"eventId\":\"" + eventId + "\",\"eventName\":\"" + eventName + "\",\"eventTime\":" + eventTime + ",\"data\":" + data + "}";
                                              SmartMqtt mqttClient = SmartMqtt.getInstance();
                                              mqttClient.sendData(PubTopic, msg.getBytes(StandardCharsets.UTF_8), new IMqttActionListener() {
                                                  @Override
                                                  public void onSuccess(IMqttToken asyncActionToken) {
                                                      Log.i("MQTT", "send Data success");
                                                      Toast.makeText(LightBulbActivity.this, "发送关灯指令成功", Toast.LENGTH_SHORT).show();
                                                  }

                                                  @Override
                                                  public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                                      Log.e("MQTT", "send Data fail");
                                                      Toast.makeText(LightBulbActivity.this, "发送关灯指令失败", Toast.LENGTH_SHORT).show();
                                                  }
                                              });
                                          }
                                      }
        );
    }

    //初始化连接
    public void MQTTinit() {
        //调用init
        SmartMqtt mqttClient = SmartMqtt.getInstance();
        mqttClient.init(this, null);
        mqttClient.setMqttCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.i("MQTT", "Connection Lost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String payload = new String(message.getPayload());//获取监听主题的消息
                Log.i("MQTT", "Message Arrived:" + payload);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.i("MQTT", "delivery Complete");
            }
        });
        IMqttToken token = mqttClient.connect();
        token.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                //订阅主题的代码可以写在这个方法内
                SmartMqtt mqttClient = SmartMqtt.getInstance();
                String IoTId = getIntent().getStringExtra("iotid");
                String subTopic = "/iot/cloud/" + IoTId + "/receive_reply";
                mqttClient.subscribe(subTopic, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.i("MQTT", "Subscribe success with topics:" + StringUtils.join(asyncActionToken.getTopics(), '|'));
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        if (exception != null) {
                            exception.printStackTrace();
                        }
                        Log.i("MQTT", "Subscribe fail");
                    }
                });
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i("MQTT", "Subscribe action fail");
            }
        });
    }

}

