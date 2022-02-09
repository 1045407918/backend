<template>
  <Layout>
    <Content>
      <Row type="flex"
           justify="space-around">
        <Col span="18">
        <div style="width:90%;margin:0 auto;margin-top:20px;">
          <h1 style="text-align:left;">员工管理
            <Button type="primary"
                    @click="addUser">新增员工</Button>
          </h1>
        </div>
        <Divider></Divider>
        <div>
          <Card style="margin-top: 10px;">
            <div style="margin: 15px">
              <Table border
                     :columns="userTable"
                     :data="userData"></Table>
            </div>
          </Card>
        </div>
        </Col>
      </Row>
      <Modal v-model="addUserModal"
             title="新增员工信息"
             @on-ok="handleAddUser">
        <div style="margin-left:20px;">
          <Form :model="toAddUser"
                :label-width="80"
                label-position="left">
            <br>
            <FormItem label="用户名">
              <Input v-model="toAddUser.username"
                     placeholder="请输入用户名" />
            </FormItem>
            <br>
            <FormItem label="密码">
              <Input v-model="toAddUser.password"
                     type="password"
                     placeholder="请输入密码" />
            </FormItem>
            <br>
            <FormItem label="员工等级">
              <Select v-model="toAddUser.userlevel"
                      style="width:200px">
                <Option v-for="item in levelList"
                        :value="item.value"
                        :key="item.value"
                        :label="item.levelStr">{{ item.levelStr }}</Option>
              </Select>
            </FormItem>
          </Form>
        </div>
      </Modal>
      <Modal v-model="editUserModal"
             title="修改员工信息"
             @on-ok="handleEditUser">
        <div style="margin-left:20px;">
          <Form :model="toEditUser"
                :label-width="80"
                label-position="left">
            <br>
            <FormItem label="员工等级">
              <Select v-model="toEditUser.userlevel"
                      style="width:200px">
                <Option v-for="item in levelList"
                        :value="item.value"
                        :key="item.value"
                        :label="item.levelStr">{{ item.levelStr }}</Option>
              </Select>
            </FormItem>
          </Form>
        </div>
      </Modal>
    </Content>
  </Layout>
</template>

<script>
export default {
  data () {
    return {
      userTable: [
        {
          title: '员工id',
          key: 'id'
        },
        {
          title: '用户名',
          key: 'username'
        },
        {
          title: '员工等级',
          key: 'levelStr'
        },
        {
          title: '操作',
          key: 'action',
          width: 140,
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  margin: '4px'
                },
                on: {
                  click: () => {
                    this.editUser(params.row.username)
                  }
                }
              }, '更改'),
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  margin: '4px'
                },
                on: {
                  click: () => {
                    this.$Modal.confirm({
                      title: '删除',
                      content: '<p>是否删除' + params.row.username + '?</p>',
                      okText: '确认',
                      cancelText: '取消',
                      onOk: () => {
                        this.deleteUser(params.row.id)
                      }
                    })
                  }
                }
              }, '删除')
            ])
          }
        }
      ],
      userData: [],
      addUserModal: false,
      toAddUser: {
        username: '',
        password: '',
        userlevel: ''
      },
      editUserModal: false,
      toEditUser: {
        username: '',
        userlevel: ''
      },
      levelList: [
        {
          value: 0,
          levelStr: '普通用户'
        },
        {
          value: 1,
          levelStr: '普通员工'
        },
        {
          value: 2,
          levelStr: '管理员'
        }
      ]
    }
  },
  mounted () {
    if (sessionStorage.getItem('role') !== 'admin') {
      this.$Message.warning('只有管理员能进行账号管理')
      this.$router.push('/admin/movie')
    }
    this.getUserData()
  },
  methods: {
    getUserData () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/staff/all'
      }).then(function (res) {
        if (res.data.success) {
          let temp = res.data.content
          for (let i = 0; i < temp.length; i++) {
            if (temp[i].userlevel === 1) {
              temp[i].levelStr = '普通员工'
            } else {
              temp[i].levelStr = '管理员'
            }
          }
          that.userData = temp
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    addUser () {
      this.addUserModal = true
    },
    editUser (username) {
      this.toEditUser.username = username
      this.editUserModal = true
    },
    handleEditUser () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/staff/update',
        data: that.toEditUser
      }).then(function (res) {
        if (res.data.success) {
          that.$Message.success('修改成功')
          that.getUserData()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    handleAddUser () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/staff/add',
        data: that.toAddUser
      }).then(function (res) {
        if (res.data.success) {
          that.$Message.success('添加成功')
          that.getUserData()
        } else {
          alert(res.data.message)
        }
        that.toAddUser = {
          username: '',
          password: '',
          userlevel: ''
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    deleteUser (id) {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/staff/del/' + id
      }).then(function (res) {
        if (res.data.success) {
          that.$Message.success('删除成功')
          that.getUserData()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    }
  }
}
</script>

<style scoped>
</style>
