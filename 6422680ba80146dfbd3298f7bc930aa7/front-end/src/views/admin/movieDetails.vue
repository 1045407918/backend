<template>
  <Layout>
    <Content class="content"
             style="width:90%;margin:0 auto;">
      <div class='movie-details'
           id="movie-details">
        <img id="movie-img"
             :src='movieDetails.posterUrl'>
        <div class="movie-basic-info">
          <div>
            <div>
              <div style="display:inline">
                <span class="title"
                      id="movie-name">{{movieDetails.name}}</span>
              </div>
              <div style="display:inline">
                <Button type="primary"
                        style="margin-left:20px;"
                        ghost
                        @click="showModifyMovieModal">修改</Button>
                <Button type="error"
                        style="margin-left:20px;"
                        ghost
                        @click="showOffMovieModal">下架</Button>
              </div>
              <div style="display:inline;float:right">
                <p id="likeCount">
                  <Icon type="md-heart"
                        color="red" />&nbsp;{{movieDetails.likeCount||0}}</p>
              </div>
            </div>
            <div class="movie-details">
              <div style="margin-top: 20px;">
                <span>简 介 ：</span>
                <p style="color:grey;">
                  <span id="movie-description">{{movieDetails.description}}</span>
                </p>
              </div>
              <div><span>上 映 ：</span><span id="movie-startDate">{{new Date(movieDetails.startDate).toLocaleDateString()}}</span></div>
              <div><span>类 型 ：</span><span id="movie-type">{{movieDetails.type}}</span></div>
              <div><span>地 区 ：</span><span id="movie-country">{{movieDetails.country}}</span></div>
              <div><span>导 演 ：</span><span id="movie-director">{{movieDetails.director}}</span></div>
              <div><span>主 演 ：</span><span id="movie-starring">{{movieDetails.starring}}</span></div>
              <div><span>编 剧 ：</span><span id="movie-writer">{{movieDetails.screenWriter}}</span></div>
            </div>
          </div>
        </div>
      </div>
      <div class="statistic-container">
        <Card>
          <div id="movie-likeDate-echarts"
               style="width: 800px;height:500px;"></div>
        </Card>
      </div>
      <Modal v-model="modifyMovieModal"
             title="修改电影信息"
             @on-ok="modifyMovie('modifyMovieForm')"
             :styles="{top: '20px'}">
        <div style="width:80%;margin:20px auto">
          <Form :model="modifyMovieForm"
                ref="modifyMovieForm"
                :label-width="80"
                label-position="left"
                :rules="ruleValidate">
            <FormItem label="电影名"
                      prop="name">
              <Input v-model="modifyMovieForm.name"
                     placeholder="必填" />
            </FormItem>
            <FormItem label="海报URL"
                      prop="posterUrl">
              <Input v-model="modifyMovieForm.posterUrl"
                     placeholder="必填" />
            </FormItem>
            <FormItem label="上映时间"
                      prop="startDate">
              <DatePicker v-model="modifyMovieForm.startDate"
                          type="date"
                          format="yyyy年MM月dd日"
                          placeholder="必填" />
            </FormItem>
            <FormItem label="片长"
                      prop="length">
              <Input v-model="modifyMovieForm.length"
                     type="number"
                     placeholder="必填" />
            </FormItem>
            <FormItem label="导演">
              <Input v-model="modifyMovieForm.director"
                     placeholder='有多人时用"/"分隔' />
            </FormItem>
            <FormItem label="编剧">
              <Input v-model="modifyMovieForm.screenWriter"
                     placeholder='有多人时用"/"分隔' />
            </FormItem>
            <FormItem label="主演">
              <Input v-model="modifyMovieForm.starring"
                     placeholder='有多人时用"/"分隔' />
            </FormItem>
            <FormItem label="类型">
              <Input v-model="modifyMovieForm.type" />
            </FormItem>
            <FormItem label="国家/地区">
              <Input v-model="modifyMovieForm.country" />
            </FormItem>
            <FormItem label="介绍">
              <Input v-model="modifyMovieForm.description"
                     type="textarea" />
            </FormItem>
          </Form>
        </div>
      </Modal>
    </Content>
  </Layout>
</template>

<style>
.content {
  text-align: 14px;
}
.movie-basic-info {
  flex: 1;
  padding: 20px 20px;
  height: 360px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  text-align: left;
}
#movie-img {
  float: left;
  margin: 15px 15px;
  height: 360px;
  width: 270px;
}
#movie-name {
  font-size: 20px;
}
#movie-description {
  max-height: 80px;
  min-height: 40px;
}
.statistic-container {
  margin-top: 60px;
  padding: 20px;
  display: flex;
  justify-content: center;
}
.movie-details div {
  margin-top: 5px;
}
</style>

<script>
export default {
  data () {
    const validateMovieName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('电影名称不能为空'))
      } else {
        callback()
      }
    }
    const validateMovieLength = (rule, value, callback) => {
      console.log(value)
      if (value === '') {
        callback(new Error('电影时长不能为空'))
      } else {
        callback()
      }
    }
    const validateMovieStartDate = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('电影上映时间不能为空'))
      } else {
        callback()
      }
    }
    const validateMoviePosterUrl = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('电影海报URL不能为空'))
      } else {
        callback()
      }
    }
    return {
      movieDetails: {},
      modifyMovieModal: false,
      offMovieModal: false,
      modifyMovieForm: {},
      ruleValidate: {
        name: [
          {
            validator: validateMovieName,
            trigger: 'blur'
          }
        ],
        length: [
          {
            validator: validateMovieLength,
            trigger: 'blur'
          }
        ],
        startDate: [
          {
            validator: validateMovieStartDate,
            trigger: 'blur'
          }
        ],
        posterUrl: [
          {
            validator: validateMoviePosterUrl,
            trigger: 'blur'
          }
        ]
      },
      movieLikeDate: []
    }
  },
  mounted: function () {
    this.getMovieLikeDate()
    this.getMovieInfo()
  },
  methods: {
    getMovieInfo () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/movie/' + this.$route.query.id + '/' + sessionStorage.getItem('id')
      }).then(function (res) {
        if (res.data.success) {
          that.movieDetails = res.data.content
          that.modifyMovieForm = that.deepCopy(that.movieDetails)
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    getMovieLikeDate () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/movie/' + this.$route.query.id + '/like/date'
      }).then(function (res) {
        if (res.data.success) {
          that.movieLikeDate = res.data.content
          that.drawEcharts()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    drawEcharts () {
      let dateArray = []
      let numberArray = []

      this.movieLikeDate.forEach((item) => {
        dateArray.push(item.likeTime)
        numberArray.push(item.likeNum)
      })

      let myChart = this.$echarts.init(document.getElementById('movie-likeDate-echarts'))

      // 指定图表的配置项和数据
      let option = {
        title: {
          text: '每日想看人数变化趋势'
        },
        xAxis: {
          type: 'category',
          data: dateArray
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: numberArray,
          type: 'line'
        }]
      }

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option)
    },
    showModifyMovieModal () {
      this.modifyMovieModal = true
    },
    showOffMovieModal () {
      this.$Modal.confirm({
        title: '二次确认',
        content: '<br /><p>注意！您的操作将不可逆地下架《' + this.movieDetails.name +
          '》。</p><br /><p>请再次确认您的操作。</p>',
        onOk: () => {
          this.offMovie()
        },
        onCancel: () => {
          this.$Message.info('取消下架')
        }
      })
    },
    modifyMovie (name) {
      console.log('modifyMovie')
      this.$refs[name].validate((valid) => {
        let that = this
        if (valid) {
          this.$axios({
            method: 'post',
            url: 'http://localhost:8080/movie/update',
            data: that.modifyMovieForm
          }).then((res) => {
            if (res.data.success) {
              this.$Message.success('修改成功')
              that.getMovieInfo()
              that.getMovieLikeDate()
            } else {
              alert(res.data.message)
            }
          }).catch(function (error) {
            alert(error)
          })
        } else {
          this.$Message.error('格式有误，请检查并重新填写')
        }
      })
    },
    offMovie () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/movie/off/batch',
        data: {
          movieIdList: [that.movieDetails.id]
        }
      }).then((res) => {
        if (res.data.success) {
          this.$Message.info('下架成功')
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    deepCopy (source) {
      let result = {}
      for (let key in source) {
        result[key] = typeof source[key] === 'object' ? '' : source[key]
      }
      return result
    }
  }
}
</script>
