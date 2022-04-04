package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 iot_device_data
 *
 * @author wxy
 * @date 2022-03-24
 */
public class IotDeviceData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 设备id
     */
    @Excel(name = "设备id")
    private Long deviceId;

    /**
     * 物模型ID
     */
    @Excel(name = "物模型ID")
    private Long modelId;

    /**
     * 值
     */
    @Excel(name = "值")
    private Double modelData;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 设备状态：未激活，未绑定，已绑定
     */
    @Excel(name = "设备状态：未激活，未绑定，已绑定")
    private String status;

    /**
     * 物模型值以后JSON值
     */
    @Excel(name = "物模型值以后JSON值")
    private String thingsModelValue;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelData(double modelData) {
        this.modelData = modelData;
    }

    public Double getModelData() {
        return modelData;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setThingsModelValue(String thingsModelValue) {
        this.thingsModelValue = thingsModelValue;
    }

    public String getThingsModelValue() {
        return thingsModelValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("deviceId", getDeviceId())
                .append("modelId", getModelId())
                .append("modelData", getModelData())
                .append("delFlag", getDelFlag())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("thingsModelValue", getThingsModelValue())
                .toString();
    }
}
