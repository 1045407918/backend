<template>
  <Layout>
    <Content>
      <Row type="flex"
           justify="space-around">
        <Col span="18">
        <div>
          <div style="width:90%;margin:0 auto;margin-top:20px;">
            <h1 style="text-align:left;">常规设置
            </h1>
          </div>
          <Divider></Divider>
          <Card style="margin-top: 10px;text-align:left;">
            <div style="margin-left: 20px;display: flex;">
              <div>用户可见排片日期：{{days}}天</div>
              <Button style="margin-left: auto"
                      type="primary"
                      @click="editDays">修改</Button>
            </div>
          </Card>
          <div style="width:90%;margin:0 auto;margin-top:20px;">
            <h1 style="text-align:left;">影厅管理
            </h1>
          </div>
          <Divider></Divider>
          <Card>
            <div style="margin-left: 20px;display: flex;">
              <Button type="primary"
                      @click="addHall">新增影厅</Button>
            </div>
            <div style="margin: 15px">
              <Table border
                     :columns="hallTable"
                     :data="hallData"></Table>
            </div>
          </Card>
        </div>
        </Col>
      </Row>
      <Modal v-model="daysModal"
             title="修改用户可见时间"
             @on-ok="handleEditDays">
        <div style="margin-left:50px;">
          <Input v-model="editedDays"
                 placeholder="输入新时间"
                 type="number"
                 style="width: 60%" />
        </div>
      </Modal>
      <Modal v-model="editHallModal"
             title="修改影厅信息"
             @on-ok="handleEditHall">
        <div style="margin-left:20px;">
          <Form :model="toEditHall"
                :label-width="80"
                label-position="left">
            <br>
            <FormItem label="影厅名称">
              <Input v-model="toEditHall.name"
                     placeholder="请输入影厅名称" />
            </FormItem>
            <br>
            <FormItem label="排数">
              <Input v-model="toEditHall.row"
                     type="number"
                     placeholder="请输入排数" />
            </FormItem>
            <br>
            <FormItem label="列数">
              <Input v-model="toEditHall.column"
                     type="number"
                     placeholder="请输入列数" />
            </FormItem>
          </Form>
        </div>
      </Modal>
      <Modal v-model="addHallModal"
             title="新增影厅信息"
             @on-ok="handleAddHall">
        <div style="margin-left:20px;">
          <Form :model="toAddHall"
                :label-width="80"
                label-position="left">
            <br>
            <FormItem label="影厅名称">
              <Input v-model="toAddHall.name"
                     placeholder="请输入影厅名称" />
            </FormItem>
            <br>
            <FormItem label="排数">
              <Input v-model="toAddHall.row"
                     type="number"
                     placeholder="请输入排数" />
            </FormItem>
            <br>
            <FormItem label="列数">
              <Input v-model="toAddHall.column"
                     type="number"
                     placeholder="请输入列数" />
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
      days: 0,
      daysModal: false,
      editedDays: '',
      hallTable: [
        {
          title: '影厅id',
          key: 'id'
        },
        {
          title: '影厅名称',
          key: 'name'
        },
        {
          title: '排数',
          key: 'row'
        },
        {
          title: '列数',
          key: 'column'
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
                    let temp = {
                      id: params.row.id,
                      name: params.row.name,
                      row: params.row.row,
                      column: params.row.column
                    }
                    this.toEditHall = temp
                    this.editHallModal = true
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
                      content: '<p>是否删除' + params.row.name + '?</p>',
                      okText: '确认',
                      cancelText: '取消',
                      onOk: () => {
                        this.deleteHall()
                      }
                    })
                  }
                }
              }, '删除')
            ])
          }
        }
      ],
      hallData: [],
      toEditHall: {},
      editHallModal: false,
      toAddHall: {
        name: '',
        row: '',
        column: ''
      },
      addHallModal: false
    }
  },
  mounted () {
    this.getHallInfo()
    this.getCanSeeDays()
  },
  methods: {
    getCanSeeDays () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/schedule/view'
      }).then(function (res) {
        if (res.data.success) {
          that.days = res.data.content
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    getHallInfo () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/hall/all'
      }).then(function (res) {
        if (res.data.success) {
          that.hallData = res.data.content
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    handleEditDays () {
      let temp = parseInt(this.editedDays)
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/schedule/view/set',
        data: {
          day: temp
        }
      }).then(function (res) {
        if (res.data.success) {
          that.getCanSeeDays()
        } else {
          alert(res.data.message)
        }
        that.editedDays = ''
      }).catch(function (error) {
        alert(error)
      })
    },
    editDays () {
      this.daysModal = true
    },
    deleteHall () {
      // 等杨弟弟接口
      this.getHallInfo()
    },
    handleEditHall () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/hall/changeHallInfo',
        data: that.toEditHall
      }).then(function (res) {
        if (res.data.success) {
          that.getHallInfo()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    addHall () {
      this.addHallModal = true
    },
    handleAddHall () {
      let that = this
      let temp = {
        name: this.toAddHall.name,
        row: parseInt(this.toAddHall.row),
        column: parseInt(this.toAddHall.column)
      }
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/hall/addHall',
        data: temp
      }).then(function (res) {
        if (res.data.success) {
          that.getHallInfo()
        } else {
          alert(res.data.message)
        }
        that.toAddHall = {
          name: '',
          row: '',
          column: ''
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
