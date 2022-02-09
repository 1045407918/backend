<template>
  <Layout>
    <Content>
      <Row type="flex"
           justify="space-around">
        <Col span="16">
        <div class="recentMovies">
          <h1 style="margin-left:20px">近期电影</h1>
          <Divider></Divider>
          <div v-for="movie in movieList"
               :key="movie.id"
               style="display:inline">
            <router-link :to="'/movie?id=' + movie.id">
            <img :src="movie.posterUrl"
                 height="320px"
                 width="240px"
                 style="margin-right:40px;margin-top:20px;">
            </router-link>
          </div>
        </div>
        </Col>
        <Col span="6">
        <Card class="hotMoviesCard">
          <p slot="title">热映榜单</p>
          <div class="hotMovies">
            <ul>
              <li v-for="(movie,index) in hotMovies"
                  :key="movie.id">
                {{index+1 + '.&nbsp;' + movie.name}}
                <span style="float:right;">
                  <Icon type="ios-heart"
                        color="red" /></span>
              </li>
            </ul>
          </div>
        </Card>
        </Col>
      </Row>
    </Content>
  </Layout>
</template>

<style>
li {
  list-style-type: none;
  text-align: left;
  margin-left: 10px;
}
.recentMovies {
  margin-top: 20px;
  margin-left: 40px;
  text-align: left;
}
.hotMoviesCard {
  margin-top: 50px;
}
</style>

<script>
export default {
  data () {
    return {
      hotMovies: {},
      movieList: {}
    }
  },
  mounted: function () {
    this.getHotMovies()
    this.getMovieList()
  },
  methods: {
    getHotMovies () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/statistics/popular/movie?days=60&movieNum=10'
      }).then(function (res) {
        if (res.data.success) {
          that.hotMovies = res.data.content
        } else {
          alert(res.data.content)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    getMovieList () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/movie/all/exclude/off'
      }).then(function (res) {
        if (res.data.success) {
          that.movieList = res.data.content.slice(0, 6)
        } else {
          alert(res.data.content)
        }
      }).catch(function (error) {
        alert(error)
      })
    }
  }
}
</script>
