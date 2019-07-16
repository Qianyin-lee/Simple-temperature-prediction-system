<template>
  <div class="Forecast">
    <el-container>
      <el-header>
        <el-row :gutter="20">
          <el-col :span="14">
            <div class="grid-content bg-purple">
              <el-date-picker
                v-model="form.date"
                type="date"
                placeholder="选择日期"
                format="yyyy 年 MM 月 dd 日"
                value-format="yyyy-MM-dd">
              </el-date-picker>
              <el-button @click="drawLine()">预测查询</el-button>
              <span> 高温:
              <el-switch v-model="type1" active-text=" 折线 " inactive-text=" 柱状 "></el-switch>
              </span>
              <span> 低温:
              <el-switch v-model="type2" active-text=" 折线 " inactive-text=" 柱状 "></el-switch>
              </span>
              <el-button @click="draw()">画图</el-button>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="14">
            <div class="block">
              <span class="demonstration">天数</span>
              <el-slider
                v-model="form.number"
                :max = '21'
                :min = '7'>
              </el-slider>
            </div>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row :gutter="20">
          <el-col :span="20">
            <div id="chartColumn" style="width: 100%; height: 500px;">
            </div>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import echarts from 'echarts'
export default {
  data () {
    return {
      chartColumn: 'line',
      form: {
        options: '4',
        date: '',
        number: '7'
      },
      day: [],
      max: [],
      min: [],
      type1: 'true',
      type2: 'true',
      option1: 'line',
      option2: 'line'
    }
  },
  methods: {
    drawLine () {
      let that = this
      console.log(that.global.ws)
      if (that.global.ws && that.global.ws.readyState === 1 && this.form.date !== '') {
        that.global.ws.send(JSON.stringify(this.form))
      }
      that.global.ws.onmessage = function (res) {
        var getdata = JSON.parse(res.data)
        func1(getdata)
      }
      let func2 = function func3 (val) {
        var getdata = val
        that.day = getdata.day
        that.max = getdata.max
        that.min = getdata.min
        alert('预测完毕！！！！')
      }
      let func1 = func2.bind(this)
    },
    draw () {
      if (this.type1) {
        this.option1 = 'line'
      } else {
        this.option1 = 'bar'
      }
      if (this.type2) {
        this.option2 = 'line'
      } else {
        this.option2 = 'bar'
      }
      this.chartColumn = echarts.init(document.getElementById('chartColumn'))
      var le = ['预测最高温', '预测最低温']
      this.chartColumn.setOption({
        title: {
          left: 'center',
          text: '气温预测'
        },
        toolbox: {
          show: true,
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        calculable: true,
        legend: {
          left: 'left',
          data: le
        },
        xAxis: {
          type: 'category',
          data: this.day,
          name: '日期', // x轴名称
          // x轴名称样式
          nameTextStyle: {
            fontWeight: 600,
            fontSize: 18
          }
        },
        dataZoom: [{
          type: 'inside',
          start: 0,
          end: 100
        },
        {
          start: 0,
          end: 10,
          handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
          handleSize: '80%',
          handleStyle: {
            color: '#fff',
            shadowBlur: 3,
            shadowColor: 'rgba(0, 0, 0, 0.6)',
            shadowOffsetX: 2,
            shadowOffsetY: 2
          }
        }],
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value} °C'
          },
          name: '温度',
          max: Math.ceil(Math.max.apply(null, this.max)),
          min: Math.floor(Math.min.apply(null, this.min)),
          nameTextStyle: {
            fontWeight: 100,
            fontSize: 18
          }
        },
        tooltip: {
          trigger: 'axis' // axis   item   none三个值
        },
        series: [
          {
            name: '预测最高温',
            data: this.max,
            markPoint: {
              data: [
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
              ]
            },
            markLine: {
              data: [
                {type: 'average', name: '平均值'}
              ]
            },
            type: this.option1
          },
          {
            name: '预测最低温',
            data: this.min,
            markPoint: {
              data: [
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
              ]
            },
            markLine: {
              data: [
                {type: 'average', name: '平均值'}
              ]
            },
            type: this.option2
          }
        ]
      })
    }
  }
}
</script>

<style>
</style>
