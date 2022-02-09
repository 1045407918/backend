<template>
  <Layout>
    <Content class="layout-content">
      <Row type="flex"
           justify="space-around">
        <Col class="layout-content-row-col"
             span="8">
        <Card class="layout-content-row-col-card"
              :bordered="false">
          <p slot="title">欢迎使用CINEMA系统</p>
          <br>
          <Form class="layout-content-row-col-card-form"
                ref="formData"
                :model="formData"
                :rules="ruleValidate"
                :label-width="80"
                label-position="left">
            <br>
            <FormItem label="用户名"
                      prop="username">
              <Input v-model="formData.username"
                     placeholder="请输入用户名" />
            </FormItem>
            <br>
            <FormItem label="密码"
                      prop="password">
              <Input type="password"
                     v-model="formData.password"
                     placeholder="请输入密码" />
            </FormItem>
            <br>
            <FormItem label="确认密码"
                      prop="repassword">
              <Input type="password"
                     v-model="formData.repassword"
                     placeholder="请再次输入密码" />
            </FormItem>
            <br>
            <FormItem>
              <Button type="primary"
                      long
                      @click="handleSubmit('formData')">注册</Button>
            </FormItem>
            <br>
            <p>
              已有账号?&nbsp;
              <router-link to="login">点击登录</router-link>
            </p>
            <br>
          </Form>
        </Card>
        </Col>
      </Row>
    </Content>
    <Footer>
      <myFooter></myFooter>
    </Footer>
  </Layout>
</template>

<style>
.layout-content {
  margin-top: 16px;
}
.layout-content-row-col {
  position: relative;
  height: 720px;
}
.layout-content-row-col-card {
  position: relative;
  margin-top: 120px;
}
.layout-content-row-col-card-form {
  margin: 0 auto;
  width: 80%;
}
</style>

<script>
import myFooter from '@/components/footer'
export default {
  data () {
    const validateUsername = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('用户名不能为空'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('密码不能为空'))
      } else {
        callback()
      }
    }
    const validateRepassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('密码不能为空'))
      } else if (value !== this.formData.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      formData: {
        username: '',
        password: '',
        repassword: ''
      },
      ruleValidate: {
        username: [
          {
            validator: validateUsername,
            trigger: 'blur'
          }
        ],
        password: [
          {
            validator: validatePassword,
            trigger: 'blur'
          }
        ],
        repassword: [
          {
            validator: validateRepassword,
            trigger: 'blur'
          }
        ]
      }
    }
  },

  methods: {
    handleSubmit (name) {
      let that = this
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.$axios({
            method: 'post',
            url: 'http://localhost:8080/register',
            data: {
              username: that.formData.username,
              password: that.formData.password,
              secondPassword: that.formData.repassword
            }
          }).then((res) => {
            if (res.data.success) {
              this.$Message.success('注册成功')
            } else {
              alert(res.data.message)
            }
          }).catch(function (error) {
            alert(error)
          })
        } else {
          this.$Message.error('请检查输入的信息')
        }
      })
    }
  },

  components: {
    myFooter
  }
}
</script>
