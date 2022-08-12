package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 iot_img_recog
 *
 * @author wxy
 * @date 2022-08-10
 */
public class IotImgRecog extends BaseEntity
        {
private static final long serialVersionUID=1L;

        /** 图片id */
            private Long imgId;

            /** 设备ID */
                                                        @Excel(name = "设备ID")
                        private Long deviceId;

            /** 图片名称 */
                                                        @Excel(name = "图片名称")
                        private String imgName;

            /** 疾病 */
                                                        @Excel(name = "疾病")
                        private String diseaseStatus;

            /** 原始图片路径 */
                                                        @Excel(name = "原始图片路径")
                        private String imgOrginPath;

            /** 识别结果路径 */
                                                        @Excel(name = "识别结果路径")
                        private String imgResultPath;

            /** 状态 */
                                                        @Excel(name = "状态")
                        private String status;

                                            public void setImgId(Long imgId)
            {
            this.imgId = imgId;
            }

    public Long getImgId()
            {
            return imgId;
            }
                            public void setDeviceId(Long deviceId)
            {
            this.deviceId = deviceId;
            }

    public Long getDeviceId()
            {
            return deviceId;
            }
                            public void setImgName(String imgName)
            {
            this.imgName = imgName;
            }

    public String getImgName()
            {
            return imgName;
            }
                            public void setDiseaseStatus(String diseaseStatus)
            {
            this.diseaseStatus = diseaseStatus;
            }

    public String getDiseaseStatus()
            {
            return diseaseStatus;
            }
                            public void setImgOrginPath(String imgOrginPath)
            {
            this.imgOrginPath = imgOrginPath;
            }

    public String getImgOrginPath()
            {
            return imgOrginPath;
            }
                            public void setImgResultPath(String imgResultPath)
            {
            this.imgResultPath = imgResultPath;
            }

    public String getImgResultPath()
            {
            return imgResultPath;
            }
                            public void setStatus(String status)
            {
            this.status = status;
            }

    public String getStatus()
            {
            return status;
            }
                    
@Override
public String toString(){
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("imgId",getImgId())
                                .append("deviceId",getDeviceId())
                                .append("imgName",getImgName())
                                .append("diseaseStatus",getDiseaseStatus())
                                .append("imgOrginPath",getImgOrginPath())
                                .append("imgResultPath",getImgResultPath())
                                .append("status",getStatus())
                                .append("createBy",getCreateBy())
                                .append("createTime",getCreateTime())
                                .append("updateBy",getUpdateBy())
                                .append("updateTime",getUpdateTime())
                .toString();
        }
        }
