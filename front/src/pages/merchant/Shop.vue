<template>
  <div>
    <el-skeleton
      class="shopForm"
      style="width: 1200px"
      :loading="loading"
      animated
      :count="5"
    >
      <!-- 展示信息 -->
      <template slot="template">
        <el-skeleton-item
          variant="h2"
          style="width: 90%; margin-top: 30px; height: 40px"
        />
      </template>
      <template>
        <el-table
          :data="
            tableData.filter(
              (data) =>
                !search ||
                data.goodsName.toLowerCase().includes(search.toLowerCase())
            )
          "
          style="width: 100%"
        >
          <el-table-column label="商品图片" prop="goodsPic">
            <template slot-scope="scope">
             
              <img :src="scope.row.imageUrl" class="avatar" alt="加载中..." width="40px" height="30px"></img>
            </template>
          </el-table-column>
          <el-table-column label="商品类别" prop="goodsCategoryName">
          </el-table-column>
          <el-table-column label="商品名字" prop="goodsName"> </el-table-column>

          <el-table-column label="原价" prop="price"> </el-table-column>
          <el-table-column label="折后价" prop="subPrice"> </el-table-column>
          <el-table-column label="数量" prop="quantity"> </el-table-column>
          <el-table-column label="状态" prop="goodsStatus">
            <template slot-scope="scope">
              <el-tag
                type="success"
                style="float: left"
                v-if="scope.row.goodsStatus === 1"
                >在售</el-tag
              >
              <el-tag
                type="info"
                style="float: left"
                v-if="scope.row.goodsStatus === 2"
                >售空</el-tag
              >
              <el-tag
                type="danger"
                style="float: left"
                v-if="scope.row.goodsStatus === 3"
                >停售</el-tag
              >
            </template>
          </el-table-column>
           <el-table-column label="商品描述" prop="goodsDescription" class-name="column_description" label-class-name="column_description_header"> </el-table-column>
          <el-table-column align="center" width="200px">
            <template slot="header" slot-scope="scope">
              <el-input
                v-model="search"
                size="mini"
                placeholder="输入商品名关键字搜索"
              />
            </template>
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                >修改</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
        <div style="margin-top: 20px">
          <el-pagination
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
          <el-button type="primary" style="float: right" @click="addGoods"
            >添加商品</el-button
          >
        </div>
      </template>
    </el-skeleton>
<div class="mask" v-show="isEdit"></div>
    <!-- 编辑用的表单 -->
   <div class="form" v-show="isEdit">
      <div class="mytitle">
       修改商品信息</div>

      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        status-icon
        label-width="100px"
        class="demo-ruleForm"
        style="background-color: rgb(255, 255, 255)"
      >
        <el-form-item class="formInput" label="原价：" prop="price">
          <el-input
            v-model="ruleForm.price"
            type="number"
            autocomplete="off"
            placeholder="输入原价"
          ></el-input>
        </el-form-item>
        <el-form-item class="formInput" label="折后价：" prop="subPrice">
          <el-input
            v-model="ruleForm.subPrice"
            type="number"
            autocomplete="off"
            placeholder="输入折后价"
          ></el-input>
        </el-form-item>
       <el-form-item class="formInput" label="数量：" prop="quantity">
          <el-input
            v-model="ruleForm.quantity"
            type="number"
            autocomplete="off"
            placeholder="输入数量"
          ></el-input>
        </el-form-item>
       <el-form-item class="formInput" label="状态：" prop="goodsStatus">
       <template>
        <el-radio v-model="ruleForm.goodsStatus" :label="1">在售</el-radio>
        <el-radio v-model="ruleForm.goodsStatus" :label="2">售空</el-radio>
        <el-radio v-model="ruleForm.goodsStatus" :label="3">停售</el-radio>
       </template>
        </el-form-item>

        <el-form-item
          style="margin-bottom: 30px; margin-left: 100px"
          label-width="0"
        >
        <el-button
            type="primary"
            plain
            style="width: 100px; margin-bottom: 10px"
            @click="isEdit=false"
            >取消</el-button
          >
          <el-button
            type="primary"
            style="width: 100px; margin-bottom: 10px ;margin-left: 100px"
            @click="editForm(ruleForm, 'ruleForm')"
            >确定</el-button
          >
        </el-form-item>
      </el-form>
   </div>
  </div>
</template>


<script>
import { getMerchantGoodsInfo } from "@/api/merchant/forGoods";
import { postMerchantGoods } from "@/api/merchant/forGoods";
import { deleteMerchantGoods } from "@/api/merchant/forGoods";
export default {
  name: "Shop",
  data() {
    return {
      //编辑
      isEdit: false,
      ruleForm: {
        price: "",
        subPrice:"",
        quantity: "",
        goodsStatus: "",
      },
      rules: {
        price: [
          {
            required: true,
            message: "请输入原价",
            trigger: "blur",
          },
        ],
        subPrice: [
          {
            required: true,
            message: "请输入折后价",
            trigger: "blur",
          },
        ],
        quantity: [
          {
            required: true,
            message: "请输入数量",
            trigger: "blur",
          },
        ],
      },
      loading: true,
      tableData: [],
      search: "",

      page: 1,
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount: 8,
      // 个数选择器（可修改）
      pageSizes: [ 5, 10, 20],
      // 默认每页显示的条数（可修改）
      pageSize: 10,
    };
  },
  created() {
    this.handleCurrentChange(1);
  },
  methods: {
    //修改商品确定按钮事件
    editForm(form) {
      this.isEdit = false;
      postMerchantGoods(form)
        .then((res) => {
          if (res.code === 1) {
            this.$message.success("修改成功！");
            this.handleCurrentChange(this.page);
          } else if (res.code === 0) {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          console.log("修改商品出错了！", err);
        });
    },
    //看看scope
    seeScope(scope) {
      console.log(scope);
    },
    //添加商品
    addGoods() {
      this.$router.push({
        path: "/merchant/addGoods",
      });
    },

    // 将页码，及每页显示的条数以参数传递提交给后台

    getData(page, pageSize) {
      console.log("page, pageSize", page, pageSize);
      let params = {
        page: page,
        pageSize: pageSize,
      };
      getMerchantGoodsInfo(params)
        .then((res) => {
          if (res.code === 1) {
            this.tableData = res.data.records;
            for (const key in this.tableData) {
              this.tableData[
                key
              ].imageUrl = `/api/goods/downloadGoodsPic?name=${this.tableData[key].goodsPic}`;
              console.log(this.tableData[key].imageUrl);
            }
            console.log(this.tableData);
            this.totalCount = res.data.total;
            this.loading = false;
          }
          if (res.code === 0) {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          console.log("获取商户商品数据出错了", err);
        });
    },
    // 处理goodsStatus

    // 分页
    // 每页显示的条数
    handleSizeChange(val) {
      // 改变每页显示的条数
      this.pageSize = val;
      // 点击每页显示的条数时，显示第一页
      this.getData(1, val);
      // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.page = 1;
    },
    // 显示第几页
    handleCurrentChange(val) {
      // 改变默认的页数
      this.page = val;

      // 切换页码时，要获取每页显示的条数
      // this.getData(val, this.pageSize);
      this.getData(val, this.pageSize);
    },
    //编辑
    handleEdit(index, row) {
      this.ruleForm.price = row.price;
      this.ruleForm.quantity = row.quantity;
      this.ruleForm.goodsId = row.goodsId;
      this.ruleForm.subPrice = row.subPrice;
      this.ruleForm.goodsStatus = row.goodsStatus;
      this.isEdit = true;
      console.log(index, row);
    },
    //删除
    handleDelete(index, row) {
      if (row.goodsStatus === 1) {
        this.$alert("该商品状态为在售，无法删除", "提示", {
          confirmButtonText: "确定",
        });
      } else {
        this.$confirm("这件商品将永远删除，是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            const params = { goodsId: row.goodsId };
            deleteMerchantGoods(params)
              .then((res) => {
                if (res.code === 1) {
                  this.$message.success("删除成功!");
                  this.handleCurrentChange(this.page);
                } else if (res.code === 0) {
                  this.$message.error(res.msg);
                }
              })
              .catch((err) => {
                console.log("删除商品出错了", err);
              });
          })
          .catch(() => {
            return;
          });
      }
    },
  },
};
</script>

<style ccoped>
.column_description div {
  display: inline-block;
  white-space: nowrap;
  width: 100%;
  overflow: hidden;
  height: 40px;
  text-overflow: ellipsis;
}
.column_description_header {
  width: 100% !important;
  height: 100% !important;
}
.el-upload input {
  display: none !important;
}
.shopForm {
  position: absolute;
}
.mask {
  background-color: rgba(0, 0, 0, 0.658);
  opacity: 0.1;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}
.mytitle {
  color: skyblue;
  font-size: 30px;
  font-weight: 600;
  padding: 10px 0px;
  margin-bottom: 50px;
  text-align: center;
  width: 100%;
  height: 40px;
}
.formInput {
  width: 400px;
}
.form {
  position: fixed;
  top: 150px;
  left: 50%;
  transform: translateX(-50%);
  width: 450px;
  height: 330px;
  background-color: rgb(255, 255, 255);
}
</style>