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

INSERT INTO `dict_node` VALUES ('7a94e183bab529d9935576df547ae85e', NULL, '基本设置', 'basic', 1, '1', '', '0', 'admin', 'admin', '2021-09-24 09:59:28', '2021-09-24 09:59:28');
INSERT INTO `dict_node` VALUES ('da00539abc61fc1bc623b2e6c1efa7c6', NULL, '文章设置', 'article', 1, '1', '', '0', 'admin', 'admin', '2021-09-27 14:13:41', '2021-09-27 14:13:41');

INSERT INTO `dict_value` VALUES ('0dbcc899addcff43acfe371a101739a9', '7a94e183bab529d9935576df547ae85e', '允许上传的文件类型', 'file_type', 7, '0', '', 4, '图片文件 (gif jpg jpeg png tiff bmp),多媒体文件 (mp3 wmv wma rmvb rm avi flv),常用档案文件 (txt doc docx xls xlsx ppt pptx zip rar pdf)', '0', 'admin', 'admin', '2021-09-28 11:14:57', '2021-09-28 11:14:57');
INSERT INTO `dict_value` VALUES ('22dc0174ea079868b209e66452d560d9', 'da00539abc61fc1bc623b2e6c1efa7c6', '转载规则设置', 'reprint', 3, '0', '', 2, '禁止转载,规范转载,付费转载', '0', 'admin', 'admin', '2021-09-28 11:42:04', '2021-09-28 11:42:04');
INSERT INTO `dict_value` VALUES ('3151492a53a2d59bae9c31ea26cba2c6', '7a94e183bab529d9935576df547ae85e', '是否允许注册', 'register', 5, '0', '允许访问者注册到你的网站, 默认的注册用户不享有任何写入权限.', 3, '不允许,允许', '0', 'admin', 'admin', '2021-09-24 10:39:50', '2021-09-28 11:05:50');
INSERT INTO `dict_value` VALUES ('5374651416fb44e668833dab1d8a0a4c', '7a94e183bab529d9935576df547ae85e', '时区', 'time_zone', 6, '0', '', 2, '格林威治（子午线）标准时间 (GMT), 中欧标准时间 阿姆斯特丹、荷兰、法国 (GMT +1), 东欧标准时间 布加勒斯特、塞浦路斯、希腊 (GMT +2), 莫斯科时间 伊拉克、埃塞俄比亚、马达加斯加 (GMT +3), 第比利斯时间 阿曼、毛里塔尼亚、留尼汪岛 (GMT +4), 新德里时间 巴基斯坦、马尔代夫 (GMT +5), 科伦坡时间 孟加拉 (GMT +6), 曼谷雅加达 柬埔寨、苏门答腊、老挝 (GMT +7), 北京时间 香港、新加坡、越南 (GMT +8), 东京平壤时间 西伊里安、摩鹿加群岛 (GMT +9), 悉尼关岛时间 塔斯马尼亚岛、新几内亚 (GMT +10), 所罗门群岛 库页岛 (GMT +11), 惠灵顿时间 新西兰、斐济群岛 (GMT +12), 佛德尔群岛 亚速尔群岛、葡属几内亚 (GMT -1), 大西洋中部时间 格陵兰 (GMT -2), 布宜诺斯艾利斯 乌拉圭、法属圭亚那 (GMT -3), 智利巴西 委内瑞拉、玻利维亚 (GMT -4), 纽约渥太华 古巴、哥伦比亚、牙买加 (GMT -5), 墨西哥城时间 洪都拉斯、危地马拉、哥斯达黎加 (GMT -6), 美国丹佛时间 (GMT -7), 美国旧金山时间 (GMT -8), 阿拉斯加时间 (GMT -9), 夏威夷群岛 (GMT -10), 东萨摩亚群岛 (GMT -11), 艾尼威托克岛 (GMT -12)', '0', 'admin', 'admin', '2021-09-24 10:45:54', '2021-09-28 10:52:18');
INSERT INTO `dict_value` VALUES ('9207bd4d3b9eb6bf407eeadd38153340', '7a94e183bab529d9935576df547ae85e', '站点地址', 'url', 2, '0', '站点地址主要用于生成内容的永久链接.', 0, '', '0', 'admin', 'admin', '2021-09-24 10:01:57', '2021-09-24 10:01:57');
INSERT INTO `dict_value` VALUES ('aa935ebeb47e0e135cadb617da4b206b', 'da00539abc61fc1bc623b2e6c1efa7c6', '头图地址', 'picture', 1, '0', '输入图片URL，则优先使用该图片作为头图', 0, '', '0', 'admin', 'admin', '2021-09-27 14:13:41', '2021-09-27 14:14:11');
INSERT INTO `dict_value` VALUES ('aac32004c20befc6b78d0154d6d089b4', 'da00539abc61fc1bc623b2e6c1efa7c6', '指定摘要内容', 'abstract', 2, '0', '默认根据后台配置的摘要字数自动生成摘要\n你可以在这里手动指定摘要', 0, NULL, '0', 'admin', 'admin', '2021-09-28 11:35:10', '2021-09-28 11:35:10');
INSERT INTO `dict_value` VALUES ('d3ce07adbd62a3f90a4a6c23e93db1f2', '7a94e183bab529d9935576df547ae85e', '关键词', 'keywords', 4, '0', '请以半角逗号 \",\" 分割多个关键字.', 5, '', '0', 'admin', 'admin', '2021-09-24 10:38:52', '2021-09-24 15:41:02');
INSERT INTO `dict_value` VALUES ('d687822cac7806ef661d0a10203a2cd1', '7a94e183bab529d9935576df547ae85e', '站点名称', 'name', 1, '1', '站点的名称将显示在网页的标题处.', 0, '', '0', 'admin', 'admin', '2021-09-24 09:59:28', '2021-09-26 14:47:31');
INSERT INTO `dict_value` VALUES ('e98f1a2e967e675b0dee09fd301e0d86', '7a94e183bab529d9935576df547ae85e', '站点描述', 'description', 3, '0', '站点描述将显示在网页代码的头部.', 0, '', '0', 'admin', 'admin', '2021-09-24 10:02:45', '2021-09-24 10:02:45');

INSERT INTO `auth_resource` VALUES ('0377812f1d8eab8dcdc9d32519100df0', '字典管理', 'f965ec8884cabcdd42e4ad7e9846bbd3', '/admin/config/dict', 'system:set:dict', 1, 'user', 0, '0', 0, '0', '1', '0', 'admin', 'admin', '2021-09-07 09:51:07', '2021-09-07 09:51:07');
INSERT INTO `auth_resource` VALUES ('56021e4e63f14ed63d7d21e91877d6d9', '资源管理', 'f93fe95a61e6aa255e0327a7801f35a9', '/admin/auth/resource', 'system:auth:resource', 1, 'book', 0, '0', 0, '0_0', '1', '0', 'admin', 'admin', '2021-08-31 16:49:12', '2021-09-03 16:32:54');
INSERT INTO `auth_resource` VALUES ('81013741e72bdd14b93445f15685f4d0', '用户管理', 'f93fe95a61e6aa255e0327a7801f35a9', '/admin/auth/user', 'system:auth:user', 1, 'user', 0, '0', 0, '0', '1', '0', 'admin', 'admin', '2021-08-31 16:43:05', '2021-09-03 16:22:34');
INSERT INTO `auth_resource` VALUES ('9061effe4b5acaa110e7309586e3b860', '角色管理', 'f93fe95a61e6aa255e0327a7801f35a9', '/admin/auth/role', 'system:auth:resource', 1, 'user-edit', 0, '0', 0, '0', '1', '0', 'admin', 'admin', '2021-08-31 16:48:15', '2021-09-03 16:22:44');
INSERT INTO `auth_resource` VALUES ('e8dbf078a353fed4d3857141e81b7511', '系统', NULL, '', '', 0, 'microsoft', 0, '1', 0, '0', '0', '0', 'admin', 'admin', '2021-08-31 15:27:50', '2021-08-31 16:41:36');
INSERT INTO `auth_resource` VALUES ('f93fe95a61e6aa255e0327a7801f35a9', '授权', 'e8dbf078a353fed4d3857141e81b7511', '', '', 0, 'key', 0, '0', 0, '0', '0', '0', 'admin', 'admin', '2021-08-31 16:42:13', '2021-08-31 16:42:13');
INSERT INTO `auth_resource` VALUES ('f965ec8884cabcdd42e4ad7e9846bbd3', '设置', 'e8dbf078a353fed4d3857141e81b7511', '', '', 0, 'cog', 0, '0', 0, '0', '0', '0', 'admin', 'admin', '2021-09-07 09:50:10', '2021-09-07 09:50:10');

SET FOREIGN_KEY_CHECKS = 1;
