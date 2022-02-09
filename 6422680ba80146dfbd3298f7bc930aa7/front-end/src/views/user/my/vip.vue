<template>
  <Layout>
    <Content>
      <Row type="flex"
           justify="space-around">
        <Col span="10">
        <Card style="height:auto;margin-top:30px;">
          <p slot="title"
             style="text-align:left;font-size:20px;">会员卡</p>
          <div slot="extra">
            <a v-if="isVIP"
               @click="chargeModal">我要充值</a>
          </div>
          <div class="card-content">
            <div v-if="!isVIP"
                 style="font-size:16px">
              <p>您还不是会员</p>
              <p><a @click="buyModal">立即成为会员</a></p>
            </div>
            <div v-else>
              <div class="content">
                <div class="line">
                  <div><b>会员卡号：</b></div>
                  <div>{{VIPCard.id}}</div>
                </div>
                <div class="line">
                  <div><b>入会日期：</b></div>
                  <div>{{new Date(VIPCard.joinDate).toLocaleDateString()}}</div>
                </div>
                <div class="line">
                  <div><b>充值优惠：</b></div>
                  <div>{{VIPDescription}}</div>
                </div>
                <Divider></Divider>
                <div class="line">
                  <div><b>余额：</b></div>
                  <div>{{VIPCard.balance}}</div>
                </div>
              </div>
            </div>
          </div>
        </Card>
        <Card v-if="isVIP" style="margin-top:40px;">
          <p slot="title"
             style="text-align:left;font-size:20px;">充值记录</p>
          <Table :columns="columns"
                 :data="chargeRecords"
                 stripe>
          </Table>
        </Card>
        </Col>
      </Row>
      <Modal v-model="buyVIPModal"
             title="购买会员卡"
             @on-ok="buyVIPCard">
        <div style="margin-left:20px;">
          <p><b>单价:</b>&nbsp;{{VIPCardPrice}}</p>
          <p><b>当前充值优惠:</b>&nbsp;{{VIPDescription}}</p>
          <div class="bankCardForm">
            <Form :model="formData"
                  :label-width="80"
                  label-position="left">
              <br>
              <FormItem label="银行卡号">
                <Input v-model="formData.account"
                       placeholder="请输入银行卡号" />
              </FormItem>
              <br>
              <FormItem label="密码">
                <Input v-model="formData.password"
                       type="password"
                       placeholder="请输入密码" />
              </FormItem>
            </Form>
          </div>
        </div>
      </Modal>
      <Modal v-model="chargeVIPModal"
             title="会员卡充值"
             @on-ok="chargeVIPCard">
        <div style="margin-left:20px;">
          <p><b>当前充值优惠:</b>&nbsp;{{VIPDescription}}</p>
          <div class="bankCardForm">
            <Form :model="formData"
                  :label-width="80"
                  label-position="left">
              <br>
              <FormItem label="银行卡号">
                <Input v-model="formData.account"
                       placeholder="请输入银行卡号" />
              </FormItem>
              <br>
              <FormItem label="密码">
                <Input v-model="formData.password"
                       type="password"
                       placeholder="请输入密码" />
              </FormItem>
              <br>
              <FormItem label="金额">
                <Input v-model="formData.amount"
                       placeholder="请输入充值金额" />
              </FormItem>
            </Form>
          </div>
        </div>
      </Modal>
    </Content>
  </Layout>
</template>

<style>
.bankCardForm {
  width: 60%;
}
.line {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.content {
  font-size: 15px;
  width: 80%;
  margin: 0 auto;
}
.content .line {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin: 3px 0;
  margin-top: 10px;
}
</style>

<script>
export default {
  data () {
    return {
      isVIP: false,
      buyVIPModal: false,
      chargeVIPModal: false,
      VIPCardPrice: 0,
      VIPDescription: '',
      formData: {
        cardNumber: '',
        password: '',
        amount: ''
      },
      VIPCard: {},
      chargeRecords: [],
      columns: [
        {
          title: '充值时间',
          key: 'time',
          align: 'center',
          render: (h, params) => {
            return h('span', params.row.time.substring(0, 10) + ' ' + params.row.time.substring(11, 19))
          }
        },
        {
          title: '充值金额',
          key: 'amount',
          align: 'center'
        },
        {
          title: '赠额',
          key: 'gift',
          align: 'center'
        }
      ]
    }
  },
  mounted: function () {
    this.getVIPInfo()
  },
  methods: {
    getChargeRecords () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/records/selectChargeRecordByUserID/' + sessionStorage.getItem('id')
      }).then(function (res) {
        if (res.data.success) {
          that.chargeRecords = res.data.content
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    getVIPInfo () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vip/' + sessionStorage.getItem('id') + '/get'
      }).then(function (res) {
        if (res.data.success) {
          that.isVIP = true
          that.VIPCard = res.data.content
          that.getChargeRecords()
        } else {
          that.isVIP = false
        }
      }).catch(function (error) {
        alert(error)
      })

      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vip/getVIPInfo'
      }).then(function (res) {
        if (res.data.success) {
          that.VIPCardPrice = res.data.content.price
          that.VIPDescription = res.data.content.description
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    buyModal () {
      this.buyVIPModal = true
      this.formData.account = ''
      this.formData.password = ''
    },
    chargeModal () {
      console.log(111)
      this.chargeVIPModal = true
      this.formData.account = ''
      this.formData.password = ''
      this.formData.amount = ''
    },
    validateBuyForm () {
      let isValidate = true
      if (!this.formData.account) {
        isValidate = false
      }
      if (!this.formData.password) {
        isValidate = false
      }
      return isValidate
    },
    validateChargeForm () {
      let isValidate = true
      if (!this.formData.account) {
        isValidate = false
      }
      if (!this.formData.password) {
        isValidate = false
      }
      if (!this.formData.amount) {
        isValidate = false
      }
      return isValidate
    },
    buyVIPCard () {
      let that = this
      if (!this.validateBuyForm()) {
        alert('请输入银行卡号和密码')
        return
      }
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/vip/add?userId=' + sessionStorage.getItem('id'),
        data: {
          cardNumber: that.formData.account,
          password: that.formData.password
        }
      }).then((res) => {
        if (res.data.success) {
          that.getVIPInfo()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    chargeVIPCard () {
      let that = this
      if (!this.validateChargeForm()) {
        alert('请输入银行卡号、密码和金额')
        return
      }
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/vip/charge',
        data: {
          cardNumber: that.formData.account,
          password: that.formData.password,
          vipId: that.VIPCard.id,
          amount: that.formData.amount
        }
      }).then((res) => {
        if (res.data.success) {
          that.getVIPInfo()
          that.getChargeRecords()
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
