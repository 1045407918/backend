<template>
  <Layout>
    <Anchor show-ink class="anchor">
      <AnchorLink href="#schedule-rate-card-container"
                  title="今日排片率" />
      <AnchorLink href="#box-office-card-container"
                  title="所有电影票房" />
      <AnchorLink href="#audience-price-card-container"
                  title="每日客单价" />
      <AnchorLink href="#place-rate-card-container"
                  title="今日上座率" />
      <AnchorLink href="#popular-movie-card-container"
                  title="最近最热电影" />
    </Anchor>
    <Row type="flex"
         justify="space-around">
      <Col span="18">
      <div class="statistic-container" id="schedule-rate-card-container">
        <Card>
          <div id="schedule-rate-container"
               style="width: 800px;height:500px;"></div>
        </Card>
      </div>
      <div class="statistic-container" id="box-office-card-container">
        <Card>
          <div id="box-office-container"
               style="width: 800px;height:500px;"></div>
        </Card>
      </div>
      <div class="statistic-container" id="audience-price-card-container">
        <Card>
          <div id="audience-price-container"
               style="width: 800px;height:500px;"></div>
        </Card>
      </div>
      <div class="statistic-container" id="place-rate-card-container">
        <Card>
          <div id="place-rate-container"
               style="width: 800px;height:500px;"></div>
        </Card>
      </div>
      <div class="statistic-container" id="popular-movie-card-container" style="margin-bottom: 200px;">
        <Card>
          <div id="popular-movie-container"
               style="width: 800px;height:500px;"></div>
        </Card>
      </div>
      </Col>
    </Row>
  </Layout>
</template>

<style>
.anchor {
  position: fixed;
  text-align: left;
  margin-top: 20px;
  margin-left: 50px;
}
.statistic-container {
  width: 100%;
  height: 560px;
  margin: 20px 20px 60px 20px;
}
</style>

<script>
export default {
  data () {
    return {
      scheduleRate: [],
      boxOffice: [],
      audiencePrice: [],
      placingRate: [],
      popularMovie: []
    }
  },
  mounted: function () {
    this.getScheduleRate()
    this.getBoxOffice()
    this.getAudiencePrice()
    this.getPlacingRate()
    this.getPopularMovie()
  },
  methods: {
    getScheduleRate () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/statistics/scheduleRate'
      }).then((res) => {
        if (res.data.success) {
          that.scheduleRate = res.data.content || []
          that.showScheduleRate()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    showScheduleRate () {
      let tableData = this.scheduleRate.map(function (item) {
        return {
          value: item.time,
          name: item.name
        }
      })
      let nameList = this.scheduleRate.map(function (item) {
        return item.name
      })
      let option = {
        title: {
          text: '今日排片率',
          subtext: new Date().toLocaleDateString(),
          x: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          x: 'center',
          y: 'bottom',
          data: nameList
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            magicType: {
              show: true,
              type: ['pie', 'funnel']
            },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        calculable: true,
        series: [
          {
            name: '面积模式',
            type: 'pie',
            radius: [30, 110],
            center: ['50%', '50%'],
            roseType: 'area',
            data: tableData
          }
        ]
      }
      let scheduleRateChart = this.$echarts.init(document.getElementById('schedule-rate-container'))
      scheduleRateChart.setOption(option)
    },
    getBoxOffice () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/statistics/boxOffice/total'
      }).then((res) => {
        if (res.data.success) {
          that.boxOffice = res.data.content || []
          that.showBoxOffice()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    showBoxOffice () {
      let tableData = this.boxOffice.map(function (item) {
        return item.boxOffice
      })
      let nameList = this.boxOffice.map(function (item) {
        return item.name
      })
      let option = {
        title: {
          text: '所有电影票房',
          subtext: '截止至' + new Date().toLocaleDateString(),
          x: 'center'
        },
        xAxis: {
          type: 'category',
          data: nameList
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: tableData,
          type: 'bar'
        }]
      }
      let scheduleRateChart = this.$echarts.init(document.getElementById('box-office-container'))
      scheduleRateChart.setOption(option)
    },
    getAudiencePrice () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/statistics/audience/price'
      }).then((res) => {
        if (res.data.success) {
          that.audiencePrice = res.data.content || []
          that.showAudiencePrice()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    showAudiencePrice () {
      let tableData = this.audiencePrice.map(function (item) {
        return item.price
      })
      let nameList = this.audiencePrice.map(function (item) {
        return new Date(item.date).toLocaleString().split(' ')[0]
      })
      let option = {
        title: {
          text: '每日客单价',
          x: 'center'
        },
        xAxis: {
          type: 'category',
          data: nameList
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: tableData,
          type: 'line'
        }]
      }
      let scheduleRateChart = this.$echarts.init(document.getElementById('audience-price-container'))
      scheduleRateChart.setOption(option)
    },
    getPlacingRate () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/statistics/PlacingRate'
      }).then((res) => {
        if (res.data.success) {
          that.placingRate = res.data.content || []
          that.showPlacingRate()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    showPlacingRate () {
      let tableData = this.placingRate.map(function (item) {
        return item.rate
      })
      let nameList = this.placingRate.map(function (item) {
        return item.name
      })
      let option = {
        title: {
          text: '当日电影上座率',
          x: 'center'
        },
        xAxis: {
          type: 'category',
          data: nameList
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: tableData,
          type: 'line'
        }]
      }
      let moviePlacingRateChart = this.$echarts.init(document.getElementById('place-rate-container'))
      moviePlacingRateChart.setOption(option)
    },
    getPopularMovie () {
      let that = this
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/statistics/popular/movie?days=30&movieNum=5'
      }).then((res) => {
        if (res.data.success) {
          that.popularMovie = res.data.content || []
          that.showPopularMovie()
        } else {
          alert(res.data.message)
        }
      }).catch(function (error) {
        alert(error)
      })
    },
    showPopularMovie () {
      let tableData = this.popularMovie.map(function (item) {
        return item.boxOffice
      })
      let nameList = this.popularMovie.map(function (item) {
        return item.name
      })
      let option = {
        title: {
          text: '最近30天最热电影(票房排名)',
          subtext: '截止至' + new Date().toLocaleDateString(),
          x: 'center'
        },
        xAxis: {
          type: 'category',
          data: nameList
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: tableData,
          type: 'bar'
        }]
      }
      let scheduleRateChart = this.$echarts.init(document.getElementById('popular-movie-container'))
      scheduleRateChart.setOption(option)
    }
  }
}
</script>
