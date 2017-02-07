-- 执行任务
DROP TABLE IF EXISTS `QUARTZ_JOBS_TASK`;
CREATE TABLE `QUARTZ_JOBS_TASK` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `configId` int(11) DEFAULT NULL COMMENT '任务配置id',
  `identifier` varchar(50) DEFAULT NULL COMMENT '任务唯一标识',
  `description` varchar(50) DEFAULT NULL COMMENT '任务描述',
  `params` varchar(1000) DEFAULT NULL COMMENT '任务参数',
  `type` enum('MONTH','DAY','ONCE','CRON') DEFAULT NULL COMMENT '执行周期类型',
  `expression` varchar(20) DEFAULT NULL COMMENT '执行表达式',
  `status` enum('EXPIRED','BLOCKED','ERROR','COMPLETE','PAUSED','NORMAL','NONE') DEFAULT NULL,
  `weight` int(10) DEFAULT '0' COMMENT '权重',
  `user` varchar(20) DEFAULT NULL COMMENT '任务维护人员',
  PRIMARY KEY (`id`),
  UNIQUE KEY `identifierIndex` (`identifier`),
  KEY `configIdIndex` (`configId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 
DEFAULT CHARSET=utf8;
