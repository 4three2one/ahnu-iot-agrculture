package com.ruoyi.system.quart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.IotDeviceData;
import com.ruoyi.system.domain.IotDeviceModel;
import com.ruoyi.system.domain.ThingsModelTemplate;
import com.ruoyi.system.service.IIotDeviceService;
import com.ruoyi.system.service.impl.IotDeviceModelServiceImpl;
import com.ruoyi.system.service.impl.ThingsModelTemplateServiceImpl;
import org.apache.commons.collections4.map.HashedMap;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ruoyi.common.utils.Threads.sleep;

@Component("quartJob")
public class quartJob {

    @Autowired
    private ThingsModelTemplateServiceImpl thingsModelTemplateService;

    @Autowired
    private IotDeviceModelServiceImpl iotDeviceModelService;

    @Autowired
    private IIotDeviceService iotDeviceService;

    @Autowired
    RedisCache redisCache;

    public void ryDevice(Long deviceId){
        IotDeviceModel t = new IotDeviceModel();
        t.setDeviceId(deviceId);
        List<IotDeviceModel> iotThingsModels = iotDeviceModelService.selectIotDeviceModelList(t);
        Long group_Id = iotDeviceService.selectIotDeviceById(deviceId).getGroupId();
        for(int i = 0;i < iotThingsModels.size(); i++){
            ThingsModelTemplate model = thingsModelTemplateService.selectThingsModelTemplateByTemplateId(iotThingsModels.get(i).getModelId());
            JSONObject specsJson = JSON.parseObject(model.getSpecs());
            try {
                String atcmd = specsJson.get("atcmd").toString();
                String index = specsJson.get("index").toString();
                /*
                * 循环发送指令
                * 像钾氮磷传感器的每个指令不同，在redis中会分别存储对应的物模型
                */
                Map<String,Object> map = new HashedMap<String,Object>();
                map.put("specs",model.getSpecs());
                map.put("modelId",model.getTemplateId());
                map.put("identifier",model.getIdentifier());
                //发送命令
                redisCache.setCacheMap(group_Id + "_" + deviceId, map);
                SendMessage(atcmd);
                /*redisCache.expire(group_Id + "_" + deviceId, 4);*/
                Thread.sleep(4000);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("键值为空");
            }
        }
    }
    public byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }
    public  byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }
    public void SendMessage(String content){
        String topic = "pull_group_15";
        String broker = "tcp://1.116.1.99:61613";
        int qos = 1;
        String userName = "admin";
        String password = "password";
        String clientId = "pubClient2";
        // 内存存储
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            // 创建客户端
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            // 创建链接参数
            MqttConnectOptions connOpts = new MqttConnectOptions();
            // 在重新启动和重新连接时记住状态
            connOpts.setCleanSession(false);
            // 设置连接的用户名
            connOpts.setUserName(userName);
            connOpts.setPassword(password.toCharArray());
            // 建立连接
            System.out.println("连接到 broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("连接成功");
            // 创建消息
            MqttMessage message = new MqttMessage(hexToByteArray(content));
            // 设置消息的服务质量
            message.setQos(qos);
            // 发布消息
            System.out.println("向" + topic + "发送消息:" + message);
            sampleClient.publish(topic, message);
            // 断开连接
            sampleClient.disconnect();
            // 关闭客户端
            sampleClient.close();
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}
