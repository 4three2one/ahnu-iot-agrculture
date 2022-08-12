import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listRecog(query) {
    return request({
        url: '/system/recog/list',
        method: 'get',
        params: query
    })
}

// 查询【请填写功能名称】详细
export function getRecog(imgId) {
    return request({
        url: '/system/recog/' + imgId,
        method: 'get'
    })
}

// 新增【请填写功能名称】
export function addRecog(data) {
    return request({
        url: '/system/recog',
        method: 'post',
        data: data
    })
}

// 修改【请填写功能名称】
export function updateRecog(data) {
    return request({
        url: '/system/recog',
        method: 'put',
        data: data
    })
}

// 删除【请填写功能名称】
export function delRecog(imgId) {
    return request({
        url: '/system/recog/' + imgId,
        method: 'delete'
    })
}

// 导出【请填写功能名称】
export function exportRecog(query) {
    return request({
        url: '/system/recog/export',
        method: 'get',
        params: query
    })
}