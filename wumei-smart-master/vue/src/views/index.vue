<template>
  <div class="app-container home">
    <el-row :gutter="40">
      <el-col :span="14">
        <el-card style="margin:-10px;" shadow="hover" body-style="background-color:#F8F8F8;">
          <div ref = "map" style="height:650px;margin:-10px;margin-top:-5px;"></div>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card style="margin:-10px;" shadow="hover">
          <h3 style="font-weight:bold">大棚环境数据</h3>
          <el-row :gutter="40" class="panel-group">
            <el-col :span="12" class="card-panel-col">
              <div class="card-panel">
                <div class="card-panel-icon-wrapper icon-message">
                  <svg-icon icon-class="air_temp" class-name="card-panel-icon" />
                </div>
                <div class="card-panel-description">
                  <div class="card-panel-text">
                    空气温度
                  </div>
                  <count-to :start-val="0" :end-val="this.static['bytes.sent']" :duration="3000" class="card-panel-num" />
                </div>
              </div>
            </el-col>
            <el-col :span="12" class="card-panel-col">
              <div class="card-panel">
                <div class="card-panel-icon-wrapper icon-shopping">
                  <svg-icon icon-class="air_hump" class-name="card-panel-icon" />
                </div>
                <div class="card-panel-description">
                  <div class="card-panel-text">
                    空气湿度
                  </div>
                  <count-to :start-val="0" :end-val="this.static['bytes.received']" :duration="3000" class="card-panel-num" />
                </div>
              </div>
            </el-col>
            <el-col :span="12" class="card-panel-col">
              <div class="card-panel">
                <div class="card-panel-icon-wrapper icon-message">
                  <svg-icon icon-class="light" class-name="card-panel-icon" />
                </div>
                <div class="card-panel-description">
                  <div class="card-panel-text">
                    光照强度
                  </div>
                  <count-to :start-val="0" :end-val="this.static['client.authenticate']" :duration="1000" class="card-panel-num" />
                </div>
              </div>
            </el-col>
            <el-col :span="12" class="card-panel-col">
              <div class="card-panel">
                <div class="card-panel-icon-wrapper icon-shopping">
                  <svg-icon icon-class="CO2" class-name="card-panel-icon" />
                </div>
                <div class="card-panel-description">
                  <div class="card-panel-text">
                    CO2浓度
                  </div>
                  <count-to :start-val="0" :end-val="this.static['client.connected']" :duration="1000" class="card-panel-num" />
                </div>
              </div>
            </el-col>
            <el-col :span="12" class="card-panel-col">
              <div class="card-panel">
                <div class="card-panel-icon-wrapper icon-message">
                  <svg-icon icon-class="H2S" class-name="card-panel-icon" />
                </div>
                <div class="card-panel-description">
                  <div class="card-panel-text">
                    H2S浓度
                  </div>
                  <count-to :start-val="0" :end-val="this.static['client.subscribe']" :duration="2000" class="card-panel-num" />
                </div>
              </div>
            </el-col>
            <el-col :span="12" class="card-panel-col">
              <div class="card-panel">
                <div class="card-panel-icon-wrapper icon-shopping">
                  <svg-icon icon-class="NH3" class-name="card-panel-icon" />
                </div>
                <div class="card-panel-description">
                  <div class="card-panel-text">
                    NH3浓度
                  </div>
                  <count-to :start-val="0" :end-val="this.static['messages.received']" :duration="2000" class="card-panel-num" />
                </div>
              </div>
            </el-col>
          </el-row>
        </el-card>
        <el-card style="margin:-10px;margin-top:20px;" shadow="hover">
          <div ref="statsChart" style="height:296px;"></div>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>
<script>
  import CountTo from 'vue-count-to'
  import * as echarts from 'echarts';
  require('echarts/theme/macarons') // echarts theme
  import {
    loadBMap
  } from './map.js'
  require('echarts/extension/bmap/bmap')
  import {listGroup} from "../api/system/group";
  export default {
    name: "Index",
    components: {
      CountTo
    },
    data() {
      return {
        // 设备列表
        deviceList: [],
        // 设备总数
        deviceCount:0,
        // emqx状态数据
        stats: {},
        // emqx统计信息
        static: {},
        // 版本号
        version: "3.8.0",
        // 服务器信息
        server: {
          jvm: {
            name: "",
            version: "",
            startTime: "",
            runTime: "",
            used: "",
            total: 100
          },
          sys: {
            computerName: "",
            osName: "",
            computerIp: "",
            osArch: ""
          },
          cpu: {
            cpuNum: 1
          },
          mem: {
            total: 2
          }
        }
      };
    },
    created() {
      this.getAllDevice()
    },
    methods: {
      getAllDevice() {
        listGroup().then(response => {
          this.deviceList = response.rows;
          this.deviceCount=response.total;
          this.$nextTick(() => {
            loadBMap().then(() => {
              this.getmap();
              this.drawStats();
            });
          })
        })
      },
      getmap() {
        var myChart = echarts.init(this.$refs.map);
        var option;
        // 格式化数据
        let convertData = function (data, status) {
          var res = [];
          for (var i = 0; i < data.length; i++) {
            var geoCoord = [data[i].lat, data[i].lon];
            if (geoCoord && data[i].status == status) {
              res.push({
                name:  data[i].groupName,
                value: geoCoord,
                status: data[i].status,
                networkAddress:data[i].address,
                activeTime: data[i].createTime
              });
            }
          }
          return res;
        };
        option = {
          title: {
            text: '设备分布和状态（数量 '+this.deviceCount+'）',
            subtext: 'wumei-smart open source living iot platform',
            target: "_blank",
            textStyle: {
              color: '#333',
              textBorderColor: '#fff',
              textBorderWidth: 10,
            },
            top: 10,
            left: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: function (params) {
              var htmlStr = '<div style="padding:5px;line-height:28px;">';
              htmlStr += "设备名称： <span style='color:#409EFF'>" + params.data.name + "</span><br />";
              htmlStr += "设备状态： ";
              if (params.data.status == 1) {
                htmlStr += "<span style='color:#E6A23C'>未激活</span>" + "<br />";
              } else if (params.data.status == 2) {
                htmlStr += "<span style='color:#F56C6C'>禁用</span>" + "<br />";
              } else if (params.data.status == 3) {
                htmlStr += "<span style='color:#67C23A'>在线</span>" + "<br />";
              } else if (params.data.status == 4) {
                htmlStr += "<span style='color:#909399'>离线</span>" + "<br />";
              }
              htmlStr += "激活时间： " + params.data.activeTime + "<br />";
              htmlStr += "所在地址： " + params.data.networkAddress + "<br />";
              htmlStr += '</div>';
              return htmlStr;
            }

          },
          bmap: {
            center: [133, 37],
            zoom: 5,
            roam: true,
            mapStyle: {
              styleJson: [{
                featureType: 'water',
                elementType: 'all',
                stylers: {
                  color: '#a0cfff'
                }
              },
                {
                  featureType: 'land',
                  elementType: 'all',
                  stylers: {
                    color: '#FFFFFF'
                  }
                },
                {
                  featureType: 'railway',
                  elementType: 'all',
                  stylers: {
                    visibility: 'off'
                  }
                },
                {
                  featureType: 'highway',
                  elementType: 'all',
                  stylers: {
                    color: '#fdfdfd'
                  }
                },
                {
                  featureType: 'highway',
                  elementType: 'labels',
                  stylers: {
                    visibility: 'off'
                  }
                },
                {
                  featureType: 'arterial',
                  elementType: 'geometry',
                  stylers: {
                    color: '#fefefe'
                  }
                },
                {
                  featureType: 'arterial',
                  elementType: 'geometry.fill',
                  stylers: {
                    color: '#fefefe'
                  }
                },
                {
                  featureType: 'poi',
                  elementType: 'all',
                  stylers: {
                    visibility: 'off'
                  }
                },
                {
                  featureType: 'green',
                  elementType: 'all',
                  stylers: {
                    visibility: 'off'
                  }
                },
                {
                  featureType: 'subway',
                  elementType: 'all',
                  stylers: {
                    visibility: 'off'
                  }
                },
                {
                  featureType: 'manmade',
                  elementType: 'all',
                  stylers: {
                    color: '#d1d1d1'
                  }
                },
                {
                  featureType: 'local',
                  elementType: 'all',
                  stylers: {
                    color: '#d1d1d1'
                  }
                },
                {
                  featureType: 'arterial',
                  elementType: 'labels',
                  stylers: {
                    visibility: 'off'
                  }
                },
                {
                  featureType: 'boundary',
                  elementType: 'all',
                  stylers: {
                    color: '#999999'
                  }
                },
                {
                  featureType: 'building',
                  elementType: 'all',
                  stylers: {
                    color: '#d1d1d1'
                  }
                },
                {
                  featureType: 'label',
                  elementType: 'labels.text.fill',
                  stylers: {
                    color: '#999999'
                  }
                }
              ]
            }
          },
          series: [{
            type: 'scatter',
            coordinateSystem: 'bmap',
            data: convertData(this.deviceList, 1),
            symbolSize: 15,
            itemStyle: {
              color: '#E6A23C'
            }
          },
            {
              type: 'scatter',
              coordinateSystem: 'bmap',
              data: convertData(this.deviceList, 2),
              symbolSize: 15,
              itemStyle: {
                color: '#F56C6C'
              }
            }, {
              type: 'scatter',
              coordinateSystem: 'bmap',
              data: convertData(this.deviceList, 4),
              symbolSize: 15,
              itemStyle: {
                color: '#909399'
              }
            }, {
              type: 'effectScatter',
              coordinateSystem: 'bmap',
              data: convertData(this.deviceList, 3),
              symbolSize: 15,
              showEffectOn: 'render',
              rippleEffect: {
                brushType: 'stroke',
                scale: 5
              },
              label: {
                formatter: '{b}',
                position: 'right',
                show: false
              },
              itemStyle: {
                color: '#67C23A',
                shadowBlur: 100,
                shadowColor: '#333'
              },
              zlevel: 1
            }
          ]
        };

        option && myChart.setOption(option);

      },
      drawStats() {
        // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.init(this.$refs.statsChart);
        var option;

        option = {
          title: {
            text: 'Mqtt 状态数据'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {},
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
          },
          yAxis: {
            type: 'category',
            axisLabel: {
              fontSize: 14
            },
            data: ['连接数量', '会话数量', '主题数量', '订阅数量', '路由数量', '保留消息']
          },
          series: [{
            name: '历史最大数',
            type: 'bar',
            data: [this.stats["connections.max"], this.stats["sessions.max"], this.stats["topics.max"], this.stats["subscribers.max"], this.stats["routes.max"], this.stats["retained.max"]],
            itemStyle: {
              color: '#409EFF'
            }
          },
            {
              name: '当前数量',
              type: 'bar',
              data: [this.stats["connections.count"], this.stats["sessions.count"], this.stats["topics.count"], this.stats["subscribers.count"], this.stats["routes.count"], this.stats["retained.count"]],
              itemStyle: {
                color: '#67C23A'
              }
            }
          ]
        };
        option && myChart.setOption(option);
      },
    },
  }
</script>

<style lang="scss" scoped>
  .panel-group {
    .card-panel-col {
      margin-bottom: 10px;
    }

    .card-panel {
      height: 68px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      color: #666;
      box-shadow: 2px 2px 10px rgba(0, 0, 0, .1);

      &:hover {
        .card-panel-icon-wrapper {
          color: #fff;
        }

        .icon-message {
          background: #36a3f7;
        }

        .icon-shopping {
          background: #34bfa3
        }
      }

      .icon-message {
        color: #36a3f7;
      }

      .icon-shopping {
        color: #34bfa3
      }

      .card-panel-icon-wrapper {
        float: left;
        margin: 10px;
        padding: 10px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }

      .card-panel-icon {
        float: left;
        font-size: 32px;
      }

      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 15px;
        margin-left: 0px;

        .card-panel-text {
          line-height: 14px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 14px;
          margin-bottom: 12px;
          text-align: right;
        }

        .card-panel-num {
          font-size: 18px;
        }
      }
    }
  }
</style>
