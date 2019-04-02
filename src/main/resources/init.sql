
# 系统初始化
  # 创建小助手账号
  INSERT INTO
    user_info(nick_name, profile,
              birthday, pen_year, region, gender, description, create_time, update_time)
      VALUES ('转笔圈小助手','/img/anonymous.jpg',NOW(),99,'中国',1,'这是小助手',NOW(),NOW());
  INSERT INTO user(username, password, user_info, permission, create_time, update_time, last_login)
      VALUES ('10000',UPPER(MD5('Root@@715')),1,0,NOW(),NOW(),NOW());
  UPDATE user SET user_id = 0 WHERE username = '10000'