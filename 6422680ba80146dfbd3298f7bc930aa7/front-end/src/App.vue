<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import index from './views/user/index.vue'
import myFooter from '@/components/footer'
export default {
  name: 'App',
  components: {
    index,
    myFooter
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
