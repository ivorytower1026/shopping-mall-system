<template>
  <div>
    <div class="mask"></div>
    <div class="form">
      <h2
        style="
          text-align: center;
          margin-top: 40px;
          color: skyblue;
          font-size: 28px;
          font-weight: 600;
        "
      >
        添加商品
      </h2>

      <div class="context">
        <!-- 上传图片 -->

        <el-upload
          class="upload"
          ref="upload"
          action="/api/goods/uploadGoodsPic"
          :before-upload="beforeUpload"
          :file-list="fileList"
          :on-success="handleUpSuccess"
          multiple
          :limit="1"
          :on-exceed="handleExceed"
          accept="image/jpeg,image/png"
          :auto-upload="false"
        >
          <el-button slot="trigger" size="small" type="primary"
            >点击添加图片</el-button
          >

          <div slot="tip" class="el-upload__tip">
            只能上传jpg/png文件，且不超过2Mb
            <br />
            最多只能添加1张图片
          </div>
        </el-upload>
        <!-- 填写商品名字，选择商品类别 -->
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="130px"
          class="goodsNameruleForm"
        >
          <el-form-item
            label="商品类别："
            prop="goodsCategoryId"
            style="margin-top: 30px"
          >
            <el-select
              v-model="ruleForm.goodsCategoryId"
              placeholder="请选择商品类别"
            >
              <el-option
                v-for="item in goodsCategoryOptions"
                :key="item.goodsCategoryId"
                :label="item.goodsCategoryName"
                :value="item.goodsCategoryId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="商品名称："
            prop="goodsName"
            style="margin-top: 30px"
          >
            <el-input
              v-model="ruleForm.goodsName"
              class="formInput"
              placeholder="请输入商品名称"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="商品原件："
            prop="price"
            style="margin-top: 30px"
          >
            <el-input
              type="number"
              v-model="ruleForm.price"
              class="formInput"
              placeholder="请输入商品原价"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="商品折后价："
            prop="subPrice"
            style="margin-top: 30px"
          >
            <el-input
              type="number"
              v-model="ruleForm.subPrice"
              class="formInput"
              placeholder="请输入商品折后价"
            ></el-input>
          </el-form-item>
          <el-form-item
            type="number"
            label="商品数量："
            prop="quantity"
            style="margin-top: 30px"
          >
            <el-input
              type="number"
              v-model="ruleForm.quantity"
              class="formInput"
              placeholder="请输入商品数量"
            ></el-input>
          </el-form-item>
        </el-form>
        <el-button class="main_btn" type="primary" @click="submitForm"
          >添加</el-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import { getGoodsCategory } from "@/api/goodsCategory/index";
import { postMerchantGoods } from "@/api/merchant/forGoods";
export default {
  name: "AddGoods",
  data() {
    const checkSubPrice = (rule, value, callback) => {
      
      if (value === "") {
        callback(new Error("请输入折后价"));
      } else {
        if ( parseFloat(value) > parseFloat(this.ruleForm.price)) {
          callback(new Error("折后价不能大于原价"));
        }
        callback();
      }
    };
    return {
      fileList: [],

      // 商品类别选择器
      goodsCategoryOptions: [],
      // 商品类别和名字
      ruleForm: {
        goodsName: "",
        goodsCategoryId: "",
        price: "",
        quantity: "",
      },
      // 商品类别和名字规则
      rules: {
        goodsName: [
          { required: true, message: "请输入商品名称", trigger: "blur" },
        ],
        price: [{ required: true, message: "请输入商品原价", trigger: "blur" }],
        subPrice: [
          { required: true, validator: checkSubPrice, trigger: "blur" },
        ],
        quantity: [
          { required: true, message: "请输入商品数量", trigger: "blur" },
        ],
        goodsCategoryId: [
          { required: true, message: "请选择商品类别", trigger: "change" },
        ],
      },
      goodsPicList: "",
      imageUrl: "",
    };
  },
  mounted() {
    this.getGoodsCategory();
  },
  methods: {
    //获取商品类别
    getGoodsCategory() {
      getGoodsCategory()
        .then((res) => {
          if (res.code === 1) {
            this.goodsCategoryOptions = res.data;
          } else if (res.code === 0) {
            console.log(res.msg);
          }
        })
        .catch((res) => {
          console.log("请求商品分类出错了！", err);
        });
    },
    //图片上传成功后
    handleUpSuccess(response) {
      if (response.code === 1) {
        this.imageUrl = `/api/goods/downloadGoodsPic?name=${response.data}`;
        this.ruleForm.goodsPic = response.data;
        postMerchantGoods(this.ruleForm)
          .then((res) => {
            if (res.code === 1) {
              this.$message.success("添加成功!");
              this.$router.push({
                path: "/merchant/shop",
              });
            } else if (res.code === 0) {
              this.$message.error(res.msg);
            }
          })
          .catch((err) => {
            console.log("添加商品出错了！", err);
          });
      }
    },
    //提交表单
    submitForm(formName) {
      formName = "ruleForm";
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //判读有没有图片
          if (this.$refs.upload.uploadFiles.length === 0) {
            this.$message.error("商品至少要有一张图片！");
            return;
          } else {
            this.$refs.upload.submit();
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    // 保存上传后要这个
    // this.$refs.upload.submit();
    // 上传商品图片到服务器时
    beforeUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;
      console.log("执行了", isLt2M);
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isLt2M;
    },
    // 选择好要上传的商品图片时
    handleExceed(files, fileList) {
      this.$message.warning(`只能上传一个文件`);
    },
  },
};
</script>

<style scoped>
.main_btn {
  position: relative;
  top: 152px;
  right: 50px;
  width: 300px;
  margin-top: 50px;
}

.goodsNameruleForm {
  float: left;
  width: 600px;
  height: 200px;
  margin-left: 100px;
}
/* 上传文件 */
.upload {
  float: left;
  margin-top: 20px;
  width: 220px;
  height: 280px;
}
.input[type="file"] {
  display: none !important;
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

.formInput {
  width: 230px;
  margin-right: 20px;
}
.form {
  position: fixed;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 1000px;
  height: 100%;
  background-color: rgb(255, 255, 255);
}
.context {
  position: relative;
  margin-left: 0px;
  top: 80px;
  right: -193px;
}
</style>