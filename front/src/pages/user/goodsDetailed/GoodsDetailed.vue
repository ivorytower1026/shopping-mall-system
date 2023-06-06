<template>
  <div>
    <div class="parentBox">
      <img
        :src="goodsInfo.goodsPic"
        alt="加载中"
        width="750px"
        height="500px"
        style="background-color: grey; float: left"
      />
      <div class="goodsInfoByIdClass">
        <div class="item">
          <div style="font-size: 20px; font-weight: 700">
            {{ goodsInfo.goodsName }}
          </div>
        </div>

        <div class="item">
          <div class="item_label">商品原价：</div>
          <div
            class="itme_content"
            style="text-decoration: line-through; color: rgba(0, 0, 0, 0.5)"
          >
            ￥{{ goodsInfo.price }}元
          </div>
        </div>
        <div class="item">
          <div class="item_label">商品折后价：</div>
          <div class="itme_content">￥ {{ goodsInfo.subPrice }}元</div>
        </div>
        <div class="item">
          <div class="item_label" style="float: left">选择数量：</div>
          <div
            class="itme_content"
            style="position: absolute; top: -9px; left: 55px"
          >
            <template>
              <el-input-number
                v-model="num"
                :min="1"
                :max="goodsQuantity"
                :disabled="isUsable"
                label="描述文字"
              ></el-input-number>
            </template>
          </div>
        </div>

        <div class="mybutton">
          <el-button type="danger" @click="clickAddCart" :disabled="isUsable"
            >加入购物车</el-button
          >
          <el-button
            type="primary"
            style="margin-left: 150px"
            @click="purchaseNow"
            :disabled="isUsable"
            >立即购买</el-button
          >
        </div>
      </div>
    </div>
    <!-- 订单界面 && 购物车界面-->

    <div v-if="orderFrame" class="mask"></div>
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      v-show="orderFrame"
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
          @change="selectValue"
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
        prop="quantity"
        label="数量："
      >
        <el-input
          v-model="ruleForm.quantity"
          type="number"
          autocomplete="off"
          :placeholder="`请输入数量(库存` + goodsInfo.quantity + `)件`"
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
      <!-- 订单按钮 -->
      <el-form-item
        label-width="0px"
        style="margin-left: 74px"
        v-if="typeNum === 1"
      >
        <el-button type="info" @click="saveOrder(3)" plain>取消支付</el-button>
        <el-button type="primary" @click="saveOrder(1)" plain
          >保存订单</el-button
        >
        <el-button type="primary" @click="saveOrder(2)">立即支付</el-button>
      </el-form-item>
      <!-- //购物车按钮 -->
      <el-form-item
        label-width="0px"
        style="margin-left: 154px"
        v-if="typeNum === 0"
      >
        <el-button type="info" @click="orderFrame = false" plain
          >取消</el-button
        >

        <el-button type="primary" @click="addToCart">确定</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
import { getGoodsInfoById } from "@/api/goods/index";
import {
  postGoodsInCart,
  postGoodsInOrder,
  getUserAddress,
} from "@/api/user/index";
export default {
  name: "GoodsDetailed",
  data() {
    //检查数量是否超过库存
    const checkQuantity = (rule, value, callback) => {
      this.ruleForm.totalPrice = parseInt(value) * this.ruleForm.subPrice;
      this.ruleForm.totalPrice =
        Math.round(this.ruleForm.totalPrice * 100) / 100;
      if (value === "") {
        callback(new Error("请输数量"));
      } else {
        if (parseInt(value) > parseInt(this.goodsInfo.quantity)) {
          callback(new Error("超过的最大库存量" + this.goodsInfo.quantity));
        }
        callback();
      }
    };
    return {
      //根据数字判断是加入购物车还是保存订单 0购物车 1订单
      typeNum: 0,
      orderFrame: false,
      goodsId: "",
      goodsInfo: {},
      num: 1,
      isUsable: false,
      goodsQuantity: 1,

      ruleForm: {
        addressOptions: "",
        subPrice: 0,
        quantity: 1,
        totalPrice: 0,
        goodsName: "",
      },
      rules: {
        quantity: [
          { required: true, validator: checkQuantity, trigger: "change" },
        ],
        addressOptions: [
          { required: true, message: "请选择收货地址", trigger: "blur" },
        ],
      },

      // 地址信息选择器
      options: {},
    };
  },
  mounted() {
    this.getGoodsInfoById();
    this.getUserAddressInfo();
  },
  methods: {
    //获取选择的地址值
    selectValue(val) {
      console.log("this.ruleForm.addressOptions", this.ruleForm.addressOptions);
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
      console.log("data", data);
      return data;
    },
    //取消支付 立即支付 保存订单
    saveOrder(value) {
      //取消支付就直接关闭
      if (value === 3) {
        this.orderFrame = false;
        return;
      }
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          this.ruleForm.quantity = parseInt(this.ruleForm.quantity);
          this.goodsInfo.subPrice =
            Math.round(this.goodsInfo.subPrice * 100) / 100;
          //从addrId中获取想要的对象
          let addrInfo = "";
          for (const key in this.options) {
            if (this.options[key].addrId === this.ruleForm.addressOptions) {
              addrInfo = this.options[key];
            }
          }
          let tempPic = this.goodsInfo.goodsPic;
          tempPic = tempPic.split("=")[1];
          let data = {
            merchantId: this.goodsInfo.merchantId,
            goodsId: this.goodsInfo.goodsId,
            goodsName: this.goodsInfo.goodsName,
            goodsQuantity: this.ruleForm.quantity,
            totalPrice: this.ruleForm.quantity * this.goodsInfo.subPrice,
            subPrice: this.goodsInfo.subPrice,
            orderStatus: value,
            addrId: addrInfo.addrId,
            address: addrInfo.address,
            consigneeName: addrInfo.consigneeName,
            consigneePhone: addrInfo.consigneePhone,
            goodsPic: tempPic,
          };
          this.orderFrame = false;
          postGoodsInOrder(data)
            .then((res) => {
              if (res.code === 1) {
                this.$message.success("已提交订单！");
              } else if (res.code === 0) {
                this.$message.error(res.msg);
              }
            })
            .catch((err) => {
              console.log("提交订单出错了", err);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    //将区域数组转为字符串并拼接详细地址合成address
    arrToStr(arr, detailedAddress) {
      var res = "";
      for (const key in arr) {
        res += arr[key];
      }
      return res + detailedAddress;
    },
    //点击加入购物车按钮
    clickAddCart() {
      this.typeNum = 0;
      this.ruleForm.goodsName = this.goodsInfo.goodsName;
      this.ruleForm.subPrice = this.goodsInfo.subPrice;
      this.ruleForm.quantity = this.num;
      this.ruleForm.totalPrice = this.num * this.ruleForm.subPrice;
      this.orderFrame = true;
    },
    //加入购物车
    addToCart() {
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          this.ruleForm.quantity = parseInt(this.ruleForm.quantity);
          this.goodsInfo.subPrice =
            Math.round(this.goodsInfo.subPrice * 100) / 100;
          //从addrId中获取想要的对象
          let addrInfo = "";
          for (const key in this.options) {
            if (this.options[key].addrId === this.ruleForm.addressOptions) {
              addrInfo = this.options[key];
            }
          }
          let data = {
            merchantId: this.goodsInfo.merchantId,
            goodsId: this.goodsInfo.goodsId,
            goodsName: this.goodsInfo.goodsName,
            goodsQuantity: this.ruleForm.quantity,
            totalPrice: this.ruleForm.quantity * this.goodsInfo.subPrice,
            subPrice: this.goodsInfo.subPrice,
            goodsPic: this.goodsInfo.goodsPic,
            goodsStatus: this.goodsInfo.goodsStatus,
            address: addrInfo.address,
            consigneeName: addrInfo.consigneeName,
            consigneePhone: addrInfo.consigneePhone,
            addrId: addrInfo.addrId,
          };
          this.orderFrame = false;
          postGoodsInCart(data)
            .then((res) => {
              if (res.code === 1) {
                this.$message.success("添加成功！");
              } else if (res.code === 0) {
                this.$message.error(res.msg);
              }
            })
            .catch((err) => {
              console.log("加入购物车出错了", err);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    //立即购买
    purchaseNow() {
      this.typeNum = 1;
      this.ruleForm.goodsName = this.goodsInfo.goodsName;
      this.ruleForm.subPrice = this.goodsInfo.subPrice;
      this.ruleForm.quantity = this.num;
      this.ruleForm.totalPrice = this.num * this.ruleForm.subPrice;
      console.log("@@@@@", this.ruleForm);
      this.orderFrame = true;
    },
    //获取商品详细信息
    getGoodsInfoById() {
      this.goodsId = this.$route.query.goodsId;
      getGoodsInfoById({ goodsId: this.goodsId })
        .then((res) => {
          if (res.code === 1) {
            this.goodsInfo = res.data;
            this.goodsInfo.goodsPic = `/api/goods/downloadGoodsPic?name=${this.goodsInfo.goodsPic}`;
            this.goodsInfo.quantity = parseInt(this.goodsInfo.quantity);
            this.goodsQuantity = this.goodsInfo.quantity;
            this.isUsable = this.goodsInfo.goodsStatus === 1 ? false : true;
            console.log(this.goodsInfo);
          } else if (res.code === 0) {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          console.log("获取具体商品出错了！", err);
        });
    },
  },
};
</script>

<style scoped>
.parentBox {
  position: relative;
  width: 1200px;
  top: 60px;
  left: 50%;
  height: 500px;
  transform: translateX(-50%);
}
.goodsInfoByIdClass {
  position: absolute;
  float: left;
  width: 200px;
  top: 0px;
  left: 850px;
}
.item {
  position: relative;
  font-weight: 500;
  width: 600px;
  height: 100px;
}
.item div {
  float: left;
}
.item_label {
  color: rgba(0, 0, 0, 0.704);
}
.itme_content {
  margin-left: 50px;
}
/* 底下的按钮 */
.mybutton {
  position: relative;
}
.mybutton button {
  position: absolute;
  top: 0px;
}
.formInput {
  width: 400px;
}
.mask {
  background-color: rgb(0, 0, 0);
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