## 发表动态
```text
PUT /state
headers:{"Content-Type":"application/json"}
request:content
response:发表结果
```
```text
{
	"content":"哪位大佬教我转笔"
}
```
```text
{
    "success": true,
    "msg": "success",
    "data": "动态发表成功"
}
```
## 获取笔圈动态
```text
GET /state/self
参数:page length
response:动态列表
```
```text
{
    "success": true,
    "msg": "success",
    "data": [
        {
            "stateId": 3,
            "content": "我是小my",
            "userVO": {
                "userId": 2,
                "nickName": "my",
                "profile": "http://zbq88.oss-cn-hangzhou.aliyuncs.com/img/1551784391318-2059583269..jpg?x-oss-process=style/square"
            },
            "createTime": "2019-03-11T17:05:08"
        },
        {
            "stateId": 2,
            "content": "哪位大佬教我转笔",
            "userVO": {
                "userId": 1,
                "nickName": "root",
                "profile": "http://zbq88.oss-cn-hangzhou.aliyuncs.com/img/1551010048961-2059583269..jpg?x-oss-process=style/square"
            },
            "createTime": "2019-03-11T12:58:46"
        },
        {
            "stateId": 1,
            "content": "VGG便宜出了",
            "userVO": {
                "userId": 1,
                "nickName": "root",
                "profile": "http://zbq88.oss-cn-hangzhou.aliyuncs.com/img/1551010048961-2059583269..jpg?x-oss-process=style/square"
            },
            "createTime": "2019-03-10T20:23:42"
        }
    ]
}
```
## 发表评论
```text
PUT /state/comment
headers:{"Content-Type":"application/json"}
request:stateId toUser(非必选) content
response:发表结果
```
```text
{
	"stateId":10,
	"content":"又是测试评论哦哦哦"
}
```
```text
{
    "success": true,
    "msg": "success",
    "data": "评论成功"
}
```