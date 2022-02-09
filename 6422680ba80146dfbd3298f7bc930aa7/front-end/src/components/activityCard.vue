<template>
  <Content>
    <Card style=" margin-top: 10px;
    min-height: 180px;
    width: 800px;
    text-align: left;">
      <Row>
        <Col span="16">
          <div style="margin-bottom: 10px">
            <div style="margin-bottom: 15px"><font size="5"><b>{{activity.name}}</b></font> </div>
            <div>活动时间: <font color="#708090">{{activity.timeStr}}</font></div>
            <div>活动描述: <font color="#708090">{{activity.description}}</font></div>
            <div>
              <div style="display: inline-block">参与电影:</div>
              <div style="display: inline-block;vertical-align: top"><font color="#708090"><span v-html="activity.movieStr"></span></font></div>
            </div>
          </div>
        </Col>
        <Col span="2">
          <Divider type="vertical" style="height: 150px;margin-left: 20px;" />
        </Col>
        <Col span="6">
          <div style="margin-bottom: 10px">
            <div style="margin-bottom: 15px"><font size="4"><b>优惠券:{{activity.coupon.name}}</b></font> </div>
            <div>满<font color="#708090">{{activity.coupon.targetAmount}}</font>减<font color="#708090">{{activity.coupon.discountAmount}}</font></div>
            <div>{{activity.coupon.description}}</div>
          </div>
        </Col>
      </Row>
    </Card>
  </Content>
</template>

<script>
export default {
  props: {
    activity: Object
  }
}
</script>

<style scoped>

</style>
