<template>
  <Layout>
    <Content class="layout-content">
      <Row type="flex"
           justify="space-around">
        <Col span="14">
        <div style="width:80%;margin:0 auto;margin-top:20px;">
          <h1 style="text-align:left;">查找结果
          </h1>
          <p style="text-align:left;">查找关键字"<b>{{this.$route.query.keyword}}</b>"，共{{movieList.length}}个结果</p>
        </div>
        <Divider></Divider>
        <ul>
          <li v-for="item in movieList"
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
      movieList: []
    }
  },
  components: {
    movieCard
  },
  mounted: function () {
    this.getSearchResult()
  },
  methods: {
    getSearchResult () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/movie/search?keyword=' + that.$route.query.keyword
      }).then(function (res) {
        if (res.data.success) {
          that.movieList = res.data.content
        } else {
          alert(res.data.content)
        }
      }).catch(function (error) {
        alert(error)
      })
    }
  },
  watch: {
    // 如果路由发生变化，再次执行该方法
    '$route': 'getSearchResult'
  }
}
</script>
