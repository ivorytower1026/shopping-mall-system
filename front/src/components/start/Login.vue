<template>
  <div>
    <img
      :src="bc_img[typeId - 1]"
      alt="loading..."
      width="100%"
      height="100%"
      style="opacity: 0.7"
    />

    <div class="form">
      <h3
        style="
          text-align: center;
          color: skyblue;
          font-size: 28px;
          font-weight: 600;
        "
      >
        {{
          typeId === 1 ? "用户登录" : typeId === 2 ? "商户登录" : "管理员登录"
        }}
      </h3>
      <el-menu
        style="background-color: rgba(255, 255, 255, 0.111)"
        class="el-menu-demo nav"
        mode="horizontal"
        default-active="1"
        @select="handeSelect"
      >
        <el-menu-item index="1">短信登录</el-menu-item>
        <el-menu-item index="2">密码登录</el-menu-item>
      </el-menu>
      <!-- 短信登录 ----------------------------------->
      <el-form
        :model="ruleForm1"
        :rules="rules1"
        ref="ruleForm1"
        v-show="f_id === 1"
        status-icon
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item class="formInput" label="手机号：" prop="phone">
          <el-input
            v-model="ruleForm1.phone"
            type="text"
            autocomplete="off"
            placeholder="手机号登录/注册"
          ></el-input>
        </el-form-item>

        <!-- 验证码 -->
        <el-form-item class="formInput" label="验证码：" prop="code">
          <el-input
            v-model="ruleForm1.code"
            type="text"
            autocomplete="off"
            placeholder="请输入验证码"
            style="width: 156px"
          ></el-input>

          <el-button style="margin-left: 30px" @click="getCode(ruleForm1.phone)"
            >获取验证码</el-button
          >
        </el-form-item>

        <!-- //图形验证码 -->
        <el-form-item
          class="formInput"
          prop="image"
          v-show="loginFailCount >= 2"
        >
          <el-input
            v-model="ruleForm1.image"
            style="width: 200px"
            type="text"
            autocomplete="off"
            placeholder="请输入右侧验证码"
          >
          </el-input>
          <canvas
            id="canvas1"
            width="90"
            height="40"
            style="margin: -14px 5px"
            @click="handleClick1"
          ></canvas>
        </el-form-item>

        <span style="display: block; margin-bottom: 20px; margin-left: 100px">
          <el-button type="info" @click="sign" plain>立即注册</el-button>
        </span>
        <el-form-item style="margin-bottom: 30px">
          <el-button
            type="primary"
            style="width: 300px"
            @click="submitForm(ruleForm1, 'ruleForm1')"
            >登录</el-button
          >
        </el-form-item>
      </el-form>

      <!-- 密码登录 ---------------------------------------------------------------------------->
      <el-form
        :model="ruleForm2"
        :rules="rules2"
        ref="ruleForm2"
        v-show="f_id === 2"
        status-icon
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item class="formInput" label="手机号：" prop="phone">
          <el-input
            v-model="ruleForm2.phone"
            type="text"
            autocomplete="off"
            placeholder="手机号登录/注册"
          ></el-input>
        </el-form-item>
        <el-form-item class="formInput" label="密码：" prop="pass">
          <el-input
            v-model="ruleForm2.pass"
            type="password"
            autocomplete="off"
            placeholder="请输入密码"
          ></el-input>
        </el-form-item>

        <!-- //图形验证码 -->
        <el-form-item
          class="formInput"
          prop="image"
          v-show="loginFailCount >= 2"
        >
          <el-input
            v-model="ruleForm2.image"
            style="width: 200px"
            type="password"
            autocomplete="off"
            placeholder="请输入右侧验证码"
          >
          </el-input>
          <canvas
            id="canvas2"
            width="90"
            height="40"
            style="margin: -14px 5px"
            @click="handleClick2"
          ></canvas>
        </el-form-item>

        <span style="display: block; margin-bottom: 20px; margin-left: 100px">
          <el-button type="info" @click="sign" plain>立即注册</el-button>
        </span>
        <el-form-item style="margin-bottom: 30px">
          <el-button
            type="primary"
            style="width: 300px"
            @click="submitForm(ruleForm2, 'ruleForm2')"
            >登录</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { userlogin } from "@/api/login/login";
import { getPhoneCode } from "@/api/common/getCode";
import ImageVerify from "@/js/image-verify";
import { administratorLogin } from "@/api/login/AdministratorLogin";
import { merchantLogin } from "@/api/login/merchanLogin";
export default {
  name: "Login",
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
    //检查验证码登录的图形验证码
    const checkimageCode1 = (rule, value, callback) => {
      if (this.loginFailCount < 2) callback();
      value = value.trim().toLowerCase();
      if (value === "") {
        callback(new Error("请输入图形验证码"));
      } else {
        if (value === this.imageVerify1.code.toLowerCase()) {
          callback();
        } else {
          callback(new Error("验证码错误"));
        }
      }
    };
    //检查密码登录的图形验证码
    const checkimageCode2 = (rule, value, callback) => {
      if (this.loginFailCount < 2) callback();
      value = value.trim().toLowerCase();
      if (value === "") {
        callback(new Error("请输入图形验证码"));
      } else {
        if (value === this.imageVerify2.code.toLowerCase()) {
          callback();
        } else {
          callback(new Error("验证码错误"));
        }
      }
    };
    //检查密码登录的密码栏
    const checkPass = (rule, value, callback) => {
      value = value.trim().toLowerCase();
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        const pattern = /^[a-zA-Z]\w{5,17}$/;
        if (pattern.test(value)) {
          callback();
        } else {
          if (value.length < 6 || value.length > 18) {
            callback(new Error("密码长度在6~18之间"));
          }
          callback(new Error("以字母开头，只能包含字母、数字和下划线"));
        }
      }
    };

    return {
      //背景图片
      bc_img: [
        require("../../assets/backgroundImg/1.jpg"),
        require("../../assets/backgroundImg/2.jpg"),
        require("../../assets/backgroundImg/4.jpg"),
      ],

      f_id: 1, //用于切换短信登陆或密码登录
      imageVerify1: "", //图片验证码实例
      imageVerify2: "",
      loginFailCount: 0, //登录失败次数 超过2次就启用图形验证码

      ruleForm1: {
        phone: "",
        code: "",
        image: "",
      },
      rules1: {
        phone: [
          {
            required: true,
            validator: checkPhone,
            trigger: "blur",
          },
        ],
        code: [
          {
            required: true,
            validator: checkCode,
            trigger: "blur",
          },
        ],
        image: [
          {
            required: true,
            validator: checkimageCode1,
            trigger: "blur",
          },
        ],
      },

      ruleForm2: {
        phone: "",
        pass: "",
        image: "",
      },
      rules2: {
        phone: [
          {
            required: true,
            validator: checkPhone,
            trigger: "blur",
          },
        ],
        pass: [
          {
            required: true,
            validator: checkPass,
            trigger: "blur",
          },
        ],
        image: [
          {
            required: true,
            validator: checkimageCode2,
            trigger: "blur",
          },
        ],
      },
      typeId: 0,
    };
  },

  mounted() {
    this.typeId = parseInt(this.$route.query.typeId);
    this.imageVerify1 = new ImageVerify({
      id: "canvas1",
      width: 90,
      height: 40,
    });
    this.imageVerify2 = new ImageVerify({
      id: "canvas2",
      width: 90,
      height: 40,
    });
  },
  methods: {
    //切换展示页
    handeSelect(key, keyPath) {
      key = Math.floor(key);
      this.f_id = key;
      this.loginFailCount = 0;
    },

    //图片验证码点击函数
    handleClick1() {
      this.imageVerify1.start();
      console.log(this.imageVerify1.code);
    },
    handleClick2() {
      this.imageVerify2.start();
      console.log(this.imageVerify2.code);
    },
    //获取验证码
    getCode(phone) {
      console.log("获取验证码");
      if (phone.trim() === "") {
        this.$message.info("请先输入手机号");
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
    //注册=================================================================================
    sign() {
      this.$router.push({
        path: "/Sign",
        query: {
          typeId: this.typeId,
        },
      });
    },

    //登录=================================================================================
    submitForm(formName, str) {
      console.log("this.typeId", this.typeId);
      console.log(formName.phone);
      this.$refs[str].validate((valid) => {
        if (valid) {
          let params = {
            phone: formName.phone,
            password: formName.pass,
            code: formName.code,
          };
          if (this.typeId === 1) {
            //用户登录
            this.userToLogin(params);
          }
          if (this.typeId === 2) {
            this.merchantToLogin(params);
          }

          if (this.typeId === 3) {
            this.administratorToLogin(params);
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    userToLogin(params) {
      userlogin(params)
        .then((res) => {
          if (String(res.code) === "1") {
            // 登录成功
            this.$store.commit("set_loginSuccess", true);
            this.$message.success(res.data.message);
            localStorage.setItem("user_id", res.data.userId);
            //向浏览器中存入token，方便后期验证
            localStorage.setItem("user_token", res.data.token);

            this.$router.push({
              path: "/user/ShoppingMall",
            });
          }
          //登录失败
          if (String(res.code) === "0") {
            this.$message.error(res.msg);
            console.log(res.msg);
            this.loginFailCount += 1;
            console.log("登录失败次数", this.loginFailCount);
          }
        })
        .catch((err) => {
          this.$message.error("请求出错了：" + err);
        });
    },

    administratorToLogin(params) {
      administratorLogin(params)
        .then((res) => {
          if (res.code === 1) {
            // 登录成功
            localStorage.setItem("administrator_id", res.data.administratorId);

            //向浏览器中存入token，方便后期验证
            localStorage.setItem(
              "administrator_token",
              res.data.administratorToken
            );
            this.$router.push({
              path: "/administrator",
              query: {
                administratorName: res.data.administratorName,
              },
            });
          }
          //注册失败
          if (String(res.code) === "0") {
            this.$message.error(res.msg);
            console.log(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error("管理员登录请求出错了" + err);
        });
    },

    //商户登录
    merchantToLogin(params) {
      merchantLogin(params)
        .then((res) => {
          if (res.code === 1) {
            // 注册成功
            localStorage.setItem("merchant_id", res.data.merchantId);
            //向浏览器中存入token，方便后期验证
            localStorage.setItem("merchant_token", res.data.merchantToken);
            this.$router.push({
              path: "/merchant",
            });
          }
          //注册失败
          if (String(res.code) === "0") {
            this.$message.error(res.msg);
            console.log(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error("商户登录请求出错了" + err);
        });
    },
  },
};
</script>

<style scoped>
.nav {
  margin-bottom: 40px;
}
.yanbutton {
  margin-top: 10px;
}
.formInput {
  width: 400px;
}
.form {
  position: fixed;
  top: 100px;
  left: 50%;
  transform: translateX(-50%);
  width: 450px;
  height: 500px;
  background-color: rgba(255, 255, 255, 0.811);
}
</style>