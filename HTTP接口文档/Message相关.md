## 发送消息
```text
POST /message
response:消息发送结果
headers:{"Content-Type":"application/json"}
参数：to,content
```
```text
{
    "success": true,
    "msg": "success",
    "data": "发送成功"
}
```
## 获取当前用户未读消息列表
```text
GET /message/unread
response:未读消息列表
```
```text
{
    "success": true,
    "msg": "success",
    "data": [
        {
            "fromUserInfo": {
                "userInfoId": null,
                "nickName": "佚名",
                "profile": "img/anonymous.jpg",
                "birthday": null,
                "penYear": null,
                "region": null,
                "gender": null,
                "description": null,
                "createTime": null,
                "updateTime": null
            },
            "msgCount": 1,
            "newestMsg": "test"
        },
        {
            "fromUserInfo": {
                "userInfoId": null,
                "nickName": "my",
                "profile": "http://zbq88.oss-cn-hangzhou.aliyuncs.com/img/1551784391318-2059583269..jpg?x-oss-process=style/square",
                "birthday": null,
                "penYear": null,
                "region": null,
                "gender": null,
                "description": null,
                "createTime": null,
                "updateTime": null
            },
            "msgCount": 10,
            "newestMsg": "ls"
        }
    ]
}
```