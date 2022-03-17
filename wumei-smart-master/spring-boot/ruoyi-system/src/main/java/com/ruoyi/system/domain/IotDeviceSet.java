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
package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备配置对象 iot_device_set
 *
 * @author kerwincui
 * @date 2021-05-06
 */
public class IotDeviceSet extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    private Long deviceSetId;

    /**
     * 设备
     */
    @Excel(name = "设备")
    private Long deviceId;

    /**
     * 设备编号
     */
    @Excel(name = "设备编号")
    private String deviceNum;

    /**
     * 报警
     */
    @Excel(name = "报警")
    private Integer isAlarm;

    /**
     * 雷达感应
     */
    @Excel(name = "雷达感应")
    private Integer isRadar;

    /**
     * 托管
     */
    @Excel(name = "托管")
    private Integer isHost;

    /**
     * 重启
     */
    @Excel(name = "重启")
    private Integer isReset;

    /**
     * 打开AP
     */
    @Excel(name = "打开AP")
    private Integer isAp;

    /**
     * 是否离线使用
     */
    @Excel(name = "是否离线使用")
    private Integer isWifiOffline;

    /**
     * 是否使用证书
     */
    @Excel(name = "是否使用证书")
    private Integer isOpenCertifi;

    /**
     * 智能配网
     */
    @Excel(name = "智能配网")
    private Integer isSmartConfig;

    /**
     * 射频遥控
     */
    @Excel(name = "射频遥控")
    private Integer isRfControl;

    /**
     * 遥控配对
     */
    @Excel(name = "遥控配对")
    private Integer isRfLearn;

    /**
     * 遥控清码
     */
    @Excel(name = "遥控清码")
    private Integer isRfClear;

    /**
     * 按键一
     */
    @Excel(name = "按键一")
    private Integer rfOneFunc;

    /**
     * 按键二
     */
    @Excel(name = "按键二")
    private Integer rfTwoFunc;

    /**
     * 按键三
     */
    @Excel(name = "按键三")
    private Integer rfThreeFunc;

    /**
     * 按键四
     */
    @Excel(name = "按键四")
    private Integer rfFourFunc;

    /**
     * 用户
     */
    @Excel(name = "用户")
    private String ownerId;

    /**
     * 配网地址
     */
    @Excel(name = "配网地址")
    private String networkAddress;

    /**
     * 配网IP
     */
    @Excel(name = "配网IP")
    private String networkIp;

    /**
     * 雷达感应间隔
     */
    @Excel(name = "雷达感应间隔")
    private Integer radarInterval;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    public void setDeviceSetId(Long deviceSetId) {
        this.deviceSetId = deviceSetId;
    }

    public Long getDeviceSetId() {
        return deviceSetId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceNum(String deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getDeviceNum() {
        return deviceNum;
    }

    public void setIsAlarm(Integer isAlarm) {
        this.isAlarm = isAlarm;
    }

    public Integer getIsAlarm() {
        return isAlarm;
    }

    public void setIsRadar(Integer isRadar) {
        this.isRadar = isRadar;
    }

    public Integer getIsRadar() {
        return isRadar;
    }

    public void setIsHost(Integer isHost) {
        this.isHost = isHost;
    }

    public Integer getIsHost() {
        return isHost;
    }

    public void setIsReset(Integer isReset) {
        this.isReset = isReset;
    }

    public Integer getIsReset() {
        return isReset;
    }

    public void setIsAp(Integer isAp) {
        this.isAp = isAp;
    }

    public Integer getIsAp() {
        return isAp;
    }

    public void setIsWifiOffline(Integer isWifiOffline) {
        this.isWifiOffline = isWifiOffline;
    }

    public Integer getIsWifiOffline() {
        return isWifiOffline;
    }

    public void setIsOpenCertifi(Integer isOpenCertifi) {
        this.isOpenCertifi = isOpenCertifi;
    }

    public Integer getIsOpenCertifi() {
        return isOpenCertifi;
    }

    public void setIsSmartConfig(Integer isSmartConfig) {
        this.isSmartConfig = isSmartConfig;
    }

    public Integer getIsSmartConfig() {
        return isSmartConfig;
    }

    public void setIsRfControl(Integer isRfControl) {
        this.isRfControl = isRfControl;
    }

    public Integer getIsRfControl() {
        return isRfControl;
    }

    public void setIsRfLearn(Integer isRfLearn) {
        this.isRfLearn = isRfLearn;
    }

    public Integer getIsRfLearn() {
        return isRfLearn;
    }

    public void setIsRfClear(Integer isRfClear) {
        this.isRfClear = isRfClear;
    }

    public Integer getIsRfClear() {
        return isRfClear;
    }

    public void setRfOneFunc(Integer rfOneFunc) {
        this.rfOneFunc = rfOneFunc;
    }

    public Integer getRfOneFunc() {
        return rfOneFunc;
    }

    public void setRfTwoFunc(Integer rfTwoFunc) {
        this.rfTwoFunc = rfTwoFunc;
    }

    public Integer getRfTwoFunc() {
        return rfTwoFunc;
    }

    public void setRfThreeFunc(Integer rfThreeFunc) {
        this.rfThreeFunc = rfThreeFunc;
    }

    public Integer getRfThreeFunc() {
        return rfThreeFunc;
    }

    public void setRfFourFunc(Integer rfFourFunc) {
        this.rfFourFunc = rfFourFunc;
    }

    public Integer getRfFourFunc() {
        return rfFourFunc;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setNetworkAddress(String networkAddress) {
        this.networkAddress = networkAddress;
    }

    public String getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkIp(String networkIp) {
        this.networkIp = networkIp;
    }

    public String getNetworkIp() {
        return networkIp;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public void setRadarInterval(Integer radarInterval) {
        this.radarInterval = radarInterval;
    }

    public Integer getRadarInterval() {
        return radarInterval;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("deviceSetId", getDeviceSetId())
                .append("deviceId", getDeviceId())
                .append("deviceNum", getDeviceNum())
                .append("isAlarm", getIsAlarm())
                .append("isRadar", getIsRadar())
                .append("isHost", getIsHost())
                .append("isReset", getIsReset())
                .append("isAp", getIsAp())
                .append("isSmartConfig", getIsSmartConfig())
                .append("isRfControl", getIsRfControl())
                .append("isRfLearn", getIsRfLearn())
                .append("isRfClear", getIsRfClear())
                .append("rfOneFunc", getRfOneFunc())
                .append("rfTwoFunc", getRfTwoFunc())
                .append("rfThreeFunc", getRfThreeFunc())
                .append("rfFourFunc", getRfFourFunc())
                .append("ownerId", getOwnerId())
                .append("networkAddress", getNetworkAddress())
                .append("networkIp", getNetworkIp())
                .append("radarInterval", getRadarInterval())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
