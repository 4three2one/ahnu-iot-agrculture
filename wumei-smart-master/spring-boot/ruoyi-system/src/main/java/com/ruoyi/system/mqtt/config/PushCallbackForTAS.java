/******************************************************************************
 * 作者：kerwincui
 * 时间：2021-06-08
 * 邮箱：164770707@qq.com
 * 源码地址：https://gitee.com/kerwincui/wumei-smart
 * author: kerwincui
 * create: 2021-06-08
 * email：164770707@qq.com
 * source:https://github.com/kerwincui/wumei-smart
 ******************************************************************************/
package com.ruoyi.system.mqtt.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.AddressUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.IIotCategoryService;
import com.ruoyi.system.service.IIotDeviceService;
import com.ruoyi.system.service.IIotDeviceSetService;
import com.ruoyi.system.service.IIotDeviceStatusService;
import com.ruoyi.system.service.impl.IotGroupServiceImpl;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * @Classname PushCallback
 * @Description 消费监听类
 */
@Component
public class PushCallbackForTAS implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(MqttPushClient.class);

    @Autowired
    private MqttConfig mqttConfig;
    @Autowired
    private IIotDeviceService iotDeviceService;
    @Autowired
    private IIotCategoryService iotCategoryService;
    @Autowired
    private IIotDeviceStatusService iotDeviceStatusService;
    @Autowired
    private IIotDeviceSetService iotDeviceSetService;
    @Autowired
    private MqttPushClient mqttPushClient;

    private static MqttClient client;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IotGroupServiceImpl iotGroupService;

    @Override
    public void connectionLost(Throwable throwable) {
        // 连接丢失后，一般在这里面进行重连
        logger.info("连接断开，可以做重连");
        if (client == null || !client.isConnected()) {
            mqttConfig.getMqttPushClient();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        // subscribe后得到的消息会执行到这里面
        logger.info("接收消息主题 : " + topic);
        logger.info("接收消息Qos : " + mqttMessage.getQos());
        String playload=new String(mqttMessage.getPayload());
        if(StringUtils.isEmpty(topic)) return;
        if(StringUtils.isEmpty(playload)) return;
      //  if(StringUtils.equals(playload,"is_online")||StringUtils.equals(playload,"869476055270484")) return;
        if (topic.equals("group/0319/push")) {
            if(StringUtils.startsWith(playload,"group_")){
                String gourpID = StringUtils.replace(playload, "group_", "");
                IotGroup iotGroup = iotGroupService.selectIotGroupById(new Long(gourpID));
                if(iotGroup==null) return;
                //更新redis信息
                redisCache.setCacheObject(playload,playload);
                redisCache.expire(playload, 130);
                //设置group状态
                iotGroup.setStatus("3");
                iotGroupService.updateIotGroup(iotGroup);

            }
            //String data = encodeHex(mqttMessage.getPayload());
//            String data = encodeHex(mqttMessage.getPayload());
//            //添加设备信息
//            IotDevice device = getTasDeviceData(data);
//
//            IotDevice deviceEntity = iotDeviceService.selectIotDeviceByNum(device.getDeviceNum());
//            /*if (deviceEntity != null) {
//                device.setDeviceId(deviceEntity.getDeviceId());
//                iotDeviceService.updateIotDevice(device);
//            } else {
//                IotCategory categoryEntity = iotCategoryService.selectIotCategoryById(device.getCategoryId());
//                if (device.getDeviceName() == null || device.getDeviceNum().length() == 0) {
//                    Random rand = new Random(); //随机生成两位数
//                    device.setDeviceName(categoryEntity.getCategoryName() + (rand.nextInt(90) + 10));
//                }
//                iotDeviceService.insertIotDevice(device);
//            }*/
//            if(deviceEntity==null) return;
//            IotDeviceStatus deviceStatus = getTasDeviceStatusData(data);
//            deviceStatus.setDeviceId(deviceEntity.getDeviceId());
//            deviceStatus.setDeviceNum(device.getDeviceNum());
//            iotDeviceStatusService.insertIotDeviceStatus(deviceStatus);
        }
    }
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        logger.info("deliveryComplete---------" + iMqttDeliveryToken.isComplete());
    }

    private  String encodeHex(byte[] bytes) {
        StringBuffer buff = new StringBuffer(bytes.length * 2);
        String b;
        for (int i = 0; i < bytes.length; i++) {
            b = Integer.toHexString(bytes[i]);
            // byte是两个字节的,而上面的Integer.toHexString会把字节扩展为4个字节
            b=b.length() > 2 ? b.substring(6, 8) : b;
            b=b.length() < 2 ? "0"+b : b;
            buff.append(b);
            buff.append(" ");
        }
        return buff.toString();
    }
    private IotDevice getTasDeviceData(String data) {
        IotDevice iotDevice=new IotDevice();
        String[] s = data.split(" ");
        iotDevice.setDeviceNum(Integer.valueOf(s[0],16).toString());
        return  iotDevice;
    }
    private IotDeviceStatus getTasDeviceStatusData(String data) {
        IotDeviceStatus iotDeviceStatus=new IotDeviceStatus();
        String[] s = data.split(" ");
        iotDeviceStatus.setAirHumidity(BigDecimal.valueOf(Integer.valueOf(s[4],16)));
        iotDeviceStatus.setAirTemperature(BigDecimal.valueOf(Integer.valueOf(s[5],16)));
        iotDeviceStatus.setIsOnline(1);
        return  iotDeviceStatus;
    }



}
