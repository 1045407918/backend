<template>
  <Layout>
    <Sider class="sider">
      <Menu theme="light"
            style="height:100%"
            accordion
            active-name="movieManage"
            :open-names="['cinema']">
        <img class='avatar'
             src="../../assets/avatar.jpg" />
        <div>
          <p>您目前的身份为</p>
          <p>{{role}}</p>
        </div>
        <div>
          <Button type="text"
                  @click="logout">注销</Button>
        </div>
        <Submenu name="cinema"
                 style="margin-top:20px;">
          <template slot="title">
            <Icon type="ios-paper" />
            影院业务
          </template>
          <MenuItem name="movieManage"
                    to="/admin/movie">电影管理</MenuItem>
          <MenuItem name="scheduleManage"
                    to="/admin/schedule">排片管理</MenuItem>
          <MenuItem name="cinemaManage"
                    to="/admin/cinema">影厅管理</MenuItem>
          <MenuItem name="refundManage"
                    to="/admin/refund">退票策略管理</MenuItem>
        </Submenu>
        <Submenu name="promotion">
          <template slot="title">
            <Icon type="ios-card" />
            促销管理
          </template>
          <MenuItem name="promotionManage"
                    to="/admin/promotion">活动管理</MenuItem>
          <MenuItem name="couponManage"
                    to="/admin/coupon">优惠券管理</MenuItem>
                    <MenuItem name="vipManage"
                    to="/admin/vip">会员卡管理</MenuItem>
        </Submenu>
        <Submenu name="stats">
          <template slot="title">
            <Icon type="ios-stats" />
            数据统计
          </template>
          <MenuItem name="statistic/scheduleRate"
                    to="/admin/statistic#schedule-rate-card-container">今日排片率</MenuItem>
          <MenuItem name="statistic/boxOffice"
                    to="/admin/statistic#box-office-card-container">所有电影票房</MenuItem>
          <MenuItem name="statistic/audiencePrice"
                    to="/admin/statistic#audience-price-card-container">每日客单价</MenuItem>
          <MenuItem name="statistic/placeRate"
                    to="/admin/statistic#place-rate-card-container">今日上座率</MenuItem>
          <MenuItem name="statistic/popularMovies"
                    to="/admin/statistic#popular-movie-card-container">热门电影</MenuItem>
        </Submenu>
        <Submenu name="staff">
          <template slot="title">
            <Icon type="ios-people" />
            用户管理
          </template>
          <MenuItem name="staffManage"
                    to="/admin/staff">员工管理</MenuItem>
        </Submenu>
      </Menu>
    </Sider>
    <Content class="admin-content">
      <router-view></router-view>
      <Footer>
        <myFooter></myFooter>
      </Footer>
    </Content>
  </Layout>
</template>

<style>
.sider {
  height: 1000px;
  position: fixed;
}
.avatar {
  height: 80px;
  width: 80px;
  border-radius: 50%;
  -webkit-border-radius: 50%;
  -moz-border-radius: 50%;
  margin: 40px auto;
}
.admin-content {
  margin-left: 200px;
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
      role: ''
    }
  },
  mounted: function () {
    let role = sessionStorage.getItem('role')
    if (role === 'admin') {
      this.role = '管理员'
    } else if (role === 'employee') {
      this.role = '员工'
    } else if (role === null) {
      this.$Message.error('请先进行登录')
      this.$router.push('/login')
    }
  },
  methods: {
    logout () {
      sessionStorage.removeItem('id')
      sessionStorage.removeItem('role')
      sessionStorage.removeItem('loginStatus')
      sessionStorage.removeItem('username')
      this.$router.push('/login')
    }
  }
}
</script>
