/*
 Navicat Premium Data Transfer

 Source Server         : dev
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : 127.0.0.1:3306
 Source Schema         : pf_blog

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 08/09/2021 09:39:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Records of auth_resource
-- ----------------------------
INSERT INTO `auth_resource` VALUES ('0377812f1d8eab8dcdc9d32519100df0', '字典管理', 'f965ec8884cabcdd42e4ad7e9846bbd3', '/system/set/dict', 'system:set:dict', 1, 'user', 0, '0', 0, '0', '1', '0', 'admin', 'admin', '2021-09-07 09:51:07', '2021-09-07 09:51:07');
INSERT INTO `auth_resource` VALUES ('56021e4e63f14ed63d7d21e91877d6d9', '资源管理', 'f93fe95a61e6aa255e0327a7801f35a9', '/system/auth/resource', 'system:auth:resource', 1, 'book', 0, '0', 0, '0_0', '1', '0', 'admin', 'admin', '2021-08-31 16:49:12', '2021-09-03 16:32:54');
INSERT INTO `auth_resource` VALUES ('81013741e72bdd14b93445f15685f4d0', '用户管理', 'f93fe95a61e6aa255e0327a7801f35a9', '/system/auth/user', 'system:auth:user', 1, 'user', 0, '0', 0, '0', '1', '0', 'admin', 'admin', '2021-08-31 16:43:05', '2021-09-03 16:22:34');
INSERT INTO `auth_resource` VALUES ('9061effe4b5acaa110e7309586e3b860', '角色管理', 'f93fe95a61e6aa255e0327a7801f35a9', '/system/auth/role', 'system:auth:resource', 1, 'user-edit', 0, '0', 0, '0', '1', '0', 'admin', 'admin', '2021-08-31 16:48:15', '2021-09-03 16:22:44');
INSERT INTO `auth_resource` VALUES ('e8dbf078a353fed4d3857141e81b7511', '系统', NULL, '', '', 0, 'microsoft', 0, '1', 0, '0', '0', '0', 'admin', 'admin', '2021-08-31 15:27:50', '2021-08-31 16:41:36');
INSERT INTO `auth_resource` VALUES ('f93fe95a61e6aa255e0327a7801f35a9', '授权', 'e8dbf078a353fed4d3857141e81b7511', '', '', 0, 'key', 0, '0', 0, '0', '0', '0', 'admin', 'admin', '2021-08-31 16:42:13', '2021-08-31 16:42:13');
INSERT INTO `auth_resource` VALUES ('f965ec8884cabcdd42e4ad7e9846bbd3', '设置', 'e8dbf078a353fed4d3857141e81b7511', '', '', 0, 'cog', 0, '0', 0, '0', '0', '0', 'admin', 'admin', '2021-09-07 09:50:10', '2021-09-07 09:50:10');

SET FOREIGN_KEY_CHECKS = 1;
