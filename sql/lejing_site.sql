/*
 Navicat Premium Data Transfer

 Source Server         : vm-132-docker
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : 192.168.40.132:33306
 Source Schema         : lejing_site

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 16/02/2021 17:02:32
*/

#CREATE DATABASE `lejing_site` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for eb_order_master
-- ----------------------------
DROP TABLE IF EXISTS `eb_order_master`;
CREATE TABLE `eb_order_master`  (
  `master_order_id` bigint NOT NULL COMMENT '主订单id',
  `master_order_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主订单号',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '下单人ID',
  `user_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '下单人手机号',
  `user_label` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '下单人标签',
  `user_community` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户所属社区',
  `order_type` enum('COMMON','PURCHASE') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'COMMON' COMMENT '订单类型：COMMON-普通订单，PURCHASE-团购订单',
  `order_status` enum('PENDING_PAYMENT','PENDING_STOCK','TO_BE_DELIVERED','SHIPPED','DEAL_DONE','CLOSED','CANCELLED') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'PENDING_PAYMENT' COMMENT '订单状态：PENDING_PAYMENT待付款，PENDING_STOCK待采购，TO_BE_DELIVERED待发货，SHIPPED已发货，DEAL_DONE已成交，CLOSED已关闭，CANCELLED已取消',
  `pay_type` enum('WX_PAY','ALI_PAY','OTHER_PAY') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'OTHER_PAY' COMMENT '支付方式：WX_PAY-微信，ALI_PAY-支付宝，OTHER_PAY-其他支付方式，默认：OTHER_PAY',
  `total_price` decimal(10, 0) NOT NULL DEFAULT 0 COMMENT '订单总金额(单位：分)',
  `actually_price` decimal(10, 0) NULL DEFAULT 0 COMMENT '实际支付金额（单位：分）',
  `postage_price` decimal(10, 0) NULL DEFAULT 0 COMMENT '订单总运费（单位：分）',
  `discount_price` decimal(10, 0) NULL DEFAULT 0 COMMENT '订单总优惠金额（单位：分）',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `deal_time` datetime NULL DEFAULT NULL COMMENT '成交时间',
  `status` enum('EFFECTIVE','INVALID') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'EFFECTIVE' COMMENT '状态( EFFECTIVE-有效；INVALID-无效， 默认-EFFECTIVE )',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除状态（0：未删，1：已删，默认：未删除）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`master_order_id`) USING BTREE,
  INDEX `order_status`(`order_status`) USING BTREE,
  INDEX `order_type`(`order_type`) USING BTREE,
  INDEX `create_time`(`create_time`) USING BTREE,
  INDEX `pay_type`(`pay_type`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '主订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eb_order_master
-- ----------------------------

-- ----------------------------
-- Table structure for eb_order_reimburse
-- ----------------------------
DROP TABLE IF EXISTS `eb_order_reimburse`;
CREATE TABLE `eb_order_reimburse`  (
  `reimburse_id` bigint NOT NULL COMMENT '主键id',
  `master_order_id` bigint NULL DEFAULT NULL COMMENT '主订单id（关联eb_order_master主键）',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `user_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户电话',
  `product_title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `actually_price` decimal(10, 0) NULL DEFAULT NULL COMMENT '退款金额（实际付款金额，单位：分）',
  `receive_account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收款账户（从哪付款就退到哪里）',
  `reimburse_status` tinyint(1) NULL DEFAULT 0 COMMENT '退款状态（0：等待退款，1：平台处理中，2：退款成功，3：退款失败，默认0等待退款）',
  `reimburse_apply_date` datetime NULL DEFAULT NULL COMMENT '申请退款日期',
  `merchant_process_date` datetime NULL DEFAULT NULL COMMENT '平台处理日期',
  `refund_successful_date` datetime NULL DEFAULT NULL COMMENT '退款成功日期',
  `reimburse_serial_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款流水号',
  `third_out_trade_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方交易流水号',
  `eb_transaction_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电商支付流水号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`reimburse_id`) USING BTREE,
  INDEX `idx_omid`(`master_order_id`) USING BTREE COMMENT 'master_order_id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单退款表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eb_order_reimburse
-- ----------------------------

-- ----------------------------
-- Table structure for eb_order_site_coupon
-- ----------------------------
DROP TABLE IF EXISTS `eb_order_site_coupon`;
CREATE TABLE `eb_order_site_coupon`  (
  `order_master_id` bigint NOT NULL COMMENT '订单id',
  `site_id` bigint NULL DEFAULT NULL COMMENT '场地id',
  `site_session_id` bigint NULL DEFAULT NULL COMMENT '场次id',
  `coupon_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '券号，优惠券码，后台处理（规则：订单创建日期加上6位随机数-yyyyMMddxxxxxx，如：20210121687587）',
  `coupon_status` enum('1','2','3','4','5') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态（1-未使用；2-已使用；3-已过期；4-已关闭；5-已退款；默认未使用：1）',
  `write_off_user` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入场卷核销人员',
  `consume_date` datetime NULL DEFAULT NULL COMMENT '入场卷消费时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除状态（0：未删，1：已删，默认：未删除）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`order_master_id`) USING BTREE,
  INDEX `idx_sid`(`site_id`) USING BTREE COMMENT '场地id索引',
  INDEX `idx_ssid`(`site_session_id`) USING BTREE COMMENT '场次id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '场地预约入场券卷号表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eb_order_site_coupon
-- ----------------------------

-- ----------------------------
-- Table structure for eb_order_snap_detail
-- ----------------------------
DROP TABLE IF EXISTS `eb_order_snap_detail`;
CREATE TABLE `eb_order_snap_detail`  (
  `order_id` bigint NOT NULL COMMENT '订单id，订单号',
  `master_order_id` bigint NOT NULL COMMENT '主订单id（关联eb_order_master主键）',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id（当前用户id，见用户表，用户其他信息去用户表捞）',
  `username` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下单用户姓名',
  `user_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预约手机号',
  `item_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '快照信息关联的产品id（全局唯一的产品，id如：场地预约的场地id等）',
  `item_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'item名称',
  `item_subtitle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'item副标题',
  `item_brand` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'item品牌',
  `item_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'itemurl链接（图片超链接）',
  `item_postage_price` decimal(10, 0) NULL DEFAULT NULL COMMENT 'item邮资，邮费',
  `item_quantity` decimal(10, 0) NULL DEFAULT NULL COMMENT 'item数量',
  `item_quantity_unit` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'item数量单位',
  `item_discount_price` decimal(10, 0) NULL DEFAULT NULL COMMENT '折扣金额(单位：分)',
  `item_unit_price` decimal(10, 0) NULL DEFAULT NULL COMMENT '订单单价(单位：分)',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除状态（0：未删，1：已删，默认：未删除）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `idx_moid`(`master_order_id`) USING BTREE COMMENT 'master_order_id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单快照表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eb_order_snap_detail
-- ----------------------------

-- ----------------------------
-- Table structure for eb_order_stock
-- ----------------------------
DROP TABLE IF EXISTS `eb_order_stock`;
CREATE TABLE `eb_order_stock`  (
  `stock_id` bigint NOT NULL COMMENT '库存id',
  `item_id` bigint NOT NULL COMMENT '场次id（表相关类目主键id）',
  `master_order_id` bigint NOT NULL COMMENT '主订单id',
  `purchase_quantity` int NULL DEFAULT NULL COMMENT '采购数量（单位：场）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`stock_id`) USING BTREE,
  INDEX `idx_moid`(`master_order_id`) USING BTREE COMMENT 'master_order_id index',
  INDEX `idx_iid`(`item_id`) USING BTREE COMMENT 'item_id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单库存表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eb_order_stock
-- ----------------------------

-- ----------------------------
-- Table structure for eb_site_invalid_session
-- ----------------------------
DROP TABLE IF EXISTS `eb_site_invalid_session`;
CREATE TABLE `eb_site_invalid_session`  (
  `invalid_id` bigint NOT NULL COMMENT '主键id',
  `site_id` bigint NOT NULL COMMENT '场地id',
  `site_session_ids` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场次id（多个场次以\",\"分割，如：1515151,54646484）',
  `project_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目编号（场地项目类型）',
  `project_status` tinyint(1) NULL DEFAULT -1 COMMENT '项目状态（1全可用，-1全不可用，0部分不可用，默认-1）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`invalid_id`) USING BTREE,
  INDEX `idx_sid`(`site_id`) USING BTREE COMMENT '场地id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '场地预约不可用场次表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eb_site_invalid_session
-- ----------------------------

-- ----------------------------
-- Table structure for eb_site_order_rule
-- ----------------------------
DROP TABLE IF EXISTS `eb_site_order_rule`;
CREATE TABLE `eb_site_order_rule`  (
  `site_id` bigint NOT NULL COMMENT '场地id',
  `use_rule` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '使用规则',
  `order_refund_minutes` int NULL DEFAULT NULL COMMENT '开场前可退款分钟数，<= 30min',
  `order_expired_refund_rule` enum('1','2','3') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '订单过期退款规则：1-过期不退，2-过期自动退，3-过期申请退，默认1-过期不退',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`site_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '场地预约订单规则表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eb_site_order_rule
-- ----------------------------

-- ----------------------------
-- Table structure for eb_site_reserve
-- ----------------------------
DROP TABLE IF EXISTS `eb_site_reserve`;
CREATE TABLE `eb_site_reserve`  (
  `site_id` bigint NOT NULL COMMENT '场地id（主键）',
  `site_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场地编码',
  `project_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目编码（同一类场地项编码相同）',
  `site_order_type` enum('1','2') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '订单类型（1：预约，2：出租，默认：1）',
  `site_title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场地名称',
  `site_subtitle` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场地名称的副标题',
  `site_price` decimal(10, 0) NULL DEFAULT NULL COMMENT '预约价格（单位：分',
  `site_location_desc` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场地的位置描述信息',
  `site_community` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场地所属社区',
  `site_manager_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场地管理员电话',
  `site_open_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开始营业时间（规则: 24H制，时分中间用\":\"号隔开；如: 08:00, 09:30，可精确到具体分钟）',
  `site_close_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结束营业时间（规则: 24H制，时分中间用\":\"号隔开；如: 19:00, 22:30，可精确到具体分钟）',
  `image_url` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场地图片（多个图片以\",\"隔开，如：http://baidu.com/521.jpg,http://baidu.com/546.jpg）',
  `site_shelf_status` tinyint(1) NULL DEFAULT 0 COMMENT '上架状态（0-待上架，1-已上架，2-已下架）',
  `site_open_status` tinyint(1) NULL DEFAULT 1 COMMENT '营运状态（0-不可用，不营运；1-可用，可运营；默认：1）',
  `sort` int NULL DEFAULT NULL COMMENT '排序字段，数值越大，越靠前',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除状态（0：未删，1：已删，默认：未删除）',
  `site_reserve_count` bigint NULL DEFAULT NULL COMMENT '历史预约量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`site_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '场地预约主表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eb_site_reserve
-- ----------------------------

-- ----------------------------
-- Table structure for eb_site_reserve_detail
-- ----------------------------
DROP TABLE IF EXISTS `eb_site_reserve_detail`;
CREATE TABLE `eb_site_reserve_detail`  (
  `detail_id` bigint NOT NULL COMMENT '场地详情id',
  `site_id` bigint NOT NULL COMMENT '场地id',
  `site_official_news` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场地公告信息（管理员发布公告）',
  `site_pub_dict_code` int NULL DEFAULT NULL COMMENT '发布的信息(关联sys_dict_data表的dict_code)',
  `site_pub_topic` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场地其他信息',
  `is_enable` tinyint(1) NULL DEFAULT 1 COMMENT '详情是否可用（1：可用，0：不可用，默认：1）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`detail_id`) USING BTREE,
  INDEX `idx_site_id`(`site_id`) USING BTREE COMMENT '场地id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '场地详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eb_site_reserve_detail
-- ----------------------------

-- ----------------------------
-- Table structure for eb_site_reserve_session
-- ----------------------------
DROP TABLE IF EXISTS `eb_site_reserve_session`;
CREATE TABLE `eb_site_reserve_session`  (
  `site_session_id` bigint NOT NULL COMMENT '场次id',
  `site_id` bigint NOT NULL COMMENT '场地id',
  `session_start_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场次开始时间点（规则: 24H制，时分中间用:号隔开；如: 08:00, 09:30，可精确到具体分钟）',
  `session_finish_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场次结束时间点（规则: 24H制，时分中间用:号隔开；如: 20:00, 21:30，可精确到具体分钟）',
  `current_price` decimal(10, 0) NULL DEFAULT NULL COMMENT '当前场次价格（单位：分，默认值从eb_site_reserve表site_price带过来）',
  `session_status` enum('0','1','2') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '该场次状态（0：可预定，1：不可预定, 默认0）',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除状态（0：未删，1：已删，默认：未删除）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`site_session_id`) USING BTREE,
  INDEX `idx_site_id`(`site_id`) USING BTREE COMMENT '场地id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '场地预约场次表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eb_site_reserve_session
-- ----------------------------

-- ----------------------------
-- Table structure for eb_site_reserve_status
-- ----------------------------
DROP TABLE IF EXISTS `eb_site_reserve_status`;
CREATE TABLE `eb_site_reserve_status`  (
  `site_status_id` bigint NOT NULL COMMENT '场地状态id',
  `site_session_id` bigint NOT NULL COMMENT '场地场次id',
  `site_id` bigint NOT NULL COMMENT '场地id',
  `site_reserve_status` tinyint(1) NULL DEFAULT 0 COMMENT '预定状态（0：可预定，1：已预定，2：不可预定, 默认0）',
  `site_stock_quantity` int NULL DEFAULT NULL COMMENT '场地库存总量',
  `site_available_stock` int NULL DEFAULT NULL COMMENT '场地可用库存',
  `effect_date` date NULL DEFAULT NULL COMMENT '生效日期（yyyy-MM-dd,关联eb_site_reserve_session表查询该日期对应的所有场次）',
  `current_price` decimal(10, 0) NOT NULL COMMENT '当前场次价格（单位：分，默认值从eb_site_reserve表site_price带过来）',
  `stock_quantity` int NULL DEFAULT NULL COMMENT '场库存量',
  `stock_freeze` int NULL DEFAULT NULL COMMENT '冻结库存',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`site_status_id`) USING BTREE,
  INDEX `idx_ssid`(`site_session_id`) USING BTREE COMMENT '场地场次id索引',
  INDEX `idx_sid`(`site_id`) USING BTREE COMMENT '场地id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '场地状态表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eb_site_reserve_status
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` bigint NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键值（可存JSON）',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否 默认N）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_sort` int NULL DEFAULT 0 COMMENT '字典排序',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` enum('Y','N') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'Y' COMMENT '是否默认（Y是 N否，默认：Y）',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态（1正常, 0停用）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
