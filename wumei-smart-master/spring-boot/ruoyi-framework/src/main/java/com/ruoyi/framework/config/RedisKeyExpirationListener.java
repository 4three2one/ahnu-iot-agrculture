package com.ruoyi.framework.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.ruoyi.system.domain.IotDevice;
import com.ruoyi.system.domain.IotGroup;
import com.ruoyi.system.service.IIotDeviceService;
import com.ruoyi.system.service.IIotGroupService;
import com.ruoyi.system.websocket.config.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

/**
 * 主要作用就是:接收过期的redis消息,获取到key,key就是订单号,然后去更新订单号的状态(说明一下:用户5分钟不支付的话取消用户的订单)
 */
@Transactional
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private IIotGroupService iotGroupService;

    @Autowired
    private IIotDeviceService iotDeviceService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {
        String keyname = message.toString();
        long id = 0;
        if(keyname.contains("group_")){
            id = Long.parseLong(keyname.substring(6));
            IotGroup iotGroup = iotGroupService.selectIotGroupById(id);
            iotGroup.setStatus("1");
            iotGroupService.updateIotGroup(iotGroup);
            try {
                Map<String,Object> map = Maps.newHashMap();
                map.put("groupId",id);
                map.put("status","1");
                webSocket.sendMessageAll(JSON.toJSONString(map),"0");
            }
            catch (IOException e){
                System.out.println("WebSocket错误");
            }
        }else if(keyname.contains("device_")){
            id = Integer.parseInt(keyname.substring(7));
            IotDevice iotDevice = iotDeviceService.selectIotDeviceByNum(""+id);
            iotDevice.setStatus("1");
            iotDeviceService.updateIotDevice(iotDevice);
        }
    }
}