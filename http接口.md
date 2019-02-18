
#HTTP接口文档

##User相关
####注册
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



