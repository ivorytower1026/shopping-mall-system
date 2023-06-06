<template>
  <div>
    <!-- 无限滚动 -->
    <div class="list" style="overflow: auto">
      <ul
        v-infinite-scroll="load"
        infinite-scroll-disabled="disabled"
        :infinite-scroll-immediate="false"
        infinite-scroll-distance="200"
      >
        <li
          v-for="(cartListObj, index) in groupByMerchantIdCart"
          class="list-item"
          :key="index"
        >
          <el-card class="box-card">
            <div slot="header" class="clearfix" :class="myStyle">
              <el-button style="float: left; padding: 3px 0" type="text"
                ><el-checkbox
                  v-model="cartListObj.checkAll"
                  :disabled="cartListObj.usable || isUpdate"
                  @change="handleCheckAllChange(cartListObj)"
                  >全选</el-checkbox
                ></el-button
              >
              <el-button
                style="float: left; margin-left: 80px"
                type="danger"
                size="small"
                @click="deleteCartByMerchantId(cartListObj)"
                plain
                >删除全部</el-button
              >
              <span style="float: right">{{ cartListObj.shopName }}</span>
            </div>

            <!-- 复选框 -->
            <el-checkbox-group
              v-model="cartListObj.cartListChecked"
              @change="handleCheckedGoodsChange(cartListObj)"
            >
              <el-checkbox
                :class="myStyle"
                style="display: block; margin-top: 10px"
                v-for="(cart, ind) in cartListObj.cartList"
                :disabled="cart.goodsStatus !== 1 || isUpdate"
                :key="ind"
                :label="cart"
              >
                <div style="position: relative">
                  <img
                    style="float: left; margin-left: 50px"
                    :src="cart.goodsPic"
                    alt="加载中"
                    width="150px"
                    height="100px"
                  />
                  <div
                    style="
                      position: relative;
                      top: 0px;
                      left: 100px;
                      width: 1000px;
                      overflow: hidden;
                      color: skyblue;
                    "
                  >
                    <span style="float: left">配送至</span>
                    <span
                      style="
                        float: left;
                        overflow: hidden;
                        margin-left: 30px;
                        width: 200px;
                      "
                      >{{ cart.address }}</span
                    >
                    <span style="float: left; margin-left: 90px">收货人</span>
                    <span style="float: left; margin-left: 30px">{{
                      cart.consigneeName
                    }}</span>
                    <span style="float: left; margin-left: 90px">手机号</span>
                    <span style="float: left; margin-left: 30px">{{
                      cart.consigneePhone
                    }}</span>
                  </div>
                  <div style="line-height: 70px">
                    <div style="position: absolute; left: 300px">
                      {{ cart.goodsName }}
                    </div>
                    <div style="position: absolute; left: 500px">
                      单价：{{ cart.subPrice }}
                    </div>
                    <div style="position: absolute; left: 700px">
                      数量：{{ cart.goodsQuantity }}
                    </div>
                    <div style="position: absolute; left: 900px">
                      总价：{{ cart.totalPrice }}
                    </div>
                    <el-popover
                      v-show="cart.goodsStatus !== 1"
                      style="position: absolute; left: 1100px"
                      placement="top-start"
                      title="该商品可能已售空或禁售，无法添加"
                      width="200"
                      trigger="hover"
                    >
                      <i
                        style="font-size: 20px"
                        slot="reference"
                        class="el-icon-question"
                      ></i>
                    </el-popover>
                    <el-button
                      style="position: absolute; left: 1200px; top: 30px"
                      type="info"
                      size="small"
                      @click="updateCartById(cart)"
                      plain
                      >修改</el-button
                    >
                    <el-button
                      style="position: absolute; left: 1280px; top: 30px"
                      type="danger"
                      size="small"
                      @click="deleteCartByCart(cart)"
                      plain
                      >删除</el-button
                    >
                  </div>
                </div>
              </el-checkbox>
            </el-checkbox-group>
          </el-card>
        </li>
      </ul>
      <p v-if="loading" style="text-align: center">加载中...</p>
      <p v-if="noMore" style="text-align: center">没有更多了</p>
    </div>
    <!-- 底部面板 -->
    <div class="dispalySelect">
      <hr />
      <div style="float: left; line-height: 40px">
        总价 ￥{{ totalPrice }}元
      </div>
      <el-button
        style="float: right; margin-right: 80px"
        type="primary"
        @click="saveOrder"
        :disabled="selectCartList.length === 0"
        >提交订单</el-button
      >
    </div>
    <!-- 修改界面 -->
    <div v-if="isUpdate" class="mask"></div>
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      v-show="isUpdate"
      status-icon
      label-width="100px"
      class="form"
    >
      <el-form-item
        style="margin-top: 50px"
        prop="addressOptions"
        class="formInput"
        label-width="120px"
        label="收货地址："
      >
        <el-select
          v-model="ruleForm.addressOptions"
          placeholder="请选择收货地址："
          no-data-text="暂无地址，快去个人中心添加吧！"
        >
          <el-option
            v-for="item in options"
            :key="item.addrId"
            :label="item.info"
            :value="item.addrId"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item class="formInput" label-width="120px" label="商品名：">
        {{ ruleForm.goodsName }}
      </el-form-item>
      <el-form-item class="formInput" label-width="120px" label="单价：">
        ￥{{ ruleForm.subPrice }}元
      </el-form-item>
      <el-form-item
        class="formInput"
        label-width="120px"
        prop="goodsQuantity"
        label="数量："
      >
        <el-input
          v-model="ruleForm.goodsQuantity"
          type="number"
          autocomplete="off"
          :placeholder="`请输入数量(库存` + goodsInfo.goodsQuantity + `)件`"
        ></el-input>
      </el-form-item>
      <el-form-item
        style="color: red"
        label-width="120px"
        class="formInput"
        label="总价："
      >
        ￥{{ ruleForm.totalPrice }}元
      </el-form-item>

      <!-- 修改按钮 -->
      <el-form-item label-width="0px" style="margin-left: 154px">
        <el-button type="info" @click="quitUpdate" plain>取消</el-button>
        <el-button type="primary" @click="updateCheck">确定</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
 

<script>
import {
  getUserCart,
  getUserAddress,
  postUpdateUserCart,
  deleteCartGroupByMerchantId,
  deleteCartByCartId,
  postCartGoodsToGoodsOrder,
} from "@/api/user/index";
export default {
  name: "UserCart",
  mounted() {
    this.getGroupByMerchantIdCart(1, 3);
    this.getUserAddressInfo();
  },
  data() {
    //检查数量是否超过库存
    const checkQuantity = (rule, value, callback) => {
      this.ruleForm.totalPrice = parseInt(value) * this.ruleForm.subPrice;
      this.ruleForm.totalPrice =
        Math.round(this.ruleForm.totalPrice * 100) / 100;
      if (value === "") {
        callback(new Error("请输数量"));
      } else {
        if (parseInt(value) > parseInt(this.goodsInfo.goodsQuantity)) {
          callback(
            new Error("超过的最大库存量" + this.goodsInfo.goodsQuantity)
          );
        }
        callback();
      }
    };
    return {
      myStyle: {},

      isUpdate: false,
      cartSize: 0,
      count: 1,
      loading: false,
      //负责全选样式控制，1.若选项组没有选中一个，就是空的 2.若选项组选中了几个但没有全选，就是一条杠（-） 3.若全选就打勾

      //最大的一个分类
      groupByMerchantIdCart: {},
      // groupByMerchantIdCart: [
      //   {
      //     shopName: "凉皮铺子",
      //     cartList: [
      //       {
      //         cartId: "11",
      //         goodsName: "饼干",
      //         goodsPic: require("../../assets/backgroundImg/3.jpg"),
      //         subPrice: 36.66,
      //         goodsQuantity: 3,
      //         totalPrice: 109.98,
      //       },
      //       {
      //         cartId: "21",
      //         goodsName: "牛奶",
      //         goodsPic: require("../../assets/backgroundImg/3.jpg"),
      //         subPrice: 12,
      //         goodsQuantity: 2,
      //         totalPrice: 36,
      //       },
      //       {
      //         cartId: "1e",
      //         goodsName: "香蕉",
      //         goodsPic: require("../../assets/backgroundImg/3.jpg"),
      //         subPrice: 10,
      //         goodsQuantity: 3,
      //         totalPrice: 30,
      //       },
      //     ],
      //     cartListChecked: [],
      //     checkAll: false,
      //   },
      //   {
      //     shopName: "哈哈健身",
      //     cartList: [
      //       {
      //         cartId: "161",
      //         goodsName: "足球",
      //         goodsPic: require("../../assets/backgroundImg/3.jpg"),
      //         subPrice: 36.66,
      //         goodsQuantity: 3,
      //         totalPrice: 109.98,
      //       },
      //       {
      //         cartId: "221",
      //         goodsName: "篮球",
      //         goodsPic: require("../../assets/backgroundImg/3.jpg"),
      //         subPrice: 12,
      //         goodsQuantity: 2,
      //         totalPrice: 36,
      //       },
      //     ],
      //     cartListChecked: [],
      //     checkAll: false,
      //   },
      // ],
      //选择的cart
      selectCartList: [],
      totalPrice: 0,

      ruleForm: {
        addressOptions: "",
        subPrice: 0,
        goodsQuantity: 1,
        totalPrice: 0,
        goodsName: "",
      },
      rules: {
        goodsQuantity: [
          { required: true, validator: checkQuantity, trigger: "change" },
        ],
        addressOptions: [
          { required: true, message: "请选择收货地址", trigger: "blur" },
        ],
      },
      goodsInfo: {},
      // 地址信息选择器
      options: {},
    };
  },
  computed: {
    noMore() {
      return this.count >= this.cartSize;
    },
    disabled() {
      return this.loading || this.noMore;
    },
  },
  watch: {
    selectCartList(val) {
      this.totalPrice = 0;
      for (const key in this.selectCartList) {
        this.totalPrice += this.selectCartList[key].totalPrice;
      }
      this.totalPrice = Math.round(this.totalPrice * 100) / 100;
    },
  },
  methods: {
    //修改取消按钮
    quitUpdate() {
      this.isUpdate = false;
      this.myStyle = "";
    },
    //修改按钮
    updateCartById(cart) {
      this.myStyle = "dynamicStyles1";
      this.ruleForm.cartId = cart.cartId;
      this.ruleForm.goodsName = cart.goodsName;
      this.ruleForm.subPrice = cart.subPrice;
      this.ruleForm.goodsQuantity = cart.goodsQuantity;
      this.ruleForm.goodsQuantity = parseInt(this.ruleForm.goodsQuantity);
      this.ruleForm.totalPrice =
        this.ruleForm.subPrice * this.ruleForm.goodsQuantity;
      this.ruleForm.totalPrice =
        Math.round(this.ruleForm.totalPrice * 100) / 100;
      this.isUpdate = true;
    },
    //确定修改
    updateCheck() {
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          //获取addrInfo对象，在将其添加到ruleForm中
          this.selectCartList = [];
          let addrInfo = "";
          for (const key in this.options) {
            if (this.options[key].addrId === this.ruleForm.addressOptions) {
              addrInfo = this.options[key];
            }
          }

          this.ruleForm.consigneeName = addrInfo.consigneeName;
          this.ruleForm.consigneePhone = addrInfo.consigneePhone;
          this.ruleForm.address = addrInfo.address;
          this.ruleForm.goodsQuantity = parseInt(this.ruleForm.goodsQuantity);
          this.ruleForm.addrId = addrInfo.addrId;
          console.log("this.ruleForm", this.ruleForm);
          //开始发起请求进行修改
          postUpdateUserCart(this.ruleForm)
            .then((res) => {
              if (res.code === 1) {
                this.$message.success("修改成功啦");
                this.getGroupByMerchantIdCart(1, this.count * 3);
              } else if (res.code === 0) {
                this.$message.error(res.msg);
              }
            })
            .catch((err) => {
              console.log("修改购物车出错了,", err);
            });
          this.isUpdate = false;
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    //按照cartId删除 后端要注意merchant_user_cart映射表，
    deleteCartByCart(cart) {
      let params = {
        cartId: cart.cartId,
      };
      this.$confirm("这条购物车信息将永远删除，是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          deleteCartByCartId(params)
            .then((res) => {
              if (res.code === 1) {
                this.$message.success("删除成功");
                this.getGroupByMerchantIdCart(1, this.count * 3);
              } else if (res.code === 0) {
                this.$message.error(res.msg);
              }
            })
            .catch((err) => {
              console.log("删除购物车数据出错了", err);
            });
        })
        .catch(() => {
          return;
        });
    },
    //按照merchantId删除
    deleteCartByMerchantId(cartListObj) {
      this.$confirm("这条购物车信息将永远删除，是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          deleteCartGroupByMerchantId(cartListObj.cartList)
            .then((res) => {
              if (res.code === 1) {
                this.$message.success("删除成功！");
                this.getGroupByMerchantIdCart(1, this.count * 3);
              } else if (res.code === 0) {
                this.$message.error(res.msg);
              }
            })
            .catch((err) => {});
        })
        .catch(() => {
          return;
        });
    },

    //获取用户地址信息
    getUserAddressInfo() {
      getUserAddress()
        .then((res) => {
          if (res.code === 1) {
            if (res.data.length === 0) {
              return;
            }
            let addressInfo = res.data;
            addressInfo = this.handlerTableData(addressInfo);
            for (const key in addressInfo) {
              addressInfo[key].info =
                addressInfo[key].consigneeName +
                "  " +
                addressInfo[key].consigneePhone +
                "  " +
                addressInfo[key].address;
            }

            this.options = addressInfo;
          }
          if (res.code === 0) {
            this.$message.info(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error("请求用户地址信息失败");
        });
    },
    //将传过来的地区字符串转为数组
    handlerTableData(data) {
      for (const key in data) {
        var arr = data[key].areaAddress.split(" ");
        //清空数组第一个数据
        var newArr = arr.slice(0);
        newArr.shift();
        data[key].areaAddress = newArr;
      }
      return data;
    },
    //提交订单 设置订单状态为 1未支付 并跳转到订单页面
    saveOrder() {
      console.log("this.selectCartList", this.selectCartList);

      postCartGoodsToGoodsOrder(this.selectCartList)
        .then((res) => {
          if (res.code === 1) {
            this.$message.success("提交成功!");
            //提交成功后跳转到订单界面
            this.$router.push({
              path: "/user/UserOrder",
            });
          } else if (res.code === 0) {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error("提交订单出错了", err);
        });
    },
    //初始化获取购物车数据
    //current：第几个商户
    //size:一共多少个商户
    getGroupByMerchantIdCart(current, size) {
      let params = {
        current: current,
        size: size,
      };
      getUserCart(params)
        .then((res) => {
          if (res.code === 1) {
            //后端判断数据有没有修改，有就进行修改
            this.groupByMerchantIdCart = res.data.groupByMerchantIdCart;
            //根据addrId给每一项赋值

            this.cartSize = res.data.total;
          } else if (res.code === 0) {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error("请求购物车数据出错了！", err);
        });
    },
    //全选按钮
    handleCheckAllChange(cartListObj) {
      let cartList = cartListObj.cartList;
      //flag是否全选
      let flag = cartListObj.checkAll;
      cartListObj.cartListChecked = flag ? cartListObj.cartList : [];
      if (flag) {
        for (const key in cartList) {
          if (!this.selectCartList.includes(cartList[key])) {
            let temp = { ...cartList[key] };

            temp.goodsPic = temp.goodsPic.split("=")[1];
            this.selectCartList.push(temp);
          }
        }
      } else {
        //删除该cartList
        for (const key in cartList) {
          this.selectCartList = this.selectCartList.filter(
            (item) => item.cartId != cartList[key].cartId
          );
        }
      }
    },
    //value是选中的数据，是一个数组
    handleCheckedGoodsChange(cartListObj) {
      let value = cartListObj.cartListChecked;

      //先删除cartIdList中存在的cartListObj中含有的cartList cartId，
      let cartListTemp = cartListObj.cartList;
      for (const key in cartListTemp) {
        this.selectCartList = this.selectCartList.filter(
          (item) => item.cartId !== cartListTemp[key].cartId
        );
      }
      //再将value全部添加进去
      for (const key in value) {
        let temp = { ...value[key] };
        temp.goodsPic = temp.goodsPic.split("=")[1];
        this.selectCartList.push(temp);
      }
      let checkedCount = value.length;
      cartListObj.checkAll = checkedCount === cartListObj.cartList.length;
    },
    //滚动加载
    load() {
      this.loading = true;
      setTimeout(() => {
        this.loading = false;
        this.count += 1;
        let params = {
          current: this.count,
          size: 3,
        };
        getUserCart(params)
          .then((res) => {
            if (res.code === 1) {
              this.groupByMerchantIdCart = this.groupByMerchantIdCart.concat(
                res.data.groupByMerchantIdCart
              );
              this.cartSize = res.data.total;
            } else if (res.code === 0) {
              this.$message.error(res.msg);
            }
          })
          .catch((err) => {
            this.$message.error("请求购物车数据出错了！", err);
          });
      }, 800);
    },
  },
};
</script>

<style scoped>
.box-card {
  margin: 20px;
  /* width: 480px;   */
}
.dynamicStyles1 {
  background-color: rgba(255, 255, 255, 0.363);
  opacity: 0.8;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.formInput {
  width: 400px;
}
.list {
  height: 550px;
}
.dispalySelect {
  margin-left: 60px;
  margin-top: 30px;
}
.mask {
  background-color: rgb(0, 0, 0) !important;
  opacity: 0.3;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}
.form {
  position: fixed;
  top: 100px;
  left: 50%;
  transform: translateX(-50%);
  width: 450px;
  height: 460px;
  background-color: rgb(255, 255, 255);
}
</style>