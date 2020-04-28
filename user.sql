SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '123', '321');
INSERT INTO `user` VALUES (2, '321', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES (3, '1234', 'caf1a3dfb505ffed0d024130f58c5cfa');
INSERT INTO `user` VALUES (4, '1', 'c4ca4238a0b923820dcc509a6f75849b');
INSERT INTO `user` VALUES (5, '1235', 'caf1a3dfb505ffed0d024130f58c5cfa');
INSERT INTO `user` VALUES (6, '11', 'b6d767d2f8ed5d21a44b0e5886680cb9');
INSERT INTO `user` VALUES (7, '12345', 'caf1a3dfb505ffed0d024130f58c5cfa');
INSERT INTO `user` VALUES (8, '54321', '01cfcd4f6b8770febfb40cb906715822');

SET FOREIGN_KEY_CHECKS = 1;
