<template>
  <div>
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
          title="个人信息"
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

          <el-descriptions-item label="用户名">{{
            userProfile.username
          }}</el-descriptions-item>
          <el-descriptions-item label="手机号"
            >{{ userProfile.phone }}
          </el-descriptions-item>
          <el-descriptions-item label="性别">{{
            sexNumToStr
          }}</el-descriptions-item>
          <el-descriptions-item label="年龄"
            >{{ userProfile.age }}
          </el-descriptions-item>
          <el-descriptions-item label="加入时间"
            >{{ userProfile.createTime }}
          </el-descriptions-item>
          <el-descriptions-item label="爱好">
            {{ userProfile.hobbys === null ? "无" : "" }}
            <el-tag
              v-for="h in userProfile.hobbys"
              :key="h.name"
              style="margin-left: 5px"
            >
              {{ h.name }}
            </el-tag>
          </el-descriptions-item>
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
          修改我的信息
        </h3>
        <!-- 用户名 -->
        <el-form-item class="formInput" label="用户名：" prop="userName">
          <el-input
            v-model="ruleForm.userName"
            type="text"
            autocomplete="off"
            :placeholder="userProfile.username"
          ></el-input>
        </el-form-item>
        <!-- 选择性别 -->
        <el-form-item class="formInput" label="性别：" prop="sex">
          <el-radio v-model="ruleForm.sex" :label="1">男</el-radio>
          <el-radio v-model="ruleForm.sex" :label="2">女</el-radio>
          <el-radio v-model="ruleForm.sex" :label="3">保密</el-radio>
        </el-form-item>
        <!-- 选择年龄 -->
        <el-form-item class="formInput" label="年龄：" prop="age">
          <el-input
            v-model="ruleForm.age"
            type="number"
            autocomplete="off"
            :placeholder="userProfile.age"
          ></el-input>
        </el-form-item>

        <!-- 选择爱好 -->
        <el-form-item class="formInput" label="爱好：" prop="hobbys">
          <div>
            <el-checkbox-group v-model="ruleForm.hobbys" size="medium">
              <el-checkbox-button
                v-for="h in editUserInfo.syshobbys"
                :label="h"
                :key="h"
                border
                >{{ h }}</el-checkbox-button
              >
            </el-checkbox-group>
          </div>
        </el-form-item>

        <el-form-item style="padding-bottom: 40px; padding-left: 20px">
          <el-button
            type="info"
            plain
            style="width: 100px"
            @click="isEdit = false"
            >取消</el-button
          >
          <el-button
            type="primary"
            style="width: 100px; margin-left: 100px"
            @click="saveUserInfo('ruleForm')"
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
            {{ userProfile.phone }}
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
            @click="getCode(userProfile.phone)"
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
            @click="isUpdatePhone = false"
            >取消</el-button
          >
          <el-button
            type="primary"
            style="width: 100px; margin-left: 100px"
            @click="updateUserPhone('ruleForm1')"
            >保存</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getUserProfile } from "@/api/user/index";
import { postUserProfile } from "@/api/user/index";
import { postUserPhone } from "@/api/user/index";
import { handlerData } from "@/js/validObj";
import { getPhoneCode } from "@/api/common/getCode";

export default {
  name: "UserInformation",
  computed: {
    //将数字的性别转为中文的性别显示
    sexNumToStr() {
      return this.userProfile.sex === 1
        ? "男"
        : this.userProfile.sex === 2
        ? "女"
        : "保密";
    },
  },

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

    return {
      loading: true,

      userProfile: {
        age: "",
        createTime: "",
        hobbys: [],
        phone: "",
        sex: "",
        username: "",
      },

      isEdit: false,
      isUpdatePhone: false,
      editUserInfo: {
        syshobbys: ["体育", "健身", "游戏", "美食", "数码"], //后端查询获得
      },

      //校验用字段
      ruleForm: {
        userName: "",
        age: "",
        sex: "",
        hobbys: "",
      },
      rules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        age: [
          { required: true, message: "请输入年龄", trigger: "blur" },
          { min: 1, max: 3, message: "年龄在 0 到 999岁", trigger: "blur" },
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
    this.getNewUserProfile();
  },
  methods: {
    //获取用户信息
    getNewUserProfile() {
      const params = {
        userId: localStorage.getItem("user_id"),
      };

      getUserProfile(params)
        .then((res) => {
          if (res.code === 1) {
            this.userProfile = handlerData(res.data);
            this.loading = false;
            console.log("this.ruleForm.sex", this.ruleForm.sex);
          }
          if (res.code === 0) {
          }
        })
        .catch((err) => {
          this.$message.error("请先登录！", err);
        });
    },
    //更改用户信息并保存
    saveUserInfo(str) {
      console.log(this.ruleForm);
      this.$refs[str].validate((valid) => {
        if (valid) {
          //将hobby转为字符串
          const hobs = this.ruleForm.hobbys;
          let str = "";
          for (const key in hobs) {
            str += hobs[key];
            str += " ";
          }

          this.ruleForm.hobbys = str;
          this.ruleForm.phone = this.userProfile.phone;
          this.ruleForm.userId = localStorage.getItem("user_id");
          postUserProfile(this.ruleForm)
            .then((res) => {
              if (res.code === 1) {
                this.isEdit = false;
                this.getNewUserProfile();
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
    updateUserPhone(str) {
      this.$refs[str].validate((valid) => {
        if (valid) {
          let params = {
            phone1: this.userProfile.phone,
            code1: this.ruleForm1.u_code1,
            phone2: this.ruleForm1.u_phone,
            code2: this.ruleForm1.u_code1,
            userId: localStorage.getItem("user_id"),
          };
          postUserPhone(params)
            .then((res) => {
              if (res.code === 1) {
                this.getNewUserProfile();
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
      this.ruleForm.hobbys = this.arrObjToArr(this.userProfile.hobbys);
      this.ruleForm.sex = this.userProfile.sex;

      console.log("点击了");
    },
    isCheckHobby(hobby) {
      if (this.ruleForm.hobbys === []) return false;
      return this.ruleForm.hobbys.includes(hobby);
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
</style>