package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 iot_device_group
 *
 * @author wxy
 * @date 2022-03-24
 */
public class IotDeviceGroup extends BaseEntity
        {
private static final long serialVersionUID=1L;

        /** 设备分组ID */
            private Long deviceGroupId;

            /** 分组ID */
                                                        @Excel(name = "分组ID")
                        private Long groupId;

            /** 设备ID */
                                                        @Excel(name = "设备ID")
                        private Long deviceId;

                                public void setDeviceGroupId(Long deviceGroupId)
            {
            this.deviceGroupId = deviceGroupId;
            }

    public Long getDeviceGroupId()
            {
            return deviceGroupId;
            }
                            public void setGroupId(Long groupId)
            {
            this.groupId = groupId;
            }

    public Long getGroupId()
            {
            return groupId;
            }
                            public void setDeviceId(Long deviceId)
            {
            this.deviceId = deviceId;
            }

    public Long getDeviceId()
            {
            return deviceId;
            }
        
@Override
public String toString(){
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("deviceGroupId",getDeviceGroupId())
                                .append("groupId",getGroupId())
                                .append("deviceId",getDeviceId())
                                .append("createTime",getCreateTime())
                .toString();
        }
        }
