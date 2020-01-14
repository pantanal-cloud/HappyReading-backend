use happy_reading;

/*2020-01-09*/
alter table book drop column  free_chapter_index;
alter table user add column balance INT(11) default '0' COMMENT '余额';
update sysconfig set version=1.1,releasedate=NOW();

/*order*/
/*==============================================================*/
/* Table: `product` */
/*==============================================================*/
CREATE TABLE `product`
(
   id BIGINT(10) NOT NULL AUTO_INCREMENT,
   name VARCHAR(20) NOT NULL COMMENT '名称',
   type INT(2) NOT NULL DEFAULT '1' COMMENT '1:充值;2:会员',
   price INT NOT NULL DEFAULT '0' COMMENT '总价，单位：分',
   discount INT NOT NULL DEFAULT '0' COMMENT '折扣，单位：分',
   real_price INT NOT NULL DEFAULT '0' COMMENT '最终价，单位：分',
   create_at datetime NOT NULL COMMENT '创建日期',
   update_at datetime DEFAULT NULL COMMENT '更新日期',
   PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 ROW_FORMAT=COMPRESSED DEFAULT CHARSET=utf8 COMMENT = '商品表';

/*==============================================================*/
/* Table: `order` */
/*==============================================================*/
CREATE TABLE `order`
(
   id BIGINT(10) NOT NULL AUTO_INCREMENT,
   user_id BIGINT(10) NOT NULL COMMENT '用户ID',
   order_no VARCHAR(20) NOT NULL COMMENT '订单号',
   type INT(2) NOT NULL DEFAULT '1' COMMENT '1:充值;2:会员',
   channel INT(2) NOT NULL DEFAULT '1' COMMENT '1:微信;2:支付宝',
   price INT NOT NULL DEFAULT '0' COMMENT '总价',
   discount INT NOT NULL DEFAULT '0' COMMENT '折扣',
   real_price INT NOT NULL DEFAULT '0' COMMENT '最终价',
   status INT(2) NOT NULL DEFAULT '1' COMMENT '1: 未支付, 2: 已支付, 3: 已取消, 4: 已失效',
   create_at datetime  NULL COMMENT '创建日期',
   pay_at datetime  NULL COMMENT '支付日期',
   UNIQUE uk_order_order_no (order_no),
   PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 ROW_FORMAT=COMPRESSED DEFAULT CHARSET=utf8 COMMENT = '订单表';
commit;