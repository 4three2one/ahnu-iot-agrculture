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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                /*
                * 如果温湿度的指令一样，则redis中只会存储一次，也只会发送一次消息
                * 像钾氮磷传感器的每个指令不同，在redis中会分别存储
                * PushCallbackForTAS类中的消息监听要先判断是那个大棚，再判断指令，最后再判断传感器编号
                */
                Map<String,Object> map = new HashedMap<String,Object>();
                map.put("deviceId",deviceId);
                map.put("groupId",group_Id);
                if(redisCache.getCacheObject(atcmd) == null) {
                    //发送命令
                    redisCache.setCacheObject(atcmd, JSON.toJSONString(map));
                    redisCache.expire(atcmd, 5);
                }

            }catch (Exception e){
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

}
