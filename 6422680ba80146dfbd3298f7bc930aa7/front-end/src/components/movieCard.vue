<template>
  <Card class="movieCard">
    <p slot="title"
       class="movieCard-movieName">
      <Icon type="ios-film-outline" />&nbsp;{{movie.name}}
      <span v-if="movie.status==1"
            style="color:grey">&nbsp;已下架</span>
    </p>
    <p slot="extra"
       id="likeCount"
       v-if="movie.likeCount">
      <Icon type="md-heart"
            color="red" />&nbsp;{{movie.likeCount}}</p>

    <div class="movieCard-content">
      <div class="movieCard-content-poster">
        <img :src="movie.posterUrl"
             height="150px"
             width="100px" />
      </div>
      <div class="movieCard-content-movieInfo">
        <p>导演：{{movie.director}}</p>
        <p>主演：{{movie.starring}}</p>
        <p style="height:75px"
           v-if="movie.description.length > 100">简介：{{movie.description.substring(0,100) + '...'}}</p>
        <p style="height:75px"
           v-else>简介：{{movie.description}}</p>
        <div style="float:right">
          <Button type="primary"
                  @click="href"
                  ghost>查看电影详情</Button>
        </div>
      </div>
    </div>
  </Card>
</template>

<style>
.movieCard {
  margin-top: 10px;
  height: 270px;
  width: 800px;
  text-align: left;
}
.movieCard p {
  word-wrap: break-word;
  word-break: break-all;
  overflow: hidden;
}
.movieCard-movieName {
  margin-left: 10px;
  font-size: 50px;
}
.movieCard-content {
  position: relative;
}
.movieCard-content-poster {
  float: left;
}
.movieCard-content-movieInfo {
  float: left;
  width: 650px;
  padding-left: 16px;
}
.movieCard-content-movieInfo p {
  margin-top: 4px;
}
</style>

<script>
export default {
  data () {
    return {
    }
  },
  props: {
    movie: Object
  },
  methods: {
    href () {
      if (sessionStorage.getItem('role') === 'user') {
        this.$router.push({
          path: '/movie',
          query: {
            id: this.movie.id
          }
        })
      } else {
        this.$router.push({
          path: '/admin/movie/details',
          query: {
            id: this.movie.id
          }
        })
      }
    }
  }
}
</script>
