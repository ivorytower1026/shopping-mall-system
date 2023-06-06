<template>
  <div class="orderForm">
    <el-table :data="tableData" class="table" empty-text="暂无订单">
      <el-table-column label="下单时间" prop="updateTime" width="150px">
      </el-table-column>
      <el-table-column label="商品图片" prop="goodsPic">
        <template slot-scope="scope">
          <img
            width="60px"
            height="40px"
            :src="`/api/goods/downloadGoodsPic?name=` + scope.row.goodsPic"
            alt="加载中..."
          />
        </template>
      </el-table-column>
      <el-table-column label="商品名" prop="goodsName" show-overflow-tooltip>
      </el-table-column>
      <el-table-column label="商品数量" prop="goodsQuantity"> </el-table-column>
      <el-table-column label="单价" prop="subPrice"> </el-table-column>
      <el-table-column label="总价" prop="totalPrice"> </el-table-column>
      <el-table-column
        label="收货人"
        prop="consigneeName"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column label="收货手机号" prop="consigneePhone">
      </el-table-column>
      <el-table-column label="地址" prop="address" show-overflow-tooltip>
      </el-table-column>

      <el-table-column align="right" width="200px">
        <template slot-scope="scope">
          <div v-if="scope.row.orderStatus === 2">
            <el-button
              style="float: right; margin-right: 20px"
              size="mini"
              @click="handleEdit(scope.$index, scope.row)"
              >发货</el-button
            >
          </div>
          <el-tag style="float: right; margin-right: 20px">{{
            setStatus(scope.row.orderStatus)
          }}</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="paginationClass"
      style="folat: left"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page"
      :page-sizes="pageSizes"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalCount"
    >
    </el-pagination>
  </div>
</template>

<script>
import { getGoodsOrder,postGoodsOrderStatus } from "@/api/merchant/index";
export default {
  name: "Order",
  data() {
    return {
      page: 1,
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount: 99,
      // 个数选择器（可修改）
      pageSizes: [10, 20, 30],
      // 默认每页显示的条数（可修改）
      pageSize: 10,
      tableData: [],
    };
  },
  mounted() {
    this.getMerchantOrder(1, this.pageSize);
  },
  methods: {
    setStatus(status) {
      if (status === 1) {
        return "待支付";
      } else if (status === 2) {
        return "待发货";
      } else if (status === 3) {
        return "待收货";
      } else if (status === 4) {
        return "已完成";
      }
    },
    handleSizeChange(val) {
      // 改变每页显示的条数
      this.pageSize = val;
      // 点击每页显示的条数时，显示第一页
      this.getMerchantOrder(1, val);
      // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.page = 1;
    },
    // 显示第几页
    handleCurrentChange(val) {
      // 改变默认的页数
      this.page = val;

      // 切换页码时，要获取每页显示的条数
      // this.getData(val, this.pageSize);
      this.getMerchantOrder(this.page, this.pageSize);
    },
    //获取商户订单
    getMerchantOrder(current, size) {
      let params = {
        current: current,
        size: size,
      };
      getGoodsOrder(params)
        .then((res) => {
          if (res.code === 1) {
            this.tableData = res.data.records;
            console.log("this.tableData", this.tableData);
            this.totalCount = res.data.total;
          } else if (res.code === 0) {
            this.$message.success(res.msg);
          }
        })
        .catch((err) => {
          console.log("请求订单信息错误", err);
        });
    },
    //发货键
    handleEdit(index, row) {
      let params = {
        orderId: row.orderId,
        orderStatus: 3,
      };
      postGoodsOrderStatus(params)
        .then((res) => {
          if (res.code === 1) {
            this.getMerchantOrder(this.page, this.pageSize);
            this.$message.success("发货成功！");
          } else if (res.code === 0) {
            this.$message.success(res.msg);
          }
        })
        .catch((err) => {
          console.log("修改失败", err);
        });
    },
  },
};
</script>

<style scoped>
.orderForm {
  position: absolute;
}
/* 分页类 */
.paginationClass {
  margin-top: 50px;
  height: 150px;
  position: relative;
  transform: translateX(-50%);
  top: 0px;
  left: 80%;
}
.table {
  width: 1200px;
}
</style>