<template>
  <div>
    <div class="mask"></div>
    <div class="form">
      <div class="mytitle">
        {{
          typeId === 1 ? "用户注册" : typeId === 2 ? "商户注册" : "管理员注册"
        }}
      </div>

      <el-form
        :model="ruleForm1"
        :rules="rules1"
        ref="ruleForm1"
        status-icon
        label-width="100px"
        class="demo-ruleForm"
        style="background-color: rgb(255, 255, 255)"
      >
        <!-- 手机号 -->
        <el-form-item class="formInput" label="手机号：" prop="u_phone">
          <el-input
            v-model="ruleForm1.u_phone"
            type="text"
            autocomplete="off"
            placeholder="手机号注册"
          ></el-input>
        </el-form-item>
        <!-- 验证码 -->
        <el-form-item class="formInput" label="验证码：" prop="u_code">
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

        <!-- 密码 -->
        <el-form-item class="formInput" label="密码：" prop="u_pass">
          <el-input
            v-model="ruleForm1.u_pass"
            type="password"
            autocomplete="off"
            placeholder="请输入密码"
          ></el-input>
        </el-form-item>
        <!-- 确认密码 -->
        <el-form-item class="formInput" label="确认密码：" prop="u_checkpass">
          <el-input
            v-model="ruleForm1.u_checkpass"
            type="password"
            autocomplete="off"
            placeholder="请再输入一次密码"
          ></el-input>
        </el-form-item>

        <!-- 邀请码 -->
        <el-form-item
          v-if="typeId === 3"
          class="formInput"
          label="邀请码："
          prop="u_invitCode"
        >
          <el-input
            v-model="ruleForm1.u_invitCode"
            type="text"
            autocomplete="off"
            placeholder="请联系已注册管理员获得邀请码"
          ></el-input>
        </el-form-item>

        <el-form-item
          style="margin-bottom: 30px; margin-left: 60px"
          label-width="0"
        >
          <el-button
            type="primary"
            style="width: 335px; margin-bottom: 10px"
            @click="submitForm(ruleForm1, 'ruleForm1')"
            >注册</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getPhoneCode } from "@/api/common/getCode";
import { usersign } from "../../api/login/login";
import { merchantSign } from "@/api/login/merchanLogin";
import { administratorSign } from "@/api/login/AdministratorLogin";

export default {
  name: "Sign",
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

    //检查密码
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
    //检查确认密码
    const checkPassAgain = (rule, value, callback) => {
      value = value.trim().toLowerCase();
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        const pattern = /^[a-zA-Z]\w{5,17}$/;
        if (pattern.test(value)) {
          if (value !== this.ruleForm1.u_pass.trim()) {
            callback(new Error("两次密码不一致，请重新输入"));
          }
          callback();
        } else {
          if (value.length < 6 || value.length > 18) {
            callback(new Error("密码长度在6~18之间"));
          }
          callback(new Error("以字母开头，只能包含字母、数字和下划线"));
        }
      }
    };
    //检查管理员邀请码
    const checkInvitCode = (rule, value, callback) => {
      if (this.typeId !== 3) callback();
      value = value.trim();
      if (value === "") {
        callback(new Error("请输入邀请码"));
      } else {
        callback();
      }
    };

    return {
      typeId: 0,

      ruleForm1: {
        u_phone: "",
        u_code: "",
        u_pass: "",
        u_checkpass: "",
        u_invitCode: "",
      },
      rules1: {
        u_invitCode: [
          {
            required: true,
            validator: checkInvitCode,
            trigger: "blur",
          },
        ],
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
        u_pass: [
          {
            required: true,
            validator: checkPass,
            trigger: "blur",
          },
        ],
        u_checkpass: [
          {
            required: true,
            validator: checkPassAgain,
            trigger: "blur",
          },
        ],
      },
    };
  },

  mounted() {
    this.typeId = parseInt(this.$route.query.typeId);
    console.log("this.typeId", this.typeId);
  },
  methods: {
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

    //注册
    submitForm(formName, str) {
      console.log(formName.u_phone);
      this.$refs[str].validate((valid) => {
        if (valid) {
          let data = {
            phone: formName.u_phone,
            password: formName.u_pass,
            code: formName.u_code,
          };
          let params1 = {
            phone: formName.u_phone,
            password: formName.u_pass,
            code: formName.u_code,
          };
          let params2 = {
            phone: formName.u_phone,
            password: formName.u_pass,
            code: formName.u_code,
            invitationCode: formName.u_invitCode,
          };
          //用户注册
          if (this.typeId === 1) {
            this.userToSign(data);
          } else if (this.typeId === 2) {
            this.merchantToSign(params1);
          } else if (this.typeId === 3) {
            this.administrator(params2);
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    //用户注册
    userToSign(data) {
      usersign(data)
        .then((res) => {
          if (String(res.code) === "1") {
            // 注册成功
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
          //注册失败
          if (String(res.code) === "0") {
            this.$message.error(res.msg);
            console.log(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error("用户注册请求出错了" + err);
        });
    },
    //商户注册 (后端要给我返回生成的id和merchantToken)
    merchantToSign(params) {
      merchantSign(params)
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
          this.$message.error("商户注册请求出错了" + err);
        });
    },
    //管理员注册(后端返回管理员id和token)
    administrator(params) {
      administratorSign(params)
        .then((res) => {
          if (res.code === 1) {
            // 注册成功
            localStorage.setItem("administrator_id", res.data.administratorId);
            //向浏览器中存入token，方便后期验证
            localStorage.setItem(
              "administrator_token",
              res.data.administratorToken
            );
            this.$router.push({
              path: "/administrator",
            });
          }
          //注册失败
          if (String(res.code) === "0") {
            this.$message.error(res.msg);
            console.log(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error("管理员注册请求出错了" + err);
        });
    },
  },
};
</script>

<style scoped>
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
.nav {
  margin-bottom: 40px;
}

.formInput {
  width: 400px;
}
.form {
  position: fixed;
  top: 110px;
  left: 50%;
  transform: translateX(-50%);
  width: 450px;
  height: 430px;
  background-color: rgb(255, 255, 255);
}
</style>