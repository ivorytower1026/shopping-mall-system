<template>
  <div>
    <!-- 地址信息 -->
    <el-skeleton style="width: 1500px" :loading="loading" :count="5">
      <!-- 展示信息 -->
      <template slot="template">
        <!-- <el-skeleton-item variant="text" style="width: 50%" /> -->
        <el-skeleton-item
          variant="h2"
          style="width: 80%; margin-top: 30px; height: 40px"
        />
      </template>
      <template>
        <el-table
          :data="tableData"
          style="width: 1200px; float: left; font-size: 16px; margin: 5px auto"
        >
          <el-table-column prop="consigneeName" label="收货人" width="180">
          </el-table-column>
          <el-table-column prop="consigneePhone" label="手机号" width="180">
          </el-table-column>
          <el-table-column prop="address" label="收货地址"> </el-table-column>
          <el-table-column prop="isDefault" align="center" width="80px">
            <template slot-scope="scope">
              <el-tag v-show="scope.row.isDefault === 1">默认</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.row)"
                >编辑</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
          <el-table-column width="150px" label="操作">
            <template slot="header" slot-scope="scope">
              <el-button type="primary" @click="addAddress()"
                >添加收货地址</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </template>
    </el-skeleton>
    <!-- 新增收货地址 -->
    <div v-show="isAdd" class="mask"></div>
    <div class="form" v-show="isAdd">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        status-icon
        label-width="100px"
        style="background-color: rgb(255, 255, 255)"
      >
        <h3 style="text-align: center; margin: 0px; padding: 40px">
          {{ formTitle }}
        </h3>
        <!-- 收货人姓名 -->
        <el-form-item
          class="formInput"
          label="收货人："
          prop="consigneeName"
          label-width="120px"
        >
          <el-input
            v-model="ruleForm.consigneeName"
            type="text"
            autocomplete="off"
            placeholder="请输入收货人姓名"
          ></el-input>
        </el-form-item>
        <!-- 手机号 -->
        <el-form-item
          class="formInput"
          label="手机号："
          prop="consigneePhone"
          label-width="120px"
        >
          <el-input
            v-model="ruleForm.consigneePhone"
            type="text"
            autocomplete="off"
            placeholder="请输入收货人手机号"
          ></el-input>
        </el-form-item>

        <!-- 收获地址 -->
        <el-form-item
          class="formInput"
          label="所在地区："
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

        <!-- 是否设为默认地址 -->
        <el-form-item
          class="formInput"
          label="设为默认地址："
          prop="isDefault"
          label-width="120px"
        >
          <el-switch
            v-model="ruleForm.isDefault"
            :active-value="1"
            :inactive-value="0"
          >
          </el-switch>
        </el-form-item>

        <el-form-item style="padding-bottom: 40px; padding-left: 20px">
          <el-button
            type="info"
            plain
            style="width: 100px"
            @click="handleCancel()"
            >取消</el-button
          >
          <el-button
            type="primary"
            style="width: 100px; margin-left: 100px"
            @click="addUserAddress('ruleForm')"
            >保存</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script>
import { postUserAddress,getUserAddress,deleteUserAddress } from "@/api/user/index";

export default {
  name: "UserPosition",
  data() {
    //城市数据

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
    // //检查地区地址
    // const checkareaAddress = (rule, value, callback) => {
    //   if (value === null) {
    //     callback(new Error("请选择地区"));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      isAdd: false, //新增地址
      loading: true,

      //地址信息
      tableData: [],
      formTitle: "新增地址",

      editForm: "",
      //添加收获信息表单
      ruleForm: {
        consigneeName: "",
        consigneePhone: "",
        areaAddress: "",
        detailedAddress: "",
        isDefault: 0,
      },
      rules: {
        consigneeName: [
          { required: true, message: "请输入收货人姓名", trigger: "blur" },
        ],
        detailedAddress: [
          { required: true, message: "请输入详细地址", trigger: "blur" },
        ],
        consigneePhone: [
          { required: true, validator: checkPhone, trigger: "blur" },
        ],
        areaAddress: [
          { required: true, message: "请选择地区", trigger: "change" },
        ],
      },

      // 地区选择
      options: this.$store.state.addressOptions,
    };
  },
  mounted() {
    this.getUserAddressInfo();
  },
  methods: {
    handleCancel() {
      this.$refs.ruleForm.clearValidate();
      this.getUserAddressInfo();
      this.isAdd = false;
    },
    //添加地址按钮事件
    addAddress() {
      this.formTitle = "新增地址";
      this.isAdd = true;
      this.ruleForm.consigneeName = "";
      this.ruleForm.consigneePhone = "";
      this.ruleForm.areaAddress = "";
      this.ruleForm.detailedAddress = "";
      this.ruleForm.isDefault = 0;
    },

    //获取用户地址信息
    getUserAddressInfo() {
      getUserAddress()
        .then((res) => {
          if (res.code === 1) {
            if (res.data.length === 0) {
              this.loading = false;
              return;
            }

            this.tableData = res.data;
            this.tableData = this.handlerTableData(this.tableData);
            console.log("this.tableData", this.tableData);

            this.loading = false;
          }
          if (res.code === 0) {
            this.$message.info(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error("请求用户地址信息失败");
        });
    },

    //编辑
    handleEdit(row) {
      console.log("进入编辑，row为");
      console.log(row);
      this.formTitle = "编辑地址";
      this.isAdd = true;
      this.ruleForm = row;
    },

    //删除用户地址信息
    handleDelete(row) {
      const params = {
        addrId: row.addrId,
      };
      this.$confirm("这条收货地址信息将永远删除，是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          deleteUserAddress(params)
            .then((res) => {
              if (res.code === 1) {
                this.getUserAddressInfo();
                this.$message({
                  type: "success",
                  message: "删除成功!",
                });
              }
            })
            .catch((err) => {
              this.$message.error("删除失败！");
            });
        })
        .catch(() => {
          return;
        });
      console.log("fffffffffffffffffffffffffff", this);

      console.log(row);
    },
    //删除用户地址

    //添加或修改
    addUserAddress(str) {
      if (this.formTitle === "新增地址") {
        this.ruleForm.addrId = null;
      }
      console.log(this.ruleForm);
      this.$refs[str].validate((valid) => {
        if (valid) {
          let data = {
            consigneeName: this.ruleForm.consigneeName,
            consigneePhone: this.ruleForm.consigneePhone,
            areaAddress: this.arrToStr(this.ruleForm.areaAddress),
            detailedAddress: this.ruleForm.detailedAddress,
            isDefault: this.ruleForm.isDefault,
            addrId: this.ruleForm.addrId,
          };
          postUserAddress(data)
            .then((res) => {
              if (res.code === 1) {
                console.log("@@this.ruleForm", this.ruleForm);
                this.getUserAddressInfo();
                this.isAdd = false;
                this.$message.success("操作成功！");
              }
              if (res.code === 0) {
                this.$message.error(res.msg);
              }
            })
            .then((err) => {
              console.log("出错了！", err);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
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
    strToarr(str) {
      console.log("进入了strToarr");

      var res = [];
      var arr = str.split(" ");
      for (const key in arr) {
        res += arr[key];
      }
      return res;
    },

    handleChange(str) {
      console.log(str);
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
  top: 80px;
  left: 50%;
  transform: translateX(-50%);
  width: 520px;
  height: 0px;
  /* background-color: rgb(255, 255, 255); */
  background-color: rgba(0, 0, 0, 0.423);
  z-index: 1;
}
</style>