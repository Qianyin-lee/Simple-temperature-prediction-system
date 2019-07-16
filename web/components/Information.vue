<template>
  <div class="Information" align = 'center'>
    <el-row>
      <el-col :span="12">
        <div class="grid-content bg-purple">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="用户名" prop="name"  placeholder="请输入用户名">
              <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex" >
              <el-select v-model="ruleForm.sex" placeholder="请选择性别">
                <el-option label="男" value="1"></el-option>
                <el-option label="女" value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label-width = '100px' label="生日" required>
              <el-form-item prop="birth">
                <el-date-picker
                  v-model="ruleForm.birth"
                  type="date"
                  placeholder="选择日期"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
            </el-form-item>
            <el-form-item label-width = '100px' label="职业">
              <el-radio v-model="ruleForm.career" label="1">学生</el-radio>
              <el-radio v-model="ruleForm.career" label="2">IT行业</el-radio>
              <el-radio v-model="ruleForm.career" label="3">气象相关行业</el-radio>
              <el-radio v-model="ruleForm.career" label="4">其他行业</el-radio>
            </el-form-item>
            <el-form-item
              prop="email"
              label="邮箱"
              :rules="[
                { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
              ]"
            >
              <el-input v-model="ruleForm.email"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="save()">立即创建</el-button>
              <el-button type="primary" @click="search()">查询</el-button>
              <el-button @click="reset()">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="grid-content bg-purple-light"></div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  data () {
    return {
      ruleForm: {
        options: '5', // 保存
        id: '',
        type: '1',
        name: '',
        sex: '',
        birth: '',
        career: '',
        email: ''
      },
      form: {
        options: '6',
        id: '',
        type: '1'
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 6, message: '长度在 2 到 6 个字符', trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        birth: [
          {
            type: 'date',
            required: true,
            message: '请选择日期',
            trigger: 'change'
          }
        ]
      }
    }
  },
  mounted () {
    this.ruleForm.id = this.$route.query.id
    this.form.id = this.ruleForm.id
  },
  methods: {
    open1 () {
      const h = this.$createElement
      this.$notify({
        title: '保存成功',
        message: h('i', {style: 'color: teal'}, ''),
        type: 'success'
      })
    },
    save () {
      let that = this
      console.log(that.global.ws)
      if (that.global.ws && that.global.ws.readyState === 1) {
        that.global.ws.send(JSON.stringify(this.ruleForm))
      }
      that.global.ws.onmessage = function (res) {
        var getdata = JSON.parse(res.data)
        func1(getdata)
      }
      let func2 = function func3 (val) {
        that.open1()
      }
      let func1 = func2.bind(this)
    },
    search () {
      let that = this
      console.log(that.global.ws)
      if (that.global.ws && that.global.ws.readyState === 1) {
        that.global.ws.send(JSON.stringify(this.form))
      }
      that.global.ws.onmessage = function (res) {
        var getdata = JSON.parse(res.data)
        func1(getdata)
      }
      let func2 = function func3 (val) {
        var getdata = val
        that.ruleForm.name = getdata.name
        that.ruleForm.sex = getdata.sex
        that.ruleForm.birth = getdata.birth
        that.ruleForm.career = getdata.career
        that.ruleForm.email = getdata.mail
      }
      let func1 = func2.bind(this)
    },
    reset () {
      this.ruleForm.name = ''
      this.ruleForm.sex = ''
      this.ruleForm.birth = ''
      this.ruleForm.career = '4'
      this.ruleForm.email = ''
    }
  }
}
</script>
