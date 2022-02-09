<template>
  <Layout>
    <Content>
      <Row type="flex"
           justify="space-around">
        <Col span="18">
        <div style="width:90%;margin:0 auto;margin-top:20px;">
          <h1 style="text-align:left;">会员卡价格
          </h1>
        </div>
        <Divider></Divider>
        <div>
          <Card>
            <Form inline
                  style="margin-top:10px;margin-left:40px;text-align:left;"
                  :label-width="100"
                  label-position="left">
              <FormItem label="会员卡价格:">
                <Input v-model="vipCardPrice"
                       type="number"
                       width="30%" />
              </FormItem>
              <FormItem>
                <Button type="primary"
                        @click="changeVIPCardPrice">修改价格</Button>
              </FormItem>
            </Form>
          </Card>
        </div>
        <div style="width:90%;margin:0 auto;margin-top:40px;">
          <h1 style="text-align:left;">会员充值策略管理
            <Button type="primary"
                    @click="addStrategy">新增策略</Button>
          </h1>
        </div>
        <Divider></Divider>
        <div>
          <Card style="margin-top: 10px;align-items: center;">
            <div style="margin: 15px">
              <Table border
                     :columns="strategyTable"
                     :data="strategyData"
                     style="width:800px;"></Table>
            </div>
          </Card>
        </div>
        </Col>
      </Row>
      <Modal v-model="addStrategyModal"
             title="新增充值策略"
             @on-ok="handleAddStrategy">
        <div style="margin-left:20px;">
          <Form :model="toAddStrategy"
                :label-width="20"
                label-position="left"
                inline>
            <FormItem label="满">
              <Input v-model="toAddStrategy.quota"
                     type="number"
                     width="30%" />
            </FormItem>
            <FormItem label="送">
              <Input v-model="toAddStrategy.gift"
                     type="number"
                     width="30%" />
            </FormItem>
          </Form>
        </div>
      </Modal>
      <Modal v-model="editStrategyModal"
             title="修改充值策略"
             @on-ok="handleEditStrategy">
        <div style="margin-left:20px;">
          <Form :model="toAddStrategy"
                :label-width="20"
                label-position="left"
                inline>
            <FormItem label="满">
              <Input v-model="toEditStrategy.quota"
                     type="number"
                     width="30%" />
            </FormItem>
            <FormItem label="送">
              <Input v-model="toEditStrategy.gift"
                     type="number"
                     width="30%" />
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
          title: '充值策略ID',
          key: 'id',
          width: 200,
          align: 'center'
        },
        {
          title: '内容',
          width: 400,
          align: 'center',
          render: (h, params) => {
            return h('span', '满' + params.row.quota + '送' + params.row.gift)
          }
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
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
                    this.editStrategy(params.row.id)
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
                      content: '<p>是否删除该策略?</p>',
                      okText: '确认',
                      cancelText: '取消',
                      onOk: () => {
                        this.deleteStrategy(params.row.id)
                      }
                    })
                  }
                }
              }, '删除')
            ])
          }
        }
      ],
      strategyData: [],
      addStrategyModal: false,
      editStrategyModal: false,
      toAddStrategy: {
        quota: '',
        gift: ''
      },
      toEditStrategy: {
        id: '',
        quota: '',
        gift: ''
      },
      vipCardPrice: ''
    }
  },
  mounted () {
    this.getStrategyData()
    let that = this
    this.$axios({
      method: 'get',
      url: 'http://localhost:8080/vip/getVIPInfo'
    }).then(function (res) {
      if (res.data.success) {
        that.vipCardPrice = res.data.content.price
      } else {
        alert(res.data.message)
      }
    }).catch(function (error) {
      alert(error)
    })
  },
  methods: {
    getStrategyData () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vip/getAllDiscountStrategy'
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
    handleAddStrategy () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/vip/addDiscountStrategy',
        data: that.toAddStrategy
      }).then(function (res) {
        if (res.data.success) {
          that.$Message.success('添加成功')
          that.getStrategyData()
        } else {
          alert(res.data.message)
        }
        that.toAddStrategy = {
          quota: '',
          gift: ''
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    handleEditStrategy () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/vip/updateDiscountStrategy',
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
    },
    deleteStrategy (id) {
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/vip/deleteDiscountStrategy/' + id
      }).then((res) => {
        if (res.data.success) {
          this.getStrategyData()
          this.$Message.success('删除成功')
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    changeVIPCardPrice () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/vip/setVIPCardPrice',
        data: {
          price: that.vipCardPrice
        }
      })
    }
  }
}
</script>
