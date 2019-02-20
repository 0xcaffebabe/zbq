##更新
```text
POST /userInfo
headers:{"Content-Type":"application/json"}
request:nickName profile birthday penYear region gender description
response:更新结果
```
```text
{
	"nickName":"测试账号5","birthday":"2019-02-18"
}
```
```text
{
    "success": false,
    "msg": "权限错误",
    "data": null
}
```
##获取当前登录用户信息
```text
GET /userInfo/self
response:UserInfoDTO
```
```text
{
    "success": true,
    "msg": "success",
    "data": {
        "nickName": "测试账号5",
        "profile": null,
        "birthday": "2019-02-18",
        "penYear": null,
        "region": null,
        "gender": null,
        "description": null
    }
}
```
