

##注册
```text
PUT /user/register
headers:{"Content-Type":"application/json"}
request:username password
response:注册结果（字符串）
```
-
```text
{
	"username":"5858",
	"password":"22222222222222222222222222222222"
}
```
```text
{
    "success": true,
    "msg": "success",
    "data": "注册成功"
}
```
##登录
```text
POST /user/login
headers:{"Content-Type":"application/json"}
request:username password
response:登录结果（字符串）
```
-
```text
{
	"username":"5858","password":"22222222222222222222222222222222"
}
```
```text
{
    "success": true,
    "msg": "success",
    "data": "登录成功"
}
```
##注销
```text
DELETE /user/logout
response:无
```
##获取当前登录用户信息
```text
GET /user/state
response:用户数据传输对象
```
```text
{
    "success": true,
    "msg": "success",
    "data": {
        "username": "5858",
        "nickName": "佚名",
        "profile": ""
    }
}
```




