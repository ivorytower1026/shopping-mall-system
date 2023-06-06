<template>
  <div class="parentBox">
    <template>
      <div class="carousel" v-if="$route.query.keyword === undefined">
        <el-carousel :interval="4000" type="card" height="340px" trigger="click">
          <el-carousel-item v-for="item in 5" :key="item">
            <a href="#">
              <!-- 管理员设置 -->
              <img
                :src="`/api/goods/downloadGoodsPic?name=Snipaste_2022-06-07_17-51-16.jpg`"
                alt="加载中"
                width="100%"
                height="100%"
            /></a>
          </el-carousel-item>
        </el-carousel>
      </div>
    </template>
    <span style="margin-left: 100px" v-if="$route.query.keyword !== undefined"
      >以下是关于<span style="color: red">*{{ keyword }}*</span
      >搜索到有关的内容</span
    >
    <!-- 有什么分类 -->
    <template v-if="$route.query.keyword === undefined">
      <el-tabs
        v-model="activeId"
        @tab-click="handleClick"
        style="width: 1200px; margin-left: 120px"
      >
        <el-tab-pane
          v-for="item in goodsCategoryOptions"
          :key="item.goodsCategoryId"
          :label="item.goodsCategoryName"
          :name="item.goodsCategoryId"
        ></el-tab-pane>
      </el-tabs>
    </template>

    <el-row :gutter="20" style="margin-top: 40px; margin-left: 100px">
      <el-col
        :span="6"
        v-for="g in goodsList"
        :key="g.id"
        style="margin-bottom: 18px"
        ><div class="grid-content bg-purple">
          <a @click="gotoGoodsInfo(g.goodsId)">
            <div class="box">
              <img
                width="210px"
                height="250px"
                :src="g.goodsPic"
                alt="加载中..."
              />
              <div>{{ g.goodsName }}</div>
              <div>
                ￥{{ g.subPrice }}元
                <span
                  style="
                    text-decoration: line-through;
                    color: rgba(0, 0, 0, 0.5);
                  "
                  >￥{{ g.price }}元</span
                >
              </div>
            </div>
          </a>
        </div></el-col
      >
    </el-row>

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
import { getGoodsCategory } from "@/api/goodsCategory/index";
import { getGoodsInfo } from "@/api/goods/index";
export default {
  name: "ShoppingMall",
  data() {
    return {
      goodsList: [],
      //表示被选中的类别
      activeId: "",
      goodsCategoryOptions: "",

      page: 1,
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount: 99,
      // 个数选择器（可修改）
      pageSizes: [16, 20, 24],
      // 默认每页显示的条数（可修改）
      pageSize: 16,
      keyword: "",
    };
  },
  //解决当前路径不刷新的问题
  watch: {
    $route(to, from) {
      // to表示要跳转的路由，from表示从哪儿跳的路由   to.path
      this.$router.go(0);
    },
  },
  mounted() {
    this.getGoodsCategory();
  },

  methods: {
    // 将页码，及每页显示的条数以参数传递提交给后台

    // 分页
    // 每页显示的条数
    handleSizeChange(val) {
      // 改变每页显示的条数
      this.pageSize = val;
      // 点击每页显示的条数时，显示第一页
      this.getGoodsInfoByGoodsCategoryId(1, val);
      // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.page = 1;
    },
    // 显示第几页
    handleCurrentChange(val) {
      // 改变默认的页数
      this.page = val;

      // 切换页码时，要获取每页显示的条数
      // this.getData(val, this.pageSize);
      this.getGoodsInfoByGoodsCategoryId(this.page, this.pageSize);
    },
    //切换商品类别时用
    handleClick(tab, event) {
      this.getGoodsInfoByGoodsCategoryId(1, this.pageSize);
    },
    //获取商品类别
    getGoodsCategory() {
      getGoodsCategory()
        .then((res) => {
          if (res.code === 1) {
            this.goodsCategoryOptions = res.data;
            this.activeId = this.goodsCategoryOptions[0].goodsCategoryId;
            this.keyword = this.$route.query.keyword;
            this.getGoodsInfoByGoodsCategoryId(1, this.pageSize);
          } else if (res.code === 0) {
            console.log(res.msg);
          }
        })
        .catch((res) => {
          console.log("请求商品分类出错了！", err);
        });
    },
    //获取对应类别的商品信息或是关键字
    getGoodsInfoByGoodsCategoryId(page, pageSize) {
      let params = {
        goodsCategoryId: this.activeId,
        page: page,
        pageSize: pageSize,
        keyword: this.keyword,
      };
      getGoodsInfo(params)
        .then((res) => {
          if (res.code === 1) {
            this.goodsList = res.data.records;
            this.totalCount = res.data.total;
            for (const key in this.goodsList) {
              this.goodsList[
                key
              ].goodsPic = `/api/goods/downloadGoodsPic?name=${this.goodsList[key].goodsPic}`;
            }
          } else if (res.code === 0) {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          console.log("请求商品信息出错了！", err);
        });
    },
    gotoGoodsInfo(goodsId) {
      this.$router.push({
        path: "/user/GoodsDetailed",
        query: {
          goodsId: goodsId,
        },
      });
    },
  },
};
</script>

<style scoped>
/* 分页类 */
.paginationClass {
  margin-top: 50px;
  height: 150px;
  position: relative;
  transform: translateX(-50%);
  top: 0px;
  left: 80%;
}
/* 走马灯 */
.carousel {
  position: relative;
  width: 1200px;
  transform: translateX(-50%);
  top: 0px;
  left: 50%;
}

.box {
  width: 240px;
  height: 360px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  padding: 15px;
  margin: 0px;
}
.box:hover {
  border: 1px solid rgba(0, 183, 255, 0.774);
}
.parentBox {
  position: relative;
  width: 1440px;
  top: 30px;
  left: 50%;
  height: 100%;
  transform: translateX(-50%);
}

.box div {
  margin-top: 13px;
  text-align: center;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>