<template>
  <Layout>
    <Content class="layout-content">
      <Row type="flex"
           justify="space-around">
        <Col class="layout-content-row-col2"
             span="8">
        <Card class="layout-content-row-col2-card"
              :bordered="false">
          <p slot="title">欢迎登录CINEMA系统</p>
          <br>
          <Form class="layout-content-row-col2-card-form"
                :model="formData"
                :label-width="80"
                label-position="left">
            <br>
            <FormItem label="用户名">
              <Input v-model="formData.username"
                     placeholder="请输入用户名" />
            </FormItem>
            <br>
            <FormItem label="密码">
              <Input v-model="formData.password"
                     type="password"
                     placeholder="请输入密码" />
            </FormItem>
            <br>
            <FormItem>
              <Button long
                      type="primary"
                      ghost
                      @click="login">登录</Button>
            </FormItem>
            <br>
            <p>
              没有账号?&nbsp;
              <router-link to="register">点击注册</router-link>
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
.layout-content-row-col1 {
  background: white;
}
.layout-content-row-col2 {
  position: relative;
  height: 720px;
}
.layout-content-row-col2-card {
  margin-top: 100px;
}
.layout-content-row-col2-card-form {
  width: 80%;
  margin: 0 auto;
}
</style>

<script>
import myFooter from '@/components/footer'
export default {
  components: {
    myFooter
  },
  data () {
    return {
      formData: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    validateLoginForm () {
      let isValidate = true
      if (!this.formData.username) {
        isValidate = false
      }
      if (!this.formData.password) {
        isValidate = false
      }
      return isValidate
    },

    login () {
      let that = this
      if (!this.validateLoginForm()) {
        alert('请输入用户名和密码')
        return
      }
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/login',
        data: {
          username: that.formData.username,
          password: that.formData.password
        }
      }).then((res) => {
        if (res.data.success) {
          sessionStorage.setItem('username', that.formData.username)
          sessionStorage.setItem('id', res.data.content.id)
          this.$Message.success('登录成功')
          if (res.data.content.userlevel === 0) {
            that.$router.push('main')
            sessionStorage.setItem('role', 'user')
            sessionStorage.setItem('loginStatus', 'user')
          } else if (res.data.content.userlevel === 1) {
            that.$router.push('admin')
            sessionStorage.setItem('role', 'employee')
            sessionStorage.setItem('loginStatus', 'employee')
          } else if (res.data.content.userlevel === 2) {
            that.$router.push('admin')
            sessionStorage.setItem('role', 'admin')
            sessionStorage.setItem('loginStatus', 'admin')
          }
        } else {
          alert(res.data.content.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    }
  }
}
</script>
