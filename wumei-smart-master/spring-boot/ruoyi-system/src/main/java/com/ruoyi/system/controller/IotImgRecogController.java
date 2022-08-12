package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.IotImgRecog;
import com.ruoyi.system.service.IIotImgRecogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author wxy
 * @date 2022-08-10
 */
@RestController
@RequestMapping("/system/recog")
public class IotImgRecogController extends BaseController {
    @Autowired
    private IIotImgRecogService iotImgRecogService;

/**
 * 查询【请填写功能名称】列表
 */
@PreAuthorize("@ss.hasPermi('system:recog:list')")
@GetMapping("/list")
        public TableDataInfo list(IotImgRecog iotImgRecog) {
        startPage();
        List<IotImgRecog> list = iotImgRecogService.selectIotImgRecogList(iotImgRecog);
        return getDataTable(list);
    }
    
    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:recog:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(IotImgRecog iotImgRecog) {
        List<IotImgRecog> list = iotImgRecogService.selectIotImgRecogList(iotImgRecog);
        ExcelUtil<IotImgRecog> util = new ExcelUtil<IotImgRecog>(IotImgRecog. class);
        return util.exportExcel(list, "recog");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:recog:query')")
    @GetMapping(value = "/{imgId}")
    public AjaxResult getInfo(@PathVariable("imgId") Long imgId) {
        return AjaxResult.success(iotImgRecogService.selectIotImgRecogById(imgId));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:recog:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IotImgRecog iotImgRecog) {
        return toAjax(iotImgRecogService.insertIotImgRecog(iotImgRecog));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:recog:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IotImgRecog iotImgRecog) {
        return toAjax(iotImgRecogService.updateIotImgRecog(iotImgRecog));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:recog:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{imgIds}")
    public AjaxResult remove(@PathVariable Long[] imgIds) {
        return toAjax(iotImgRecogService.deleteIotImgRecogByIds(imgIds));
    }
}
