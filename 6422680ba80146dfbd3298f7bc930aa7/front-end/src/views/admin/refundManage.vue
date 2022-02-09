<template>
  <Layout>
    <Content>
      <Row type="flex"
           justify="space-around">
        <Col span="18">
        <div style="width:90%;margin:0 auto;margin-top:20px;">
          <h1 style="text-align:left;">退票策略管理
            <Button type="primary"
                    @click="addStrategy">新增策略</Button>
          </h1>
        </div>
        <Divider></Divider>
        <div>
          <Card style="margin-top: 10px;">
            <div style="margin: 15px">
              <Table border
                     :columns="strategyTable"
                     :data="strategyData"
                     style="width:800px;"></Table>
            </div>
            <div style="text-align:left;margin-top:40px;float:right;">
              <Select class="strategySelect"
                      v-model="selectedStrategyId"
                      placeholder="选择生效的退票策略">
                <Option v-for="strategy in strategyData"
                        :disabled="strategy.inuse"
                        :value="strategy.id"
                        :label="strategy.time"
                        :key="strategy.id" />
              </Select>
              <Button type="primary"
                      style="float:right;margin-top:10px;"
                      @click="changeStrategy"
                      ghost>
                提交
              </Button>
            </div>
          </Card>
        </div>
        </Col>
      </Row>
      <Modal v-model="addStrategyModal"
             title="新增退票策略"
             @on-ok="handleAddStrategy">
        <div style="margin-left:20px;">
          <Form :model="toAddStrategy"
                :label-width="80"
                label-position="left">
            <FormItem label="时间">
              <Input v-model="toAddStrategy.time"
                     type="number"
                     placeholder="请输入可退票时间" />
            </FormItem>
          </Form>
        </div>
      </Modal>
      <Modal v-model="editStrategyModal"
             title="修改退票策略"
             @on-ok="handleEditStrategy">
        <div style="margin-left:20px;">
          <Form :model="toEditStrategy"
                :label-width="80"
                label-position="left">
            <FormItem label="时间">
              <Input v-model="toEditStrategy.time"
                     type="number"
                     placeholder="请输入可退票时间" />
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
      strategyTable: [
        {
          title: '退票策略ID',
          key: 'id',
          width: 200,
          align: 'center'
        },
        {
          title: '退票时间',
          key: 'time',
          width: 200,
          align: 'center'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 200,
          render: (h, params) => {
            return h('Button', {
              props: {
                type: 'primary',
                size: 'small'
              },
              style: {
                margin: '4px'
              },
              on: {
                click: () => {
                  this.editStrategy(params.row.id)
                }
              }
            }, '更改')
          }
        },
        {
          title: '生效中',
          key: 'inuse',
          align: 'center',
          render: (h, params) => {
            if (params.row.inUse === 1) {
              return h('span', '√')
            } else {
              return h('span', '×')
            }
          }
        }
      ],
      strategyData: [
        {
          id: 0,
          time: 1,
          inuse: true
        },
        {
          id: 1,
          time: 5,
          inuse: false
        },
        {
          id: 3,
          time: 10,
          inuse: false
        },
        {
          id: 4,
          time: 100,
          inuse: false
        }
      ],
      selectedStrategyId: '',
      addStrategyModal: false,
      toAddStrategy: {
        time: ''
      },
      editStrategyModal: false,
      toEditStrategy: {
        id: '',
        time: ''
      }
    }
  },
  mounted () {
    this.getStrategyData()
  },
  methods: {
    getStrategyData () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/ticket/refundStrategy/all'
      }).then(function (res) {
        if (res.data.success) {
          that.strategyData = res.data.content
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    addStrategy () {
      this.addStrategyModal = true
    },
    editStrategy (id) {
      this.editStrategyModal = true
      this.toEditStrategy.id = id
    },
    handleEditStrategy () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/ticket/refundStrategy/update',
        data: that.toEditStrategy
      }).then(function (res) {
        if (res.data.success) {
          that.$Message.success('修改成功')
          that.getStrategyData()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
      that.toEditStrategy = {
        id: '',
        time: ''
      }
    },
    handleAddStrategy () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/ticket/refundStrategy/add',
        data: that.toAddStrategy
      }).then(function (res) {
        if (res.data.success) {
          that.$Message.success('添加成功')
          that.getStrategyData()
        } else {
          alert(res.data.message)
        }
        that.toAddStrategy = {
          time: ''
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    changeStrategy () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/ticket/refundStrategy/use?id=' + that.selectedStrategyId
      }).then((res) => {
        if (res.data.success) {
          this.$Message.success('应用成功！')
          this.getStrategyData()
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
