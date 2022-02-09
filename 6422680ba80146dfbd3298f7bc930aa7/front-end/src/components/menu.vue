<template>
  <Menu class="menu"
        mode="horizontal"
        theme="dark">
    <div class="menu-left">
      <MenuItem name="LOGO" to="/main"><b>CINEMA</b></MenuItem>
      <MenuItem name="movielist" to="/movielist">所有电影</MenuItem>
    </div>
    <div class="menu-right"
         v-if="loginStatus != 'user'">
      <MenuItem name="search">
      <Input v-model="searchKey"
             placeholder="请输入搜索关键词"
             icon="ios-search"
             @click="search" />
      </MenuItem>
      <MenuItem name="login"
                to="/login">登录</MenuItem>
      <MenuItem name="register"
                to="/register">注册</MenuItem>
    </div>
    <div class="menu-right"
         v-else>
      <MenuItem name="search">
      <Input v-model="searchKey"
             placeholder="请输入搜索关键词"
             icon="ios-search"
             @on-click="search" />
      </MenuItem>
      <Submenu name="profile">
        <template slot="title">
          <Icon type="md-person" />我的账户
        </template>
        <MenuItem name="myTickets" to='/my/tickets'>我的影票</MenuItem>
        <MenuItem name="myOrders" to='/my/orders'>我的订单</MenuItem>
        <MenuItem name="myVIP" to='/my/vip'>我的会员</MenuItem>
        <MenuItem name="myCoupons" to='/my/coupons'>我的优惠券</MenuItem>
        <MenuItem name="logout" v-on:click.native="logout()">注销登录</MenuItem>
      </Submenu>
    </div>
  </Menu>
</template>

<style>
.menu {
  position: relative;
}
.menu-search {
  float: right;
  margin-right: 20px;
}
.menu-right {
  float: right;
  margin: 0 auto;
  margin-right: 20px;
}
</style>

<script>
export default {
  name: 'menu',
  props: ['loginStatus'],
  data () {
    return {
      searchKey: ''
    }
  },
  methods: {
    logout () {
      sessionStorage.removeItem('loginStatus')
      sessionStorage.removeItem('id')
      this.$router.push('/login')
    },
    search () {
      if (this.searchKey === '') {
        this.$Message.warning('关键字不能为空')
        return
      }
      let that = this
      this.$router.push({
        path: '/search',
        query: {
          keyword: that.searchKey
        }
      })
    }
  }
}
</script>
