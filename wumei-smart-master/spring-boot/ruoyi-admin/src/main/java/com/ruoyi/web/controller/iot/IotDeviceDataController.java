package com.ruoyi.web.controller.iot;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.IotDeviceData;
import com.ruoyi.system.service.IIotDeviceDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author wxy
 * @date 2022-03-24
 */
@RestController
@RequestMapping("/system/data")
public class IotDeviceDataController extends BaseController {
    @Autowired
    private IIotDeviceDataService iotDeviceDataService;

/**
 * 查询【请填写功能名称】列表
 */
@PreAuthorize("@ss.hasPermi('system:data:list')")
@GetMapping("/list")
        public TableDataInfo list(IotDeviceData iotDeviceData) {
        startPage();
        List<IotDeviceData> list = iotDeviceDataService.selectIotDeviceDataList(iotDeviceData);
        return getDataTable(list);
    }
    
    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:data:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(IotDeviceData iotDeviceData) {
        List<IotDeviceData> list = iotDeviceDataService.selectIotDeviceDataList(iotDeviceData);
        ExcelUtil<IotDeviceData> util = new ExcelUtil<IotDeviceData>(IotDeviceData. class);
        return util.exportExcel(list, "data");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:data:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iotDeviceDataService.selectIotDeviceDataById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:data:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IotDeviceData iotDeviceData) {
        return toAjax(iotDeviceDataService.insertIotDeviceData(iotDeviceData));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:data:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IotDeviceData iotDeviceData) {
        return toAjax(iotDeviceDataService.updateIotDeviceData(iotDeviceData));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:data:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iotDeviceDataService.deleteIotDeviceDataByIds(ids));
    }
}
