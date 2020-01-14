use happy_reading;

insert into sysconfig (id,version,releasedate) values(1,1.0,now());

insert into role(id,name,state,isadmin,authorities) values (1,'管理员',0,true,'WEBMENU;WEBACTION');
insert into role(id,name,state,isadmin,authorities) values (2,'用户',0,false,'WEBMENU:DEVICE:ADD,AUTHORITY;WEBMENU:METERS;WEBMENU:PROJECT:ADD;WEBMENU:USER');

insert into user(username,password,state,roleid) values("admin","admin123",0,1);

-- product
insert product values(1, "1元充值", 1, 1, 0, 1, "2020-01-14 14:18:00","2020-01-14 14:18:00");
insert product values(2, "3元充值", 1, 3, 0, 3, "2020-01-14 14:18:00","2020-01-14 14:18:00");
insert product values(3, "10元充值", 1, 10, 0, 10, "2020-01-14 14:18:00","2020-01-14 14:18:00");
insert product values(4, "30元充值", 1, 30, 0, 30, "2020-01-14 14:18:00","2020-01-14 14:18:00");
insert product values(5, "50元充值", 1, 50, 0, 50, "2020-01-14 14:18:00","2020-01-14 14:18:00");
insert product values(6, "100元充值", 1, 100, 0, 100, "2020-01-14 14:18:00","2020-01-14 14:18:00");

commit;