/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : cinema

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 17/06/2019 19:13:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `a_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `end_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `coupon_id` int(11) NULL DEFAULT NULL,
  `start_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (2, '春季外卖节', '春季外卖节', '2019-04-24 01:55:59', 5, '2019-04-21 01:55:59');
INSERT INTO `activity` VALUES (3, '春季外卖节', '春季外卖节', '2019-04-24 01:55:59', 6, '2019-04-21 01:55:59');
INSERT INTO `activity` VALUES (4, '测试活动', '测试活动', '2019-04-27 00:00:00', 8, '2019-04-21 00:00:00');

-- ----------------------------
-- Table structure for activity_movie
-- ----------------------------
DROP TABLE IF EXISTS `activity_movie`;
CREATE TABLE `activity_movie`  (
  `activity_id` int(11) NULL DEFAULT NULL,
  `movie_id` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_movie
-- ----------------------------
INSERT INTO `activity_movie` VALUES (2, 10);
INSERT INTO `activity_movie` VALUES (2, 11);
INSERT INTO `activity_movie` VALUES (2, 16);
INSERT INTO `activity_movie` VALUES (4, 10);

-- ----------------------------
-- Table structure for bank_card
-- ----------------------------
DROP TABLE IF EXISTS `bank_card`;
CREATE TABLE `bank_card`  (
  `card_number` int(11) NULL DEFAULT NULL,
  `password` int(11) NULL DEFAULT NULL,
  `card_balance` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bank_card
-- ----------------------------
INSERT INTO `bank_card` VALUES (123123123, 123123, 100000);

-- ----------------------------
-- Table structure for comsumption_order_record
-- ----------------------------
DROP TABLE IF EXISTS `comsumption_order_record`;
CREATE TABLE `comsumption_order_record`  (
  `orderID` int(11) NOT NULL AUTO_INCREMENT,
  `payed_time` timestamp(0) NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `movieID` int(11) NULL DEFAULT NULL,
  `scheduleID` int(11) NULL DEFAULT NULL,
  `state` int(11) NULL DEFAULT NULL,
  `initial_amount` double NULL DEFAULT NULL,
  `actual_payed_amount` double NULL DEFAULT NULL,
  `payment_method` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`orderID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comsumption_order_record
-- ----------------------------
INSERT INTO `comsumption_order_record` VALUES (1, NULL, '2019-06-12 20:50:39', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `comsumption_order_record` VALUES (2, NULL, '2019-06-12 20:56:52', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `comsumption_order_record` VALUES (3, NULL, '2019-06-12 21:02:41', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `comsumption_order_record` VALUES (4, NULL, '2019-06-12 21:06:51', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `comsumption_order_record` VALUES (5, NULL, '2019-06-12 21:44:47', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `comsumption_order_record` VALUES (6, NULL, '2019-06-12 21:45:45', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `comsumption_order_record` VALUES (7, NULL, '2019-06-17 14:31:16', 10, 69, 0, 50, 0, 0);
INSERT INTO `comsumption_order_record` VALUES (8, NULL, '2019-06-17 14:31:37', 10, 69, 0, 150, 0, 0);
INSERT INTO `comsumption_order_record` VALUES (9, NULL, '2019-06-17 15:44:25', 10, 69, 0, 50, 0, 0);

-- ----------------------------
-- Table structure for comsumption_record
-- ----------------------------
DROP TABLE IF EXISTS `comsumption_record`;
CREATE TABLE `comsumption_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total_origin` double NULL DEFAULT NULL,
  `total_result` double NULL DEFAULT NULL,
  `couponID_used` int(11) NULL DEFAULT 0,
  `userID` int(11) NULL DEFAULT NULL,
  `orderID` int(11) NULL DEFAULT NULL,
  `VIPID` int(11) NULL DEFAULT 0,
  `time` timestamp(0) NULL DEFAULT NULL,
  `ticketID` int(11) NULL DEFAULT 123123123,
  `bankcard_number` int(12) NULL DEFAULT 123123123,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comsumption_record
-- ----------------------------
INSERT INTO `comsumption_record` VALUES (1, 20.5, 20.5, 0, 15, 3, 0, '2019-06-12 21:02:41', 0, 123123123);
INSERT INTO `comsumption_record` VALUES (2, 41, 41, 0, 15, 3, 0, '2019-06-12 21:02:41', 1, 123123123);
INSERT INTO `comsumption_record` VALUES (3, 61.5, 61.5, 0, 15, 3, 0, '2019-06-12 21:02:41', 2, 123123123);
INSERT INTO `comsumption_record` VALUES (4, 20.5, 20.5, 0, 15, 4, 0, '2019-06-12 21:06:51', 15, 123123123);
INSERT INTO `comsumption_record` VALUES (5, 41, 41, 0, 15, 4, 0, '2019-06-12 21:06:51', 55, 123123123);
INSERT INTO `comsumption_record` VALUES (6, 61.5, 61.5, 0, 15, 4, 0, '2019-06-12 21:06:51', 57, 123123123);
INSERT INTO `comsumption_record` VALUES (7, 20.5, 20.5, 0, 15, 5, 1, '2019-06-12 21:44:47', 15, 0);
INSERT INTO `comsumption_record` VALUES (8, 41, 41, 0, 15, 5, 1, '2019-06-12 21:44:47', 55, 0);
INSERT INTO `comsumption_record` VALUES (9, 61.5, 61.5, 0, 15, 5, 1, '2019-06-12 21:44:47', 57, 0);
INSERT INTO `comsumption_record` VALUES (10, 20.5, 20.5, 0, 15, 6, 1, '2019-06-12 21:45:45', 15, 0);
INSERT INTO `comsumption_record` VALUES (11, 41, 41, 0, 15, 6, 1, '2019-06-12 21:45:45', 55, 0);
INSERT INTO `comsumption_record` VALUES (12, 61.5, 61.5, 0, 15, 6, 1, '2019-06-12 21:45:45', 57, 0);

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `target_amount` float NULL DEFAULT NULL,
  `discount_amount` float NULL DEFAULT NULL,
  `start_time` timestamp(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `end_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon
-- ----------------------------
INSERT INTO `coupon` VALUES (1, '测试优惠券', '春季电影节', 20, 5, '2019-04-21 01:47:54', '2019-04-24 01:47:59');
INSERT INTO `coupon` VALUES (5, '测试优惠券', '品质联盟', 30, 4, '2019-04-21 05:14:46', '2019-04-25 05:14:51');
INSERT INTO `coupon` VALUES (6, '春节电影节优惠券', '电影节优惠券', 50, 10, '2019-04-21 05:15:11', '2019-04-22 05:14:56');
INSERT INTO `coupon` VALUES (8, '测试优惠券', '123', 100, 99, '2019-04-21 00:00:00', '2019-04-27 00:00:00');

-- ----------------------------
-- Table structure for coupon_user
-- ----------------------------
DROP TABLE IF EXISTS `coupon_user`;
CREATE TABLE `coupon_user`  (
  `coupon_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon_user
-- ----------------------------
INSERT INTO `coupon_user` VALUES (8, 15);
INSERT INTO `coupon_user` VALUES (5, 15);
INSERT INTO `coupon_user` VALUES (8, 15);
INSERT INTO `coupon_user` VALUES (6, 15);
INSERT INTO `coupon_user` VALUES (5, 15);
INSERT INTO `coupon_user` VALUES (8, 15);
INSERT INTO `coupon_user` VALUES (6, 15);

-- ----------------------------
-- Table structure for hall
-- ----------------------------
DROP TABLE IF EXISTS `hall`;
CREATE TABLE `hall`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `column` int(11) NULL DEFAULT NULL,
  `row` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hall
-- ----------------------------
INSERT INTO `hall` VALUES (1, '1号厅', 10, 5);
INSERT INTO `hall` VALUES (2, '2号厅', 12, 8);
INSERT INTO `hall` VALUES (3, '3号厅', 12, 8);

-- ----------------------------
-- Table structure for movie
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poster_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `director` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `screen_writer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `starring` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `length` int(11) NOT NULL,
  `start_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `status` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movie
-- ----------------------------
INSERT INTO `movie` VALUES (10, 'http://n.sinaimg.cn/translate/640/w600h840/20190312/ampL-hufnxfm4278816.jpg', '大森贵弘 /伊藤秀樹', '', '神谷浩史 /井上和彦 /高良健吾 /小林沙苗 /泽城美雪', '动画', NULL, NULL, 120, '2019-04-14 22:54:31', '夏目友人帐', '在人与妖怪之间过着忙碌日子的夏目，偶然与以前的同学结城重逢，由此回忆起了被妖怪缠身的苦涩记忆。此时，夏目认识了在归还名字的妖怪记忆中出现的女性·津村容莉枝。和玲子相识的她，现在和独子椋雄一同过着平稳的生活。夏目通过与他们的交流，心境也变得平和。但这对母子居住的城镇，却似乎潜伏着神秘的妖怪。在调查此事归来后，寄生于猫咪老师身体的“妖之种”，在藤原家的庭院中，一夜之间就长成树结出果实。而吃掉了与自己形状相似果实的猫咪老师，竟然分裂成了3个', 0);
INSERT INTO `movie` VALUES (11, '', '安娜·波顿', NULL, '布利·拉尔森', '动作/冒险/科幻', NULL, NULL, 120, '2019-04-16 22:55:31', '惊奇队长', '漫画中的初代惊奇女士曾经是一名美国空军均情报局探员，暗恋惊奇先生。。。', 0);
INSERT INTO `movie` VALUES (12, '', '1', NULL, '1', '1', NULL, NULL, 120, '2019-04-16 22:57:31', '1', '1', 0);
INSERT INTO `movie` VALUES (13, '2', '2', NULL, '2', '2', NULL, NULL, 120, '2019-04-16 22:52:31', '2', '2', 0);
INSERT INTO `movie` VALUES (14, '', '2', NULL, '2', '2', NULL, NULL, 120, '2019-04-18 21:23:15', '2', '2', 1);
INSERT INTO `movie` VALUES (15, '1', '1', '1', '1', '1', '1', '1', 111, '2019-04-16 23:00:24', 'nnmm,,,', '1', 0);
INSERT INTO `movie` VALUES (16, 'https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2549523952.webp', '林孝谦', 'abcˆ', '陈意涵', '爱情', '大陆', NULL, 123, '2019-04-18 21:23:15', '比悲伤更悲伤的故事', '唱片制作人张哲凯(刘以豪)和王牌作词人宋媛媛(陈意涵)相依为命，两人自幼身世坎坷只有彼此为伴，他们是亲人、是朋友，也彷佛是命中注定的另一半。父亲罹患遗传重症而被母亲抛弃的哲凯，深怕自己随时会发病不久人世，始终没有跨出友谊的界线对媛媛展露爱意。眼见哲凯的病情加重，他暗自决定用剩余的生命完成他们之间的终曲，再为媛媛找个可以托付一生的好男人。这时，事业有 成温柔体贴的医生(张书豪)适时的出现让他成为照顾媛媛的最佳人选，二人按部就班发展着关系。一切看似都在哲凯的计划下进行。然而，故事远比这里所写更要悲伤......', 1);

-- ----------------------------
-- Table structure for movie_like
-- ----------------------------
DROP TABLE IF EXISTS `movie_like`;
CREATE TABLE `movie_like`  (
  `movie_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `like_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`movie_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movie_like
-- ----------------------------
INSERT INTO `movie_like` VALUES (10, 12, '2019-03-25 10:40:19');
INSERT INTO `movie_like` VALUES (11, 1, '2019-03-22 17:38:12');
INSERT INTO `movie_like` VALUES (11, 2, '2019-03-23 17:38:12');
INSERT INTO `movie_like` VALUES (11, 3, '2019-03-22 16:38:12');
INSERT INTO `movie_like` VALUES (12, 1, '2019-03-23 17:48:46');
INSERT INTO `movie_like` VALUES (12, 3, '2019-03-25 14:36:22');
INSERT INTO `movie_like` VALUES (14, 1, '2019-03-23 17:38:12');
INSERT INTO `movie_like` VALUES (16, 12, '2019-03-23 23:27:48');

-- ----------------------------
-- Table structure for refund_strategy
-- ----------------------------
DROP TABLE IF EXISTS `refund_strategy`;
CREATE TABLE `refund_strategy`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` int(20) NULL DEFAULT NULL,
  `inUse` int(11) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of refund_strategy
-- ----------------------------
INSERT INTO `refund_strategy` VALUES (1, 6, 0);
INSERT INTO `refund_strategy` VALUES (2, 4, 0);
INSERT INTO `refund_strategy` VALUES (3, 8, 1);

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hall_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `start_time` timestamp(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `end_time` timestamp(0) NULL DEFAULT NULL,
  `fare` double NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES (20, 1, 12, '2019-04-14 01:00:00', '2019-04-14 02:00:00', 20.5);
INSERT INTO `schedule` VALUES (21, 1, 10, '2019-04-11 20:00:00', '2019-04-11 21:00:00', 90);
INSERT INTO `schedule` VALUES (27, 1, 11, '2019-04-18 02:01:00', '2019-04-18 04:01:00', 20.5);
INSERT INTO `schedule` VALUES (28, 1, 11, '2019-04-20 00:00:00', '2019-04-20 02:00:00', 20.5);
INSERT INTO `schedule` VALUES (30, 1, 11, '2019-04-19 02:01:00', '2019-04-19 04:01:00', 20.5);
INSERT INTO `schedule` VALUES (31, 1, 11, '2019-04-13 00:00:00', '2019-04-13 02:00:00', 20.5);
INSERT INTO `schedule` VALUES (32, 1, 11, '2019-04-13 04:00:00', '2019-04-13 06:00:00', 20.5);
INSERT INTO `schedule` VALUES (37, 1, 11, '2019-04-15 08:00:00', '2019-04-15 10:00:00', 20.5);
INSERT INTO `schedule` VALUES (38, 1, 11, '2019-04-15 01:00:00', '2019-04-15 03:00:00', 20.5);
INSERT INTO `schedule` VALUES (40, 1, 10, '2019-04-11 00:00:00', '2019-04-11 02:00:00', 20.5);
INSERT INTO `schedule` VALUES (41, 1, 11, '2019-04-11 03:00:00', '2019-04-11 05:00:00', 20.5);
INSERT INTO `schedule` VALUES (42, 1, 11, '2019-04-11 06:00:00', '2019-04-11 08:00:00', 20.5);
INSERT INTO `schedule` VALUES (43, 1, 10, '2019-04-11 09:00:00', '2019-04-11 11:00:00', 20.5);
INSERT INTO `schedule` VALUES (44, 2, 10, '2019-04-11 09:00:00', '2019-04-11 11:00:00', 20.5);
INSERT INTO `schedule` VALUES (45, 2, 10, '2019-04-11 06:00:00', '2019-04-11 08:00:00', 20.5);
INSERT INTO `schedule` VALUES (46, 2, 11, '2019-04-11 03:00:00', '2019-04-11 05:00:00', 20.5);
INSERT INTO `schedule` VALUES (47, 2, 11, '2019-04-11 00:00:00', '2019-04-11 02:00:00', 20.5);
INSERT INTO `schedule` VALUES (48, 2, 10, '2019-04-11 21:00:00', '2019-04-11 23:59:00', 20.5);
INSERT INTO `schedule` VALUES (50, 1, 10, '2019-04-16 00:00:00', '2019-04-16 03:00:00', 2);
INSERT INTO `schedule` VALUES (51, 1, 10, '2019-04-17 13:00:00', '2019-04-17 15:00:00', 9);
INSERT INTO `schedule` VALUES (52, 1, 10, '2019-04-18 13:00:00', '2019-04-18 15:00:00', 9);
INSERT INTO `schedule` VALUES (53, 1, 16, '2019-04-19 15:00:00', '2019-04-19 18:00:00', 9);
INSERT INTO `schedule` VALUES (54, 1, 16, '2019-04-17 03:00:00', '2019-04-17 06:00:00', 9);
INSERT INTO `schedule` VALUES (55, 1, 15, '2019-04-18 07:00:00', '2019-04-18 09:00:00', 9);
INSERT INTO `schedule` VALUES (56, 2, 10, '2019-04-19 21:00:00', '2019-04-19 23:59:00', 20.5);
INSERT INTO `schedule` VALUES (57, 2, 10, '2019-04-20 21:00:00', '2019-04-20 23:59:00', 20.5);
INSERT INTO `schedule` VALUES (58, 2, 10, '2019-04-21 21:00:00', '2019-04-21 23:59:00', 20.5);
INSERT INTO `schedule` VALUES (61, 1, 13, '2019-04-20 19:00:00', '2019-04-20 21:00:00', 25);
INSERT INTO `schedule` VALUES (62, 1, 11, '2019-04-20 16:00:00', '2019-04-20 18:00:00', 25);
INSERT INTO `schedule` VALUES (63, 2, 15, '2019-04-21 00:01:30', '2019-04-21 13:30:00', 30);
INSERT INTO `schedule` VALUES (64, 1, 16, '2019-04-22 10:00:00', '2019-04-22 13:30:00', 30);
INSERT INTO `schedule` VALUES (65, 1, 10, '2019-04-23 10:00:00', '2019-04-23 13:30:00', 30);
INSERT INTO `schedule` VALUES (66, 2, 13, '2019-04-21 15:31:29', '2019-04-16 23:59:00', 20.5);
INSERT INTO `schedule` VALUES (67, 2, 10, '2019-04-25 21:00:00', '2019-04-25 23:59:00', 20.5);
INSERT INTO `schedule` VALUES (68, 2, 10, '2019-04-26 21:00:00', '2019-04-26 23:59:00', 20.5);
INSERT INTO `schedule` VALUES (69, 1, 10, '2019-06-21 09:24:00', '2019-06-21 13:23:00', 50);

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket`  (
  `user_id` int(11) NULL DEFAULT NULL,
  `schedule_id` int(11) NULL DEFAULT NULL,
  `column_index` int(11) NULL DEFAULT NULL,
  `order_id` int(11) NULL DEFAULT NULL,
  `row_index` int(11) NULL DEFAULT NULL,
  `state` tinyint(4) NULL DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `can_refund` tinyint(4) UNSIGNED NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ticket
-- ----------------------------
INSERT INTO `ticket` VALUES (12, 50, 5, NULL, 3, 2, 1, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (12, 50, 5, NULL, 3, 2, 2, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (12, 50, 5, NULL, 3, 2, 3, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (12, 50, 5, NULL, 3, 2, 4, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (12, 50, 5, NULL, 3, 2, 5, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 50, 4, NULL, 3, 2, 6, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 0, NULL, 0, 1, 15, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 2, NULL, 0, 1, 16, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 1, NULL, 1, 1, 17, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 11, NULL, 7, 1, 18, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (13, 50, 4, NULL, 2, 1, 19, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 66, 3, NULL, 2, 1, 20, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (12, 50, 1, NULL, 1, 1, 21, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (13, 50, 4, NULL, 3, 1, 22, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 50, 2, NULL, 2, 1, 23, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 0, NULL, 7, 2, 24, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 5, NULL, 4, 2, 25, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 6, NULL, 4, 2, 26, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 6, NULL, 2, 2, 27, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 7, NULL, 2, 2, 28, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 0, NULL, 4, 2, 29, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 0, NULL, 3, 2, 30, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 0, NULL, 2, 2, 31, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 10, NULL, 0, 2, 32, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 11, NULL, 0, 2, 33, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 8, NULL, 0, 2, 34, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 9, NULL, 0, 2, 35, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 5, NULL, 0, 2, 36, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 6, NULL, 0, 2, 37, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 6, NULL, 7, 2, 38, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 7, NULL, 7, 2, 39, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 8, NULL, 7, 2, 40, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 11, NULL, 4, 2, 41, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 11, NULL, 5, 2, 42, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 9, NULL, 6, 2, 43, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 10, NULL, 6, 2, 44, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 11, NULL, 6, 2, 45, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 3, NULL, 5, 1, 46, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 4, NULL, 5, 1, 47, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 5, NULL, 5, 1, 48, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 11, NULL, 2, 2, 49, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 11, NULL, 3, 2, 50, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 9, NULL, 4, 2, 51, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 9, NULL, 3, 1, 52, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 58, 10, NULL, 3, 1, 53, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 65, 7, NULL, 4, 2, 54, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 65, 8, NULL, 4, 1, 55, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 65, 9, NULL, 4, 2, 56, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 65, 7, NULL, 3, 1, 57, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 65, 8, NULL, 3, 2, 58, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 65, 9, NULL, 3, 2, 59, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 65, 0, NULL, 0, 1, 60, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 65, 0, NULL, 1, 1, 61, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (15, 65, 0, NULL, 2, 1, 62, '2019-04-23 21:50:52', 1);
INSERT INTO `ticket` VALUES (3, 69, 9, 0, 0, 2, 63, '2019-06-17 14:23:25', 1);
INSERT INTO `ticket` VALUES (3, 69, 9, 0, 1, 2, 64, '2019-06-17 14:31:16', 1);
INSERT INTO `ticket` VALUES (3, 69, 7, 0, 2, 2, 65, '2019-06-17 14:31:37', 1);
INSERT INTO `ticket` VALUES (3, 69, 8, 0, 2, 2, 66, '2019-06-17 14:31:37', 1);
INSERT INTO `ticket` VALUES (3, 69, 9, 0, 2, 2, 67, '2019-06-17 14:31:37', 1);
INSERT INTO `ticket` VALUES (12, 69, 9, 0, 0, 2, 68, '2019-06-17 15:44:25', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userlevel` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_uindex`(`id`) USING BTREE,
  UNIQUE INDEX `user_username_uindex`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'testname', '123456', 0);
INSERT INTO `user` VALUES (3, 'test', '123456', 0);
INSERT INTO `user` VALUES (5, 'test1', '123456', 0);
INSERT INTO `user` VALUES (7, 'test121', '123456', 0);
INSERT INTO `user` VALUES (8, 'root', '123456', 2);
INSERT INTO `user` VALUES (10, 'roottt', '123123', 0);
INSERT INTO `user` VALUES (12, 'zhourui', '123456', 0);
INSERT INTO `user` VALUES (13, 'abc123', 'abc123', 0);
INSERT INTO `user` VALUES (15, 'dd', '123', 0);

-- ----------------------------
-- Table structure for view
-- ----------------------------
DROP TABLE IF EXISTS `view`;
CREATE TABLE `view`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of view
-- ----------------------------
INSERT INTO `view` VALUES (1, 7);

-- ----------------------------
-- Table structure for vip_card
-- ----------------------------
DROP TABLE IF EXISTS `vip_card`;
CREATE TABLE `vip_card`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `balance` float NULL DEFAULT NULL,
  `join_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `vip_card_user_id_uindex`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vip_card
-- ----------------------------
INSERT INTO `vip_card` VALUES (1, 15, 977, '2019-06-12 21:45:45');
INSERT INTO `vip_card` VALUES (2, 12, 1320, '2019-06-07 17:05:48');

-- ----------------------------
-- Table structure for vip_charge_record
-- ----------------------------
DROP TABLE IF EXISTS `vip_charge_record`;
CREATE TABLE `vip_charge_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp(0) NULL DEFAULT NULL,
  `amount` double NULL DEFAULT NULL,
  `gift` int(11) NULL DEFAULT NULL,
  `vipId` int(11) NULL DEFAULT NULL,
  `rental` double NULL DEFAULT NULL,
  `balance` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vip_charge_record
-- ----------------------------
INSERT INTO `vip_charge_record` VALUES (1, '2019-06-08 17:36:19', 1000, 100, 1, 1100, 1100);

-- ----------------------------
-- Table structure for vip_discount_strategy
-- ----------------------------
DROP TABLE IF EXISTS `vip_discount_strategy`;
CREATE TABLE `vip_discount_strategy`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quota` int(11) NULL DEFAULT NULL,
  `gift` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vip_discount_strategy
-- ----------------------------
INSERT INTO `vip_discount_strategy` VALUES (1, 1000, 100);
INSERT INTO `vip_discount_strategy` VALUES (2, 500, 20);
INSERT INTO `vip_discount_strategy` VALUES (3, 750, 50);

SET FOREIGN_KEY_CHECKS = 1;
