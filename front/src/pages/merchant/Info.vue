<template>
  <div class="infoForm">
    <el-skeleton style="width: 1500px" :loading="loading" animated :count="5">
      <!-- 展示信息 -->
      <template slot="template">
        <!-- <el-skeleton-item variant="text" style="width: 50%" /> -->
        <el-skeleton-item
          variant="h2"
          style="width: 80%; margin-top: 30px; height: 40px"
        />
      </template>
      <template>
        <el-descriptions
          title="店铺信息"
          style="width: 1200px; float: left; font-size: 16px; margin: 5px auto"
          border
          :column="1"
        >
          <template slot="extra">
            <el-button type="Info" size="medium" plain @click="updatePhone"
              >换绑手机号</el-button
            >
          </template>
          <template slot="extra">
            <el-button type="primary" size="medium" @click="edit"
              >修改信息</el-button
            >
          </template>

          <el-descriptions-item label="商铺名字">{{
            merchantInfo.shopName
          }}</el-descriptions-item>
          <el-descriptions-item label="商铺电话"
            >{{ merchantInfo.phone }}
          </el-descriptions-item>
          <el-descriptions-item label="商铺位置">{{
            merchantInfo.shopAddress
          }}</el-descriptions-item>
          <el-descriptions-item label="入驻时间">{{
            merchantInfo.createTime
          }}</el-descriptions-item>
        </el-descriptions>
      </template>
    </el-skeleton>

    <!-- 修改信息界面 -->
    <div v-show="isEdit" class="mask"></div>
    <div class="form" v-show="isEdit">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        status-icon
        label-width="100px"
        style="background-color: rgb(255, 255, 255)"
      >
        <h3 style="text-align: center; margin: 0px; padding: 40px">
          修改商铺信息
        </h3>
        <!-- 商铺名字 -->
        <el-form-item
          class="formInput"
          label="商铺名字："
          prop="shopName"
          style="padding-left: 20px"
        >
          <el-input
            v-model="ruleForm.shopName"
            type="text"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <!-- 收获地址 -->
        <el-form-item
          class="formInput"
          label="商铺所在地区："
          prop="areaAddress"
          label-width="120px"
        >
          <div class="block">
            <el-cascader
              :props="{ checkStrictly: true }"
              clearable
              style="width: 280px"
              expandTrigger="hover"
              v-model="ruleForm.areaAddress"
              :options="options"
              @change="handleChange(ruleForm.areaAddress)"
            ></el-cascader>
          </div>
        </el-form-item>
        <!-- 详细地址 -->
        <el-form-item
          class="formInput"
          label="详细地址："
          prop="detailedAddress"
          label-width="120px"
        >
          <el-input
            v-model="ruleForm.detailedAddress"
            type="text"
            autocomplete="off"
            placeholder="请输入详细地址"
          ></el-input>
        </el-form-item>

        <el-form-item style="padding-bottom: 40px; padding-left: 20px">
          <el-button
            type="info"
            plain
            style="width: 100px"
            @click="handlerCancelEdit()"
            >取消</el-button
          >
          <el-button
            type="primary"
            style="width: 100px; margin-left: 100px"
            @click="saveMerchantInfo('ruleForm')"
            >保存</el-button
          >
        </el-form-item>
      </el-form>
    </div>

    <!-- 换绑手机号界面 -->
    <div v-show="isUpdatePhone" class="mask"></div>
    <div class="form" v-show="isUpdatePhone">
      <el-form
        :model="ruleForm1"
        :rules="rules1"
        ref="ruleForm1"
        status-icon
        label-width="100px"
        style="background-color: rgb(255, 255, 255)"
      >
        <h3 style="text-align: center; margin: 0px; padding: 40px">
          换绑手机号
        </h3>
        <!-- 原手机号 -->
        <el-form-item class="formInput" label="原始手机号：">
          <div style="font-size: 16px">
            {{ merchantInfo.phone }}
          </div>
        </el-form-item>

        <!-- 原手机号验证码 -->
        <el-form-item class="formInput" label="验证码：" prop="u_code1">
          <el-input
            v-model="ruleForm1.u_code1"
            type="text"
            autocomplete="off"
            placeholder="原手机号验证码"
            style="width: 156px"
          ></el-input>

          <el-button
            style="margin-left: 30px"
            @click="getCode(merchantInfo.phone)"
            >获取验证码</el-button
          >
        </el-form-item>

        <!-- 新手机号 -->
        <el-form-item class="formInput" label="新手机号：" prop="u_phone">
          <el-input
            v-model="ruleForm1.u_phone"
            type="text"
            autocomplete="off"
            placeholder="请输入新的手机号"
          ></el-input>
        </el-form-item>

        <!-- 新手机号验证码 -->
        <el-form-item class="formInput" label="验证码：" prop="u_code2">
          <el-input
            v-model="ruleForm1.u_code2"
            type="text"
            autocomplete="off"
            placeholder="新手机号验证码"
            style="width: 156px"
          ></el-input>

          <el-button
            style="margin-left: 30px"
            @click="getCode(ruleForm1.u_phone)"
            >获取验证码</el-button
          >
        </el-form-item>

        <el-form-item style="padding-bottom: 40px; padding-left: 20px">
          <el-button
            type="info"
            plain
            style="width: 100px"
            @click="handlerCancelEditPhone()"
            >取消</el-button
          >
          <el-button
            type="primary"
            style="width: 100px; margin-left: 100px"
            @click="updateMerchantPhone('ruleForm1')"
            >保存</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getMerchantInfo } from "@/api/merchant/index";
import { postMerchantInfo } from "@/api/merchant/index";
import { postMerchantPhone } from "@/api/merchant/index";

import { getPhoneCode } from "@/api/common/getCode";

export default {
  name: "Info",

  data() {
    //检查手机号
    const checkPhone = (rule, value, callback) => {
      value = value.trim();
      if (value === "") {
        callback(new Error("请输入手机号"));
      } else {
        const pattern =
          /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
        if (pattern.test(value)) {
          callback();
        } else {
          callback(new Error("手机号格式错误"));
        }
      }
    };
    //检查验证码
    const checkCode = (rule, value, callback) => {
      value = value.trim();
      if (value === "") {
        callback(new Error("请输入验证码"));
      } else {
        if (value.length === 6) {
          callback();
        } else {
          callback(new Error("验证码为6位"));
        }
      }
    };
    //检查商铺名字
    const checkShopName = (rule, value, callback) => {
      value = value.trim();
      if (value === "") {
        callback(new Error("请输入商铺名字"));
        
      } else {
        if(value.length>=12){
          callback(new Error('商店名字过长'))
        }
        callback();
      }
    };

    return {
      loading: true,
      options: this.$store.state.addressOptions,
      merchantInfo: {
        // shopName: "超级商店",
        // phone: "13812345678",
        // shopAddress: "上海中心花园",
        // createTime: "2022-10-12 20:37:36",
      },

      isEdit: false,
      isUpdatePhone: false,

      //校验用字段
      ruleForm: {
        shopName: "",
        areaAddress: "",
        areaAddress: "",
      },
      rules: {
        shopName: [
          { required: true,validator: checkShopName, trigger: "blur" },
        ],
        detailedAddress: [
          { required: true, message: "请输入详细地址", trigger: "blur" },
        ],
        areaAddress: [
          { required: true, message: "请选择地区", trigger: "change" },
        ],
      },

      //换绑手机号窗口
      ruleForm1: {
        u_phone: "",
        u_code1: "",
        u_code2: "",
      },
      rules1: {
        u_phone: [
          {
            required: true,
            validator: checkPhone,
            trigger: "blur",
          },
        ],
        u_code1: [
          {
            required: true,
            validator: checkCode,
            trigger: "blur",
          },
        ],
        u_code2: [
          {
            required: true,
            validator: checkCode,
            trigger: "blur",
          },
        ],
      },
    };
  },

  mounted() {
    this.getNewmerchantInfo();
  },
  methods: {
    //取消编辑按钮
    handlerCancelEdit() {
      this.$refs.ruleForm.clearValidate();
      this.isEdit = false;
    },
    // 取消换绑手机号按钮
    handlerCancelEditPhone() {
      this.$refs.ruleForm1.clearValidate();
      this.isUpdatePhone = false;
    },

    //获取商户信息
    getNewmerchantInfo() {
      const params = {
        merchantId: localStorage.getItem("merchant_id"),
      };

      getMerchantInfo(params)
        .then((res) => {
          if (res.code === 1) {
            this.merchantInfo = res.data;
            this.$store.commit("set_merchantName", this.merchantInfo.shopName);
            if (this.merchantInfo.shopAddress === null) {
              this.merchantInfo.shopAddress = "未知";
            }

            this.loading = false;
          }
          if (res.code === 0) {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error("请求商户信息出错了", err);
        });
    },
    //更改商户信息并保存
    saveMerchantInfo(str) {
      console.log(this.ruleForm);
      this.$refs[str].validate((valid) => {
        if (valid) {
          const data = {
            merchantId: localStorage.getItem("merchant_id"),
            shopName: this.ruleForm.shopName,
            areaAddress: this.arrToStr(this.ruleForm.areaAddress),
            detailedAddress: this.ruleForm.detailedAddress,
          };
          postMerchantInfo(data)
            .then((res) => {
              if (res.code === 1) {
                this.isEdit = false;
                this.getNewmerchantInfo();
                this.$message.success("修改成功");
              }
              if (res.code === 0) {
                this.$message.error(res.msg);
              }
              // this.isEdit = false;
            })
            .catch((err) => {
              console.log("请先登录", err);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    //点击换绑手机号操作
    updatePhone() {
      this.isUpdatePhone = true;
    },

    //更改手机号发请求用的
    updateMerchantPhone(str) {
      this.$refs[str].validate((valid) => {
        if (valid) {
          let params = {
            phone1: this.merchantInfo.phone,
            code1: this.ruleForm1.u_code1,
            phone2: this.ruleForm1.u_phone,
            code2: this.ruleForm1.u_code1,
            merchantId: localStorage.getItem("merchant_id"),
          };
          postMerchantPhone(params)
            .then((res) => {
              if (res.code === 1) {
                this.getNewmerchantInfo();
                this.$message.success("修改成功");
                this.isUpdatePhone = false;
              }
              if (res.code === 0) {
                this.$message.error(res.msg);
              }
            })
            .catch((err) => {
              console.log("请先登录！", err);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    //获取传值的手机号对应的验证码
    getCode(phone) {
      console.log("获取验证码");
      const pattern =
        /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if (phone.trim() === "") {
        this.$message.info("请先输入手机号");
        return;
      }
      if (!pattern.test(phone)) {
        this.$message.info("手机号格式错误");
        return;
      }
      const params = {
        phone,
      };
      getPhoneCode(params)
        .then((res) => {
          if (res.code === 1) {
            this.$message.success("已发送验证码");
          }
          if (res.code === 0) {
            this.$message.error("发送验证码失败");
          }
        })
        .catch((err) => {
          this.$message.error("请求出错了：" + err);
        });
    },
    //点击（修改信息）时做的操作
    edit() {
      this.isEdit = true;
      this.ruleForm = this.merchantInfo;
      this.ruleForm.areaAddress = this.ruleForm.areaAddress.split(" ");
      console.log(this.ruleForm);
      console.log("点击了");
    },

    arrToStr(arr) {
      var res = "";
      for (const key in arr) {
        if (key !== 0) {
          res += " ";
        }
        res += arr[key];
      }
      return res;
    },
    arrObjToArr(arrObj) {
      console.log("进入了arrObjToArr");
      console.log("arrObj的值", arrObj);
      const arr = [];
      for (const key in arrObj) {
        arr.push(arrObj[key].name);
      }
      return arr;
    },
  },
};
</script>

<style scoped>
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
.formInput {
  width: 400px;
  margin-left: 20px;
}
.form {
  position: fixed;
  top: 130px;
  left: 50%;
  transform: translateX(-50%);
  width: 480px;
  height: 0px;
  /* background-color: rgb(255, 255, 255); */
  background-color: rgba(0, 0, 0, 0.423);
  z-index: 1;
}
.infoForm {
  position: absolute;
}
</style>