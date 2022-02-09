<template>
  <Layout>
    <Row type="flex"
         justify="space-around">
      <Col span="18">
      <div style="width:90%;margin:0 auto;margin-top:20px;">
        <h1 style="text-align:left;">排片管理
        </h1>
      </div>
      <Divider></Divider>
      <div>
        <Card>
          <div style="display: flex;justify-content: center">
            <Form inline>
              <FormItem>
                <DatePicker v-model="selectedDate"
                            type="date"
                            format="yyyy年MM月dd日"
                            placeholder="选择日期"></DatePicker>
              </FormItem>
              <FormItem>
                <Select class="hallSelect"
                        v-model="selectedHallId">
                  <Option v-for="hall in hallList"
                          :value="hall.id"
                          :key="hall.id"
                          :label="hall.name" />
                </Select>
              </FormItem>
              <FormItem>
                <Button type="primary"
                        @click="getScheduleByHallId">查询</Button>
              </FormItem>
              <FormItem style="position: relative;left: 200px">
                <Button type="primary"
                        @click="addSchedule">新添排片</Button>
              </FormItem>
            </Form>
          </div>
          <Row style="height: 760px;width: 100%;">
            <Col span="2"
                 style="display: flex;flex-direction: column;justify-content: space-between;height: 720px;margin-top: 22px">
            <div>00:00</div>
            <div>06:00</div>
            <div>12:00</div>
            <div>18:00</div>
            <div>24:00</div>
            </Col>
            <Col span="22"
                 style="display: flex;">
            <div style="display: flex;flex-direction: column;height: 100%;width: 14%;margin-left: 2px"
                 v-for="day in schedules"
                 v-bind:key="day.date">
              <div>{{day.date.substring(0,10)}}</div>
              <Card class="day_canvas "
                    :padding="padding">
                <Button class="default_movie_button"
                        v-for="movie in day.scheduleItemList"
                        :key="movie.id"
                        type="info"
                        :style="movie.style"
                        @click="editSchedule(movie)">
                  <span>{{movie.movieName}}</span>
                  <br>
                  <span>{{movie.startTimeStr}}-{{movie.endTimeStr}}</span>
                </Button>
              </Card>
            </div>
            </Col>
          </Row>
        </Card>
      </div>
      </Col>
    </Row>
    <Modal v-model="addScheduleModal"
           title="新增排片信息"
           @on-ok="handleAddSchedule">
      <div style="margin-left:20px;">
        <Form :model="toAddSchedule"
              :label-width="80"
              label-position="left">
          <br>
          <FormItem label="放映电影">
            <Select v-model="toAddSchedule.movieId"
                    style="width:200px">
              <Option v-for="item in movieList"
                      :value="item.id"
                      :key="item.id"
                      :label="item.name">{{ item.name }}</Option>
            </Select>
          </FormItem>
          <FormItem label="影厅">
            <Select v-model="toAddSchedule.hallId"
                    style="width:200px">
              <Option v-for="item in hallList"
                      :value="item.id"
                      :key="item.id"
                      :label="item.name">{{ item.name }}</Option>
            </Select>
          </FormItem>
          <FormItem label="开始时间">
            <DatePicker type="datetime"
                        format="yyyy-MM-dd HH:mm"
                        placeholder="选择日期和时间"
                        style="width: 200px"
                        v-model="toAddSchedule.startTime"></DatePicker>
          </FormItem>
          <FormItem label="结束时间">
            <DatePicker type="datetime"
                        format="yyyy-MM-dd HH:mm"
                        placeholder="选择日期和时间"
                        style="width: 200px"
                        v-model="toAddSchedule.endTime"></DatePicker>
          </FormItem>
          <FormItem label="票价">
            <Input v-model="toAddSchedule.fare"
                   type="number"
                   placeholder="请输入票价" />
          </FormItem>
        </Form>
      </div>
    </Modal>
    <Modal v-model="editScheduleModal"
           title="修改排片信息"
           @on-ok="handleEditSchedule">
      <div style="margin-left:20px;">
        <Form :model="toEditSchedule"
              :label-width="80"
              label-position="left">
          <br>
          <FormItem label="放映电影">
            <Select v-model="toEditSchedule.movieId"
                    style="width:200px">
              <Option v-for="item in movieList"
                      :value="item.id"
                      :key="item.id"
                      :label="item.name">{{ item.name }}</Option>
            </Select>
          </FormItem>
          <FormItem label="影厅">
            <Select v-model="toEditSchedule.hallId"
                    style="width:200px">
              <Option v-for="item in hallList"
                      :value="item.id"
                      :key="item.id"
                      :label="item.name">{{ item.name }}</Option>
            </Select>
          </FormItem>
          <FormItem label="开始时间">
            <DatePicker type="datetime"
                        format="yyyy-MM-dd HH:mm"
                        placeholder="Select date and time(Excluding seconds)"
                        style="width: 200px"
                        v-model="toEditSchedule.startTime"></DatePicker>
          </FormItem>
          <FormItem label="结束时间">
            <DatePicker type="datetime"
                        format="yyyy-MM-dd HH:mm"
                        placeholder="Select date and time(Excluding seconds)"
                        style="width: 200px"
                        v-model="toEditSchedule.endTime"></DatePicker>
          </FormItem>
          <FormItem label="票价">
            <Input v-model="toEditSchedule.fare"
                   type="number"
                   placeholder="请输入票价" />
          </FormItem>
          <FormItem label="删除">
            <Button type="error" @click="handleDeleteSchedule(toEditSchedule.id)">删除排片</Button>
          </FormItem>
        </Form>
      </div>
    </Modal>
  </Layout>
</template>

<style>
.hallSelect {
  text-align: left;
}
.day_canvas {
  position: relative;
  height: 724px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}
.default_movie_button {
  min-height: 30px;
  width: 100%;
}
</style>

<script>
export default {
  data () {
    return {
      padding: 2,
      selectedDate: new Date(),
      selectedHallId: 1,
      hallList: [],
      schedules: [],
      movieList: [],
      toEditSchedule: {
        id: 0,
        hallId: '',
        hallName: '',
        movieId: '',
        movieName: '',
        startTime: '',
        endTime: '',
        fare: null
      },
      editScheduleModal: false,
      toAddSchedule: {
        id: 0,
        hallId: '',
        hallName: '',
        movieId: '',
        movieName: '',
        startTime: '',
        endTime: '',
        fare: null
      },
      addScheduleModal: false
    }
  },
  mounted: function () {
    let that = this
    this.$axios({
      method: 'get',
      url: 'http://localhost:8080/hall/all'
    }).then(function (res) {
      if (res.data.success) {
        that.hallList = res.data.content
      }
    })
    this.$axios({
      method: 'get',
      url: 'http://localhost:8080/movie/all'
    }).then(function (res) {
      if (res.data.success) {
        that.movieList = res.data.content
      }
    })
    this.getScheduleByHallId()
  },
  methods: {
    getScheduleByHallId () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/schedule/search?hallId=' + that.selectedHallId + '&startDate=' + that.selectedDate.toLocaleDateString().replace(/-/g, '/')
      }).then(function (res) {
        if (res.data.success) {
          that.schedules = that.computeScheduleStyle(res.data.content)
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    computeScheduleStyle (content) {
      let tempList = content
      for (let j = 0; j < tempList.length; j++) {
        let heightCounter = 0
        for (let i = 0; i < tempList[j].scheduleItemList.length; i++) {
          let end = new Date(tempList[j].scheduleItemList[i].endTime)
          let start = new Date(tempList[j].scheduleItemList[i].startTime)
          let height = Math.round((end.getTime() - start.getTime()) / 1000 / 60 / 60 / 24 * 720)
          let marginTop = Math.round((start.getHours() + start.getMinutes() / 60) / 24 * 720 - heightCounter)
          heightCounter = heightCounter + height
          tempList[j].scheduleItemList[i].style = 'position: relative;top: ' + marginTop + 'px;height: ' + height + 'px'
          tempList[j].scheduleItemList[i].startTimeStr = tempList[j].scheduleItemList[i].startTime.substring(11, 16)
          tempList[j].scheduleItemList[i].endTimeStr = tempList[j].scheduleItemList[i].endTime.substring(11, 16)
        }
      }
      return tempList
    },
    editSchedule (movie) {
      this.toEditSchedule = movie
      this.editScheduleModal = true
    },
    addSchedule () {
      this.toAddSchedule = {
        id: 0,
        hallId: '',
        hallName: '',
        movieId: '',
        movieName: '',
        startTime: '',
        endTime: '',
        fare: ''
      }
      this.addScheduleModal = true
    },
    handleEditSchedule () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/schedule/update',
        data: {
          id: that.toEditSchedule.id,
          hallId: that.toEditSchedule.hallId,
          movieId: that.toEditSchedule.movieId,
          startTime: that.toEditSchedule.startTime,
          endTime: that.toEditSchedule.endTime,
          fare: that.toEditSchedule.fare
        }
      }).then((res) => {
        if (res.data.success) {
          this.$Message.success('修改成功')
          that.getScheduleByHallId()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    handleAddSchedule () {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/schedule/add',
        data: {
          id: that.toAddSchedule.id,
          hallId: that.toAddSchedule.hallId,
          movieId: that.toAddSchedule.movieId,
          startTime: that.toAddSchedule.startTime,
          endTime: that.toAddSchedule.endTime,
          fare: that.toAddSchedule.fare
        }
      }).then((res) => {
        if (res.data.success) {
          this.$Message.success('添加成功')
          that.getScheduleByHallId()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    handleDeleteSchedule (id) {
      let that = this
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/schedule/delete/batch',
        data: {
          scheduleIdList: [id]
        }
      }).then((res) => {
        if (res.data.success) {
          this.$Message.success('删除成功')
          that.editScheduleModal = false
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    }
  }
}
</script>
