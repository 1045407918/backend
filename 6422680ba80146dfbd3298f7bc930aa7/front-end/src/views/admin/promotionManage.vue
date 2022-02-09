<template>
  <Layout>
    <Content class="layout-content" style="min-height: 600px">
      <Row type="flex"
           justify="space-around">
        <Col span="18">
          <div style="width:90%;margin:0 auto;margin-top:20px;">
            <h1 style="text-align:left;">活动管理
              <Switch size="large"
                      v-model="showSwitch"
                      @on-change="switchActivity"
                      style="margin-left:50px;">
                <span slot="open">可用</span>
                <span slot="close">全部</span>
              </Switch>
              <Button type="primary"
                      style="float:right;margin-top:10px;"
                      @click="addActivity"
                      ghost>
                <Icon type="md-add-circle" />&nbsp;添加活动</Button>
            </h1>
          </div>
          <Divider></Divider>
          <ul>
            <li v-for="item in showList"
                v-bind:key="item.id">
              <activityCard v-bind:activity="item"></activityCard>
            </li>
          </ul>
        </Col>
      </Row>
      <Modal v-model="addActivityModal"
             title="新增活动"
             @on-ok="handleAddActivity('activityForm')"
             :styles="{top: '20px'}">
        <div style="width:100%;margin:20px auto">
          <Form :model="activityForm"
                ref="activityForm"
                :label-width="100"
                label-position="left"
                :rules="ruleValidate">
            <FormItem label="活动名称"
                      prop="name">
              <Input v-model="activityForm.name"
                     placeholder="输入活动名称" />
            </FormItem>
            <FormItem label="活动介绍">
              <Input v-model="activityForm.description"
                     placeholder="输入活动介绍(非必须)" />
            </FormItem>
            <FormItem label="起止时间" :required="isRequired">
              <Row>
                <Col span="11">
                  <FormItem prop="startTime">
                    <DatePicker type="date" placeholder="开始日期" v-model="activityForm.startTime"></DatePicker>
                  </FormItem>
                </Col>
                <Col span="2" style="text-align: center">-</Col>
                <Col span="11">
                  <FormItem prop="endTime">
                    <DatePicker type="date" placeholder="结束日期" v-model="activityForm.endTime"></DatePicker>
                  </FormItem>
                </Col>
              </Row>
            </FormItem>
            <FormItem label="优惠券名称"
                      prop="couponName">
              <Input v-model="activityForm.couponName"
                     placeholder="输入优惠券名称" />
            </FormItem>
            <FormItem label="优惠券描述"
                      prop="couponDescription">
              <Input v-model="activityForm.couponDescription"
                     placeholder="输入优惠券描述" />
            </FormItem>
            <FormItem label="需满金额"
                      prop="targetAmount">
              <Input v-model="activityForm.targetAmount"
                     type="number"
                     placeholder='输入金额' />
            </FormItem>
            <FormItem label="折扣金额"
                      prop="discountAmount">
              <Input v-model="activityForm.discountAmount"
                     type="number"
                     placeholder='输入金额' />
            </FormItem>
            <FormItem label="参与电影">
               <FormItem
                 v-for="item in selectedMovie"
                 :key="item.index"
                 :label="'电影' + (item.index+1)">
                 <Row>
                   <Col span="18">
                     <Select v-model="item.id" style="width:200px">
                       <Option v-for="movie in movieList" :value="movie.id" :key="movie.id" :label="movie.name">{{movie.name}}</Option>
                     </Select>
                   </Col>
                   <Col span="4" offset="1">
                     <Button @click="handleRemove(item.index)" v-bind:disabled="selectedMovie.length > (item.index + 1) || selectedMovie.length === 1">删除</Button>
                   </Col>
                 </Row>
               </FormItem>
            </FormItem>
            <FormItem>
              <Row>
                <Col span="12">
                  <Button type="dashed" long @click="handleAdd" icon="md-add">添加</Button>
                </Col>
              </Row>
            </FormItem>
          </Form>
        </div>
      </Modal>
    </Content>
  </Layout>
</template>

<script>
import activityCard from '@/components/activityCard'
export default {
  components: {
    activityCard
  },
  data () {
    return {
      isRequired: true,
      showSwitch: false,
      activityList: [],
      movieList: [],
      showList: [],
      addActivityModal: false,
      activityForm: {
        name: '',
        description: '',
        startTime: '',
        endTime: '',
        movieList: [],
        couponName: '',
        couponDescription: '',
        targetAmount: '',
        discountAmount: ''
      },
      selectedMovie: [
        {
          index: 0,
          id: 0
        }
      ],
      ruleValidate: {
        name: [
          { required: true, message: '活动名称不能为空', trigger: 'blur' }
        ],
        startTime: [
          { required: true, type: 'date', message: '请选择日期', trigger: 'change' }
        ],
        endTime: [
          { required: true, type: 'date', message: '请选择日期', trigger: 'change' }
        ],
        couponName: [
          { required: true, message: '优惠券名称不能为空', trigger: 'blur' }
        ],
        couponDescription: [
          { required: true, message: '优惠券描述不能为空', trigger: 'blur' }
        ],
        targetAmount: [
          { required: true, message: '请输入有效数字', trigger: 'blur' }
        ],
        discountAmount: [
          { required: true, message: '请输入有效数字', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    this.getActivityList()
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
          that.movieList.push({
            id: 0,
            name: '所有电影'
          })
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    getActivityList () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/activity/get'
      }).then(function (res) {
        if (res.data.success) {
          that.activityList = res.data.content
          that.transShowList()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    switchActivity () {
      this.transShowList()
    },
    addActivity () {
      this.addActivityModal = true
    },
    transShowList () {
      let tempList = []
      for (let i = 0; i < this.activityList.length; i++) {
        let tempItem = this.activityList[i]
        let movieStr = ''
        if (this.activityList[i].movieList.length === 0) {
          movieStr = '所有在映电影'
        } else {
          this.activityList[i].movieList.forEach(function (movie) {
            movieStr += movie.name + '<br>'
          })
        }
        tempItem.movieStr = movieStr
        tempItem.timeStr = this.activityList[i].startTime.substring(0, 10) + '至' + this.activityList[i].endTime.substring(0, 10)
        tempList.push(tempItem)
      }
      if (this.showSwitch) {
        let onlyUseful = []
        for (let i = 0; i < tempList.length; i++) {
          let startTimeStamp = new Date(tempList[i].startTime)
          let endTimeStamp = new Date(tempList[i].endTime)
          let today = new Date()
          if ((today.getTime() - endTimeStamp.getTime()) <= 0 && (today.getTime() - startTimeStamp.getTime()) > 0) {
            onlyUseful.push(tempList[i])
          }
        }
        this.showList = onlyUseful
      } else {
        this.showList = tempList
      }
    },
    handleAdd () {
      this.selectedMovie.push(
        {
          index: this.selectedMovie.length,
          id: Number
        }
      )
    },
    handleRemove (index) {
      let selectedMovie = this.selectedMovie
      let tempList = this.selectedMovie.slice(0, index)
      for (let i = index + 1; i < selectedMovie; i++) {
        tempList.push({
          index: (i - 1),
          id: selectedMovie[i].id
        })
      }
      this.selectedMovie = tempList
    },
    handleAddActivity (name) {
      this.$refs[name].validate((valid) => {
        let that = this
        if (valid) {
          // 发起请求
          this.$axios({
            method: 'post',
            url: 'http://localhost:8080/activity/publish',
            data: that.createActivityFrom()
          }).then(function (res) {
            if (res.data.success) {
              that.getActivityList()
            } else {
              alert(res.data.message)
            }
            // 刷新表单
            that.selectedMovie = [
              {
                index: 0,
                id: 0
              }
            ]
            that.activityForm = {
              name: '',
              description: '',
              startTime: '',
              endTime: '',
              movieList: [],
              coupon: {
                name: '',
                description: '',
                targetAmount: '',
                discountAmount: ''
              }
            }
          }).catch(function (error) {
            alert(error)
          })
        } else {
          this.$Message.error('Fail!')
        }
      })
    },
    createActivityFrom () {
      let tempList = []
      if (this.selectedMovie.length === 1) {
        if (this.selectedMovie[0].id !== 0) {
          tempList.push(this.selectedMovie[0].id)
        }
      } else {
        this.selectedMovie.forEach(function (item) {
          tempList.push(item.id)
        })
      }
      let result = {
        name: this.activityForm.name,
        description: this.activityForm.description,
        startTime: this.activityForm.startTime,
        endTime: this.activityForm.endTime,
        movieList: tempList,
        couponForm: {
          name: this.activityForm.couponName,
          description: this.activityForm.couponDescription,
          startTime: this.activityForm.startTime,
          endTime: this.activityForm.endTime,
          targetAmount: this.activityForm.targetAmount,
          discountAmount: this.activityForm.discountAmount
        }
      }
      console.log(result)
      return result
    }
  }

}
</script>

<style scoped>

</style>
