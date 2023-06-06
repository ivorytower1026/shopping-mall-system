/*
 Navicat MySQL Data Transfer

 Source Server         : for_project
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : shopping_mall

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 04/12/2022 10:47:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `administrator_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员id',
  `administrator_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理员姓名',
  `administrator_head_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理员头像',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `invitation_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邀请码',
  PRIMARY KEY (`administrator_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES ('123456', 'admin', NULL, NULL, NULL, 0, '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', '13612345678', '7rfogyUSMbfwU5cpCsXTDA');
INSERT INTO `administrator` VALUES ('1579818619167117314', NULL, NULL, NULL, NULL, 0, '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', '13688596475', 'g7tvVJDX9ridmgqaUolxt');
INSERT INTO `administrator` VALUES ('1579818873589403650', NULL, NULL, NULL, NULL, 0, '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', '19844587693', 'PGChVq72cXuJDRKRjVdX');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `goods_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品id',
  `goods_category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品类型',
  `merchant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商户id',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名字',
  `price` decimal(16, 2) NULL DEFAULT NULL COMMENT '商品原价',
  `sub_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '折后价',
  `quantity` bigint NULL DEFAULT NULL COMMENT '商品数量',
  `goods_status` tinyint(1) NULL DEFAULT 1 COMMENT '1表示在售 2表示售空 3表示停售',
  `goods_pic` varchar(4095) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'pic_goods_default.jpg' COMMENT '图片路径列表，可设置多张图片，用空格隔开',
  `goods_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品的描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('13326654', '1224546', '1580156482208796673', '脑白金1', 999.90, 100.00, 100, 1, 'pic_goods_default.jpg', NULL, '2022-10-14 17:21:35', '2022-10-31 21:35:56', 0);
INSERT INTO `goods` VALUES ('13365210', '1224546', '12255566', '脑白金666', 663.30, 333.60, 100, 1, 'pic_goods_default.jpg', NULL, '2022-10-14 17:21:38', NULL, 0);
INSERT INTO `goods` VALUES ('133652103', '1545424535', '1580156482208796673', '脑白金5', 4442.00, 3366.00, 77, 2, 'pic_goods_default.jpg', NULL, '2022-10-14 17:21:45', NULL, 0);
INSERT INTO `goods` VALUES ('13365216', '1224546', '1580156482208796673', '脑白金4', 5431.77, 123.16, 66, 1, 'pic_goods_default.jpg', NULL, '2022-10-14 17:21:42', '2022-10-29 16:38:31', 0);
INSERT INTO `goods` VALUES ('13365425', '1224546', '1580156482208796673', '舒络胫3', 4.00, 2.00, 14, 3, 'pic_goods_default.jpg', NULL, '2022-10-14 17:21:41', NULL, 0);
INSERT INTO `goods` VALUES ('1581992790840086530', '1224546', '12255566', 'fdsaf', 123.00, 66.00, 312, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 20:57:28', '2022-10-17 20:57:28', 0);
INSERT INTO `goods` VALUES ('1581998111704797185', '265454', '1580156482208796673', '足球', 136.00, 33.20, 122, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 21:18:37', '2022-10-17 21:18:37', 0);
INSERT INTO `goods` VALUES ('1581998296266756097', '265454', '12255566', '跑步鞋', 123.00, 12.30, 11, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 21:19:21', '2022-10-17 21:19:21', 0);
INSERT INTO `goods` VALUES ('1581998407902351362', '5154534324', '12255566', '浮动', 123.00, 13.20, 12, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 21:19:47', '2022-10-17 21:19:47', 0);
INSERT INTO `goods` VALUES ('1581998740313526273', '1545424535', '12255566', 'sd', 123.00, 1.02, 1231, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 21:21:06', '2022-10-17 21:21:06', 0);
INSERT INTO `goods` VALUES ('1581998811088211969', '1224546', '1580156482208796673', 'sa', 123.00, 33.30, 1233, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 21:21:23', '2022-10-17 21:21:23', 0);
INSERT INTO `goods` VALUES ('1582002654937145345', '265454', '12255566', 'fdsa', 123.00, 11.00, 123, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 21:36:40', '2022-10-17 21:36:40', 0);
INSERT INTO `goods` VALUES ('1582003592930332673', '1224546', '1580156482208796673', 'fds', 123.00, 11.00, 3123, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 21:40:23', '2022-10-17 21:40:23', 0);
INSERT INTO `goods` VALUES ('1582004402951098369', '1224546', '1580156482208796673', '213', 12.00, 1.00, 12, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 21:43:37', '2022-10-17 21:43:37', 0);
INSERT INTO `goods` VALUES ('1582004473285382145', '5154534324', '12255566', '123', 13.00, 11.00, 2131, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 21:43:53', '2022-10-17 21:43:53', 0);
INSERT INTO `goods` VALUES ('1582004660351340545', '265454', '1580156482208796673', '12321', 321.00, 11.00, 31, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 21:44:38', '2022-10-17 21:44:38', 0);
INSERT INTO `goods` VALUES ('1582009469959737346', '1545424535', '12255566', 'fas', 123.00, 11.00, 3123, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 22:03:45', '2022-10-17 22:03:45', 0);
INSERT INTO `goods` VALUES ('1582009723815792642', '1545424535', '1580156482208796673', 'fdas', 13.00, 11.00, 23, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 22:04:45', '2022-10-17 22:04:45', 0);
INSERT INTO `goods` VALUES ('1582009762365640706', '265454', '12255566', 'sda', 123.00, 1.00, 123, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 22:04:54', '2022-10-17 22:04:54', 0);
INSERT INTO `goods` VALUES ('1582009920541233153', '1224546', '1580156482208796673', 'ds', 123.00, 1.00, 123, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 22:05:32', '2022-10-17 22:05:32', 0);
INSERT INTO `goods` VALUES ('1582010015651270657', '1545424535', '12255566', 'fa', 123.00, 11.00, 2131, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 22:05:55', '2022-10-17 22:05:55', 0);
INSERT INTO `goods` VALUES ('1582010116553641986', '1224546', '1580156482208796673', 'fds', 123.00, 1.00, 213, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 22:06:19', '2022-10-17 22:06:19', 0);
INSERT INTO `goods` VALUES ('1582011289343954946', '265454', '12255566', 'df', 129.00, 11.00, 3123, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 22:10:58', '2022-10-18 10:39:35', 0);
INSERT INTO `goods` VALUES ('1582012734831173634', '1224546', '12255566', 'sadf', 123.00, 11.00, 23112, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 22:16:43', '2022-10-17 22:16:43', 0);
INSERT INTO `goods` VALUES ('1582012734831173635', '1224546', '12255566', 'sadf', 123.00, 11.00, 23112, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 22:16:43', '2022-10-17 22:16:43', 0);
INSERT INTO `goods` VALUES ('1582012734831173636', '1224546', '1580156482208796673', 'sadf', 123.00, 1.00, 23112, 1, 'pic_goods_default.jpg', NULL, '2022-10-17 22:16:43', '2022-10-17 22:16:43', 0);
INSERT INTO `goods` VALUES ('1582020918505435138', '1545424535', '1580156482208796673', 'asf', 1.00, 11.00, 23, 1, 'fe313da8-7f03-4ab0-8b7d-f52896d56d06.jpg', NULL, '2022-10-17 22:49:14', '2022-10-17 22:49:14', 0);
INSERT INTO `goods` VALUES ('1582020918643847169', '1545424535', '12255566', 'asf', 1.00, 11.00, 23, 1, '5229da98-5cf7-4dbc-9b2c-cc5fbcd93b8d.jpg', NULL, '2022-10-17 22:49:14', '2022-10-17 22:49:14', 0);
INSERT INTO `goods` VALUES ('1582020918643847170', '1545424535', '12255566', 'asf', 1.00, 11.00, 23, 1, 'f26b7f80-195f-4819-950c-8442f3f09a33.jpg', NULL, '2022-10-17 22:49:14', '2022-10-17 22:49:14', 0);
INSERT INTO `goods` VALUES ('1582020918706761730', '1545424535', '12255566', 'asf', 1.00, 11.00, 23, 1, '2200db64-8783-4482-b890-8c3298952d0a.jpg', NULL, '2022-10-17 22:49:14', '2022-10-17 22:49:14', 0);
INSERT INTO `goods` VALUES ('1582021220692455426', '1545424535', '12255566', 'asf', 123.00, 12.00, 123, 1, 'b26d8b95-822e-4712-94ce-256833309ac3.jpg', NULL, '2022-10-17 22:50:26', '2022-10-17 22:50:26', 0);
INSERT INTO `goods` VALUES ('1582021221996883969', '1545424535', '1580156482208796673', 'asf', 123.00, 33.00, 123, 1, 'c5fd6ef4-1aad-423b-861d-d2cdfbc5e514.jpg', NULL, '2022-10-17 22:50:27', '2022-10-17 22:50:27', 0);
INSERT INTO `goods` VALUES ('1582021591133384705', '1545424535', '1580156482208796673', '123', 123.00, 33.00, 123, 1, '71abea26-bfdc-4274-8d0e-dd9965e93cbe.jpg', NULL, '2022-10-17 22:51:55', '2022-10-17 22:51:55', 0);
INSERT INTO `goods` VALUES ('1582021592442007554', '1545424535', '1580156482208796673', '123', 123.00, 3.00, 123, 1, '77e121ef-c361-4535-b1dd-e1d46c83ec9c.jpg', NULL, '2022-10-17 22:51:55', '2022-10-17 22:51:55', 0);
INSERT INTO `goods` VALUES ('1582021839645896706', '1224546', '1580156482208796673', 'asdf', 123.00, 13.11, 123, 1, '6f7db08a-c897-4e88-b43a-b7d626aef512.jpg', NULL, '2022-10-17 22:52:54', '2022-10-31 21:35:04', 0);
INSERT INTO `goods` VALUES ('1582021840899993602', '1224546', '1580156482208796673', 'asdf', 123.00, 120.00, 123, 1, '0cfbee40-c4a4-4f62-ac05-e46fc0dce580.jpg', NULL, '2022-10-17 22:52:54', '2022-10-31 21:35:43', 0);
INSERT INTO `goods` VALUES ('1582350127870251009', '515645445', '1580156482208796673', '奥迪', 1313.00, 123.00, 0, 2, '9012346f-5109-473d-a715-9bce7f1e4aaa.jpg', NULL, '2022-10-18 20:37:24', '2022-10-29 16:57:49', 0);
INSERT INTO `goods` VALUES ('1586024583230689282', '1224546', '1586022083043823617', '可莉', 1666.00, 166.00, 10, 1, 'cf97d8d7-5dd2-4eed-a2ca-968ee260132f.jpg', NULL, '2022-10-28 23:58:22', '2022-10-28 23:58:22', 0);
INSERT INTO `goods` VALUES ('1586025027503951874', '1224546', '1586024803016413185', '蜘蛛侠', 18888.88, 188.88, 10, 1, 'fd693863-cb93-4626-8878-3509ea582e9f.jpg', NULL, '2022-10-29 00:00:08', '2022-10-29 00:00:08', 0);
INSERT INTO `goods` VALUES ('1586031659352879106', '1224546', '1586031527983083522', '原神1', 1666.33, 166.30, 12, 1, 'aa8c9b73-68e0-4289-a716-53ca63af3469.jpg', NULL, '2022-10-29 00:26:29', '2022-10-29 00:26:29', 0);
INSERT INTO `goods` VALUES ('1591709281193861122', '1224546', '1580156482208796673', '香蕉', 1688.00, 168.00, 12, 1, '1fabf759-a01f-475c-8a12-566380854b2e.jpg', NULL, '2022-11-13 16:27:20', '2022-11-13 16:27:20', 0);
INSERT INTO `goods` VALUES ('1596865935897473026', '1545424535', '1580156482208796673', '新的商品', 162.00, 160.00, 166, 1, 'd22149c2-36b2-4c7f-9967-a6a99703e0c3.jpg', NULL, '2022-11-27 21:58:02', '2022-11-27 21:58:02', 0);
INSERT INTO `goods` VALUES ('2554543', '1224546', '1580156482208796673', '脑白金9', 4.00, 2.00, 4214, 3, 'pic_goods_default.jpg', NULL, '2022-10-14 17:21:56', NULL, 0);
INSERT INTO `goods` VALUES ('2554544', '1224546', '1580156482208796673', '脑白金6', 41.00, 32.00, 412, 1, 'pic_goods_default.jpg', NULL, '2022-10-14 17:21:48', NULL, 0);
INSERT INTO `goods` VALUES ('25545441', '1224546', '1580156482208796673', '脑白金7', 1434.00, 32.00, 4124, 2, 'pic_goods_default.jpg', NULL, '2022-10-14 17:21:50', NULL, 0);
INSERT INTO `goods` VALUES ('25545446', '1224546', '1580156482208796673', '脑白金8', 1134.00, 112.00, 44, 1, 'pic_goods_default.jpg', NULL, '2022-10-14 17:21:52', NULL, 0);

-- ----------------------------
-- Table structure for goods_category
-- ----------------------------
DROP TABLE IF EXISTS `goods_category`;
CREATE TABLE `goods_category`  (
  `goods_category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品类型id',
  `goods_category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品类型名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`goods_category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品类别' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods_category
-- ----------------------------
INSERT INTO `goods_category` VALUES ('1224546', '健康', '2022-10-14 17:00:02', '2022-10-14 17:00:05', 0);
INSERT INTO `goods_category` VALUES ('1545424535', '养生', '2022-10-16 10:48:45', '2022-10-16 10:48:46', 0);
INSERT INTO `goods_category` VALUES ('265454', '运动', '2022-10-16 10:48:17', '2022-10-16 10:48:19', 0);
INSERT INTO `goods_category` VALUES ('5154534324', '游戏', '2022-10-16 10:48:30', '2022-10-16 10:48:32', 0);
INSERT INTO `goods_category` VALUES ('515645445', '汽车', '2022-10-16 10:49:03', '2022-10-16 10:49:05', 0);

-- ----------------------------
-- Table structure for goods_order
-- ----------------------------
DROP TABLE IF EXISTS `goods_order`;
CREATE TABLE `goods_order`  (
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单id',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `merchant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商户id',
  `goods_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品id',
  `consignee_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `addr_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址id',
  `consignee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人',
  `goods_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名',
  `goods_quantity` int NULL DEFAULT NULL COMMENT '商品数量',
  `sub_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '折后价',
  `total_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '总价',
  `order_status` tinyint(1) NULL DEFAULT NULL COMMENT '1 未支付  2 待发货 3 待收货 4已完成 5已取消',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods_order
-- ----------------------------
INSERT INTO `goods_order` VALUES ('1591695723143950338', '1577670602066509826', '1586024803016413185', '1586025027503951874', '13899999999', '天津市翻斗大街26号', '1586724494905442306', '图图', 'fd693863-cb93-4626-8878-3509ea582e9f.jpg', '蜘蛛侠', 2, 188.88, 377.76, 1, '2022-11-13 15:33:27', '2022-11-13 15:33:27', 0);
INSERT INTO `goods_order` VALUES ('1591695877464977410', '1577670602066509826', '1580156482208796673', '13326654', '13222222222', '北京市超级研究所', '1586375300344713217', '超级人类', 'pic_goods_default.jpg', '脑白金1', 1, 100.00, 100.00, 3, '2022-11-13 15:34:04', '2022-11-13 16:11:42', 0);
INSERT INTO `goods_order` VALUES ('1591705323121451010', '1577670602066509826', '1580156482208796673', '13326654', '13899999999', '天津市翻斗大街26号', '1586724494905442306', '图图', 'pic_goods_default.jpg', '脑白金1', 1, 100.00, 100.00, 2, '2022-11-13 16:11:36', '2022-11-13 16:11:36', 0);
INSERT INTO `goods_order` VALUES ('1591709419857551361', '1577670602066509826', '1580156482208796673', '1591709281193861122', '13222222222', '北京市超级研究所', '1586375300344713217', '超级人类', '1fabf759-a01f-475c-8a12-566380854b2e.jpg', '香蕉', 2, 168.00, 336.00, 3, '2022-11-13 16:27:53', '2022-11-27 21:55:36', 0);
INSERT INTO `goods_order` VALUES ('1596864906166480897', '1596863486142918658', '1580156482208796673', '1582004402951098369', '15233698874', '山西省晋城市阳城县冬冬饭店', '1596863825936068609', 'aac', 'pic_goods_default.jpg', '213', 2, 1.00, 2.00, 2, '2022-11-27 21:53:57', '2022-11-27 21:53:57', 0);
INSERT INTO `goods_order` VALUES ('1596864972746862594', '1596863486142918658', '1586022083043823617', '1586024583230689282', '15233698874', '山西省晋城市阳城县冬冬饭店', '1596863825936068609', 'aac', 'cf97d8d7-5dd2-4eed-a2ca-968ee260132f.jpg', '可莉', 6, 166.00, 996.00, 2, '2022-11-27 21:54:12', '2022-11-27 21:54:17', 0);

-- ----------------------------
-- Table structure for merchant
-- ----------------------------
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant`  (
  `merchant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商户id',
  `shop_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商户店名',
  `shop_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商户店所在地址',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `shop_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商店图片',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `is_usable` tinyint(1) NULL DEFAULT 1 COMMENT '账号是否可用 不可用为0 默认为1',
  `area_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省份 城市 区',
  `detailed_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`merchant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of merchant
-- ----------------------------
INSERT INTO `merchant` VALUES ('12255566', 'fdsafas', '河北省秦皇岛市北戴河区ko', '13699448711', NULL, '2022-10-12 23:21:15', '2022-11-08 23:15:57', 0, '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', 1, '河北省 秦皇岛市 北戴河区', 'ko');
INSERT INTO `merchant` VALUES ('1580156482208796673', '凉皮铺子', '北京市1', '19866666666', NULL, '2022-10-12 23:04:18', '2022-11-13 16:21:34', 0, '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', 1, '北京市', '1');
INSERT INTO `merchant` VALUES ('1586022083043823617', 'id_13612345689', NULL, '13612345689', NULL, '2022-10-28 23:48:26', '2022-10-28 23:48:26', 0, '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', 1, NULL, NULL);
INSERT INTO `merchant` VALUES ('1586024803016413185', 'id_15233669985', NULL, '15233669985', NULL, '2022-10-28 23:59:15', '2022-10-28 23:59:15', 0, '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', 1, NULL, NULL);
INSERT INTO `merchant` VALUES ('1586031527983083522', 'id_13633699956', NULL, '13633699956', NULL, '2022-10-29 00:25:58', '2022-10-29 00:25:58', 0, '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', 1, NULL, NULL);

-- ----------------------------
-- Table structure for merchant_order
-- ----------------------------
DROP TABLE IF EXISTS `merchant_order`;
CREATE TABLE `merchant_order`  (
  `merchant_order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `goods_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `count` int NOT NULL COMMENT '商品数量',
  `price` decimal(16, 2) NOT NULL COMMENT '价格  表示 14位数字的金额',
  `order_status` tinyint(1) NOT NULL COMMENT '1 未支付  2 待发货 3 待收货 4已完成',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `is_deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`merchant_order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of merchant_order
-- ----------------------------
INSERT INTO `merchant_order` VALUES ('123154', '1579499524590067713', '13326654', 3, 299.99, 2, NULL, NULL, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '123456' COMMENT '密码',
  `user_head_pic_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'pic_default.jpg' COMMENT '用户头像',
  `hobbys` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '爱好',
  `sex` tinyint UNSIGNED NULL DEFAULT 3 COMMENT '性别 1表示男     2表示女 3未知',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_usable` tinyint(1) NULL DEFAULT 1 COMMENT '账号是否可用 不可用为0 默认为1',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('15154', '小明', '15456', '123456', 'pic_default.jpg', NULL, 1, 3, 0, NULL, '2022-11-08 23:01:33', 0);
INSERT INTO `user` VALUES ('1576110323134382082', '小红', '1541564544', '123456', 'pic_default.jpg', NULL, 1, NULL, 0, NULL, '2022-11-08 23:01:32', 1);
INSERT INTO `user` VALUES ('1576120496385196034', '小李', '1541564544', '123456', 'pic_default.jpg', NULL, 1, NULL, 0, NULL, '2022-11-08 22:58:07', 0);
INSERT INTO `user` VALUES ('1576916770425802754', '小青', '13413245684', 'ss02021131', 'pic_default.jpg', NULL, 1, NULL, 0, NULL, '2022-11-08 23:00:51', 0);
INSERT INTO `user` VALUES ('1576918834891657218', '小青', '13413245681', 'ss02021131', 'pic_default.jpg', NULL, 1, NULL, 0, NULL, '2022-11-08 23:01:39', 0);
INSERT INTO `user` VALUES ('1577111174428213250', '天喜', '11111111111', '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', 'pic_default.jpg', NULL, 3, NULL, 0, '2022-10-04 16:08:53', '2022-10-05 16:18:21', 1);
INSERT INTO `user` VALUES ('1577214094540763137', 'id_13899653441', '13899653441', '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', 'pic_default.jpg', '跳舞 打球 运动', 1, NULL, 0, '2022-10-04 16:28:38', '2022-10-04 16:28:38', 1);
INSERT INTO `user` VALUES ('1577517436209590274', '6666', '16522365879', '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', 'pic_default.jpg', '美食 体育', 3, 15, 0, '2022-10-05 12:34:00', '2022-12-03 21:33:22', 1);
INSERT INTO `user` VALUES ('1577661957224284162', 'fadsfasd', '13612525236', '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', 'pic_default.jpg', '健身 数码 美食', 3, 19, 0, '2022-10-05 22:08:17', '2022-10-05 22:35:23', 1);
INSERT INTO `user` VALUES ('1577670602066509826', '9563', '13685566954', '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', 'pic_default.jpg', '健身 数码 美食 游戏', 2, 18, 0, '2022-10-05 22:42:38', '2022-11-08 23:16:36', 1);
INSERT INTO `user` VALUES ('1578398117433303042', 'me', '13699685547', '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', 'pic_default.jpg', '健身 游戏', 3, 999, 0, '2022-10-07 22:53:31', '2022-10-07 23:56:00', 1);
INSERT INTO `user` VALUES ('1579441216143441921', 'ffff', '13699584763', '97e55e54e53e51e102e97e100e52e100e102e56e51e50e56e56e101e100e56e56e56e56e54e54e51e102e56e102e102e53e56e53e', 'pic_default.jpg', '健身 美食 游戏 数码', 2, 42, 0, '2022-10-10 19:58:25', '2022-10-10 23:33:25', 1);
INSERT INTO `user` VALUES ('1583426854646255618', 'id_15666666666', '15666666666', '51e48e49e98e48e49e50e48e48e55e57e102e52e48e53e51e99e99e99e51e51e97e99e100e49e99e97e49e56e98e57e101e', 'pic_default.jpg', NULL, 3, NULL, 0, '2022-10-21 19:55:55', '2022-10-21 19:55:55', 1);
INSERT INTO `user` VALUES ('1596863486142918658', 'test man', '13699563321', '49e99e100e56e101e55e54e53e56e98e98e54e100e98e50e54e102e101e100e49e99e101e49e55e57e52e48e98e55e100e98e100e', 'pic_default.jpg', '健身', 3, 16, 0, '2022-11-27 21:48:18', '2022-11-27 21:54:43', 1);

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `addr_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地址id',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `is_default` tinyint(1) NULL DEFAULT 0 COMMENT '1表示设为默认地址',
  `consignee_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收件人手机号',
  `consignee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收件人姓名',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `area_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省份 城市 区',
  `detailed_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`addr_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_address
-- ----------------------------
INSERT INTO `user_address` VALUES ('1578398328650063874', '1578398117433303042', 0, '13622563148', 'newPeople', NULL, NULL, 0, ' 贵州省 遵义市 播州区', '一区中学路口', '贵州省遵义市播州区一区中学路口');
INSERT INTO `user_address` VALUES ('1578406916973969409', '1578398117433303042', 0, '13622032101', '16988745', NULL, NULL, 0, ' 河北省 秦皇岛市 北戴河区', '65', '河北省秦皇岛市北戴河区65');
INSERT INTO `user_address` VALUES ('1578411914298441729', '1578398117433303042', 0, '13211504413', '1326454', NULL, NULL, 0, ' 新疆维吾尔自治区 哈密市 伊吾县', '666', '新疆维吾尔自治区哈密市伊吾县666');
INSERT INTO `user_address` VALUES ('1578413648966107138', '1578398117433303042', 1, '13622569985', '152', NULL, NULL, 0, ' 台湾省', '1号区', '台湾省1号区');
INSERT INTO `user_address` VALUES ('1581908097282535425', '1579441216143441921', 0, '13955526365', 'fasdf', NULL, NULL, 0, ' 山西省 长治市 潞城区', 'hahha', '山西省长治市潞城区hahha');
INSERT INTO `user_address` VALUES ('1584528048312979458', '1579441216143441921', 1, '19633352263', 'ajsfjd', NULL, NULL, 0, ' 河北省 秦皇岛市 北戴河区', '新区域', '河北省秦皇岛市北戴河区新区域');
INSERT INTO `user_address` VALUES ('1586375300344713217', '1577670602066509826', 0, '13222222222', '超级人类', NULL, '2022-11-13 15:32:02', 0, ' 北京市', '超级研究所', '北京市超级研究所');
INSERT INTO `user_address` VALUES ('1586724494905442306', '1577670602066509826', 1, '13899999999', '图图', '2022-10-30 22:19:34', '2022-11-13 15:31:44', 0, ' 天津市', '翻斗大街26号', '天津市翻斗大街26号');
INSERT INTO `user_address` VALUES ('1596863825936068609', '1596863486142918658', 1, '15233698874', 'aac', '2022-11-27 21:49:39', '2022-11-27 21:49:42', 0, ' 山西省 晋城市 阳城县', '冬冬饭店', '山西省晋城市阳城县冬冬饭店');
INSERT INTO `user_address` VALUES ('1599032419871100930', '1577517436209590274', 1, '13699958447', 'test user', '2022-12-03 21:26:52', '2022-12-03 21:26:52', 0, ' 河北省 秦皇岛市 抚宁区', 'test_position', '河北省秦皇岛市抚宁区test_position');

-- ----------------------------
-- Table structure for user_cart
-- ----------------------------
DROP TABLE IF EXISTS `user_cart`;
CREATE TABLE `user_cart`  (
  `cart_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '购物车id',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `merchant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商户id',
  `goods_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品id',
  `goods_status` tinyint(1) NULL DEFAULT NULL COMMENT '1表示在售 2表示售空 3表示停售',
  `sub_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '单价',
  `goods_quantity` int NULL DEFAULT NULL COMMENT '商品数量',
  `goods_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名',
  `total_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '总价',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `addr_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址id',
  `consignee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人',
  `consignee_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`cart_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_cart
-- ----------------------------
INSERT INTO `user_cart` VALUES ('1590935937360105473', '1577517436209590274', '1586024803016413185', '1586025027503951874', 1, 188.88, 2, '/api/goods/downloadGoodsPic?name=fd693863-cb93-4626-8878-3509ea582e9f.jpg', '蜘蛛侠', 377.76, 0, '1599032419871100930', 'test user', '13699958447', '河北省秦皇岛市抚宁区test_position', '2022-11-11 13:14:20', '2022-12-04 09:58:00');
INSERT INTO `user_cart` VALUES ('1590936497987555329', '1577670602066509826', '1580156482208796673', '1582010116553641986', 1, 1.00, 1, '/api/goods/downloadGoodsPic?name=pic_goods_default.jpg', 'fds', 1.00, 0, '1586375300344713217', '超级人类', '13222222222', '北京市超级研究所', '2022-11-11 13:16:34', '2022-11-13 15:49:00');

-- ----------------------------
-- Table structure for user_cart_bind_merchant
-- ----------------------------
DROP TABLE IF EXISTS `user_cart_bind_merchant`;
CREATE TABLE `user_cart_bind_merchant`  (
  `user_cart_bind_merchant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户与商户的绑定id',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `merchant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_cart_bind_merchant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_cart_bind_merchant
-- ----------------------------
INSERT INTO `user_cart_bind_merchant` VALUES ('1590935937330745346', '1577670602066509826', '1586024803016413185', '2022-11-11 13:14:20', '2022-11-11 13:14:20');
INSERT INTO `user_cart_bind_merchant` VALUES ('1590936497924640769', '1577670602066509826', '1580156482208796673', '2022-11-11 13:16:34', '2022-11-11 13:16:34');
INSERT INTO `user_cart_bind_merchant` VALUES ('1599032750122209282', '1577517436209590274', '1586024803016413185', '2022-12-03 21:28:11', '2022-12-03 21:28:11');

SET FOREIGN_KEY_CHECKS = 1;
