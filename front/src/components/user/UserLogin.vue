<template>
  <div>
    <div class="form">
      <el-menu
        class="el-menu-demo nav"
        mode="horizontal"
        default-active="1"
        @select="handeSelect"
      >
        <el-menu-item index="1">短信登录</el-menu-item>
        <el-menu-item index="2">密码登录</el-menu-item>
        <!-- <button class="x_button" @click="destroySelf">X</button> -->
        <el-button
          type="info"
          icon="el-icon-close"
          circle
          style="
            color: black;
            position: relative;
            top: 8px;
            right: -200px;
            background-color: rgba(63, 61, 61, 0.118);
          "
          @click="destroySelf"
        ></el-button>
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
        style="background-color: rgb(255, 255, 255)"
      >
        <el-form-item class="formInput" label="手机号" prop="u_phone">
          <el-input
            v-model="ruleForm1.u_phone"
            type="text"
            autocomplete="off"
            placeholder="手机号登录/注册"
          ></el-input>
        </el-form-item>

        <!-- 验证码 -->
        <el-form-item class="formInput" label="验证码" prop="u_code">
          <el-input
            v-model="ruleForm1.u_code"
            type="text"
            autocomplete="off"
            placeholder="请输入验证码"
            style="width: 156px"
          ></el-input>

          <el-button
            style="margin-left: 30px"
            @click="getCode(ruleForm1.u_phone)"
            >获取验证码</el-button
          >
        </el-form-item>

        <!-- //图形验证码 -->
        <el-form-item
          class="formInput"
          prop="u_image"
          v-show="loginFailCount >= 2"
        >
          <el-input
            v-model="ruleForm1.u_image"
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
        style="background-color: rgb(255, 255, 255)"
      >
        <el-form-item class="formInput" label="手机号" prop="u_phone">
          <el-input
            v-model="ruleForm2.u_phone"
            type="text"
            autocomplete="off"
            placeholder="手机号登录/注册"
          ></el-input>
        </el-form-item>
        <el-form-item class="formInput" label="密码" prop="u_pass">
          <el-input
            v-model="ruleForm2.u_pass"
            type="password"
            autocomplete="off"
            placeholder="请输入密码"
          ></el-input>
        </el-form-item>

        <!-- //图形验证码 -->
        <el-form-item
          class="formInput"
          prop="u_image"
          v-show="loginFailCount >= 2"
        >
          <el-input
            v-model="ruleForm2.u_image"
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
import { userlogin } from "../../api/login/login";
import { getPhoneCode } from "@/api/common/getCode";
import ImageVerify from "../../js/image-verify";
export default {
  name: "UserLogin",
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
      f_id: 1, //用于切换短信登陆或密码登录
      imageVerify1: "", //图片验证码实例
      imageVerify2: "",
      loginFailCount: 0, //登录失败次数 超过2次就启用图形验证码

      ruleForm1: {
        u_phone: "",
        u_code: "",
        u_image: "",
      },
      rules1: {
        u_phone: [
          {
            required: true,
            validator: checkPhone,
            trigger: "blur",
          },
        ],
        u_code: [
          {
            required: true,
            validator: checkCode,
            trigger: "blur",
          },
        ],
        u_image: [
          {
            required: true,
            validator: checkimageCode1,
            trigger: "blur",
          },
        ],
      },

      ruleForm2: {
        u_phone: "",
        u_pass: "",
        u_image: "",
      },
      rules2: {
        u_phone: [
          {
            required: true,
            validator: checkPhone,
            trigger: "blur",
          },
        ],
        u_pass: [
          {
            required: true,
            validator: checkPass,
            trigger: "blur",
          },
        ],
        u_image: [
          {
            required: true,
            validator: checkimageCode2,
            trigger: "blur",
          },
        ],
      },
    };
  },

  mounted() {
    console.log("this.$store.state.isLogin", this.$store.state.isLogin);
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
    destroySelf() {
      this.$store.commit("set_isLogin", false);
      console.log("调用了自我销毁方法");
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
    //用户注册
    sign() {
      console.log(123);
      this.destroySelf();
      this.$router.push({
        path: "/UserSign",
      });
    },

    //用户登录
    submitForm(formName, str) {
      console.log(formName.u_phone);
      this.$refs[str].validate((valid) => {
        if (valid) {
          let params = {
            phone: formName.u_phone,
            password: formName.u_pass,
            code: formName.u_code,
          };
          userlogin(params)
            .then((res) => {
              if (String(res.code) === "1") {
                // 登录成功
                this.$store.commit("set_isLogin", false);
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
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
/* .x_button {
  position: relative;

  top: 8px;
  right: -224px;
  border: 1px solid rgba(63, 61, 61, 0.418);
  color: rgba(0, 0, 0, 0.418);
  background-color: rgb(255, 255, 255);
  border-radius: 50%;
} */
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
  top: 150px;
  left: 50%;
  transform: translateX(-50%);
  width: 450px;
  height: 430px;
  background-color: rgb(255, 255, 255);
}
</style>