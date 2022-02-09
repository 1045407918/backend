<template>
  <Layout>
    <Row type="flex"
         justify="space-around">
      <Col span="14">
      <div class="myCoupons">
        <h1>我的优惠券</h1>
        <Divider></Divider>
      </div>
      <Card>
        <Table :columns="columns"
               :data="myCoupons"
               stripe>
        </Table>
      </Card>
      </Col>
    </Row>
  </Layout>
</template>

<style>
.myCoupons {
  text-align: left;
  width: 90%;
  margin: 40px auto;
}
</style>

<script>
export default {
  data () {
    return {
      myCoupons: [],
      columns: [
        {
          title: '名称',
          key: 'name',
          align: 'center'
        },
        {
          title: '说明',
          key: 'description',
          align: 'center'
        },
        {
          title: ' ',
          key: 'discount',
          align: 'center',
          render: (h, params) => {
            return h('span', '满' + params.row.targetAmount + '减' + params.row.discountAmount)
          }
        },
        {
          title: '有效期',
          key: 'date',
          align: 'center',
          render: (h, params) => {
            return h('span', params.row.startTime.substring(0, 10) + ' ~ ' + params.row.endTime.substring(5, 10))
          }
        }
      ]
    }
  },
  mounted: function () {
    let that = this
    this.$axios({
      method: 'get',
      url: 'http://localhost:8080/coupon/' + sessionStorage.getItem('id') + '/get'
    }).then(function (res) {
      if (res.data.success) {
        that.myCoupons = res.data.content
      } else {
        alert(res.data.message)
      }
    }).catch(function (error) {
      alert(error)
    })
  }
}
</script>
