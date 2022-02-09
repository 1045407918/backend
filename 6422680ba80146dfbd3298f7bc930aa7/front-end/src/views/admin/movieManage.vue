<template>
  <Layout>
    <Content class="layout-content">
      <Row type="flex"
           justify="space-around">
        <Col span="18">
        <div style="width:90%;margin:0 auto;margin-top:20px;">
          <h1 style="text-align:left;">电影管理
            <Switch size="large"
                    @on-change="switchMovieList"
                    style="margin-left:50px;">
              <span slot="open">全部</span>
              <span slot="close">在映</span>
            </Switch>
            <Button type="primary"
                    style="float:right;margin-top:10px;"
                    @click="showAddMovieModal"
                    ghost>
              <Icon type="md-add-circle" />&nbsp;添加电影</Button>
          </h1>
        </div>
        <Divider></Divider>
        <ul v-if="trigger" style="margin-left:20px;">
          <li v-for="item in movieList"
              v-bind:key="item.id">
            <movieCard :movie="item"></movieCard>
          </li>
        </ul>
        <ul v-else style="margin-left:20px;">
          <li v-for="item in excludeOffMovieList"
              v-bind:key="item.id">
            <movieCard :movie="item"></movieCard>
          </li>
        </ul>
        </Col>
      </Row>
      <Modal v-model="addMovieModal"
             title="上架电影"
             @on-ok="addMovie('addMovieForm')"
             :styles="{top: '20px'}">
        <div style="width:80%;margin:20px auto">
          <Form :model="addMovieForm"
                ref="addMovieForm"
                :label-width="80"
                label-position="left"
                :rules="ruleValidate">
            <FormItem label="电影名"
                      prop="name">
              <Input v-model="addMovieForm.name"
                     placeholder="必填" />
            </FormItem>
            <FormItem label="海报URL"
                      prop="posterUrl">
              <Input v-model="addMovieForm.posterUrl"
                     placeholder="必填" />
            </FormItem>
            <FormItem label="上映时间"
                      prop="startDate">
              <DatePicker v-model="addMovieForm.startDate"
                          type="date"
                          format="yyyy年MM月dd日"
                          placeholder="必填" />
            </FormItem>
            <FormItem label="片长"
                      prop="length">
              <Input v-model="addMovieForm.length"
                     type="number"
                     placeholder="必填" />
            </FormItem>
            <FormItem label="导演">
              <Input v-model="addMovieForm.director"
                     placeholder='有多人时用"/"分隔' />
            </FormItem>
            <FormItem label="编剧">
              <Input v-model="addMovieForm.screenWriter"
                     placeholder='有多人时用"/"分隔' />
            </FormItem>
            <FormItem label="主演">
              <Input v-model="addMovieForm.starring"
                     placeholder='有多人时用"/"分隔' />
            </FormItem>
            <FormItem label="类型">
              <Input v-model="addMovieForm.type" />
            </FormItem>
            <FormItem label="国家/地区">
              <Input v-model="addMovieForm.country" />
            </FormItem>
            <FormItem label="介绍">
              <Input v-model="addMovieForm.description"
                     type="textarea" />
            </FormItem>
          </Form>
        </div>
      </Modal>
    </Content>
  </Layout>
</template>

<style>
li {
  list-style-type: none;
}
</style>

<script>
import movieCard from '@/components/movieCard'
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
      trigger: true,
      movieList: [],
      excludeOffMovieList: [],
      addMovieModal: false,
      addMovieForm: {
        name: '',
        posterUrl: '',
        director: '',
        screenWriter: '',
        starring: '',
        type: '',
        country: '',
        language: '',
        startDate: '',
        length: '',
        description: ''
      },
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
      }
    }
  },
  components: {
    movieCard
  },
  mounted: function () {
    this.getMovieList()
  },
  methods: {
    getMovieList () {
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
    switchMovieList (status) {
      this.trigger = status
    },
    showAddMovieModal () {
      this.addMovieModal = true
    },
    addMovie (name) {
      console.log('addMovie')
      this.$refs[name].validate((valid) => {
        let that = this
        if (valid) {
          this.$axios({
            method: 'post',
            url: 'http://localhost:8080/movie/add',
            data: that.addMovieForm
          }).then((res) => {
            if (res.data.success) {
              this.$Message.success('添加成功')
              that.getMovieList()
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
    }
  }
}
</script>
