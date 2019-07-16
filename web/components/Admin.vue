<template>
  <div id="app">
    <br>
    <!--搜索框-->
    <el-row>
        <el-col :span="1" class="grid">
            <el-button type="success" icon="el-icon-search" size="mini" @click="send()">查询</el-button>
        </el-col>
         <el-col :span="3" class="grid">
            <el-button type="success" icon="el-icon-delete" size="mini" @click="open">删除</el-button>
        </el-col>
    </el-row>
    <br>
      <el-table
    :data="tableData"
    style="width: 100%">
    <el-table-column
      label=""
      width="180">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.date }}</span>
      </template>
    </el-table-column>
    <el-table-column prop="id" label="账号" width="200">
    </el-table-column>
    <el-table-column
      label="姓名"
      width="180">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>姓名: {{ scope.row.name }}</p>
          <p>生日: {{ scope.row.birth }}</p>
          <p>邮箱: {{ scope.row.mail }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ scope.row.name }}</el-tag>
          </div>
        </el-popover>
      </template>
    </el-table-column>
    </el-table>
    <br>
    <!--新增按钮-->
    <el-col :span="1" class="grid">
      <el-button type="success"
        @click="(show1 = !show1), (can = false), (form1.type = '1'), (form1.id = ''), (form1.options = '1'), (form1.password = '')"
        round
        icon="el-icon-circle-plus-outline">新增</el-button>
        <el-collapse-transition>
          <div v-show="show1">
            <el-form :model="ruleForm" status-icon :rules="rules" ref="form" label-width="120px">
              <div class = "label1"><el-form-item></el-form-item></div>
              <el-form-item label='账号:' prop="id">
                <el-input
                  v-model.trim="form1.id"
                  style="width:300px"
                  placeholder="请输入用户名"
                ></el-input>
              </el-form-item>
              <el-form-item label='密码:' prop="password">
                <el-input
                  v-model.trim="form1.password"
                  style="width:300px"
                  placeholder="请输入密码"
                  show-password
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="success" round @click="add()">添加</el-button>
              </el-form-item>
          </el-form>
        </div>
      </el-collapse-transition>
    </el-col>
    <br>
  </div>
</template>
<script>
export default {
  data () {
    return {
      show1: false,
      show2: false,
      form: {
        options: '7'
      },
      form1: {
        options: '1',
        type: '1',
        id: '',
        password: ''
      },
      form3: {
        options: '8',
        id: ''
      },
      tableData: [],
      input: '',
      activeIndex: '1',
      activeIndex2: '1'
    }
  },
  methods: {
    open1 () {
      const h = this.$createElement
      this.$notify({
        title: '查询成功',
        message: h('i', {style: 'color: teal'}, ''),
        type: 'success'
      })
    },
    open2 () {
      const h = this.$createElement
      this.$notify({
        title: '增加成功',
        message: h('i', {style: 'color: teal'}, ''),
        type: 'success'
      })
    },
    open3 () {
      const h = this.$createElement
      this.$notify({
        title: '删除成功',
        message: h('i', {style: 'color: teal'}, ''),
        type: 'success'
      })
    },
    delete1 () {
      let that = this
      console.log(that.global.ws)
      if (that.global.ws && that.global.ws.readyState === 1) {
        that.global.ws.send(JSON.stringify(that.form3))
      }
      that.global.ws.onmessage = function (res) {
        var getdata = JSON.parse(res.data)
        func1(getdata)
      }
      let func2 = function func3 (val) {
        that.open3()
      }
      let func1 = func2.bind(this)
    },
    send () {
      let that = this
      console.log(that.global.ws)
      if (that.global.ws && that.global.ws.readyState === 1) {
        that.global.ws.send(JSON.stringify(that.form))
      }
      that.global.ws.onmessage = function (res) {
        var getdata = JSON.parse(res.data)
        func1(getdata)
      }
      let func2 = function func3 (val) {
        var getdata = val
        that.tableData = getdata
        this.show1 = false
        that.open1()
      }
      let func1 = func2.bind(this)
    },
    add () {
      let that = this
      console.log(that.global.ws)
      if (that.global.ws && that.global.ws.readyState === 1 && that.form1.name !== '' && that.form1.password !== '') {
        that.global.ws.send(JSON.stringify(that.form1))
      }
      that.global.ws.onmessage = function (res) {
        var getdata = JSON.parse(res.data)
        func1(getdata)
      }
      let func2 = function func3 (val) {
        that.open2()
      }
      let func1 = func2.bind(this)
    },
    open () {
      this.$prompt('请输入id', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        this.form3.id = value
        let that = this
        console.log(that.global.ws)
        if (that.global.ws && that.global.ws.readyState === 1) {
          that.global.ws.send(JSON.stringify(that.form3))
        }
        that.global.ws.onmessage = function (res) {
          var getdata = JSON.parse(res.data)
          func1(getdata)
        }
        let func2 = function func3 (val) {
          that.open3()
        }
        let func1 = func2.bind(this)
      })
    }
  }
}
</script>
<style>
  #app {
    font-family: Helvetica, sans-serif;
    text-align: center;
  }
</style>
