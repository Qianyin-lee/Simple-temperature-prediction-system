<template>
  <div class="Login">
    <el-row>
      <el-col :span="8" :offset="8">
        <div class="grid-content bg-purple-light">
          <div align='center'>
            <img src="@/assets/logo1.jpg" height="350" width="350"/>
          </div>
          <h1 align='center'>{{ msg }}</h1>
          <div align='center'>
            <el-button
              @click="(show2 = !show2), (show1 = false), (show3 = false), (can = false), (form.type = '1'), (form.id = ''), (form.options = '1'), (form.password = '')"
              round
              type="warning"
              >用户注册</el-button
            >
            <el-button
              @click="(show1 = !show1), (show2 = false), (show3 = false), (can = false), (form.type = '1'), (form.id = ''), (form.options = '2'), (form.password = '')"
              type="primary"
              round
              >用户登录</el-button
            >
            <el-button
              @click="(show3 = !show3), (show1 = false), (show2 = false), (can = false), (form.type = '2'), (form.id = ''), (form.options = '2'), (form.password = '')"
              round
              >管理员登录</el-button
            >
          </div>
          <div style align='center'>
            <el-collapse-transition>
              <div v-show="show1">
                <el-form :model="ruleForm" status-icon :rules="rules" ref="form" label-width="120px">
                  <div class = "label1"><el-form-item></el-form-item></div>
                  <el-form-item label='账号:' prop="id">
                    <el-input
                      v-model.trim="form.id"
                      style="width:300px"
                      placeholder="请输入用户名"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label='密码:' prop="password">
                    <el-input
                      v-model.trim="form.password"
                      style="width:300px"
                      placeholder="请输入密码"
                      show-password
                    ></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="success" round @click="goHome">登 录</el-button>
                    <el-button type="danger" round @click="(can = false), (form.type = '1'), (form.id = ''), (form.options = '2'), (form.password = '')">重 置</el-button>
                  </el-form-item>
                  <div class = "label1"><el-form-item></el-form-item></div>
                </el-form>
              </div>
            </el-collapse-transition>
          </div>
          <div style align='center'>
            <el-collapse-transition>
              <div v-show="show2">
                <el-form :model="ruleForm" status-icon :rules="rules" ref="form" label-width="120px">
                  <el-form-item label='用户类型:'>
                    <el-radio v-model="form.type" label="1">用户</el-radio>
                    <el-radio v-model="form.type" label="2">管理员</el-radio>
                  </el-form-item>
                  <el-form-item label='用户账号:' prop="id">
                    <el-input
                      v-model.trim="form.id"
                      style="width:300px"
                      placeholder="请输入用户名"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label='用户密码:' prop="password">
                    <el-input
                      v-model.trim="form.password"
                      style="width:300px"
                      placeholder="请输入密码"
                      show-password
                    ></el-input>
                  </el-form-item>
                  <el-form-item label='确认密码:' prop="password">
                    <el-input
                      v-model.trim="password1"
                      style="width:300px"
                      placeholder="请再次输入密码"
                      show-password
                    ></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="success" round @click="goHome">注 册</el-button>
                    <el-button type="danger" round @click="(can = false), (form.type = '1'), (form.id = ''), (form.options = '1'), (form.password = '')">重 置</el-button>
                  </el-form-item>
                  <div class = "label1"><el-form-item></el-form-item></div>
                </el-form>
              </div>
            </el-collapse-transition>
          </div>
          <div style align='center'>
            <el-collapse-transition>
              <div v-show="show3">
                <el-form :model="ruleForm" status-icon :rules="rules" ref="form" label-width="120px">
                  <div class = "label1"><el-form-item></el-form-item></div>
                  <el-form-item label='管理员账号:' prop="id">
                    <el-input
                      v-model.trim="form.id"
                      style="width:300px"
                      placeholder="请输入用户名"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label='管理员密码:' prop="password">
                    <el-input
                      v-model.trim="form.password"
                      style="width:300px"
                      placeholder="请输入密码"
                      show-password
                    ></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="success" round @click="goHome">登 录</el-button>
                    <el-button type="danger" round @click="(can = false), (form.type = '2'), (form.id = ''), (form.options = '2'), (form.password = '')">重 置</el-button>
                  </el-form-item>
                  <div class = "label1"><el-form-item></el-form-item></div>
                </el-form>
              </div>
            </el-collapse-transition>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  name: 'Login',
  data () {
    var validateid = (rule, value, callback) => {
      if (this.form.id === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.form.id !== '') {
          this.$refs.form.validateField('id')
        }
        callback()
      }
    }
    var validatepass = (rule, value, callback) => {
      if (this.form.password === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.form.password !== '') {
          this.$refs.form.validateField('password')
        }
        callback()
      }
    }
    return {
      msg: '基于Spark技术的气温预测分析系统',
      form: {
        options: '2',
        type: '1',
        id: '',
        password: ''
      },
      password1: '',
      backgroundImage: 'url(' + require('../assets/back.jpg') + ')',
      show1: false,
      show2: false,
      show3: false,
      visible: true,
      can: false,
      ready: false,
      text: '',
      data: '',
      answer: '',
      massage: '',
      websocket: '',
      rules: {
        id: [
          { validator: validateid, trigger: 'blur' }
        ],
        password: [
          { validator: validatepass, trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    if ('WebSocket' in window) {
      this.initWebSocket()
    } else {
      alert('当前浏览器 Not support websocket')
    }
  },
  beforeDestroy () {
    this.onbeforeunload()
  },
  methods: {
    open1 () {
      const h = this.$createElement
      this.$notify({
        title: '登录成功',
        message: h('i', {style: 'color: teal'}, ''),
        type: 'success'
      })
    },
    open2 () {
      const h = this.$createElement
      this.$notify.error({
        title: '登录失败',
        message: h('i', {style: 'color: teal'}, '登录失败!用户名或密码错误')
      })
    },
    open3 () {
      const h = this.$createElement
      this.$notify({
        title: '注册成功',
        message: h('i', {style: 'color: teal'}, ''),
        type: 'success'
      })
    },
    open4 () {
      const h = this.$createElement
      this.$notify.error({
        title: '注册失败',
        message: h('i', {style: 'color: teal'}, '注册失败！当前用户名已被注册')
      })
    },
    open5 () {
      const h = this.$createElement
      this.$notify.error({
        title: '注册失败',
        message: h('i', {style: 'color: teal'}, '两次密码不一致')
      })
    },
    goHome: function () {
      let that = this
      console.log(that.global.ws)
      if (that.global.ws && that.global.ws.readyState === 1) {
        if (that.form.password === that.password1 || that.form.options === '2') {
          that.global.ws.send(JSON.stringify(this.form))
        } else {
          that.open5()
        }
      }
      that.global.ws.onmessage = function (res) {
        var getdata = JSON.parse(res.data)
        func1(getdata)
      }
      let func2 = function func3 (val) {
        this.answer = val.answer
        if (this.answer === 'yes2' && this.form.type === '1') {
          that.open1()
          this.$router.push({path: '/Option', query: { id: this.form.id }})
        }
        if (this.answer === 'yes2' && this.form.type === '2') {
          that.open1()
          this.$router.push({path: '/OptionA', query: { id: this.form.id }})
        }
        if (this.answer === 'no2') {
          that.open2()
          that.id = ''
          that.password = ''
        }
        if (this.answer === 'yes1' && that.form.type === '1') {
          that.open3()
          this.$router.push({path: '/Option', query: { id: this.form.id }})
        }
        if (this.answer === 'yes1' && that.form.type === '2') {
          that.open3()
          this.$router.push({path: '/OptionA', query: { id: this.form.id }})
        }
        if (this.answer === 'no1') {
          that.open4()
          this.id = ''
          this.password = ''
        }
      }
      let func1 = func2.bind(this)
    }
  }
}
</script>

<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>

<style>
.label1 {
  margin-bottom: 2px;
  text-align: center;
  color: rgb(5, 5, 5);
  padding: 10px 10px;
  box-sizing: border-box;
  margin-right: 20px;
}
</style>

<style>
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #ffffff00;
  }
  .bg-purple {
    background: #ffffff00;
  }
  .bg-purple-light {
    background: #ffffff00;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc00;
  }
</style>
