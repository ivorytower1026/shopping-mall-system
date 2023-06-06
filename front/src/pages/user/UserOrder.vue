<template>
  <div>
    <template>
      <el-table :data="tableData" class="table" empty-text="暂无订单">
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
        <el-table-column label="商品数量" prop="goodsQuantity">
        </el-table-column>
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
            <el-tag style="float: right" v-if="scope.row.orderStatus !== 1">{{
              setStatus(scope.row.orderStatus)
            }}</el-tag>
            <el-button
              style="float: right"
              v-if="scope.row.orderStatus === 1 || scope.row.orderStatus === 4"
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
              >删除</el-button
            >
            <div v-if="scope.row.orderStatus === 1">
              <el-button
                style="float: right; margin-right: 20px"
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                >{{ setStatus(scope.row.orderStatus) }}</el-button
              >
            </div>
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
    </template>
  </div>
</template>

<script>
import {
  getGoodsOrder,
  postGoodsOrderStatus,
  deleteGoodsOrder,
} from "@/api/user/index";
export default {
  name: "UserOrder",
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
    this.getUserOrder(1, this.pageSize);
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
      this.getUserOrder(1, val);
      // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.page = 1;
    },
    // 显示第几页
    handleCurrentChange(val) {
      // 改变默认的页数
      this.page = val;

      // 切换页码时，要获取每页显示的条数
      // this.getData(val, this.pageSize);
      this.getUserOrder(this.page, this.pageSize);
    },
    //获取用户订单
    getUserOrder(current, size) {
      let params = {
        current: current,
        size: size,
      };
      getGoodsOrder(params)
        .then((res) => {
          if (res.code === 1) {
            this.tableData = res.data.records;
            this.totalCount = res.data.total;
          } else if (res.code === 0) {
            this.$message.success(res.msg);
          }
        })
        .catch((err) => {
          console.log("请求订单信息错误", err);
        });
    },

    //支付键
    handleEdit(index, row) {
      let params = {
        orderId: row.orderId,
        orderStatus: 2,
      };
      postGoodsOrderStatus(params)
        .then((res) => {
          if (res.code === 1) {
            this.getUserOrder(this.page, this.pageSize);
            this.$message.success("支付成功！");
          } else if (res.code === 0) {
            this.$message.success(res.msg);
          }
        })
        .catch((err) => {
          console.log("修改失败", err);
        });
    },
    //删除键
    handleDelete(index, row) {
      let params = {
        orderId: row.orderId,
      };

      this.$confirm("这条订单信息将永远删除，是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          deleteGoodsOrder(params)
            .then((res) => {
              if (res.code === 1) {
                this.getUserOrder(this.page, this.pageSize);
                this.$message({
                  type: "success",
                  message: "删除成功!",
                });
              }
            })
            .catch((err) => {
              this.$message.error("删除失败！");
            });
        })
        .catch(() => {
          return;
        });
    },
  },
};
</script>

<style>
.table {
  width: 1400px;
  margin-left: 50px;
  margin-top: 40px;
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
</style>