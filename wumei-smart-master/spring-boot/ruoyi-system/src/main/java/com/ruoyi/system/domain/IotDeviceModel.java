package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 iot_device_model
 *
 * @author wxy
 * @date 2022-03-27
 */
public class IotDeviceModel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long deviceModelId;

    /**
     * deviceID
     */
    @Excel(name = "deviceID")
    private Long deviceId;

    /**
     * modelID
     */
    @Excel(name = "modelID")
    private Long modelId;

    public void setDeviceModelId(Long deviceModelId) {
        this.deviceModelId = deviceModelId;
    }

    public Long getDeviceModelId() {
        return deviceModelId;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("deviceModelId", getDeviceModelId())
                .append("deviceId", getDeviceId())
                .append("modelId", getModelId())
                .append("createTime", getCreateTime())
                .toString();
    }
}
