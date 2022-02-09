<template>
  <Layout>
    <Content>
      <Row type="flex"
           justify="space-around">
        <Col span="18"
             class="myTickets">
        <Card class="myTicketsCard">
          <p slot="title">我的电影票</p>
          <div class="myTicketsTable">
            <Table :columns="columns"
                   :data="myTickets"
                   stripe>
            </Table>
          </div>
        </Card>
        </Col>
      </Row>
    </Content>
  </Layout>
</template>

<style>
.myTicketsCard {
  margin-top: 20px;
}
</style>

<script>
export default {
  data () {
    return {
      modal: false,
      QRCodeUrl: '',
      columns: [
        {
          title: '电影名称',
          key: 'movieName',
          align: 'center'
        },
        {
          title: '影厅名',
          key: 'hallName',
          align: 'center'
        },
        {
          title: '座位',
          key: 'seat',
          align: 'center',
          render: (h, params) => {
            return h('span', (params.row.rowIndex + 1) + '排' + (params.row.columnIndex + 1) + '座')
          }
        },
        {
          title: '放映开始时间',
          key: 'startTime',
          align: 'center',
          render: (h, params) => {
            return h('span', params.row.startTime.split('T')[0] + ' ' + params.row.startTime.substring(11, 16))
          }
        },
        {
          title: '预计结束时间',
          key: 'endTime',
          align: 'center',
          render: (h, params) => {
            return h('span', params.row.endTime.split('T')[0] + ' ' + params.row.endTime.substring(11, 16))
          }
        },
        {
          title: '状态',
          key: 'state',
          align: 'center'
        },
        {
          title: ' ',
          key: 'refund',
          align: 'center',
          render: (h, params) => {
            if (params.row.state === '已完成' && params.row.canRefund === 1 && new Date(params.row.startTime) > new Date()) {
              return h('Button', {
                on: {
                  click: () => {
                    this.$Modal.confirm({
                      title: '退票',
                      content: '<p>电影名:&nbsp;' + params.row.movieName + '</p>' +
                        '<p>影厅名:&nbsp;' + params.row.hallName + '</p>' +
                        '<p>座位号:&nbsp;' + (params.row.rowIndex + 1) + '排' + (params.row.columnIndex + 1) + '座</p>' +
                        '<p>时间:&nbsp;' + params.row.startTime.split('T')[0] + ' ' + params.row.startTime.substring(11, 16) + ' ~ ' + params.row.endTime.substring(11, 16),
                      okText: '确认退票',
                      cancelText: '取消',
                      onOk: () => {
                        this.refundTicket(params.row.tickid)
                      }
                    })
                  }
                }
              }, '点击退票')
            } else if (params.row.state === '未完成') {
              return h('Button', {
                on: {
                  click: () => {
                    this.$router.push('/my/orders')
                  }
                }
              }, '前往订单')
            } else if (params.row.state === '已失效') {
              return h('span', ' ')
            }
          }
        }
      ],
      myTickets: []
    }
  },
  mounted: function () {
    this.getMyTickets()
  },
  methods: {
    getMyTickets () {
      let that = this
      this.$axios({
        methods: 'get',
        url: 'http://localhost:8080/ticket/get/' + sessionStorage.getItem('id')
      }).then(function (res) {
        if (res.data.success) {
          that.myTickets = res.data.content
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    refundTicket (ticketId) {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/ticket/refundTicket?ticketId=' + ticketId
      }).then(function (res) {
        if (res.data.success) {
          that.getMyTickets()
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
