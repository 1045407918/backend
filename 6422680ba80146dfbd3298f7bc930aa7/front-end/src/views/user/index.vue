<template>
  <Layout>
    <Header>
      <Menu v-bind:loginStatus="loginStatus"></Menu>
    </Header>
    <Content style="min-height: 600px">
      <div style="min-height: 200px;">
        <router-view></router-view>
      </div>
    </Content>
    <Footer>
      <myFooter></myFooter>
    </Footer>
  </Layout>
</template>

<script>
import Menu from '@/components/menu'
import movieCard from '@/components/movieCard'
import myFooter from '@/components/footer'
export default {
  components: {
    Menu,
    movieCard,
    myFooter
  },
  data () {
    return {
      loginStatus: 'guest'
    }
  },
  created: function () {
    this.loginStatus = sessionStorage.getItem('loginStatus')
  },
  methods: {
  }
}
</script>
