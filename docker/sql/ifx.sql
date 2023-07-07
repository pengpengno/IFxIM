SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `user_nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐值',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `pwdhash` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '加盐hash',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `active` tinyint(4) NULL DEFAULT 1 COMMENT '删除标志',
  `version` int(11) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1630119513067073537 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账户表' ROW_FORMAT = Dynamic;

INSERT INTO `account` VALUES (1630119475372863488, 'wangpeng', NULL, NULL, 'wangpeng', 'kutpxdj1', NULL, 'dbce92729fef9f27bfcf4f0b425b39a40e17645172e36efedeae36b9e86bfa23', '2023-02-27 08:15:44', '2023-02-27 08:15:44', 1, 1);
INSERT INTO `account` VALUES (1630119513067073536, 'pengpeng', NULL, NULL, 'pengpeng', 'hh6jf58x', NULL, '5c189f2ebce7ceaa037a8b18844f2739fdeab1779b43c13260c77f0792fc86f1', '2023-02-27 08:15:53', '2023-02-27 08:15:53', 1, 1);


DROP TABLE IF EXISTS `account_relation`;
CREATE TABLE `account_relation`  (
  `id` bigint(20) NOT NULL,
  `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `account_relations` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '账号关系集合',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `active` tinyint(4) NULL DEFAULT 1 COMMENT '删除标志',
  `version` int(11) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账户关系表' ROW_FORMAT = Dynamic;




DROP TABLE IF EXISTS `chat_msg`;
CREATE TABLE `chat_msg`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `session_id` bigint(32) NULL DEFAULT NULL COMMENT '接受的会话Id',
  `from_account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送的账户',
  `content_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息类型',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  `create_user_id` bigint(32) NULL DEFAULT NULL COMMENT '创建用户id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `active` tinyint(4) NULL DEFAULT 1 COMMENT '删除标志',
  `version` int(5) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 178 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '信息表' ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `chat_msg_record`;
CREATE TABLE `chat_msg_record`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `session_id` bigint(32) NULL DEFAULT NULL COMMENT '会话Id',
  `msg_id` bigint(32) NULL DEFAULT NULL COMMENT '消息id',
  `to_user_id` bigint(32) NULL DEFAULT NULL COMMENT '接受用户id',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息状态',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建用户id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `active` tinyint(4) NULL DEFAULT 1 COMMENT '删除标志',
  `version` int(5) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息记录表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `session`;
CREATE TABLE `session`  (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `session_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会话名称（系统预定义）',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建账号',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `active` tinyint(4) NULL DEFAULT 1 COMMENT '删除标志',
  `version` int(11) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1644223948273614849 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会话表' ROW_FORMAT = Dynamic;


INSERT INTO `session` VALUES (1644223948273614848, '模板', 1630119475372863488, '2023-04-07 06:21:52', '2023-04-07 06:21:52', 1, 1);

DROP TABLE IF EXISTS `session_account`;
CREATE TABLE `session_account`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `session_id` bigint(20) NULL DEFAULT NULL COMMENT '会话',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户标识',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `active` tinyint(4) NULL DEFAULT 1 COMMENT '删除标志',
  `version` int(11) NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会话账户中间表' ROW_FORMAT = Dynamic;


INSERT INTO `session_account` VALUES (1, 1644223948273614848, 1630119475372863488, 1630119513067073536, '2023-04-07 06:26:51', '2023-04-07 06:26:51', 1, 1);
INSERT INTO `session_account` VALUES (2, 1644223948273614848, 1630119513067073536, 1630119513067073536, '2023-04-07 06:26:51', '2023-04-07 06:26:51', 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
