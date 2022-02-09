<template>
  <Card style="min-height: 600px;margin: 15px">
    <div style="min-height: 500px">
      <Steps :current="step"
             size="small"
             style="margin-top: 15px">
        <Step title="选择座位"></Step>
        <Step title="确认订单"></Step>
        <Step title="支付完成"></Step>
      </Steps>
      <!--选择座位-->
      <Row v-if="step == 0">
        <Col span="16">
        <Card style="margin-top: 50px;width: 80%;min-height: 400px">
          <h3 style="margin-bottom: 10px">选择座位</h3>
          <div style="position:relative;right: 100px">
            <div class="selected-seat"
                 style="display: inline-block;position: relative;top:10px"></div>
            已选
          </div>
          <div style="position:relative;right: 100px;margin-bottom: 10px">
            <div class="unselected-seat"
                 style="display: inline-block;position: relative;top:10px"></div>
            未选
          </div>
          <div v-for="row in seatsArray.length"
               v-bind:key="row">
            <div v-for="col in seatsArray[0].length"
                 v-bind:key="col"
                 style="display: inline-block">
              <div v-bind:class="seatsArray[row-1][col-1]===1?'selected-seat':(seatsArray[row-1][col-1]===0?'unselected-seat':'add-seat')"
                   @click="addSeat(row-1,col-1)">
              </div>
            </div>
          </div>
        </Card>
        </Col>
        <Col span="8">
        <div style="margin-top: 50px;width: 80%;min-height: 400px">
          <div style=" display: flex;flex-direction: row; align-items: center;">
            <img v-bind:src="posterUrl"
                 style="width: 150px;height: 200px" />
            <Divider type="vertical"
                     style="height: 200px;margin-left: 20px" />
            <div style="display: flex;flex-direction: column; align-items: flex-start;padding-top: 20px;margin-left: 10px">
              <div style="margin-bottom: 8px">片名: {{movieName}}</div>
              <div style="margin-bottom: 8px">语言: {{movieLanguage}}</div>
              <div style="margin-bottom: 8px">类型：{{movieType}}</div>
              <div style="margin-bottom: 8px">片长：{{movieLength}}分钟</div>
              <div style="margin-bottom: 8px">票价：{{ticketPrice}}元/张</div>
            </div>
          </div>
          <Divider style="width: 180px"></Divider>
          <div style=" display: flex;flex-direction: row; align-items: center;">
            <div style="display: flex;flex-direction: column; align-items: flex-start;margin-left: 10px">
              <div style="margin-bottom: 3px">影厅: {{hallName}}</div>
              <div style="margin-bottom: 3px">场次：{{scheduleTime}}</div>
              <div style="margin-bottom: 3px">座位：{{pickedSeatsToStr}}</div>
            </div>
          </div>
          <Button type="primary"
                  size="large"
                  style="margin-top: 20px"
                  v-on:click="lockSeats"
                  v-bind:disabled="pickedSeats.length === 0">确定选座</Button>
        </div>
        </Col>
      </Row>
      <!--支付订单-->
      <div v-else-if="step === 1"
           style="margin-top: 50px;padding: 10px;">
        <Table :columns="orderTable"
               :data="tableInfo"
               style="min-height: 80px"></Table>
        <div style="display: flex;justify-content: flex-start">
          <div style="margin-top: 15px">
            <Select v-model="selectedCouponIndex"
                    style="width:200px;"
                    v-bind:disabled="couponList.length === 0"
                    v-bind:placeholder="couponList.length === 0?'无可用优惠券':'请选择优惠券'"
                    @on-change="couponSelect()">
              <Option v-for="coupon in couponList"
                      :value="coupon.index"
                      :key="coupon.index"
                      :label="coupon.name">
                <span>{{coupon.name}}</span>
                <span v-if="coupon.index !== 0"
                      style="float:right;color:#ccc">满{{coupon.targetAmount}}减{{coupon.discountAmount}}</span>
              </Option>
            </Select>
          </div>
          <div style="margin-top: 15px;margin-left: 20px">
            <RadioGroup v-model="selectedPay"
                        type="button">
              <Radio label="银行卡">
                <Icon type="ios-card-outline" />
                <span>银行卡支付</span>
              </Radio>
              <Radio label="会员卡"
                     v-bind:disabled="!isVip">
                <Icon type="md-contact" />
                <span>会员卡支付</span>
              </Radio>
            </RadioGroup>
          </div>
          <div style="margin-top: 15px;margin-left: auto;margin-right: 100px;display: flex;flex-direction: column;align-items: flex-start">
            <div>总金额：{{tableInfo[0].totalPrice}}</div>
            <div>优惠金额：{{discountAmount}}元</div>
          </div>
        </div>
        <Divider style="width: 60%"></Divider>
        <div style="display: flex;flex-direction: column;align-items: flex-end;margin-right: 100px;">
          <div>实际付款：{{ticketPrice*pickedSeats.length-discountAmount}}元</div>
          <Button size="large"
                  style="margin-top: 10px"
                  type="primary"
                  v-on:click="handleConfirm">确定支付</Button>
        </div>
      </div>
      <!--订单完成-->
      <div v-else-if="step === 2"
           style="margin-top: 50px;padding: 10px">
        <div>
          <Icon type="md-checkmark-circle-outline"
                size="300" />
        </div>
        <div>购买成功，请至<router-link to="/my/orders">我的电影票</router-link>中查看</div>
      </div>
    </div>
    <!--银行卡支付modal-->
    <Modal v-model="bankCardModal"
           title="银行卡支付"
           @on-ok="handleBankCard">
      <div style="margin-left:20px;">
        <div>
          <Form :model="bankCardData"
                :label-width="80"
                label-position="left">
            <br>
            <FormItem label="银行卡号">
              <Input v-model="bankCardData.account"
                     placeholder="请输入银行卡号" />
            </FormItem>
            <br>
            <FormItem label="密码">
              <Input v-model="bankCardData.password"
                     type="password"
                     placeholder="请输入密码" />
            </FormItem>
          </Form>
        </div>
      </div>
    </Modal>
  </Card>
</template>
<style>
.selected-seat {
  background: url("../../assets/selected-seat.png") center center no-repeat;
  background-size: 100% 100%;
  width: 24px;
  height: 24px;
  margin: 2px;
  border: none;
  outline: none;
}
.unselected-seat {
  background: url("../../assets/unselected-seat.png") center center no-repeat;
  background-size: 100% 100%;
  width: 24px;
  height: 24px;
  margin: 2px;
  border: none;
  outline: none;
}
.add-seat {
  background: url("../../assets/add-seat.png") center center no-repeat;
  background-size: 100% 100%;
  width: 24px;
  height: 24px;
  margin: 2px;
  border: none;
  outline: none;
}
</style>

<script>
export default {
  name: 'ticketOrder',
  data () {
    return {
      scheduleId: '',
      step: 0,
      seatsArray: [[0, 0, 0, 0, 0], [0, 0, 0, 0, 0]],
      posterUrl: '',
      movieName: '',
      movieLanguage: '',
      movieType: '',
      movieLength: 0,
      hallName: '',
      startTime: '',
      endTime: '',
      ticketPrice: 0,
      pickedSeats: [],
      isVip: false,
      vipInfo: Object, //
      orderTable: [
        { title: '电影', key: 'movieName' },
        { title: '影厅', key: 'hallName' },
        { title: '场次', key: 'scheduleTime' },
        { title: '座位', key: 'seats' },
        { title: '单价', key: 'ticketPrice' },
        { title: '总价', key: 'totalPrice' }
      ],
      totalPrice: 0,
      tableInfo: [],
      couponList: [],
      discountAmount: 0,
      selectedCouponIndex: '',
      selectedPay: '银行卡',
      selectedCouponId: 0,
      bankCardModal: false,
      bankCardData: {
        account: '',
        password: ''
      },
      selectedTicketId: []
    }
  },
  computed: {
    scheduleTime () {
      return this.startTime.substring(5, 10) + ' ' + this.startTime.substring(11, 16) + ' 至 ' + this.endTime.substring(5, 10) + ' ' + this.endTime.substring(11, 16)
    },
    pickedSeatsToStr () {
      let result = ''
      if (this.pickedSeats.length === 0) {
        result = '还未选择座位'
      } else {
        for (let i = 0; i < this.pickedSeats.length; i++) {
          result = result + (this.pickedSeats[i].rowIndex + 1) + '排' + (this.pickedSeats[i].columnIndex + 1) + '座' + ' '
        }
      }
      return result
    }
  },
  mounted () {
    this.initSeats(this.$route.query.scheduleId)
    this.initMovieDetail(this.$route.query.id)
    this.getStep(this.$route.query.isUnfinished, this.$route.query.orderId)
    this.initVIP()
    this.initCoupon()
  },
  methods: {
    // 获取会员信息
    initVIP () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vip/' + sessionStorage.getItem('id') + '/get'
      }).then(function (res) {
        if (res.data.success) {
          that.isVip = true
          that.vipInfo = res.data.content
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    // 获取座位信息
    initSeats (scheduleId) {
      this.scheduleId = scheduleId
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/ticket/get/occupiedSeats?scheduleId=' + scheduleId
      }).then(function (res) {
        if (res.data.success) {
          that.seatsArray = res.data.content.seats
          that.hallName = res.data.content.scheduleItem.hallName
          that.ticketPrice = res.data.content.scheduleItem.fare
          that.startTime = res.data.content.scheduleItem.startTime
          that.endTime = res.data.content.scheduleItem.endTime
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    // 获取电影信息
    initMovieDetail (movieId) {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/movie/' + movieId + '/' + sessionStorage.getItem('id')
      }).then(function (res) {
        if (res.data.success) {
          that.movieName = res.data.content.name
          that.posterUrl = res.data.content.posterUrl
          that.movieLanguage = res.data.content.language
          that.movieType = res.data.content.type
          that.movieLength = res.data.content.length
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    // 处理选择座位和取消
    addSeat (row, col) {
      let pickedArray = []
      let tempArray = this.seatsArray
      if (this.seatsArray[row][col] === 0) {
        tempArray[row][col] = 3
      } else if (this.seatsArray[row][col] === 3) {
        tempArray[row][col] = 0
      }
      this.seatsArray = tempArray.slice()
      for (let i = 0; i < this.seatsArray.length; i++) {
        for (let j = 0; j < this.seatsArray[0].length; j++) {
          if (this.seatsArray[i][j] === 3) {
            pickedArray.push({
              rowIndex: i,
              columnIndex: j
            })
          }
        }
      }
      this.pickedSeats = pickedArray.slice()
    },
    // 锁定座位获取orderInfo；并获取会员卡信息
    lockSeats () {
      let that = this
      // 锁定座位
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/ticket/lockSeat',
        data: {
          userId: sessionStorage.getItem('id'),
          scheduleId: that.scheduleId,
          seats: that.pickedSeats
        }
      }).then(function (res) {
        if (res.data.success) {
          console.log(res.data.content)
          that.initOrder(res.data.content, false)
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    initCoupon () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/coupon/' + sessionStorage.getItem('id') + '/get'
      }).then(function (res) {
        if (res.data.success) {
          let tempCoupons = []
          tempCoupons.push({
            index: 0,
            name: '不使用奖券',
            targetAmount: 0,
            discountAmount: 0
          })
          for (let i = 0; i < res.data.content.length; i++) {
            let temp = res.data.content[i]
            temp.index = i + 1
            tempCoupons.push(temp)
          }
          that.couponList = tempCoupons
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    // 根据orderInfo处理
    initOrder (orderInfo) {
      this.step = 1
      let tempTable = [{
        movieName: this.movieName,
        hallName: this.hallName,
        scheduleTime: this.scheduleTime,
        seats: this.pickedSeatsToStr,
        ticketPrice: this.ticketPrice + '元',
        totalPrice: orderInfo.initial_amount
      }]
      this.totalPrice = orderInfo.initial_amount
      this.tableInfo = tempTable
      this.getTicketIdByOrder(orderInfo.orderID)
    },
    // 选择优惠券
    couponSelect () {
      console.log(this.selectedCouponIndex)
      if (typeof (this.selectedCouponIndex) !== 'function') {
        if (this.couponList[this.selectedCouponIndex].targetAmount <= this.totalPrice) {
          this.discountAmount = this.couponList[this.selectedCouponIndex].discountAmount
        }
        if (this.selectedCouponIndex !== 0) {
          this.selectedCouponId = this.couponList[this.selectedCouponIndex].id
        }
      }
    },
    // 确认，当用会员卡购买时自动发送请求
    handleConfirm () {
      let that = this
      if (this.selectedPay === '银行卡') {
        this.bankCardModal = true
      } else {
        this.$axios({
          method: 'post',
          url: 'http://localhost:8080/ticket/vip/buy?ticketId=' + that.selectedTicketId + '&couponId=' + that.selectedCouponId
        }).then(function (res) {
          if (res.data.success) {
            that.step = 2
          } else {
            alert(res.data.message)
          }
        }).catch(function (error) {
          alert(error)
        })
      }
    },
    // 用银行卡购买
    handleBankCard () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/ticket/buy?ticketId=' + that.selectedTicketId + '&couponId=' + that.selectedCouponId,
        data: {
          cardNumber: that.bankCardData.account,
          password: that.bankCardData.password
        }
      }).then(function (res) {
        if (res.data.success) {
          that.step = 2
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    getTicketIdByOrder (orderId) {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/ticket/getByOrderID/' + orderId
      }).then(function (res) {
        if (res.data.success) {
          let tempSeats = []
          let tempList = []
          for (let i = 0; i < res.data.content.length; i++) {
            tempSeats.push(
              {
                rowIndex: res.data.content[i].rowIndex,
                columnIndex: res.data.content[i].columnIndex
              }
            )
            tempList.push(res.data.content[i].id)
          }
          that.pickedSeats = tempSeats
          that.selectedTicketId = tempList
          that.refreshTable()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    getStep (isUnfinished, orderId) {
      if (isUnfinished) {
        let that = this
        this.$axios({
          method: 'get',
          url: 'http://localhost:8080/records/getOrderRecord/' + orderId
        }).then(function (res) {
          if (res.data.success) {
            that.initOrder(res.data.content)
          } else {
            alert(res.data.message)
          }
        }).catch(function (error) {
          alert(error)
        })
      }
    },
    refreshTable () {
      let tempTable = [{
        movieName: this.movieName,
        hallName: this.hallName,
        scheduleTime: this.scheduleTime,
        seats: this.pickedSeatsToStr,
        ticketPrice: this.ticketPrice + '元',
        totalPrice: this.totalPrice + '元'
      }]
      this.tableInfo = tempTable
    }
  }
}
</script>

<style scoped>
</style>
