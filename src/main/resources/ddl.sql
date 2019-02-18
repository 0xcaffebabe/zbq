# 创建用户表
CREATE TABLE user(
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20) NOT NULL UNIQUE ,
  password VARCHAR(32) NOT NULL ,
  user_info INT NOT NULL ,
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

