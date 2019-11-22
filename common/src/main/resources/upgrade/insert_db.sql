use happy_reading;

insert into sysconfig (id,version,releasedate) values(1,1.0,now());

insert into role(id,name,state,isadmin,authorities) values (1,'管理员',0,true,'WEBMENU;WEBACTION');
insert into role(id,name,state,isadmin,authorities) values (2,'用户',0,false,'WEBMENU:DEVICE:ADD,AUTHORITY;WEBMENU:METERS;WEBMENU:PROJECT:ADD;WEBMENU:USER');

insert into user(username,password,state,roleid) values("admin","admin123",0,1);

commit;