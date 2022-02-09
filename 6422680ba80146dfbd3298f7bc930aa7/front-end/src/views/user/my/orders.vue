<template>
  <Layout>
    <Content>
      <Row type="flex"
           justify="space-around">
        <Col span="18">
        <div style="width:90%;margin:0 auto;margin-top:20px;">
          <h1 style="text-align:left;">订单记录
          </h1>
        </div>
        <Divider></Divider>
        <Card>
          <Table :columns="columns"
                 :data="orderList"></Table>
        </Card>
        </Col>
      </Row>
    </Content>
  </Layout>
</template>

<script>
export default {
  data () {
    return {
      orderList: [],
      columns: [
        {
          title: '订单编号',
          key: 'orderID',
          align: 'center'
        },
        {
          title: '创建时间',
          key: 'create_time',
          align: 'center',
          render: (h, params) => {
            return h('span', params.row.create_time.substring(0, 10) + ' ' + params.row.create_time.substring(11, 19))
          }
        },
        {
          title: '原价',
          key: 'initial_amount',
          align: 'center'
        },
        {
          title: '完成时间',
          key: 'payed_time',
          align: 'center',
          render: (h, params) => {
            if (params.row.state === 1) {
              return h('span', params.row.payed_time.substring(0, 10) + ' ' + params.row.payed_time.substring(11, 19))
            } else {
              return h('span', '订单未完成')
            }
          }
        },
        {
          title: '实付',
          key: 'actual_payed_amount',
          align: 'center',
          render: (h, params) => {
            if (params.row.state === 1 && params.row.actual_payed_time !== '') {
              return h('span', params.row.actual_payed_amount)
            } else if (params.row.state === 0) {
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
                    this.$router.push({
                      path: '/order',
                      query: {
                        id: params.row.movieID,
                        scheduleId: params.row.scheduleID,
                        orderId: params.row.orderID,
                        isUnfinished: true
                      }
                    })
                  }
                }
              }, '前去支付')
            } else {
              return h('span', '订单超时已取消')
            }
          }
        }
      ]
    }
  },
  mounted: function () {
    this.getOrderList()
  },
  methods: {
    getOrderList () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/records/getOrderRecordByUser/' + sessionStorage.getItem('id')
      }).then(function (res) {
        if (res.data.success) {
          that.orderList = res.data.content
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
