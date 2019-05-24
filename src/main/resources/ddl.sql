# 创建用户表
CREATE TABLE user(
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20) NOT NULL UNIQUE ,
  password VARCHAR(32) NOT NULL ,
  user_info INT NOT NULL ,
  permission INT NOT NULL ,
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL,
  last_login DATETIME
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
CREATE TABLE tb_user_permission(
  user_permission_id INT PRIMARY KEY  AUTO_INCREMENT,
  login BOOLEAN DEFAULT TRUE comment '登录权限',
  content_publish BOOLEAN DEFAULT FALSE COMMENT '内容发布权限',
  course_publish BOOLEAN DEFAULT FALSE COMMENT '课程发布权限',
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
  visible INT DEFAULT TRUE,
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建点赞表
CREATE TABLE tb_like(
  like_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  like_type TINYINT NOT NULL COMMENT '0代表动态点赞,1代表内容点赞',
  content_id INT NOT NULL ,
  like_user INT NOT NULL ,
  create_time DATETIME NOT NULL,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建评论表
CREATE TABLE tb_comment(
  comment_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  comment_type TINYINT NOT NULL COMMENT '0代表动态评论,1代表内容评论,2代表章节',
  topic_id INT NOT NULL ,
  content TEXT NOT NULL ,
  from_user INT NOT NULL ,
  to_user INT,
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建位置表
CREATE TABLE location(
  location_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user INT NOT NULL ,
  longitude DECIMAL(10,6) NOT NULL ,
  latitude DECIMAL(10,6) NOT NULL ,
  address VARCHAR(255) ,
  anonymous BOOLEAN NOT NULL ,
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建内容表
CREATE TABLE content(
  content_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user INT NOT NULL ,
  title VARCHAR(255) NOT NULL ,
  content TEXT NOT NULL ,
  tags VARCHAR(255),
  visible BOOLEAN DEFAULT FALSE,
  create_time DATETIME NOT NULL ,
  update_time DATETIME
)ENGINE=InnoDB CHARSET=utf8;

# 创建课程表
CREATE TABLE course(
  course_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
  course_name VARCHAR(255) NOT NULL ,
  course_desc VARCHAR(255) NOT NULL ,
  course_img VARCHAR(2048) NOT NULL ,
  course_level VARCHAR(10) NOT NULL ,
  publisher INT NOT NULL ,
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建单课表
CREATE TABLE lesson(
  lesson_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  course_id INT NOT NULL ,
  lesson_name VARCHAR(255) NOT NULL ,
  lesson_content TEXT NOT NULL ,
  weight INT NOT NULL DEFAULT 0,
  create_time DATETIME NOT NULL ,
  update_time DATETIME
)ENGINE=InnoDB CHARSET=utf8;

# 创建学习记录表
CREATE TABLE learning(
  learning_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  course_id INT NOT NULL ,
  lesson_id INT NOT NULL ,
  user INT NOT NULL ,
  create_time DATETIME NOT NULL ,
  update_time DATETIME
)ENGINE=InnoDB CHARSET=utf8;

# 创建访问日志表
CREATE TABLE web_log(
  log_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  ip VARCHAR(255) NOT NULL ,
  url VARCHAR(2048) NOT NULL ,
  ua VARCHAR(1024) ,
  user INT,
  time_consuming INT NOT NULL ,
  create_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建用户账户绑定表
CREATE TABLE user_account(
  user_account_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user INT NOT NULL ,
  account_type TINYINT(2) NOT NULL COMMENT '账户绑定类型，0为邮箱',
  account_name VARCHAR(255) NOT NULL ,
  valid BOOLEAN DEFAULT FALSE,
  create_time DATETIME NOT NULL ,
  update_time DATETIME
)ENGINE=InnoDB CHARSET=utf8;

# 创建用户登录日志表
CREATE TABLE user_login_log(
  login_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  login_user INT NOT NULL ,
  login_ip VARCHAR(255) NOT NULL,
  login_type INT NOT NULL ,
  create_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8;

# 创建广播聊天表
CREATE TABLE broadcast(
  broadca_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user INT NOT NULL ,
  content TEXT NOT NULL ,
  anonymous BOOLEAN NOT NULL ,
  create_time DATETIME NOT NULL ,
  update_time DATETIME
)ENGINE=InnoDB CHARSET=utf8mb4;

# 创建收藏联系表
CREATE TABLE collection(
  collection_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  collection_type TINYINT NOT NULL COMMENT '收藏类型：0代表视频，1代表内容，2代表课程',
  content_id INT NOT NULL ,
  user INT NOT NULL ,
  create_time DATETIME NOT NULL ,
  update_time DATETIME
)ENGINE=InnoDB CHARSET=utf8mb4;

# 创建视频搜索日志表
CREATE TABLE video_search_log(
  log_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user INT NOT NULL ,
  kw VARCHAR(255) NOT NULL ,
  create_time DATETIME NOT NULL ,
  update_time DATETIME NOT NULL
)ENGINE=InnoDB CHARSET=utf8mb4;

# 创建课程评分表
CREATE TABLE course_rating(
  rating_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  course INT NOT NULL ,
  user INT NOT NULL ,
  rating DECIMAL(4,1) NOT NULL,
  content VARCHAR(255) NOT NULL ,
  create_time DATETIME NOT NULL ,
  update_time DATETIME
)ENGINE=InnoDB CHARSET=utf8mb4;

# 创建订阅关注表
