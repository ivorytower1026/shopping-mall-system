<template>
  <div>
    <template>
      <el-table :data="tableData" class="table" empty-text="暂无用户">
        <el-table-column label="商铺名" prop="shopName" show-overflow-tooltip>
        </el-table-column>
        <el-table-column label="手机号" prop="phone"> </el-table-column>
        <el-table-column label="商铺地址" prop="shopAddress"> </el-table-column>
        
        <el-table-column label="状态" prop="isUsable" align="center">
          <template slot-scope="scope">
            <el-tag size="mini" v-if="scope.row.isUsable === 1">可用</el-tag>
            <el-tag size="mini" type="danger" v-if="scope.row.isUsable === 0"
              >禁用</el-tag
            >
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.isUsable === 1"
              size="mini"
              type="danger"
              plain
              @click="handle(scope.row, 0)"
              >禁用</el-button
            >
            <el-button
              v-if="scope.row.isUsable === 0"
              size="mini"
              @click="handle(scope.row, 1)"
              >启用</el-button
            >
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
  getMerchantInfo,
  updateMerchantStatus,
} from "@/api/administrator/index";
export default {
  name: "ManageMerchant",
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
    this.getMerchant(1, this.pageSize);
  },
  methods: {
    //是否启用
    handle(row, val) {
      let params = {
        merchantId: row.merchantId,
        isUsable: val,
      };
      updateMerchantStatus(params)
        .then((res) => {
          if (res.code === 1) {
            this.getMerchant(this.page, this.pageSize);
            if (val === 1) {
              this.$message.success("启用成功");
            } else if (val === 0) {
              this.$message.success("禁用成功");
            }
          } else if (res.code === 0) {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          console.log("启用禁用出错了");
        });
    },
    handleSizeChange(val) {
      // 改变每页显示的条数
      this.pageSize = val;
      // 点击每页显示的条数时，显示第一页
      this.getMerchant(1, val);
      // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.page = 1;
    },
    // 显示第几页
    handleCurrentChange(val) {
      // 改变默认的页数
      this.page = val;

      // 切换页码时，要获取每页显示的条数
      // this.getData(val, this.pageSize);
      this.getMerchant(this.page, this.pageSize);
    },
    //获取用户信息
    getMerchant(current, size) {
      let params = {
        current: current,
        size: size,
      };
      getMerchantInfo(params)
        .then((res) => {
          if (res.code === 1) {
            this.tableData = res.data.records;
            this.totalCount = res.data.total;
          } else if (res.code === 0) {
            this.$message.success(res.msg);
          }
        })
        .catch((err) => {
          console.log("请求用户信息失败", err);
        });
    },
  },
};
</script>

<style>
</style>