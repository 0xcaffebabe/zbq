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
            "fromUser": 3,
            "msgCount": 1,
            "newestMsg": "test"
        },
        {
            "fromUser": 2,
            "msgCount": 10,
            "newestMsg": "ls"
        }
    ]
}
```