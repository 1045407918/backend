<template>
  <Layout>
    <Content>
      <Row type="flex"
           justify="space-around">
        <Col span="18">
        <div style="width:90%;margin:0 auto;margin-top:20px;">
          <h1 style="text-align:left;">优惠券管理
          </h1>
        </div>
        <Divider></Divider>
        <Card>
          <div style="display:inline;text-align:left;">
            <p>最低消费额度&nbsp;&nbsp;
              <Input type="number"
                     style="width:30%"
                     v-model="consumption" />
              <Button type="primary"
                      style="float:right;"
                      @click="getUserList"
                      ghost>查询</Button>
            </p>
          </div>
        </Card>
        <Card style="margin-top:20px;">
          <Table :columns="columns"
                 :data="userList"
                 @on-selection-change="changeSelectedUserList">
            stripe>
          </Table>
        </Card>
        <div style="text-align:left;margin-top:20px;float:right;">
          <Select class="activitySelect"
                  v-model="selectedActivityId"
                  placeholder="选择赠送的优惠券对应的活动">
            <Option v-for="activity in activityList"
                    :value="activity.id"
                    :label="activity.name"
                    :key="activity.id" />
          </Select>
          <Button type="primary"
                  :disabled="disabled"
                  style="float:right;margin-top:10px;"
                  @click="sendCoupons"
                  ghost>
            赠送
          </Button>
        </div>
        </Col>
      </Row>
    </Content>
  </Layout>
</template>

<style>
.activitySelect {
  text-align: left;
}
</style>

<script>
export default {
  data () {
    return {
      disabled: true,
      consumption: '',
      userList: [],
      selectedUserList: [],
      activityList: [],
      selectedActivityId: '',
      columns: [
        {
          title: '用户ID',
          key: 'userId',
          align: 'center'
        },
        {
          title: 'VIP卡ID',
          key: 'vipCardId',
          align: 'center'
        },
        {
          title: '消费金额',
          key: 'amount',
          align: 'center'
        },
        {
          type: 'selection',
          align: 'center'
        }
      ]
    }
  },
  mounted: function () {
    this.getActivityList()
  },
  methods: {
    changeSelectedUserList (selection) {
      this.selectedUserList = []
      if (selection.length > 0) {
        this.disabled = false
        for (let i = 0; i < selection.length; i++) {
          this.selectedUserList.push(selection[i].userId)
        }
      } else {
        this.disabled = true
      }
    },
    sendCoupons () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/vip/giveCoupon',
        data: {
          userId: that.selectedUserList,
          activityId: that.selectedActivityId
        }
      })
    },
    getUserList () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vip/getUserByAmount?amount=' + that.consumption
      }).then(function (res) {
        if (res.data.success) {
          that.userList = res.data.content
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    getActivityList () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/activity/get'
      }).then(function (res) {
        if (res.data.success) {
          that.activityList = res.data.content
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
