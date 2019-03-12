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

# 创建消息表
CREATE TABLE message(
  message_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  from_user INT NOT NULL ,
  to_user INT NOT NULL ,
  content TEXT NOT NULL ,
  has_read BOOLEAN DEFAULT FALSE,
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建动态表
CREATE TABLE state(
  state_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  content TEXT NOT NULL ,
  user INT NOT NULL ,
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建点赞表
CREATE TABLE tb_like(
  like_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  like_type TINYINT NOT NULL COMMENT '0代表动态点赞',
  content_id INT NOT NULL ,
  like_user INT NOT NULL ,
  create_time DATETIME NOT NULL,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;