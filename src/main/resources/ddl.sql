# 创建用户表
CREATE TABLE user(
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20) NOT NULL UNIQUE ,
  password VARCHAR(32) NOT NULL ,
  user_info INT NOT NULL ,
  permission INT NOT NULL ,
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
) ENGINE=InnoDB CHARSET=utf8;

# 创建用户信息表
CREATE TABLE user_info(
  user_info_id INT PRIMARY KEY AUTO_INCREMENT,
  nick_name VARCHAR(32) DEFAULT '佚名',
  profile VARCHAR(2048) DEFAULT '',
  birthday DATE  DEFAULT '1000-01-01',
  pen_year INT DEFAULT 0,
  region VARCHAR(10) DEFAULT '',
  gender TINYINT DEFAULT 0,
  description VARCHAR(255) DEFAULT '',
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建权限表
CREATE TABLE permission(
  permission_id INT PRIMARY KEY  AUTO_INCREMENT,
  content_publish VARCHAR(2) DEFAULT 'N' COMMENT '内容发布权限',
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建好友关系表
CREATE TABLE friend(
  user_id INT NOT NULL ,
  friend_id INT NOT NULL ,
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建登录权限表
CREATE TABLE login_acl(
  login_acl_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL ,
  login_state BOOLEAN DEFAULT TRUE,
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建好友添加消息表
CREATE TABLE friend_add(
  friend_add_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  from_user INT NOT NULL ,
  to_user INT NOT NULL ,
  msg varchar(256),
  visible BOOLEAN DEFAULT TRUE,
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

