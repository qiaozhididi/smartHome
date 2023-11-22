package iot.app.smarthome.mqtt;

import android.content.Context;
import android.util.Log;

import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * MQTT 客户端
 */
public class SmartMqtt {
    //客户端
    private MqttAndroidClient client;
    //连接选项
    private MqttConnectOptions mqttConnectOptions;

    private String clienID;

    private Context context;//上下文

    private static SmartMqtt instance;//入口操作管理

    private static boolean isInit = false;

    public static boolean isDebug = true;
    private MqttCallback mqttCallback;

    public MqttCallback getMqttCallback() {
        return mqttCallback;
    }

    public void setMqttCallback(MqttCallback mqttCallback) {
        this.mqttCallback = mqttCallback;
        if (client != null) {
            client.setCallback(this.mqttCallback);
        }
    }

    /**
     * 获取实例
     *
     * @return SmartMqtt
     */
    public static SmartMqtt getInstance() {
        if (instance == null) {
            synchronized (SmartMqtt.class) {
                if (instance == null) {
                    instance = new SmartMqtt();
                }
            }
        }
        return instance;
    }

    private SmartMqtt() {

    }

    /**
     * 初始化
     *
     * @param context
     * @param client_id
     * @return
     */
    public String init(Context context, String client_id) {
        this.context = context;
        //配置连接信息
        mqttConnectOptions = new MqttConnectOptions();
        //TODO:实训3.1 设置MQTT地址，注意要进行地址转换
        mqttConnectOptions.setServerURIs(new String[]{"tcp://10.0.2.2:1883"});
//        mqttConnectOptions.setServerURIs(new String[]{});
        //是否清除缓存
        mqttConnectOptions.setCleanSession(false);
        //是否重连
        mqttConnectOptions.setAutomaticReconnect(true);
        //设置心跳,30s
        mqttConnectOptions.setKeepAliveInterval(30);
        //TODO:实训3.1 登录用户名
        mqttConnectOptions.setUserName("iotweb");
        //TODO:实训3.1 登录密码
        mqttConnectOptions.setPassword("iotweb".toCharArray());
        //超时时间
        mqttConnectOptions.setConnectionTimeout(100);
        mqttCallback = new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.i("MQTT", "Connection Lost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                Log.i("MQTT", "recevie MQTT message:" + topic + " ->" + message.getPayload());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.i("MQTT", "delivery MQTT message complete");
            }
        };
        if (client_id == null) {
            this.clienID = RandomStringUtils.random(10);
        } else {
            this.clienID = client_id;
        }
        //第一个参数上下文，第二个 服务器地址， 第三个 客户端ID，如果存在此ID连接了服务器。那么连接失败！
        if (client == null) {
            client = new MqttAndroidClient(context, mqttConnectOptions.getServerURIs()[0], clienID);
            //client.setCallback(mqttCallback);
        }
        isInit = true;
        return clienID;
    }

    /**
     * 链接服务器
     *
     * @return
     */
    public IMqttToken connect() {
        IMqttToken token = null;
        //开始连接服务器
        try {
            token = client.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i("MQTT", "MQTT Server" + mqttConnectOptions.getServerURIs().toString() + " connected");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e("MQTT", "MQTT connect fail" + exception.getMessage());
                }
            });

        } catch (MqttException e) {
            Log.i("MQTT", e.getMessage());
        }
        return token;
    }

    /**
     * 发送数据
     *
     * @param topic    主题
     * @param data     数据
     * @param listener 监听器
     */
    public void sendData(String topic, byte[] data, IMqttActionListener listener) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(data);

        try {
            Log.i("MQTT", "public topic:" + topic + " -> " + new String(data));
            client.publish(topic, mqttMessage, null, listener);
        } catch (Exception e) {
            Log.e("MQTT", e.getMessage());
        }
    }

    /**
     * 订阅主题
     *
     * @param topic    主题
     * @param listener 监听器
     */
    public void subscribe(String topic, IMqttActionListener listener) {
        if (client == null || !client.isConnected()) {
            return;
        }
        try {
            client.unsubscribe(topic);
            Log.i("MQTT", "Subscribe topic:" + topic);
//            QoS 0：消息最多传递一次，如果当时客户端不可用，则会丢失该消息。
//            QoS 1：消息传递至少 1 次。
//            QoS 2：消息仅传送一次。
            client.subscribe(topic, 2, null, listener);
        } catch (MqttException e) {
            Log.e("MQTT", e.getMessage());
        }
    }

    public boolean isConnned() {
        return client != null && client.isConnected();
    }

    public MqttAndroidClient getClient() {
        return client;
    }

    public void disconnect() throws InterruptedException, MqttException {
        isInit = false;
        if (client != null && client.isConnected()) {
            client.unregisterResources();
            client.setCallback(null);
            Thread.sleep(50);
            client.disconnect();
        }
        client = null;
    }

}