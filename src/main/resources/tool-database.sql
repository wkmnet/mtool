--选择数据库
use oracle;

--创建数据库表格
drop table if exists toolMenu;
create table if not exists toolMenu(
    id varchar(50) primary key comment '主健',
    parentId varchar(50) not null comment '父菜单主健',
    menuName varchar(60) comment '菜单名称',
    menuLink varchar(200) comment '菜单链接'
)ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;