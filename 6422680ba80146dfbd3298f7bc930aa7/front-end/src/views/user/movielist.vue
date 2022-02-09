<template>
  <Layout>
    <Content class="layout-content">
      <Row type="flex"
           justify="space-around">
        <Col span="14">
        <div style="width:80%;margin:0 auto;margin-top:20px;">
          <h1 style="text-align:left;">电影清单
            <Switch size="large"
                  @on-change="switchMovieList"
                  style="margin-left:50px;">
            <span slot="open">全部</span>
            <span slot="close">在映</span>
            </Switch>
          </h1>
        </div>
        <Divider></Divider>
        <ul v-if="trigger">
          <li v-for="item in movieList"
              v-bind:key="item.id">
            <movieCard :movie="item"></movieCard>
          </li>
        </ul>
        <ul v-else>
          <li v-for="item in excludeOffMovieList"
              v-bind:key="item.id">
            <movieCard :movie="item"></movieCard>
          </li>
        </ul>
        </Col>
      </Row>
    </Content>
  </Layout>
</template>

<script>
import movieCard from '@/components/movieCard'
export default {
  data () {
    return {
      trigger: true,
      movieList: [],
      excludeOffMovieList: []
    }
  },
  components: {
    movieCard
  },
  mounted: function () {
    let that = this
    this.$axios({
      method: 'get',
      url: 'http://localhost:8080/movie/all'
    }).then(function (res) {
      if (res.data.success) {
        that.movieList = res.data.content
      } else {
        alert(res.data.content)
      }
    }).catch(function (error) {
      alert(error)
    })
    this.$axios({
      method: 'get',
      url: 'http://localhost:8080/movie/all/exclude/off'
    }).then(function (res) {
      if (res.data.success) {
        that.excludeOffMovieList = res.data.content
      } else {
        alert(res.data.content)
      }
    }).catch(function (error) {
      alert(error)
    })
  },
  methods: {
    switchMovieList (status) {
      this.trigger = status
    }
  }
}
</script>
